package com.wawaji.common.avalidations;

import android.text.TextUtils;

import com.wawaji.app.R;
import com.wawaji.common.notification.Notification;


/**
 * 用户名校验器
 *
 * @author admin
 * @since 2016.03.09
 */
public class UserNameValidation extends ValidationExecutor {

    @Override
    public boolean doValidate(String text) {

        if (TextUtils.isEmpty(text)) {
            Notification.showToastMsg(R.string.please_enter_account);
            return false;
        }
        return true;
    }

}