package com.wawaji.common.base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.wawaji.app.R;


/**
 * 基础Dialog
 *
 * @author admin
 * @version V1.0 <BaseDialog>
 * @since 2016/7/14 17:07
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseDialog implements DialogInterface {

    protected final Context mContext; // 上下文

    protected final Dialog mDialog; // 声明Dialog

    private View mView; // Dialog布局

    private final ViewGroup.LayoutParams mParams; // 布局参数

    protected OnButtonClickChangeListener mOnButtonClick;

    // 构造方法
    public BaseDialog(Context context) {
        this(context, R.style.Dialog_Common);
    }

    // 构造方法
    public BaseDialog(Context context, int style_id) {
        mContext = context;
        mDialog = new Dialog(mContext, style_id);
        mParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        initDialog();
        initView();
        setListener();
    }

    @Override
    public void setContentView(View view) {
        mView = view;
        mDialog.setContentView(mView, mParams);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mView = inflater.inflate(layoutResID, null);
        mDialog.setContentView(mView, mParams);
        Window window = mDialog.getWindow();
        if (null != window) {
            window.setBackgroundDrawableResource(R.color.white_transparent);
        }
    }

    @Override
    public void setWidth(int width) {
        mParams.width = width;
    }

    @Override
    public void setHeight(int height) {
        mParams.height = height;
    }

    @Override
    public void setGravity(int gravity) {
        Window window = mDialog.getWindow();
        if (null != window) {
            window.setGravity(gravity);
        }
    }

    @Override
    public void setAnimation(int anim) {
        Window w = mDialog.getWindow();
        if (w != null) {
            w.setWindowAnimations(anim);
        }
    }

    @Override
    public void showDialog() {
        if (!((Activity) mContext).isFinishing()) {
            mDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (mDialog.isShowing() && !((Activity) mContext).isFinishing())
            mDialog.dismiss();
    }

    @Override
    public boolean isShowing() {
        return null != mDialog && mDialog.isShowing();
    }

    // 初始化弹框
    protected abstract void initDialog();

    /**
     * 设置Button点击
     *
     * @param l 点击监听
     */
    public void setOnButtonClickChangeListenr(OnButtonClickChangeListener l) {
        this.mOnButtonClick = l;
    }

    /**
     * 点击监听
     */
    public interface OnButtonClickChangeListener {

        void onClickCancel();

        void onClickSure();

        void onClickCopy();

    }

}