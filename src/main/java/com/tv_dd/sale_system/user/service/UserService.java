package com.tv_dd.sale_system.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.tv_dd.sale_system.branch.model.Branch;
import com.tv_dd.sale_system.branch.repository.BranchRepository;
import com.tv_dd.sale_system.user.dto.UserRequest;
import com.tv_dd.sale_system.user.dto.UserResponse;
import com.tv_dd.sale_system.user.model.User;
import com.tv_dd.sale_system.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {
        String username = request.username().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        Branch branch = branchRepository.findById(request.branchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());
        user.setEnable(true);
        user.setBranch(branch);

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }
}
