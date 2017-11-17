package com.wawaji.app.contract;

import android.content.Context;

import com.wawaji.common.base.BasePresenter;
import com.wawaji.common.base.BaseView;


/**
 * WebView MVP架构
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2016/07/18 09:53
 */
public interface WebViewContract {

    @SuppressWarnings("unused")
    interface View extends BaseView{

        void closeActivity();

//        void onStartAct(Class<?> cla);
//
//        void onStartAct(Class<?> cla, Bundle bundle);

        void onLoading();

        void onComplete();

        void onReload(String url);

        void loadJs(String js);

        void closeDialog();

        void onRefresh();

        void executeJs(String js);

        void closeSelf();

        boolean requestPermission();

        boolean requestReadSdKaPermission();

    }

    @SuppressWarnings("unused")
    interface Presenter extends BasePresenter {

        void setContext(Context mContext);

        void onDestroy();

        void call();

    }

}