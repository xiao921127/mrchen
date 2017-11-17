package com.wawaji.app.entity;

import android.text.TextUtils;

import com.wawaji.app.AppApplication;
import com.wawaji.app.AppConfig;
import com.wawaji.app.config.StringConfig;
import com.wawaji.common.base.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 配置实体类
 *
 * @author admin
 * @version V1.0 <配置实体类>
 * @since 2017/07/31 19:49
 */

public class ConfigEntity implements BaseEntity {

    @Override
    public void fromJson(String json) throws JSONException {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        JSONObject jsonObject = new JSONObject(json);
        String baseacturl = jsonObject.optString("baseacturl");
        String service_qq = jsonObject.optString("service_qq");
        String jprule = jsonObject.optString("jprulehtml");
        String pay_pay_arr = jsonObject.optString("pay_pay_arr");
        String pay_recharge_arr = jsonObject.optString("pay_recharge_arr");
        String delaytime = jsonObject.optString("delaytime");
        String default_num = jsonObject.optString("default_num"); // 默认选择数量
        AppApplication.set(AppConfig.APP_API_URL, baseacturl); // 基础接口地址
        AppApplication.set(StringConfig.PRE_SERVICE_QQ, service_qq); // 客服QQ
        AppApplication.set(StringConfig.PRE_AUCTION_RULE, jprule); // 竞拍规则
        AppApplication.set(StringConfig.PRE_ORDER_PAY, pay_pay_arr); // 订单支付方式
        AppApplication.set(StringConfig.PRE_RECHARGE_PAY, pay_recharge_arr); // 充值支付方式
        AppApplication.set(StringConfig.PRE_AUCTION_DELAY, Integer.valueOf(delaytime)); // 默认选择数量
        AppApplication.set(StringConfig.PRE_DOWNLOAD_CONTENT, jsonObject.optString(StringConfig.PRE_DOWNLOAD_CONTENT)); // App更新内容
    }

}