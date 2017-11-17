package com.wawaji.common.base.dialog;

import android.view.View;

/**
 * 自定义Dialog接口
 *
 * @version V1.0 <自定义Dialog接口>
 * @author admin
 * @since 2016/7/14
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface DialogInterface {

    void setContentView(View view);

    void setContentView(int layoutResID);

    void setWidth(int width);

    void setHeight(int height);

    void setGravity(int gravity);

    void setAnimation(int anim);

    boolean isShowing();

    void showDialog();

    void dismissDialog();

    void initView();

    void setListener();
}
