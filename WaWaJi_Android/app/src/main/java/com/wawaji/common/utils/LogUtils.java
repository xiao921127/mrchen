package com.wawaji.common.utils;


import com.wawaji.app.BuildConfig;

import java.util.Locale;

import timber.log.Timber;


/**
 * 
 * 用于管理Log日志的打印
 *
 * @version V1.0 <Log日志打印>
 * @author admin
 * @since 2016/6/14
 */
public class LogUtils {

    public static boolean allowV = false;

    public static boolean allowD = false;

    public static boolean allowE = false;

    public static boolean allowI = false;

    public static boolean allowW = false;

    public static boolean allowWtf = false;

    /**
     * 私有化构造方法
     */
    private LogUtils(){
    }

    // LogUtils 初始化
    public static void init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    /**
     * 获取当前页面Tag
     * @return str
     */
    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(Locale.getDefault(), tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    public static void d(String s, Object... objects) {
        if (!allowD) return;
        Timber.tag(generateTag());
        Timber.d(s, objects);
    }

    public static void d(Throwable throwable, String s, Object... objects) {
        if (!allowD) return;
        Timber.tag(generateTag());
        Timber.d(throwable, s, objects);
    }

    public static void e(String s, Object... objects) {
        if (!allowE) return;
        Timber.tag(generateTag());
        Timber.e(s, objects);
    }

    public static void e(Throwable throwable, String s, Object... objects) {
        if (!allowE) return;
        Timber.tag(generateTag());
        Timber.e(throwable, s, objects);
    }

    public static void i(String s, Object... objects) {
        if (!allowI) return;
        Timber.tag(generateTag());
        Timber.i(s, objects);
    }

    public static void i(Throwable throwable, String s, Object... objects) {
        if (!allowI) return;
        Timber.tag(generateTag());
        Timber.i(throwable, s, objects);
    }

    public static void v(String s, Object... objects) {
        if (!allowV) return;
        Timber.tag(generateTag());
        Timber.v(s, objects);
    }

    public static void v(Throwable throwable, String s, Object... objects) {
        if (!allowV) return;
        Timber.tag(generateTag());
        Timber.v(throwable, s, objects);
    }

    public static void w(String s, Object... objects) {
        if (!allowW) return;
        Timber.tag(generateTag());
        Timber.w(s, objects);
    }

    public static void w(Throwable throwable, String s, Object... objects) {
        if (!allowW) return;
        Timber.tag(generateTag());
        Timber.w(throwable, s, objects);
    }

}