package com.wawaji.app.model;

import android.text.TextUtils;

import com.wawaji.app.contract.VerifyContract;
import com.wawaji.app.entity.VerifyEntity;
import com.wawaji.app.interf.OnImageCodeChangeListener;
import com.wawaji.app.interf.OnVerifyChangeListener;
import com.wawaji.common.https.RequestHandler;
import com.wawaji.common.https.api.RequestApi;
import com.wawaji.common.https.entity.HttpResponse;
import com.wawaji.common.notification.Notification;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 验证模型
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2016/11/22 11:35
 */
public class VerifyModel implements VerifyContract.Model {

    private static final String TAG = VerifyModel.class.getSimpleName();

    @Override
    public void onSendVerify(final VerifyEntity entity, final OnVerifyChangeListener l) {
        cancelRequest();
        RequestApi.sendPhoneVerfy(entity, new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                String data = result.data;
                if (TextUtils.isEmpty(data)) {
                    return;
                }
                l.onVerifySuccess();
                try {
                    JSONObject jb = new JSONObject(data);
                    if ("1".equalsIgnoreCase(jb.optString("is_show_voice"))) {
                        l.showVoiceDialog(true);
                    } else {
                        l.showVoiceDialog(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(HttpResponse result) {
//                if ("1".equalsIgnoreCase(result.status.is_show_voice)) {
//                    l.showVoiceDialog(true);
//                } else {
//                    l.showVoiceDialog(false);
//                }
                int code = result.status.code;
                VerifyEntity verify = new VerifyEntity();
                try {
                    verify.fromJson(result.data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (4022 == code) {
                    entity.token = verify.token;
                    l.showImageDialog(verify.image);
                } else if (1300 == code) {
                    l.onEncode(verify.token, result.server_time);
                } else {
                    l.onVerifyFailure();
                    Notification.showToastMsg(result.status.errorDesc);
                }

            }

            @Override
            public void onFailure() {
                l.onVerifyFailure();
            }
        }, TAG);
    }

    @Override
    public void onRefreshImage(final OnImageCodeChangeListener l) {
        RequestApi.refreshImage(new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                String data = result.data;
                try {
                    JSONObject jb = new JSONObject(data);
                    VerifyEntity entity = new VerifyEntity();
                    entity.image = jb.optString("img");
                    entity.token = jb.optString("verify_token");
                    l.onImageSuccess(entity);
                } catch (JSONException e) {
                    e.printStackTrace();
                    l.onImageFailure();
                }
            }

            @Override
            public void onFailure() {
                l.onImageFailure();
            }

            @Override
            public void onError(HttpResponse result) {
                l.onImageFailure();
            }
        }, TAG);
    }

    @Override
    public void cancelRequest() {
        RequestApi.cancelRequestByTag(TAG);
    }
}
