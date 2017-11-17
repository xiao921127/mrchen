package com.wawaji.common.avalidations;

import android.text.TextUtils;

import com.wawaji.app.R;
import com.wawaji.common.notification.Notification;


/**
 * 图片验证码校验器
 * @since 2016.03.09
 * @author admin
 *
 */
public class ImageCodeValidation extends ValidationExecutor{

	@Override
	public boolean doValidate(String text) {
		if (TextUtils.isEmpty(text)) {
			Notification.showToastMsg(R.string.please_enter_code);
			return false;
		}
		return true;
	}

}