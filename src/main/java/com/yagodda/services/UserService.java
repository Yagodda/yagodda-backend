package com.yagodda.services;


import com.yagodda.models.User;
import com.yagodda.payload.request.SingUpRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> getByUsername(String username);

    Optional<User> getByPhoneNumber(String username);

    Optional<User> getById(Long userId);

    void createUser(SingUpRequest singUpRequest);
}
