package com.vessel.gather.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vesselzhang
 * @date 2018/1/18
 */

public class CommonUtils {

    /**
     * 手机号验证
     * 2016年12月5日下午4:34:46
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 400验证
     * 2016年12月5日下午4:34:46
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isFourMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[4][0][0][0-9]{7}$"); // 验证400
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     * 2016年12月5日下午4:34:21
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(final String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

}
