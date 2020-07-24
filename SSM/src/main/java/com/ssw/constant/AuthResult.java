package com.ssw.constant;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/16/10:25
 * @Description:
 */
public class AuthResult {
    private String code;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public AuthResult(){

    }

    public AuthResult(String code,String data){
        this.code = code;
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuthResult{" +
                "code='" + code + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

