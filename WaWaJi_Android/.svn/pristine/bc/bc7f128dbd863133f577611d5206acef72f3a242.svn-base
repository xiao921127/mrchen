package com.wawaji.common.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 列表适配器
 *
 * @param <T> 列表数据类型
 * @author admin
 * @version 1.0
 * @since 2016/6/8
 */
@SuppressWarnings({"SameParameterValue", "WeakerAccess", "unused"})
public class BaseListAdapter<T> extends BaseAdapter {

    protected final Context mContext; // 上下文

    private LayoutInflater mInflater;

    protected ArrayList<T> mDatas; // = new ArrayList<T>(); // 数据列表

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public BaseListAdapter(Context context, ArrayList<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    /**
     * 获得 LayoutInflater
     * @return mInflater
     */
    protected LayoutInflater getLayoutInflater() {
        if (null == mInflater) {
            mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    /**
     * 设置列表数据
     *
     * @param data 数据
     */
    public void setData(ArrayList<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    /**
     * 获得列表数据
     *
     * @return mDatas
     */
    public ArrayList<T> getData() {
        return mDatas == null ? (mDatas = new ArrayList<>()) : mDatas;
    }

    /**
     * 添加列表Item数据（列表末尾）
     *
     * @param obj 对象
     */
    public void addItem(T obj) {
        if (mDatas != null) {
            mDatas.add(obj);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加列表Item数据（列表指定位置）
     *
     * @param pos 位置
     * @param obj 对象
     */
    public void addItem(int pos, T obj) {
        if (mDatas != null) {
            mDatas.add(pos, obj);
        }
        notifyDataSetChanged();
    }

    /**
     * 移除列表指定对象数据
     *
     * @param obj 对象
     */
    public void removeItem(T obj) {
        mDatas.remove(obj);
        notifyDataSetChanged();
    }

    /**
     * 清除列表数据
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (mDatas.size() > position) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position < 0) {
            position = 0; // 若列表没有数据，是没有footview/headview的
        }
        return getRealView(position, convertView, parent);
    }

    /**
     * @param position 位置
     * @param view 视图
     * @param parent 父控件
     * @return view
     */
    protected View getRealView(int position, View view, ViewGroup parent) {
        return null;
    }

    /**
     * 显示TextView
     *
     * @param textView 控件
     * @param text 文本
     * @param needGone 是否显示
     */
    protected void setText(TextView textView, String text, boolean needGone) {
        if (TextUtils.isEmpty(text)) {
            if (needGone) {
                textView.setVisibility(View.GONE);
            }
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
    }

    /**
     * 显示TextView
     *
     * @param textView 控件
     * @param text 文本
     * @param needGone 是否显示
     */
    protected void setText(TextView textView, int text, boolean needGone) {
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
     * @param text 文本
     */
    protected void setText(TextView textView, String text) {
        setText(textView, text, false);
    }

    /**
     * 显示TextView
     *
     * @param textView 控件
     * @param text 文本
     */
    protected void setText(TextView textView, int text) {
        setText(textView, text, false);
    }

    /**
     * 跳转页面
     *
     * @param cla 类名
     */
    public void startIntent(Class<?> cla) {
        Intent intent = new Intent(mContext, cla);
        mContext.startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param cla    类名
     * @param bundle 传递参数
     */
    public void startIntent(Class<?> cla, Bundle bundle) {
        Intent intent = new Intent(mContext, cla);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

}