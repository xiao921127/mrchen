package com.wawaji.app.model;

import android.text.TextUtils;
import android.util.Log;

import com.wawaji.app.contract.MyContract;
import com.wawaji.app.entity.MyEntity;
import com.wawaji.app.interf.BaseListChangeListener;
import com.wawaji.common.https.RequestHandler;
import com.wawaji.common.https.api.RequestApi;
import com.wawaji.common.https.entity.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/10 10:12
 */

public class MyModel implements MyContract.Model {

    private final String TAG = HomeModel.class.getSimpleName();

    @Override
    public void cancelRequest() {
        RequestApi.cancelRequestByTag(TAG);
    }

    /**
     * 获取首页数据
     *
     * @param page     页数
     * @param listener 回调
     */
    @Override
    public void getMyData(int page, final BaseListChangeListener<MyEntity> listener) {
        RequestApi.myData(page, new RequestHandler() {
            @Override
            public void onSucceed(HttpResponse result) {
                Log.e("MrChen","MY成功"+result.status.code+result.data);
                ArrayList<MyEntity> list = new ArrayList<>();
                if (TextUtils.isEmpty(result.data)) {
                    listener.onRefresh(list, 0);
                    return;
                }
                try {
//                    JSONObject json = new JSONObject(result.data);
                    JSONArray array =new JSONArray(result.data);
                    int length = array.length();
                    for (int i = 0; i < length; i++) {
                        MyEntity entity = new MyEntity();
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
                Log.e("MrChen","MY异常");
                listener.onError(result);
            }

            @Override
            public void onFailure() {
                Log.e("MrChen","MY网络异常");
                listener.onFailure();
            }
        }, TAG);
    }
}