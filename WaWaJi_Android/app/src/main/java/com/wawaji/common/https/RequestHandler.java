package com.wawaji.common.https;

import android.text.TextUtils;

import com.wawaji.common.https.entity.HttpResponse;
import com.wawaji.common.notification.Notification;

import org.json.JSONException;

/**
 * 网络请求回调接口
 *
 * @author admin
 * @since 2016/6/8
 */
@SuppressWarnings("WeakerAccess")
public abstract class RequestHandler {

    protected static final int KEY_SUCCESS_CODE = 1;
    protected static final int KEY_LOGIN_OUT = 5;

    private boolean is_show_toast;

    // 构造方法
    public RequestHandler() {
        this(false);
    }

    // 构造方法
    public RequestHandler(boolean is_show_toast) {
        this.is_show_toast = is_show_toast;
    }

    /**
     * 网络请求成功
     *
     * @param json 数据
     */
    @SuppressWarnings({"JavaDoc", "WeakerAccess"})
    public void onSuccess(String json) {
        if (!TextUtils.isEmpty(json)) {
            HttpResponse result = new HttpResponse();
            try {
                result.fromJson(json);
                if (KEY_LOGIN_OUT == result.status.errorCode) {
//                    BaseUserEntitiy.doLogout();
                    Notification.showToastMsg(result.status.errorDesc);
                }
//                String html5 = result.login_tips_box;
//                if (!TextUtils.isEmpty(html5)) {
//                    // 网页弹框
//                    AppManager.putCacheMap(AppManager.KEY_WEB_URL, html5);
//                    AppManager.sendBroadcast(AppManager.APP_ACTION_WEB);
//                }
                if (KEY_SUCCESS_CODE == result.status.code) {
                    onSucceed(result);
                } else {
                    onError(result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                onFailure();
            }
        } else {
            onFailure();
        }
    }

//    /**
//     * 网络请求失败
//     */
//    protected void onFailure() {
//        if (is_show_toast) {
//            Notification.showToastMsg(R.string.str_app_no_network);
//        }
//    }

    public abstract void onSucceed(HttpResponse result);

    public abstract void onError(HttpResponse result);

    public abstract void onFailure();

}