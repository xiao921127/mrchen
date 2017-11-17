package com.wawaji.app.contract;

import com.wawaji.common.base.BaseModel;
import com.wawaji.common.base.BasePresenter;
import com.wawaji.common.base.BaseView;

/**
 * 开始页面MVP架构
 *
 * @author admin
 * @version V1.0 <Splash>
 * @since 2016/6/24
 */
public interface SplashContract {

    /**
     * 视图
     */
    interface View extends BaseView{}

    /**
     * 控制器
     */
    interface Presenter extends BasePresenter {}

    /**
     * 模型
     */
    interface Model extends BaseModel {

        /**
         * 调用配置接口
         *
         * @param listener 接口回调
         */
        void onConfig(onConfigChangeListener listener);

    }

    /**
     * 配置接口回调
     */
    interface onConfigChangeListener {

        void onConfigSuccess();

        void onConfigFailure();

    }

}