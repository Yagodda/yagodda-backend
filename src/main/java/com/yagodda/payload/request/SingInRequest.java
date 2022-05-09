package com.yagodda.payload.request;

import lombok.Data;

@Data
public class SingInRequest {
    private String phoneNumber;
    private String password;
}
