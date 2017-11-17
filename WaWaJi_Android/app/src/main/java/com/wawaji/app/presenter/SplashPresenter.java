package com.wawaji.app.presenter;

import android.os.Handler;
import android.os.Looper;

import com.wawaji.app.contract.SplashContract;
import com.wawaji.app.model.ConfigModel;
import com.wawaji.app.ui.HomeAct;


/**
 * Splash控制器
 *
 * @author admin
 * @version V1.0 <Splash控制器>
 * @since 2016/6/24
 */
public class SplashPresenter implements SplashContract.Presenter, SplashContract.onConfigChangeListener {

    private final SplashContract.View mView; // 开始页面

    private ConfigModel mModel; // 配置模型

    private Handler mHandler = new Handler(Looper.getMainLooper()); // Handler线程

//    private Bundle mBundle;//数据

    /**
     * 构造方法
     *
     * @param view 开始页面
     */
    public SplashPresenter(SplashContract.View view) {
        mView = view;
        mModel = new ConfigModel();
        mModel.onConfig(this);
    }

//    @Override
//    public void setBundle(Bundle bundle) {
//        mBundle = bundle;
//    }

//    @Override
//    public void start() {
//        boolean isChecked = AppApplication.get(StringConfig.KEY_IS_MESSAGE, true);
//        if (isChecked) {
//            MiPushClient.setLocalNotificationType(AppApplication.sContext, -1);
//        } else {
//            MiPushClient.setLocalNotificationType(AppApplication.sContext, 0);
//        }
//        mModel_c.onConfig(this);
//    }
//
//    @Override
//    public void onStop() {
//        if (null != mModel_s) {
//            mModel_s.cancelRequest();
//            mModel_s = null;
//        }
//        if (null != mModel_c) {
//            mModel_c.cancelRequest();
//            mModel_c = null;
//        }
//        if (null != mHandler) {
//            mHandler.removeCallbacksAndMessages(null);
//            mHandler = null;
//        }
//    }

    @Override
    public void onConfigSuccess() {
//        if (mModel_s.isInstalled()) {
        delayedAct(HomeAct.class, 1000);
//        } else {
//            //如果是第一次安装 直接跳转到引导页面
//            delayedAct(GuideAct.class, 2 * 1000);
//        }
    }

    @Override
    public void onConfigFailure() {
//        if (mModel_s.isInstalled()) {
        delayedAct(HomeAct.class, 1000);
//        } else {
//            //如果是第一次安装 直接跳转到引导页面
//            delayedAct(GuideAct.class, 1000);
//        }
    }

    /**
     * 延时跳转页面
     *
     * @param cla 类名
     */
    private void delayedAct(final Class<?> cla, long time) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.startAct(cla, null);
            }
        }, time);
    }

    @Override
    public void onClick(int type) {

    }
}