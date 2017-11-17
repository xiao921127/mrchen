package com.wawaji.app.entity;

import com.wawaji.common.base.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 玩偶实体类
 * <p>
 * Created by admin on 2017/11/3.
 */

public class DollsEntity implements BaseEntity {

    public String id; // 玩偶id

    public String title; // 玩偶名

    public String price; // 价值

    public String img; // 图片

    public int playing; // 房间状态


    @Override
    public void fromJson(String json) throws JSONException {
        JSONObject jb = new JSONObject(json);
        id = jb.optString("id");
        title = jb.optString("title");
        price = jb.optString("price");
        img = jb.optString("img");
        playing = jb.optInt("playing");
    }
}
