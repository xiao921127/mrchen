package com.wawaji.common.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.wawaji.common.utils.LogUtils;


/**
 * Application基础类
 *
 * @author admin
 * @version 1.0
 * @since 2016/6/8
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseApplication extends MultiDexApplication {

    // Application上下文实例
    @SuppressLint("StaticFieldLeak")
    public static Context sContext;

    // Resource
    public static Resources sResource;

    /**
     * 每个类实例对应一把锁，每个 synchronized 方法都必须获得调用该方法的类实例的锁方能 执行，否则所属线程阻塞，方法一
     * 旦执行，就独占该锁，直到从该方法返回时才将锁释放，此后被阻塞的线程方能获得该锁，重新进入可执行状态。这种机制确保了同
     * 一时刻对于每一个类实例，其所有声明为 synchronized 的成员函数中至多只有一个处于可执行状态（因为至多只有一个能够获
     * 得该类实例对应的锁），从而有效避免了类成员变量的访问冲突（只要所有可能访问类成员变量的方法均被声明为 synchronized）。
     */
//    public static synchronized BaseApplication getInstance() {
//        return (BaseApplication) mApp;
//    }
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Application上下文
        sContext = getApplicationContext();

        // 初始化Resource
        sResource = sContext.getResources();

        init();
        initLog();
    }

    protected abstract void init();

    /**
     * 初始化Log日志设置
     */
    private void initLog() {
        LogUtils.init();
        LogUtils.allowV = false;
        LogUtils.allowD = true; // 接口日志
        LogUtils.allowE = true; // 调试日志
        LogUtils.allowW = false;
        LogUtils.allowI = false;
        LogUtils.allowWtf = false;
    }

    private static boolean sIsAtLeastGB = false;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }
    }

    // SharePrefrence配置文件名
    private final static String PREF_NAME = "config.pref";

    /**
     * 获取SharedPreferences 实例
     *
     * @return SharedPreferences
     */
    public static SharedPreferences getPreferences() {
        return sContext.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
    }

    /**
     * 获取SharedPreferences 实例
     *
     * @param prefName 文件名
     * @return SharedPreferences
     */
    public static SharedPreferences getPreferences(String prefName) {
        return sContext.getSharedPreferences(prefName,
                Context.MODE_PRIVATE);
    }

    /**
     * SharedPreferences添加数据
     *
     * @param key   参数
     * @param value 值
     */
    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        apply(editor);
    }

    /**
     * SharedPreferences添加数据
     *
     * @param key   参数
     * @param value 值
     */
    public static void set(String key, float value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        apply(editor);
    }

    /**
     * SharedPreferences添加数据
     *
     * @param key   参数
     * @param value 值
     */
    public static void set(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        apply(editor);
    }

    /**
     * SharedPreferences添加数据
     *
     * @param key   参数
     * @param value 值
     */
    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    /**
     * SharedPreferences添加数据
     *
     * @param key   参数
     * @param value 值
     */
    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }

//    public static void set(String key,Object value){
//        SharedPreferences.Editor editor = getPreferences().edit();
//        editor.putStringSet()
//    }

    /**
     * SharedPreferences取数据
     *
     * @param key      参数
     * @param defValue 值
     * @return int
     */
    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    /**
     * SharedPreferences取数据
     *
     * @param key      参数
     * @param defValue 值
     * @return float
     */
    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    /**
     * SharedPreferences取数据
     *
     * @param key      参数
     * @param defValue 值
     * @return str
     */
    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    /**
     * SharedPreferences取数据
     *
     * @param key      参数
     * @param defValue 值
     * @return str
     */
    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    /**
     * SharedPreferences取数据
     *
     * @param key      参数
     * @param defValue 值
     * @return str
     */
    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    /**
     * 移除SharedPreferences数据
     *
     * @param key 参数
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove(key);
        editor.apply();
    }

//    /**
//     * 获取屏幕尺寸
//     *
//     * @return init[]
//     */
//    public static int[] getDisplaySize() {
//        return new int[]{getPreferences().getInt("screen_width", 480),
//                getPreferences().getInt("screen_height", 854)};
//    }

    /**
     * 获取屏幕参数
     *
     * @return 屏幕参数
     */
    public static String getDisplaySize() {
        return getDisplayHeight() + "," + getDisplayWidth();
    }

    private static int height = 0;
    private static int width = 0;

    /**
     * 得到屏幕高度
     *
     * @return int
     */
    public static int getDisplayHeight() {
        if (0 == height) {
            height = getPreferences().getInt("screen_height", 854);
        }
        return height;
    }

    /**
     * 得到屏幕宽度
     *
     * @return int
     */
    public static int getDisplayWidth() {
        if (0 == width) {
            width = getPreferences().getInt("screen_width", 480);
        }
        return width;
    }

    /**
     * 保存屏幕尺寸
     *
     * @param activity Activity
     */
    public static void saveDisplaySize(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt("screen_width", displaymetrics.widthPixels);
        editor.putInt("screen_height", displaymetrics.heightPixels);
        editor.putFloat("density", displaymetrics.density);
        editor.apply();
    }

    /**
     * SharedPreferences写入后提交数据
     *
     * @param editor SharedPreferences.Editor
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
        if (sIsAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    /**
     * 从xml文件获取字符串
     *
     * @param id 字符串ID
     * @return str
     */
    public static String getStringById(int id) {
        return sResource.getString(id);
    }

    private boolean ifAppear;  //判断App是否打开

    /**
     * the App disappear into background
     */
    public void appDisappear() {
        ifAppear = false;
    }

    /**
     * the App appear onto foreground
     */
    public void appAppear() {
        ifAppear = true;
    }

    /**
     * if the app in to foreground
     *
     * @return boolean
     */
    public boolean ifAppear() {
        return ifAppear;
    }

}