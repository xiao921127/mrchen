package com.wawaji.common.notification;

import android.os.Handler;
import android.os.Looper;

import com.wawaji.app.AppApplication;


/**
 * 通知提示类(包括Toast、Dialog等)
 *
 * @author admin
 * @since 2015.11.25
 */
public class Notification {

    private static final Handler mHandler = new Handler(Looper.getMainLooper()); // Handler线程

    /**
     * 显示toast消息
     *
     * @param msg 要显示的消息
     */
    public static void showToastMsg(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
               AppToast.showText(msg);
            }
        });
    }

    /**
     * 显示toast消息
     *
     * @param resId 要显示的消息id
     */
    public static void showToastMsg(final int resId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String msg = AppApplication.getStringById(resId);
                AppToast.showText(msg);
            }
        });
    }
}