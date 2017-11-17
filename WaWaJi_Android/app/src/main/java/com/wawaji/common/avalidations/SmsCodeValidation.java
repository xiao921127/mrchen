package com.wawaji.common.avalidations;

import android.text.TextUtils;

import com.wawaji.app.R;
import com.wawaji.common.notification.Notification;



/**
 * 短信验证码校验器
 * @since 2016.03.09
 * @author admin
 *
 */
public class SmsCodeValidation extends ValidationExecutor{

	@Override
	public boolean doValidate(String text) {
		if (TextUtils.isEmpty(text)) {
			Notification.showToastMsg(R.string.input_sms_code);
			return false;
		}
		return true;
	}

}
