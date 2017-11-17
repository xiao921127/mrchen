package com.wawaji.app.model;

import android.text.TextUtils;

import com.wawaji.app.contract.PlayDetailsContract;
import com.wawaji.app.entity.PlayEntity;
import com.wawaji.app.interf.OnRequestChangeListener;
import com.wawaji.common.https.RequestHandler;
import com.wawaji.common.https.api.RequestApi;
import com.wawaji.common.https.entity.HttpResponse;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/13 11:35
 */

public class PlayDetailsModel implements PlayDetailsContract.Model {

    private final String TAG = PlayModel.class.getSimpleName();

    @Override
    public void cancelRequest() {
        RequestApi.cancelRequestByTag(TAG);
    }

    /**
     * 获取娃娃详情
     *
     * @param id       房间id
     * @param listener 请求回调
     */
    @Override
    public void getPlayDetails(String id,final OnRequestChangeListener<PlayEntity> listener) {
        RequestApi.playDatails(id, new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                if (TextUtils.isEmpty(result.data)) {
                    listener.onError();
                    return;
                }
                PlayEntity entity = new PlayEntity();
                try {
                    entity.fromJson(result.data);
                } catch (Exception e) {
                    listener.onError();
                    e.printStackTrace();
                }
                listener.onSuccess(entity);
            }

            @Override
            public void onError(HttpResponse result) {
                listener.onError();
            }

            @Override
            public void onFailure() {
                listener.onError();
            }
        },TAG);

    }
}
