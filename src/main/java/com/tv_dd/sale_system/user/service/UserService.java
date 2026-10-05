package com.tv_dd.sale_system.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.tv_dd.sale_system.user.dto.UserRequest;
import com.tv_dd.sale_system.user.dto.UserResponse;
import com.tv_dd.sale_system.user.model.User;
import com.tv_dd.sale_system.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {
        String username = request.username().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }
}
