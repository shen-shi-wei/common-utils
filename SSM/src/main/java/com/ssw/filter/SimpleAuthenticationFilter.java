package com.ssw.filter;


import com.alibaba.fastjson.JSONObject;
import com.ssw.constant.ApiHelper;
import com.ssw.constant.ApiResultEntity;
import com.ssw.constant.AuthResult;
import com.ssw.service.ITokenAuthenticationService;
import com.ssw.service.impl.TokenAuthenticationServiceImpl;
import com.ssw.utils.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleAuthenticationFilter implements Filter {
    private ITokenAuthenticationService tokenAuthenticationService;

    private final static String[] whiteApi = {"/token/*","/user/toLogin","/user/doLogin"
            ,"/tim/timer","/tim/startCamera","/tim/cancelCamera","sce/schedule","/sce/startCamera","/sce/cancelCamera"};

    /**
     * 简单的拦截用户请求
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "text/html;charset=UTF-8");

        if (ApiHelper.getSecurityEnable()) {
            chain.doFilter(request, response);
            return;
        }

        // 获取请求的url
        String url = req.getRequestURI();
        //区分http与https
        if ("https".equals(req.getHeader("X-Forwarded-Schema"))){
            url = req.getHeader("X-Forwarded-Request-Url");
        }

        String docPath = req.getSession().getServletContext().getRealPath("/");
        List<String> list = getFiles(docPath);
        for (int i = 0; i < list.size(); i++) {
            if (url.indexOf(list.get(i)) > 0) {
                chain.doFilter(request, response);
                return;
            }
        }

        for (int i = 0; i < whiteApi.length; i++) {
            if (url.indexOf(whiteApi[i]) > 0) {
                chain.doFilter(request, response);
                return;
            }
        }

        String token = req.getHeader("authentication");

        if (StringUtil.isNullOrEmpty(token)) {
            resp.getWriter().write(JSONObject.toJSONString(new ApiResultEntity("100001","token param absence",null))+url);
            return;
        }

        AuthResult result = tokenAuthenticationService.checkAccessToken(token);

        if ("100000".equals(result.getCode())) {
            chain.doFilter(request, response);
            return;
        } else {
            resp.getWriter().write(JSONObject.toJSONString(result)+url);
            return;
        }
    }


    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(filterConfig.getServletContext());
        tokenAuthenticationService = context.getBean(TokenAuthenticationServiceImpl.class);
    }

    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * 递归获取某路径下的所有文件，文件夹，并输出
     */
    public static List<String> getFiles(String path) {
        File file = new File(path);
        List<String> list = new ArrayList<>();
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    list.add("/"+files[i].getName()+"/");
                } else {
                    list.add("/"+files[i].getName());
                }

            }
        } else {
            list.add("/"+file.getName());
        }
        return list;
    }
}
