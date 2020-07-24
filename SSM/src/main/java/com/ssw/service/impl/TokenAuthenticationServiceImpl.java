package com.ssw.service.impl;

import com.ssw.constant.AuthResult;
import com.ssw.constant.TokenEnum;
import com.ssw.entity.AuthenticationInfo;
import com.ssw.mapper.TokenAuthenticationMapper;
import com.ssw.service.ITokenAuthenticationService;
import com.ssw.utils.AccessTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/16/10:26
 * @Description:  token
 */
@Service("tokenAuthenticationService")
public class TokenAuthenticationServiceImpl implements ITokenAuthenticationService {

    @Autowired
    private TokenAuthenticationMapper tokenAuthenticationMapper;

    @Override
    public AuthResult getAccessToken(String appKey, String appSecret) {
        AuthenticationInfo info = tokenAuthenticationMapper.match(appKey, appSecret);
        String token = null;
        if (info == null) {
            return new AuthResult(TokenEnum.TOKEN_PARAM_ERROR.code, TokenEnum.TOKEN_PARAM_ERROR.data);
        }

        try {
            token = AccessTokenUtil.generatorCrypt(appKey, appSecret);
        } catch (Exception e) {
            return new AuthResult(TokenEnum.GENER_TOKEN_ERROR.code, TokenEnum.GENER_TOKEN_ERROR.data);
        }
        if (token == null) {
            return new AuthResult(TokenEnum.GENER_TOKEN_ERROR.code, TokenEnum.GENER_TOKEN_ERROR.data);
        }

        return new AuthResult(TokenEnum.SUCCESS.code, token);
    }

    @Override
    public AuthResult checkAccessToken(String authentication) {
        String[] result = null;
        try {
            result = AccessTokenUtil.departToken(authentication);
            AuthenticationInfo info = tokenAuthenticationMapper.match(result[1], result[2]);
            if (info == null) {
                return new AuthResult(TokenEnum.CHECK_TOKEN_ERROR.code, TokenEnum.CHECK_TOKEN_ERROR.data);
            } else if (Long.valueOf(result[3])+AccessTokenUtil.EXPIRE_TIME_MS<System.currentTimeMillis()) {
                return new AuthResult(TokenEnum.TOKEN_TIME_EXPIRED.code, TokenEnum.TOKEN_TIME_EXPIRED.data);
            } else {
                return new AuthResult(TokenEnum.SUCCESS.code, TokenEnum.SUCCESS.data);
            }
        } catch (Exception e) {
            return new AuthResult(TokenEnum.CHECK_TOKEN_ERROR.code, e.getMessage());
        }
    }
}
