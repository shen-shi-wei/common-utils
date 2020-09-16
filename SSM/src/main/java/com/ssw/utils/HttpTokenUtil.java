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
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
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

//    public static void main(String[] args) {
//        System.out.println(getToken());
//    }

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

    public static void main(String[] args) {
//        String url = "https://sso.pinming.cn/sso/login/jsonp.do?userName=782351&password=e10adc3949ba59abbe56e057f20f883e&appKey=2c948cc973c802cb0173e205961b423e&validExpire=1";
        String s = HttpPing("sso.pinming.cn",
                "/sso/login/jsonp.do?userName=782351&password=e10adc3949ba59abbe56e057f20f883e&appKey=2c948cc973c802cb0173e205961b423e&validExpire=1",
                "10.203.86.156",
                "https");
        System.out.println("111111:" + s);
    }

    public static String HttpPing(String dns, String uri, String qlb, String scheme) {
        String url = scheme + "://" + dns + uri;
        CloseableHttpClient httpclient = null;
        if (scheme.equals("http")) {
            httpclient = getHttpClient(qlb);
        } else if (scheme.equals("https")) {
            httpclient = getHttpsClient(qlb);
        } else {
            return null;
        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResp = null;
        try {
            httpResp = httpclient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == org.apache.http.HttpStatus.SC_OK) {
                return EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static CloseableHttpClient getHttpClient(String qlb) {
        HttpHost proxy = new HttpHost(qlb, 80, "http");
        //把代理设置到请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        return httpclient;
    }

    private static CloseableHttpClient getHttpsClient(String qlb) {//这里设置客户端不检测服务器ssl证书
        try {
            X509TrustManager x509mgr = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[]{x509mgr}, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            HttpHost proxy = new HttpHost(qlb, 443, "https");
//            RequestConfig defaultRequestConfig = RequestConfig.custom()
//                    .setProxy(proxy)
//                    .build();
//            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(defaultRequestConfig).build();
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

