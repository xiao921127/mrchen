package com.wawaji.app.model;

import android.text.TextUtils;

import com.wawaji.app.contract.HomeContract;
import com.wawaji.app.entity.DollsEntity;
import com.wawaji.app.interf.BaseListChangeListener;
import com.wawaji.common.https.RequestHandler;
import com.wawaji.common.https.api.RequestApi;
import com.wawaji.common.https.entity.HttpResponse;
import com.wawaji.common.notification.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 主页Model
 * <p>
 * Created by admin on 2017/11/3.
 */

public class HomeModel implements HomeContract.Model {

    private final String TAG = HomeModel.class.getSimpleName();

    @Override
    public void cancelRequest() {
        RequestApi.cancelRequestByTag(TAG);
    }

    @Override
    public void getHomeData(int page, final BaseListChangeListener<DollsEntity> listener) {
        RequestApi.homeData(page, new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                ArrayList<DollsEntity> list = new ArrayList<>();
                if (TextUtils.isEmpty(result.data)) {
                    listener.onRefresh(list, 0);
                    return;
                }
                try {
                    JSONObject json = new JSONObject(result.data);
                    JSONArray array = json.getJSONArray("wawa_list");
                    int length = array.length();
                    for (int i = 0; i < length; i++) {
                        DollsEntity entity = new DollsEntity();
                        entity.fromJson(array.getString(i));
                        list.add(entity);
                    }
                    listener.onRefresh(list, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(result);
                }
            }

            @Override
            public void onError(HttpResponse result) {
                listener.onError(result);
                Notification.showToastMsg(result.status.errorDesc);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        }, TAG);
    }

}
