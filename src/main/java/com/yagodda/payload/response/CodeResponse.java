package com.yagodda.payload.response;

import lombok.Data;

@Data
public class CodeResponse {
    private String code;
    private Boolean isSend;

    public CodeResponse(String code, Boolean isSend) {
        this.code = code;
        this.isSend = isSend;
    }
}
