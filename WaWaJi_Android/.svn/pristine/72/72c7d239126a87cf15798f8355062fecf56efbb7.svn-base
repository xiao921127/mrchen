package com.wawaji.common.https.entity;

import android.text.TextUtils;

import com.wawaji.common.base.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 网络请求实体类
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2016/7/4
 */
@SuppressWarnings("WeakerAccess")
public class HttpResponse implements BaseEntity {

    public String data; // 数据源

    public Status status;

    public Paginated paginated;

    public String server_time;

    public String login_tips_box;

    @Override
    public void fromJson(String json) throws JSONException {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        JSONObject jb = new JSONObject(json);
        data = jb.optString("data");
        String str1 = jb.optString("status");
        status = new Status();
        status.fromJson(str1);

        String str2 = jb.optString("paginated");
        paginated = new Paginated();
        paginated.fromJson(str2);

        server_time = jb.optString("server_time");
        login_tips_box = jb.optString("login_tips_box");

    }

}