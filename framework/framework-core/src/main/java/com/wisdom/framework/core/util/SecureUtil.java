package com.wisdom.framework.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author hyberbin on 2016/10/26.
 */
public class SecureUtil {

    public static byte[] digestMD5(String msg, String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return digest("md5",msg.getBytes(encoding));
    }

    public static byte[] digest(String algorithm, byte[] bytes) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest alga = MessageDigest.getInstance(algorithm);
        alga.update(bytes);
        return alga.digest();
    }

    public static String MD5String(String msg, String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return byte2hex(digestMD5(msg, encoding)).toUpperCase();
    }


    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }
}
