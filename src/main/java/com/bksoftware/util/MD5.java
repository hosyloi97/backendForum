package com.bksoftware.util;

import java.security.MessageDigest;

public class MD5 {

    public static String encode(String src) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(src.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String pass1 = "123456";
        System.out.println(encode(pass1));
    }
}
