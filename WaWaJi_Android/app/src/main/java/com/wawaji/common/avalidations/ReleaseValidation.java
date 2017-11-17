package com.wawaji.common.avalidations;

import android.text.TextUtils;

import com.wawaji.app.R;
import com.wawaji.common.notification.Notification;



/**
 * 用户名校验器
 * 
 * @since 2016.03.09
 * @author admin
 * 
 */
public class ReleaseValidation extends ValidationExecutor {

	// 构造方法
	public ReleaseValidation(){}

	@Override
	public boolean doValidate(String text) {

		if (TextUtils.isEmpty(text)) {
			Notification.showToastMsg(R.string.please_enter_release_content);
			return false;
		} else if(text.length() < 1){
			Notification.showToastMsg(R.string.enter_content_promt);
			return false;
		}
		return true;
	}

}