package com.wawaji.common.base;

import android.os.Bundle;

/**
 *
 * 基础View
 *
 * @version V1.0 <基础View>
 * @author admin
 * @since  2016/7/8
 */
public interface BaseView {

//    void onStartAnim();
//
//    void onStopAnim();

    /**
     * 跳转页面
     *
     * @param cls 页面class
     * @param bundle 数据
     */
    void startAct(Class<?> cls, Bundle bundle);

}
