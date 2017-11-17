package com.wawaji.app.contract;

import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.entity.VerifyEntity;
import com.wawaji.app.interf.OnRequestChangeListener;
import com.wawaji.common.base.BaseModel;
import com.wawaji.common.base.BasePresenter;
import com.wawaji.common.base.BaseView;

/**
 * 登录注册MVP架构
 * <p>
 * Created by admin on 2017/11/8.
 */

public interface LoginContract {

    /**
     * 视图
     */
    interface View extends BaseView{

        void onLoading();

        void onComplete();

    }

    /**
     * 控制器
     */
    interface Presenter extends BasePresenter {

        /**
         * 手机号码密码登录
         *
         * @param phone 手机号
         * @param pwd   密码
         */
        void loginPhonePwd(String phone, String pwd);

        /**
         * 发送验证码
         *
         * @param entity 验证码数据
         */
        void sendVerify(VerifyEntity entity);
    }

    /**
     * 模型
     */
    interface Model extends BaseModel {

        /**
         * 手机号密码登录
         *
         * @param phone    手机号
         * @param pwd      密码
         * @param listener 回调
         */
        void loginPhonePwd(String phone, String pwd, OnRequestChangeListener<UserEntity> listener);

        /**
         * 手机号登录或注册
         *
         * @param mobile 手机号
         * @param verify 验证码
         * @param listener 回调
         */
        void userPhoneLogin(String mobile,String verify,OnRequestChangeListener<String> listener);

        /**
         * 用户资料刷新
         */
        void refreshInfo();

    }

}
