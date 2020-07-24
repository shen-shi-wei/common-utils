package com.ssw.constant;

public enum TokenEnum {
    SUCCESS("100000", "success"),
    TOKEN_PARAM_ABSENCE("100001", "token param absence"),
    TOKEN_PARAM_ERROR("100002", "token param error"),
    GENER_TOKEN_ERROR("100003", "generate token error"),
    AUTHENTICATION_NOT_EXIST("100004", "token is not exist"),
    CHECK_TOKEN_ERROR("100005", "token check error"),
    TOKEN_TIME_EXPIRED("100006", "token is expired");

    public String code;
    public String data;

    TokenEnum(String code, String data) {
        this.code = code;
        this.data = data;
    }
}
