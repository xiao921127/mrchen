package com.wawaji.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.wawaji.app.config.StringConfig;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.common.base.BaseApplication;

import java.util.List;


/**
 * 自定义Application
 *
 * @author admin
 * @since 2016/6/8
 */
public class AppApplication extends BaseApplication {

    @SuppressLint("StaticFieldLeak")
    private static AppApplication mApp = null;

    /**
     * 获取Application 实例
     *
     * @return AppApplication
     */
    public static AppApplication getInstance() {
        return mApp;
    }

    @Override
    protected void init() {
        mApp = this;
        initUser();
//        initFresco();
        initMiPush();
        initUMengSdk();
        initUMAnalytics();
        initFileDownloadManager();

    }

    /**
     * 初始化用户信息
     */
    private void initUser() {
        SharedPreferences sp = getPreferences(AppConfig.USER);
        String uid = sp.getString(StringConfig.KEY_USER_UID, null);
        String sid = sp.getString(StringConfig.KEY_USER_SID, null);
        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(sid)) {
            UserEntity user = getCurUser();
            user.uid = uid;
            user.sid = sid;
            user.nickname = sp.getString(StringConfig.KEY_USER_NICKNAME, null);
            user.avatar = sp.getString(StringConfig.KEY_USER_AVATAR, null);
            user.mobile = sp.getString(StringConfig.KEY_USER_MOBILE, null);
//            user.alipay = sp.getString(StringConfig.KEY_USER_ALIPAY, null);
            user.username = sp.getString(StringConfig.KEY_USER_REAL_NAME, null);
        }
    }

//    /**
//     * 初始化图片加载类
//     */
//    @SuppressWarnings("deprecation")
//    private void initFresco() {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
//                .newBuilder(this, okHttpClient)
//                .build();
//        Fresco.initialize(this, config);
//    }

    // 初始化友盟SDK
    private void initUMengSdk() {
//        PlatformConfig.setWeixin(AppConfig.WX_APP_ID, AppConfig.WX_APP_KEY);
//        PlatformConfig.setSinaWeibo(AppConfig.SINA_APP_ID, AppConfig.SINA_APP_KEY, AppConfig.SINA_REDIRECT_URL);
//        PlatformConfig.setQQZone(AppConfig.QZ_APP_ID, AppConfig.QZ_APP_KEY);
//        UMShareAPI.get(this);
//        Log.LOG = false; // Log开关
//        Config.DEBUG = false; // DEBUG开关
    }

    private UserEntity mUser; // 当登录陆用户

    /**
     * 获得用户信息
     *
     * @return UserEntity
     */
    public UserEntity getCurUser() {
        if (mUser == null) {
            mUser = new UserEntity();
        }
        return mUser;
    }

    /**
     * 设置用户别名
     *
     * @param user 用户信息
     */
    public void setCurUser(UserEntity user) {
        this.mUser = user;
    }

    /**
     * 设置推送别名
     *
     * @param uid 用户ID
     */
    public void setPushAlias(String uid) {
        //去掉以前的,保持一个设备只有一个alias
//        List<String> srcAlias = MiPushClient.getAllAlias(this);
//
//        if (srcAlias != null) {
//            for (String str : srcAlias) {
//                if (!TextUtils.isEmpty(str) && str.equalsIgnoreCase(uid)) {
//                    continue;
//                }
//                MiPushClient.unsetAlias(this, str, null);
//            }
//        }
//        if (!TextUtils.isEmpty(uid)) {
//            MiPushClient.setAlias(this, uid, null);
//        }
    }

    /**
     * 注销推送别名
     */
    public void unSetPushAlias() {
        //去掉以前的,保持一个设备只有一个alias
//        List<String> srcAlias = MiPushClient.getAllAlias(this);
//        if (srcAlias != null) {
//            for (String str : srcAlias) {
//                MiPushClient.unsetAlias(this, str, null);
//            }
//        }
    }

    /**
     * 初始化小米推送服务
     */
    private void initMiPush() {
//        boolean is_msg = get(StringConfig.KEY_IS_MESSAGE, true);
//        //初始化push推送服务
//        if (shouldInit() && is_msg) {
//            MiPushClient.registerPush(this, AppConfig.MI_APP_ID, AppConfig.MI_APP_KEY);
//        } else {
//            MiPushClient.unregisterPush(this);
//        }
//        //打开Log
//        LoggerInterface newLogger = new LoggerInterface() {
//
//            @Override
//            public void setTag(String tag) {
//            }
//
//            @Override
//            public void log(String content, Throwable t) {
//                LogUtils.d(content, t);
//            }
//
//            @Override
//            public void log(String content) {
//                LogUtils.d(content);
//            }
//        };
//        Logger.setLogger(this, newLogger);
    }

    /**
     * 关闭推送
     */
    @SuppressWarnings("unused")
    public void closeMiPush() {
//        MiPushClient.unregisterPush(this);
    }

    /**
     * 检查APP是否重复运行
     *
     * @return boolean
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化下载Manager
     */
    private void initFileDownloadManager() {
//        FileDownloader.init(this, new FileDownloadHelper.OkHttpClientCustomMaker() {
//            @Override
//            public okhttp3.OkHttpClient customMake() {
//                return null;
//            }
//        }, 1);
    }

    /**
     * 初始化友盟统计SDK
     */
    private void initUMAnalytics() {
//        MobclickAgent.setDebugMode(true);
//        // 初始化友盟SDK，参数（context，app_key，频道id,场景模式选择,是否开启crash模式）
////        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getApplicationContext(),
////                AppConfig.UMENG_APP_KEY, "TextChannel", MobclickAgent.EScenarioType.E_UM_NORMAL, true));
//        // 禁止默认的页面统计方式，这样将不会再自动统计Activity
////        MobclickAgent.openActivityDurationTrack(false);
//        // 场景类型设置接口
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        new Test().sendMessage(this, AppConfig.UMENG_APP_KEY); // 友盟广告检测
    }

}