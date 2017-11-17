package com.wawaji.app.entity;

import com.wawaji.common.base.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 玩家记录实体
 * <p>
 * Created by admin on 2017/11/6.
 */

public class PlayerRecordEntity implements BaseEntity {

    public String nickname;//昵称

    public String avatar;//头像

    public String ctime;//时间

    public String msg;//弹幕信息

    @Override
    public void fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        nickname = jsonObject.optString("nickname");
        avatar = jsonObject.optString("avatar");
        ctime = jsonObject.optString("ctime");
        msg = jsonObject.optString("msg");

    }
}
