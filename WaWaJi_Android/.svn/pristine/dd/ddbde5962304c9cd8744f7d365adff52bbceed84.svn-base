package com.wawaji.app.model;

import android.text.TextUtils;

import com.wawaji.app.contract.SplashContract;
import com.wawaji.app.entity.ConfigEntity;
import com.wawaji.common.https.RequestHandler;
import com.wawaji.common.https.api.RequestApi;
import com.wawaji.common.https.entity.HttpResponse;

import org.json.JSONException;

/**
 * 配置Model
 *
 * @author admin
 * @version V1.0 <配置模型>
 * @since 206/08/17 09:53
 */
public class ConfigModel implements SplashContract.Model {

    private final String TAG = ConfigModel.class.getSimpleName();

    @Override
    public void onConfig(final SplashContract.onConfigChangeListener listener) {
        RequestApi.config(new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                if (TextUtils.isEmpty(result.data)) {
                    listener.onConfigFailure();
                    return;
                }
                try {
                    ConfigEntity entity=new ConfigEntity();
                    entity.fromJson(result.data);
                    listener.onConfigSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onConfigFailure();
                }
            }

            @Override
            public void onFailure() {
                listener.onConfigFailure();
            }

            @Override
            public void onError(HttpResponse result) {
                listener.onConfigFailure();
            }
        }, TAG);
    }

    @Override
    public void cancelRequest() {
        RequestApi.cancelRequestByTag(TAG);
    }

}