package com.yagodda.services.impl;

import com.yagodda.models.User;
import com.yagodda.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userService.getByPhoneNumber(phoneNumber).orElse(null);
    }

    public User loadUserById(Long userId) {
        return userService.getById(userId).orElse(null);
    }
}
