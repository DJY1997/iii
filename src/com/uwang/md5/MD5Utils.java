package com.uwang.md5;

import java.security.MessageDigest;

/**
 * MD5工具类，提供字符串MD5加密（校验）、文件MD5值获取(校验)功能
 */
public class MD5Utils {

	private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public MD5Utils() {
    }

    public static String hexdigest(String string) {
        String s = null;

        try {
            s = hexdigest(string.getBytes());
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return s;
    }

    public static String hexdigest(byte[] bytes) {
        String s = null;
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(bytes);
            byte[] tmp = e.digest();
            char[] str = new char[32];
            int k = 0;

            for(int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            s = new String(str);
        } catch (Exception var8) {
            var8.printStackTrace();
        }
        return s;
    }
    
    public static void main(String[] args) {
        String ss="123457";							 
        System.out.println(MD5Utils.hexdigest(ss));  
        
    }
}