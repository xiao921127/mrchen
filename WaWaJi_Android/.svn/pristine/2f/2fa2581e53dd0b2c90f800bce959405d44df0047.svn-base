package com.wawaji.app.entity;

import com.wawaji.common.base.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/10 10:00
 */

public class MyEntity implements BaseEntity {

    public String id; // 玩偶id

    public String wid; //

    public String title; // 名字

    public String img; // 图片

    public String ctime; // 时间


    @Override
    public void fromJson(String json) throws JSONException {
        JSONObject jb = new JSONObject(json);
        id = jb.optString("id");
        wid = jb.optString("wid");
        title = jb.optString("title");
        img = jb.optString("img");
        ctime = jb.optString("ctime");
    }
}
