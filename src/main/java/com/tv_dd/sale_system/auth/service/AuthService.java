package com.tv_dd.sale_system.auth.service;

import java.util.List;
import com.tv_dd.sale_system.auth.dto.AuthenticateResponse;
import com.tv_dd.sale_system.auth.dto.LoginRequest;
import com.tv_dd.sale_system.auth.dto.MeResponse;
import com.tv_dd.sale_system.branch.model.Branch;
import com.tv_dd.sale_system.clientapplication.model.ClientApplication;
import com.tv_dd.sale_system.clientapplication.repository.ClientApplicationRepository;
import com.tv_dd.sale_system.organization.model.Organization;
import com.tv_dd.sale_system.role.model.Role;
import com.tv_dd.sale_system.user.model.User;
import com.tv_dd.sale_system.user.model.UserRole;
import com.tv_dd.sale_system.user.repository.UserRepository;
import com.tv_dd.sale_system.user.repository.UserRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final ClientApplicationRepository clientApplicationRepository;
        private final UserRoleRepository userRoleRepository;

        @Transactional
        public User authenticate(LoginRequest request) {
                String clientAppName = request.clientAppName().trim();
                ClientApplication clientApplication = clientApplicationRepository.findByName(clientAppName)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Client application not found"));

                if (!Boolean.TRUE.equals(clientApplication.getEnable())) {
                        throw new DisabledException("Client application is disabled");
                }

                String username = request.username().trim().toLowerCase();
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

                if (!Boolean.TRUE.equals(user.getEnable())) {
                        throw new DisabledException("User is disabled");
                }

                if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                        throw new BadCredentialsException("Invalid username or password");
                }

                boolean hasAccess = userRoleRepository.findByUserId(user.getId()).stream()
                                .map(UserRole::getRole)
                                .anyMatch(role -> Boolean.TRUE.equals(role.getEnable())
                                                && role.getClientApplication() != null
                                                && role.getClientApplication().getId()
                                                                .equals(clientApplication.getId()));

                if (!hasAccess) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                                        "User does not have access to this application");
                }

                log.info("Authentication successful username={} clientApp={}", user.getUsername(),
                                clientApplication.getName());

                return user;
        }

        /**
         * Enabled role names the user holds for the given client application,
         * used to populate JWT authorities at token-issuance time only.
         */
        @Transactional
        public List<String> resolveEnabledRoleNames(User user, String clientAppName) {
                ClientApplication clientApplication = clientApplicationRepository.findByName(clientAppName)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Client application not found"));

                return userRoleRepository.findByUserId(user.getId()).stream()
                                .map(UserRole::getRole)
                                .filter(role -> Boolean.TRUE.equals(role.getEnable())
                                                && role.getClientApplication() != null
                                                && role.getClientApplication().getId()
                                                                .equals(clientApplication.getId()))
                                .map(Role::getName)
                                .toList();
        }

        @Transactional
        public AuthenticateResponse buildAuthenticateResponse(User user, String accessToken) {
                Branch branch = user.getBranch();
                Organization organization = branch.getOrganization();

                List<AuthenticateResponse.RoleSummary> roles = userRoleRepository.findByUserId(user.getId()).stream()
                                .map(userRole -> AuthenticateResponse.RoleSummary.from(userRole.getRole()))
                                .toList();

                return new AuthenticateResponse(
                                accessToken,
                                user.getId(),
                                branch.getId(),
                                organization.getId(),
                                organization.getName(),
                                roles,
                                user.getFullName());
        }

        @Transactional
        public MeResponse buildMeResponse(Integer userId) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

                Branch branch = user.getBranch();
                Organization organization = branch.getOrganization();

                List<AuthenticateResponse.RoleSummary> roles = userRoleRepository.findByUserId(user.getId()).stream()
                                .map(userRole -> AuthenticateResponse.RoleSummary.from(userRole.getRole()))
                                .toList();

                return new MeResponse(
                                user.getId(),
                                user.getUsername(),
                                branch.getId(),
                                organization.getId(),
                                organization.getName(),
                                roles,
                                user.getFullName());
        }
}
