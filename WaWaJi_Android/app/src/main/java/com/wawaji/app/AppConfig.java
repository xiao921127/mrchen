package com.wawaji.app;

import android.os.Environment;

import java.io.File;

/**
 * App相关配置文件
 *
 * @version V1.0 <App相关配置>
 * @author admin
 * @since 2016/6/16
 */
@SuppressWarnings("WeakerAccess")
public final class AppConfig {

    // 私有化构造方法
    private AppConfig(){}

    // App编码格式
    public static final String FORMAT_CODE = "utf-8";

    // APP域名地址
    public static final String APP_API_URL = "api_url";

    // App系统版本
    public static final String APP_TARGET_OS = "android";

    // META-DATA字段
    public static final String META_DATA = "UMENG_CHANNEL";

    // 和服务器定的一个字符串(用户UID)
    public final static String HEADER_LOGIN_KEY = "user-session";

    // 和服务器定的一个字符串(分辨率大小)
    public final static String HEADER_SCREEN_KEY = "user-screen";

    // 和服务器定的一个字符串(设备ID)
    public final static String HEADER_DEVICE_KEY = "device-id";

    // 和服务器定的一个字符串(版本号)
    public final static String HEADER_VERSION_KEY = "v";

    // 和服务器定的一个字符串android or ios
    public final static String HEADER_TARGET_KEY = "target";

    // 和服务器定的一个字符串
    public final static String HEADER_CHANNEL_KEY = "channel";

    // 和服务器定的一个字符串
    public final static String HEADER_TIME_KEY = "now-time";

    // 和服务器定的一个字符串(第一次安装)
    public final static String HEADER_FIRST_INSTALL = "first-install";

    //第一次安装
    public final static String FIRST_INSTALL = "first_install";

    // 存储用户信息
    public final static String USER = "user.pref";

    //默认分类
    public final static String DEFALUT_CATE = "[{\"id\":\"0\",\"title\":\"推荐\"},{\"id\":\"2\",\"title\":\"男装 \"},{\"id\":\"3\",\"title\":\"数码家电\"},{\"id\":\"4\",\"title\":\"运动\"},{\"id\":\"5\",\"title\":\"鞋品\"},{\"id\":\"6\",\"title\":\"美食\"},{\"id\":\"7\",\"title\":\"居家\"},{\"id\":\"8\",\"title\":\"女装\"},{\"id\":\"9\",\"title\":\"配饰\"},{\"id\":\"10\",\"title\":\"内衣 \"},{\"id\":\"11\",\"title\":\"美妆\"},{\"id\":\"12\",\"title\":\"儿童\"}]";

    // 默认存放缓存的路径
    public final static String DEFAULT_CACHE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "kelezhuan"
            + File.separator
            + "images"
            + File.separator;

    // 默认存放JSON文件缓存的路径
    @SuppressWarnings("unused")
    public final static String DEFAULT_JSON_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "kelezhuan"
            + File.separator
            + "json"
            + File.separator;

    // 默认存放APK文件的路径
    public final static String DEFAULT_DOWNLOAD_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "kelezhuan"
            + File.separator
            + "apk"
            + File.separator
            + "kelezhuan.apk";

    // 默认存放缓存的路径
    public final static String DEFAULT_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "kelezhuan"
            + File.separator
            + "images"
            + File.separator;

    // 默认头像图片缓存路径
    public final static String APP_DEFAULT_IAMGE_AVATAR = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "kelezhuan"
            + File.separator
            + "images"
            + File.separator
            + "user_mine_avatar.jpg"
            + File.separator;

    public final static int DEFAULT_LOAD_DELAY_TIME = 300;
}