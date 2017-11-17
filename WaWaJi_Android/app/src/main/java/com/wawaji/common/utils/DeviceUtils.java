package com.wawaji.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.wawaji.app.AppApplication;

/**
 * 获取手机设备信息
 *
 * @author admin
 * @version <2.0>
 * @since 2016/6/8
 */
@SuppressWarnings("StringBufferReplaceableByString")
@SuppressLint("HardwareIds")
public final class DeviceUtils {
    
    private static final String KEY_DEVICE_TOKEN = "DEVICE_TOKEN"; // 设备号本地保存字段
    private static final String KEY_DEFAULT_VERSION = "1.0.0"; // 默认版本号
    private static final String KEY_DEFAULT_IMEI = "0000000000000"; // 默认IMEI
    public static final String KEY_APP_CHANNEL = "UMENG_CHANNEL"; // 版本渠道
    private static final String KEY_DEVICE_IMEI = "IM";
    private static final String KEY_DEVICE_ANDROID = "AD";
    private static final String KEY_DEVICE_ID = "ID";

    // 私有化构造方法
    private DeviceUtils() {
        // This utility class is not publicly instantiable
    }


    /**
     * 获取设备号
     *
     * @return str
     */
    public static String getDeviceId() {
        String deviceId = AppApplication.get(KEY_DEVICE_TOKEN, null);
        if (!TextUtils.isEmpty(deviceId)) {
            // 不为空，返回设备号
//            LogUtils.e("deviceId:" + deviceId);
            return deviceId;
        }
        StringBuilder str = new StringBuilder();
        try {
            TelephonyManager tm = (TelephonyManager) AppApplication.getInstance()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            // IMEI
            String device_id = tm.getDeviceId();
            if (!KEY_DEFAULT_IMEI.equals(device_id)) {
//                LogUtils.e("device_id:" + device_id);
                str.append(KEY_DEVICE_IMEI);
                str.append(device_id);
                device_id = str.toString();
                AppApplication.set(KEY_DEVICE_TOKEN, device_id);
                return device_id;
            }

            // 序列号
//            String sn = tm.getSimSerialNumber();
//            if (!TextUtils.isEmpty(sn)) {
//                LogUtils.e("sn:" + sn);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设备ID
        String android_id = Settings.Secure.getString(AppApplication.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(android_id)) {
//            LogUtils.e("android_id:" + android_id);
            str.append(KEY_DEVICE_ANDROID);
            str.append(android_id);
            android_id = str.toString();
            AppApplication.set(KEY_DEVICE_TOKEN, android_id);
            return android_id;
        }

        str.append(KEY_DEVICE_ID);
        // 添加设备相关信息
        str.append(Build.BOARD.length() % 10);
        str.append(Build.BRAND.length() % 10);
        str.append(Build.DEVICE.length() % 10);
        str.append(Build.DISPLAY.length() % 10);
        str.append(Build.HOST.length() % 10);
        str.append(Build.ID.length() % 10);
        str.append(Build.MODEL.length() % 10);
        str.append(Build.PRODUCT.length() % 10);
        str.append(Build.TAGS.length() % 10);
        str.append(Build.TYPE.length() % 10);
        str.append(Build.USER.length() % 10);
        str.append(Build.MANUFACTURER.length() % 10);
        str.append(Build.SERIAL.length() % 10);
        str.append(Build.BOOTLOADER.length() % 10);
        str.append(Build.FINGERPRINT.length() % 10);
        String id = str.toString();
//        LogUtils.e("id:" + id);
        AppApplication.set(KEY_DEVICE_TOKEN, id);
        return id;
    }

    /**
     * 获取app版本名
     *
     * @return str
     */
    public static String getVersion() {
        String versionName = KEY_DEFAULT_VERSION;
        try {
            Context context = AppApplication.getInstance();
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取app版本号
     *
     * @return int
     */
    public static int getVersionCode() {
        int versionCode = 1;
        try {
            Context context = AppApplication.getInstance();
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值或者异常)，则返回值为空
     */
    public static String getAppMetaData(String key) {
        String resultData = null;
        try {
            PackageManager packageManager = AppApplication.getInstance().getPackageManager();
            if (null == packageManager) {
                return null;
            }
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(AppApplication.getInstance().getPackageName(), PackageManager.GET_META_DATA);
            if (null == applicationInfo) {
                return null;
            }
            if (applicationInfo.metaData != null) {
                resultData = applicationInfo.metaData.getString(key);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }

}