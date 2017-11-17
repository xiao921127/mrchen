package com.wawaji.common.https.entity;

import android.text.TextUtils;

import com.wawaji.common.base.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 请求状态
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2016/7/4
 */
public class Status implements BaseEntity {

    public int code; // 请求结果Code

    public Integer errorCode; // 错误代号

    public String errorDesc; // 错误信息

//    public String is_show_voice; // 是否是语音验证码
//
//    public String img; // 图片验证码
//
//    public String verify_token; // Token

//    public String extends_data; // 额外数据

    @Override
    public void fromJson(String json) throws JSONException {
        if (!TextUtils.isEmpty(json)) {
            JSONObject jb = new JSONObject(json);
            code = jb.optInt("code");
            errorCode = jb.optInt("errorCode");
            errorDesc = jb.optString("msg");
//            is_show_voice = jb.optString("is_show_voice");
//            img = jb.optString("img");
//            verify_token = jb.optString("verify_token");
//            extends_data = jb.optString("extends_data");
        }
    }

}
