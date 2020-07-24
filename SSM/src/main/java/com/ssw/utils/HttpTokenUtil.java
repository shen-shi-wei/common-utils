package com.ssw.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/16/10:11
 * @Description:
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class HttpTokenUtil {
    private static String serviceUrl;

    private HttpTokenUtil() {
    }

    private static final String httpClientPost(String url, ArrayList<NameValuePair> list) {
        String result = "";
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        try {
            NameValuePair[] params = new NameValuePair[list.size()];

            for (int i = 0; i < list.size(); ++i) {
                params[i] = (NameValuePair) list.get(i);
            }

            postMethod.addParameters(params);
            client.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (Exception var10) {
            System.out.println(var10);
        } finally {
            postMethod.releaseConnection();
        }

        return result;
    }

    public static String getToken() {
        ArrayList<NameValuePair> list = new ArrayList();
        list.add(new NameValuePair("appKey", "21996072"));
        list.add(new NameValuePair("appSecret", "SDxjzllwijf0nz5zxczx"));
        String result = null;

        try {
            String resultJson = httpClientPost(serviceUrl, list);
            JSONObject json = JSONObject.parseObject(resultJson);
            if (json.containsKey("code") && json.containsKey("data") && "100000".equals(json.get("code"))) {
                result = json.get("data").toString();
            }
        } catch (Exception var4) {
            result = "{\"code\":\"100005\",\"data\":\"token check error\"}";
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(getToken());
    }

    static {
        Properties prop = new Properties();

        try {
            InputStream in = HttpTokenUtil.class.getClassLoader().getResourceAsStream("common.properties");
            prop.load(in);
            serviceUrl = "http://".concat(prop.getProperty("4d_host").concat(":").concat(prop.getProperty("4d_port")).concat("/4DAnalog/token/getAccessToken.action"));
            in.close();
        } catch (Exception var2) {
            System.out.println("error:");
        }

    }
}

