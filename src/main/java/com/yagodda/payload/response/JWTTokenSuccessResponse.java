package com.yagodda.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JWTTokenSuccessResponse {
    private String token;
    private boolean success;
}
