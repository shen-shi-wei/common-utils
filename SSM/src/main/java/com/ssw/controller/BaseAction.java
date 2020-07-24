package com.ssw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.ssw.constant.ApiResultEntity;
import com.ssw.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseAction {

    public void sendBackData(HttpServletResponse response, HttpServletRequest request, ApiResultEntity entity) {
        String data = JSON.toJSONString(entity, filter);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String wapperJsonpcallback1 = StringUtil.wapperJsonpcallback(request, data);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(wapperJsonpcallback1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    private ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if(v==null)
                return "";
            return v;
        }
    };
}
