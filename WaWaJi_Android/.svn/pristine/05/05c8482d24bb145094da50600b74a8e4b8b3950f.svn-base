package com.wawaji.app.model;

import android.text.TextUtils;

import com.wawaji.app.AppApplication;
import com.wawaji.app.contract.LoginContract;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.interf.OnRequestChangeListener;
import com.wawaji.common.https.RequestHandler;
import com.wawaji.common.https.api.RequestApi;
import com.wawaji.common.https.entity.HttpResponse;
import com.wawaji.common.notification.Notification;
import com.wawaji.common.utils.LogUtils;

import org.json.JSONException;

/**
 * 登录模型
 * <p>
 * Created by admin on 2017/11/8.
 */

public class LoginModel implements LoginContract.Model {

    private final String TAG = LoginModel.class.getSimpleName();

    @Override
    public void cancelRequest() {
        RequestApi.cancelRequestByTag(TAG);
    }

    @Override
    public void loginPhonePwd(String phone, String pwd, final OnRequestChangeListener<UserEntity> listener) {
        RequestApi.loginPhonePwd(phone, pwd, new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                LogUtils.e("result " + result);
            }

            @Override
            public void onError(HttpResponse result) {
                Notification.showToastMsg(result.status.errorDesc);
                listener.onError();
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        }, TAG);
    }

    @Override
    public void userPhoneLogin(String mobile, String verify, final OnRequestChangeListener<String> listener) {
        RequestApi.userPhoneLogin(mobile, verify, new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                if (TextUtils.isEmpty(result.data)) {
                    listener.onError();
                    return;
                }
                UserEntity entity = new UserEntity();
                try {
                    entity.fromJson(result.data);
                    entity.doOnline();
                    listener.onSuccess(null);
                } catch (JSONException e) {
                    listener.onError();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(HttpResponse result) {
                listener.onError();
                Notification.showToastMsg(result.status.errorDesc);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        }, TAG);
    }

    @Override
    public void refreshInfo() {
        RequestApi.refreshInfo(new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                try {
                    UserEntity info = new UserEntity();
                    info.fromJson(result.data);
                    UserEntity user = AppApplication.getInstance().getCurUser();
                    user.nickname = info.nickname;
                    user.username = info.username;
                    user.avatar = info.avatar;
                    user.wmoney = info.wmoney;
                    user.mobile = info.mobile;
                    user.catch_count = info.catch_count;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(HttpResponse result) {

            }

            @Override
            public void onFailure() {

            }
        }, TAG);
    }

}
