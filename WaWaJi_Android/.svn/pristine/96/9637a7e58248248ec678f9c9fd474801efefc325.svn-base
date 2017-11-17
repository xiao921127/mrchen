package com.wawaji.app.model;

import com.wawaji.app.contract.SettingsContract;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.interf.OnRequestChangeListener;
import com.wawaji.common.https.RequestHandler;
import com.wawaji.common.https.api.RequestApi;
import com.wawaji.common.https.entity.HttpResponse;
import com.wawaji.common.notification.Notification;

/**
 * 设置模型
 * <p>
 * Created by admin on 2017/11/10.
 */

public class SettingsModel implements SettingsContract.Model {

    private final String TAG = PlayModel.class.getSimpleName();

    @Override
    public void cancelRequest() {
        RequestApi.cancelRequestByTag(TAG);
    }

    @Override
    public void logout(final OnRequestChangeListener<String> listener) {
        RequestApi.userLogout(new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                Notification.showToastMsg(result.status.errorDesc);
                UserEntity.doLogout();
                listener.onSuccess(null);
            }

            @Override
            public void onError(HttpResponse result) {
                listener.onError();
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        },TAG);
    }
}
