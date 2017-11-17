package com.wawaji.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 基础Fragment
 *
 * @author admin
 * @version V1.0 <集成Fragment>
 * @since 2016/6/16
 */
@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
public abstract class BaseFragment extends Fragment {

    protected Context mContext = null;
    protected boolean isAppear = false;
    private boolean isContainer = false;

    private View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isContainer) {
            rootView = initView(inflater, container);
            setView();
            setListener();
            return rootView;
        }
        if (rootView == null) {
            rootView = initView(inflater, container);
            setView();
            setListener();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        if (null != rootView) {
            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 获得布局
     */
    protected void setIsContainer(boolean is) {
        isContainer = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isAppear = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isAppear = true;
    }

    /**
     * 跳转页面
     *
     * @param cla 类名
     */
    protected void startIntent(Class<?> cla) {
        Intent intent = new Intent(mContext, cla);
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param cla    类名
     * @param bundle 传递参数
     */
    protected void startIntent(Class<?> cla, Bundle bundle) {
        Intent intent = new Intent(mContext, cla);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示TextView
     *
     * @param textView 控件
     * @param text     文本
     * @param needGone 是否隐藏
     */
    protected void setText(TextView textView, String text, boolean needGone) {
        if (null == textView) {
            return;
        }
        if (TextUtils.isEmpty(text)) {
            if (needGone) {
                textView.setVisibility(View.GONE);
            }
        } else {
            textView.setText(text);
        }
    }

    /**
     * 显示TextView
     *
     * @param textView 控件
     * @param text     文本
     * @param needGone 是否隐藏
     */
    protected void setText(TextView textView, int text, boolean needGone) {
        if (null == textView) {
            return;
        }
        String str = String.valueOf(text);
        if (TextUtils.isEmpty(str)) {
            if (needGone) {
                textView.setVisibility(View.GONE);
            }
        } else {
            textView.setText(str);
        }
    }

    /**
     * 显示TextView
     *
     * @param textView 控件
     * @param text     文本
     */
    protected void setText(TextView textView, String text) {
        setText(textView, text, false);
    }

    /**
     * 显示TextView
     *
     * @param textView 控件
     * @param text     文本
     */
    protected void setText(TextView textView, int text) {
        setText(textView, text, false);
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    protected abstract void setView();

    protected abstract void setListener();

}