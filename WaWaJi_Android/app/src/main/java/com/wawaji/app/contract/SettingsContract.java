package com.wawaji.app.contract;

import com.wawaji.app.interf.OnRequestChangeListener;
import com.wawaji.common.base.BaseModel;
import com.wawaji.common.base.BasePresenter;
import com.wawaji.common.base.BaseView;

/**
 * 设置页面MVP架构
 * <p>
 * Created by admin on 2017/10/25.
 */

public interface SettingsContract {

    interface View extends BaseView{

        void onLoading();

        void onComplete();

        /**
         * 退出成功
         */
        void logoutSuccess();

    }

    interface Presenter extends BasePresenter {

    }

    interface Model extends BaseModel {

        /**
         * 退出登录
         *
         * @param listener 回调
         */
        void logout(OnRequestChangeListener<String> listener);

    }

}
