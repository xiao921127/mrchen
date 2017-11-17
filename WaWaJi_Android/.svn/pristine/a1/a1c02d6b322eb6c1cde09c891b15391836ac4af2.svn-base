package com.wawaji.app.contract;


import com.wawaji.app.entity.VerifyEntity;
import com.wawaji.app.interf.OnImageCodeChangeListener;
import com.wawaji.app.interf.OnVerifyChangeListener;
import com.wawaji.common.base.BaseModel;

/**
 * 验证码MVP架构
 *
 * @author admin
 * @version V1.0 <验证码>
 * @since 2016/11/22 11:35
 */
public interface VerifyContract {

    interface Model extends BaseModel {

        void onSendVerify(VerifyEntity entity, final OnVerifyChangeListener l);

        void onRefreshImage(OnImageCodeChangeListener l);
    }

    interface View{

        void onTimerCancel();

        void onTimer(String text);

        void onLoading();

        void onComplete();

        void closeSelf();
    }

    interface Presenter {

        void start();

        void onClick(int type);

        void onSendverify(VerifyEntity verifyEntity);

        void onRegister(String phone, String text, String invite);

    }

}
