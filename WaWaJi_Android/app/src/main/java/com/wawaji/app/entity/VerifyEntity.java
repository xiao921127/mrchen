package com.wawaji.app.entity;

import com.wawaji.common.base.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 验证码实体类
 *
 * @author admin
 * @version V1.0 <验证码>
 * @since 2016/11/22 11:30
 */
public class VerifyEntity implements BaseEntity {

    public String phone;//手机号

    public String verify;//验证码

    public int isShowVoice = 0;//是否显示语言验证码

    public String token;//加密key

    public String sysnType;// 第三方类型

    public String image;//验证码图片

    public String type = "login";// 类型 : 注册、登录

    public VerifyEntity(String phone) {
        this.phone = phone;
    }

    public VerifyEntity() {
    }

    @Override
    public void fromJson(String json) throws JSONException {
        JSONObject jb = new JSONObject(json);
        isShowVoice = jb.optInt("is_show_voice");
        token = jb.optString("verify_token");
        image = jb.optString("img");
    }
}