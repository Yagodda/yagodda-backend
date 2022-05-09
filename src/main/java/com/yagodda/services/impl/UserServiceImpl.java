package com.yagodda.services.impl;

import com.yagodda.models.User;
import com.yagodda.models.enums.Role;
import com.yagodda.payload.request.SingUpRequest;
import com.yagodda.repositories.UserRepository;
import com.yagodda.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<User> getById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void createUser(SingUpRequest singUpRequest) {
        User user = new User();
        user.setFullName(singUpRequest.getFullName());
        user.setUsername(singUpRequest.getUsername());
        user.setEmail(singUpRequest.getEmail());
        user.setPhoneNumber(singUpRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(singUpRequest.getPassword()));
        user.setActive(Boolean.TRUE);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        log.info("Saving new User with phone number: {}", singUpRequest.getPhoneNumber());
        userRepository.save(user);
    }

}
