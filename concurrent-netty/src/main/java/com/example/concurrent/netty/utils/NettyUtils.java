package com.example.concurrent.netty.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author szf
 * @describe: DOTO
 * @Date 2022/5/31 15:37
 * @Copyright: 2022 http://www.gzwison.com/ Inc. All rights reserved.
 * 注意：本内容仅限于广州智臣科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class NettyUtils {


    /**
     * 十六进制数值 转十六进制字符串
     *
     * @param bytes byte数组
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 十六进制转换为十进制
     *
     * @param content
     * @return
     */
    public static int covert(String content) {
        int number = 0;
        String[] HighLetter = {"A", "B", "C", "D", "E", "F"};
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            map.put(i + "", i);
        }
        for (int j = 10; j < HighLetter.length + 10; j++) {
            map.put(HighLetter[j - 10], j);
        }
        String[] str = new String[content.length()];
        for (int i = 0; i < str.length; i++) {
            str[i] = content.substring(i, i + 1);
        }
        for (int i = 0; i < str.length; i++) {
            number += map.get(str[i]) * Math.pow(16, str.length - 1 - i);
        }
        return number;
    }

    /**
     * 十进制转换为ASCII码
     *
     * @param ascii
     * @return
     */
    public static String dec2Str(String ascii) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ascii.length() - 1; i += 2) {
            String h = ascii.substring(i, (i + 2));
            // 这里第二个参数传10表10进制
            int decimal = Integer.parseInt(h, 10);
            sb.append((char) decimal);
        }
        return sb.toString();
    }
}
