package com.ssw.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static com.ssw.utils.AES.parseByte2HexStr;
import static com.ssw.utils.AES.parseHexStr2Byte;
import static java.lang.System.currentTimeMillis;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/16/9:56
 * @Description:  简单的token，生成、验证（解密）
 */
public class AccessTokenUtil {
    private final static String AES_KEY = "7.9poikm50pg8";

    private static SecretKeySpec key = null;

    public final static boolean TIME_STAMP_LOCK = true;

    public final static Long EXPIRE_TIME_MS = 86400000L;


    static {
        // 创建AES的秘钥生成器
        KeyGenerator kgen = null;
        try {
            String charset = "utf-8";
            kgen = KeyGenerator.getInstance("AES");
            // 利用AES_KEY的随机数，初始化刚刚创建的秘钥生成器
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(AES_KEY.getBytes());
            kgen.init(128, secureRandom);
            // 生成一个秘钥
            SecretKey secretKey = kgen.generateKey();
            // 获取二进制的秘钥
            byte[] enCodeFormat = secretKey.getEncoded();
            // 根据二进制秘钥，获取AES专用秘钥
            key = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //生成密钥
    public static String generatorCrypt(String appKey,String appSecret) throws Exception {
        return encrypt(generatorToken(appKey,appSecret));
    }

    //分解密钥
    public static String[] departToken(String content) throws Exception {
        String[] result = decrypt(content).split("&");
        return result;
    }

    private static String decrypt(String encryptContent) throws Exception {
        // 将十六进制的加密文本转换为byte数组
        byte[] content = parseHexStr2Byte(encryptContent);
        // 创建AES密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化AES密码器为解密器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        // 进行AES解密
        byte[] result = cipher.doFinal(content);
        // 将二进制转为字符串
        return new String(result);
    }

    private static String encrypt(String content) throws Exception{
        // 创建AES密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 将文本进行utf-8的编码
        byte[] byteContent = content.getBytes("utf-8");
        // 初始化AES密码器为加密器
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 进行AES加密
        byte[] result = cipher.doFinal(byteContent);
        // 将byte数组转为十六进制的字符串
        // 因为result存储的是字节，直接new string(result)会按照ASCII表输出，这会导致乱码，在传给解码器解码的时候，重新转为字节的时候就会对不上了
        // 因为我要进行前后端的传输，所以这样传给前端，前端不能识别，于是对byte进行转码成16进制的字符串
        return parseByte2HexStr(result);
    }

    private static synchronized String generatorToken(String appKey,String appSecret) {
        StringBuilder token = new StringBuilder(String.format("%.12f", Math.random()).substring(2,12));
        if (TIME_STAMP_LOCK == false) {
            return token.append("&").append(appKey).append("&").append(appSecret).toString();
        } else {
            return token.append("&").append(appKey).append("&").append(appSecret).append("&").append(String.valueOf(currentTimeMillis())).toString();
        }
    }

    public static void main(String[] args) {
        try {
//            System.out.println(generatorToken("dsds", "sdz"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
