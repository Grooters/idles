package io.github.grooters.idles.utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {

    public static String md5(String message){
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(message.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String toBase64(String message){

        return Base64.encodeToString(message.getBytes(StandardCharsets.UTF_8), Base64.CRLF);

    }

    public static String fromBase64(String message){

        return new String(Base64.decode(message, Base64.CRLF), StandardCharsets.UTF_8);

    }

}
