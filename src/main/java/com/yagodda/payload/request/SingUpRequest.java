package com.yagodda.payload.request;

import lombok.Data;

@Data
public class SingUpRequest {
    private String phoneNumber;
    private String email;
    private String fullName;
    private String username;
    private String password;
    private String confirmPassword;
}
