package com.wawaji.common.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Date工具类
 *
 * @author admin
 * @since 2016.06.30
 */
public class DateUtils {

    /**
     * 转换时间
     *
     * @param time 时间
     * @return str
     */
    public static String gmtToDate(String time) {
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String str = null;
        try {
            Date date = format.parse(time);
            format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            str = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 将日期转换成时间戳
     *
     * @param time 时间
     * @return str
     */
    @SuppressWarnings("unused")
    public static String getStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sf.parse(time);
            long t = (date.getTime() / 1000);
            return String.valueOf(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间戳转换成日期
     * @return str
     */
    public static String getStringDate(String str) {
        long time = Long.valueOf(str);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return formatter.format(time * 1000);
    }

    /**
     * 获取当前时间
     *
     * @return str
     */
    @SuppressLint("SimpleDateFormat")
    public static String getClientTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

}