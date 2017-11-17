package com.wawaji.common.base.dialog;

import android.content.Context;
import android.view.Gravity;

import com.wawaji.app.R;
import com.wawaji.common.utils.CommonUtils;


/**
 * 加载中弹框
 *
 * @version V1.0 <加载中>
 * @author admin
 * @since 2016/07/15 10:48
 */
@SuppressWarnings("SameParameterValue")
public class LoadingDialog extends BaseDialog {

    // 构造方法
    public LoadingDialog(Context context) {
        this(context, R.style.Dialog_Loading);
    }

    // 构造方法
    private LoadingDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }

    @Override
    protected void initDialog() {
        setWidth(CommonUtils.dip2px(100));
        setHeight(CommonUtils.dip2px(100));
        setContentView(R.layout.view_dialog_loading);
        setGravity(Gravity.CENTER);
        mDialog.setCanceledOnTouchOutside(false); // 触摸不消失
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }

}