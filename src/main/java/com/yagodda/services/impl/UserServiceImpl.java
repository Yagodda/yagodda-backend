package com.yagodda.services.impl;

import com.yagodda.models.User;
import com.yagodda.models.enums.Role;
import com.yagodda.payload.request.SingUpRequest;
import com.yagodda.repositories.UserRepository;
import com.yagodda.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    @Value("${sms.user}")
    private String smsUser;

    @Value("${sms.password}")
    private String smsPassword;

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
        Optional<User> optionalUserFromDb = userRepository.findByPhoneNumber(singUpRequest.getPhoneNumber());
        if (optionalUserFromDb.isPresent()) {
            User userFromDb = optionalUserFromDb.get();
            userFromDb.setFullName(singUpRequest.getFullName());
            userFromDb.setUsername(singUpRequest.getUsername());
            userFromDb.setEmail(singUpRequest.getEmail());
            userFromDb.setPhoneNumber(singUpRequest.getPhoneNumber());
            userFromDb.setPassword(passwordEncoder.encode(singUpRequest.getPassword()));
            userFromDb.setActive(Boolean.TRUE);
            userFromDb.getRoles().add(Role.ROLE_USER);
            log.info("Saving new User with phone number: {}", singUpRequest.getPhoneNumber());
            userRepository.save(userFromDb);
        } else log.warn("Not found User with phone number: {}", singUpRequest.getPhoneNumber());
    }

    @Override
    public String sendSmsCode(String phoneNumber) {
        String code = String.valueOf(1000 + new Random().nextInt(9999 - 1000));
        sendSms(phoneNumber, code);
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
        return code;
    }

    private void sendSms(String phoneNumber, String code) {
        String requestUrl = String.format("https://smsc.ru/sys/send.php?login=%s&psw=%s&phones=%s&mes=%s",
                smsUser, smsPassword, phoneNumber, code);
        System.out.println(requestUrl);
        restTemplate.getForEntity(requestUrl, null);
        log.info("Send message with code: {} to {}", code, phoneNumber);
    }
}
