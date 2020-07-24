package com.ssw.controller.token;

import com.alibaba.fastjson.JSON;
import com.ssw.constant.ApiResultEntity;
import com.ssw.constant.AuthResult;
import com.ssw.constant.TokenEnum;
import com.ssw.controller.BaseAction;
import com.ssw.service.ITokenAuthenticationService;
import com.ssw.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/16/10:15
 * @Description:
 */

@Controller
@RequestMapping("/token")
public class AuthenticationController  extends BaseAction {

    @Autowired
    private ITokenAuthenticationService tokenAuthenticationService;

    @RequestMapping("/getAccessToken")
    public void getAccessToken(HttpServletResponse response, HttpServletRequest request) {
        String appKey = request.getParameter("appKey");
        String appSecret = request.getParameter("appSecret");
        ApiResultEntity entity = new ApiResultEntity();
        if (StringUtil.isNullOrEmpty(appKey)||StringUtil.isNullOrEmpty(appSecret)){
            sendBackData(response,request, entity.setSuccessCodeAndData(JSON.toJSONString(new AuthResult(TokenEnum.TOKEN_PARAM_ABSENCE.code,TokenEnum.TOKEN_PARAM_ABSENCE.data))));
            return;
        }
        AuthResult authResult = tokenAuthenticationService.getAccessToken(appKey,appSecret);
        sendBackData(response,request,entity.setSuccessCodeAndData(JSON.toJSONString(authResult)));
    }

    @RequestMapping("/checkAccessToken")
    public void checkAccessToken(HttpServletResponse response, HttpServletRequest request) {
        String authentication = request.getParameter("authentication");
        ApiResultEntity entity = new ApiResultEntity();
        if (authentication == null) {
            sendBackData(response, request, entity.setSuccessCodeAndData(JSON.toJSONString(new AuthResult(TokenEnum.AUTHENTICATION_NOT_EXIST.code,TokenEnum.AUTHENTICATION_NOT_EXIST.data))));
            return;
        }
        sendBackData(response,request,entity.setSuccessCodeAndData(JSON.toJSONString(tokenAuthenticationService.checkAccessToken(authentication))));
    }




}
