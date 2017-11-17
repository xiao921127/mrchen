package com.wawaji.app.presenter;

import android.os.Bundle;

import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.contract.SettingsContract;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.interf.OnRequestChangeListener;
import com.wawaji.app.model.SettingsModel;
import com.wawaji.app.ui.LoginAct;
import com.wawaji.app.ui.WebAct;


/**
 * 设置控制器
 * <p>
 * Created by admin on 2017/11/10.
 */

public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View mView;

    private SettingsModel mModel;

    public SettingsPresenter(SettingsContract.View view) {
        mView = view;
        mModel = new SettingsModel();
    }

    @Override
    public void onClick(int type) {
        Bundle bundle;
        if (!UserEntity.isLogin() && (type == 2 || type == 3)) {
            mView.startAct(LoginAct.class, null);
            return;
        }
        switch (type) {
            case 1://退出登录
                mView.onLoading();
                mModel.logout(mLogoutListener);
                break;
            case 2://邀请奖励
                bundle = new Bundle();
                bundle.putString("url", AppApplication.getStringById(R.string.url_invitation));
                mView.startAct(WebAct.class, bundle);
                break;
            case 3://输入邀请码
                bundle = new Bundle();
                bundle.putString("url", AppApplication.getStringById(R.string.url_fill_invite_code));
                mView.startAct(WebAct.class, bundle);
                break;
        }
    }

    /**
     * 退出登录回调
     */
    private OnRequestChangeListener<String> mLogoutListener = new OnRequestChangeListener<String>() {
        @Override
        public void onSuccess(String s) {
            mView.onComplete();
            mView.logoutSuccess();
        }

        @Override
        public void onFailure() {
            mView.onComplete();
        }

        @Override
        public void onError() {
            mView.onComplete();
        }
    };
}
