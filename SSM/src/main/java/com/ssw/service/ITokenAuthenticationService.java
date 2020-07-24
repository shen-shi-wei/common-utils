package com.ssw.service;

import com.ssw.constant.AuthResult;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/16/10:17
 * @Description:
 */
public interface ITokenAuthenticationService {

    AuthResult getAccessToken(String appKey, String appSecret);
    AuthResult checkAccessToken(String authentication);


}
