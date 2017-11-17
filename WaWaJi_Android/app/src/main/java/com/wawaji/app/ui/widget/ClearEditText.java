package com.wawaji.app.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.wawaji.app.R;


/**
 * 带清除按钮的输入框
 *
 * @author admin
 * @version V1.0 <输入框>
 * @since 2017.03.08
 */
@SuppressWarnings("unused")
@SuppressLint("AppCompatCustomView")
public class ClearEditText extends EditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearTextIcon; // 清除图标
    private TextWatcher mTextWatcher; //  输入框改变监听
    private OnTouchListener mOnTouchListener; // 触摸事件
    private OnFocusChangeListener mOnFocusChangeListener; // 焦点事件
    private OnDrawableClickListener mOnDrawableClick; // 清除按钮点击监听

    // 构造方法
    public ClearEditText(Context context) {
        super(context);
        initView();
    }

    // 构造方法
    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    // 构造方法
    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_edittext_clear);
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable); //Wrap the drawable so that it can be tinted pre Lollipop
        DrawableCompat.setTint(wrappedDrawable, getCurrentHintTextColor());
        mClearTextIcon = wrappedDrawable;
        mClearTextIcon.setBounds(0, 0, mClearTextIcon.getIntrinsicHeight() -10 , mClearTextIcon.getIntrinsicHeight() - 10);
        setClearIconVisible(false);
        super.addTextChangedListener(this);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
    }

    /**
     * 设置文本改变监听
     * @param textWatcher 监听器
     */
    public void setOnTextChangeListener(TextWatcher textWatcher){
        mTextWatcher = textWatcher;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (null != mOnFocusChangeListener) {
            mOnFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        if (mClearTextIcon.isVisible() && x > getWidth() - getPaddingRight() - mClearTextIcon.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setError(null);
                setText("");
            }
            return true;
        }
        return null != mOnTouchListener && mOnTouchListener.onTouch(view, motionEvent);
    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocused()) {
            setClearIconVisible(s.length() > 0);
        }
        if(null != mTextWatcher) {
            mTextWatcher.onTextChanged(s, start, before, count);
        }
    }

    /**
     * 隐藏清除图标
     * @param visible 是显示
     */
    private void setClearIconVisible(boolean visible) {
        mClearTextIcon.setVisible(visible, false);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(
                compoundDrawables[0],
                compoundDrawables[1],
                visible ? mClearTextIcon : null,
                compoundDrawables[3]);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(null != mTextWatcher) {
            mTextWatcher.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(null != mTextWatcher) {
            mTextWatcher.afterTextChanged(s);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (null != mOnDrawableClick) {
                    Drawable drawableLeft = getCompoundDrawables()[0];
                    if (null != drawableLeft && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        mOnDrawableClick.onDrawableClick();
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置清除按钮回调监听
     * @param listener 清除按钮回调
     */
    public void setOnDrawableClickListener(OnDrawableClickListener listener) {
        mOnDrawableClick = listener;
    }

    /**
     * 图片点击监听
     */
    @SuppressWarnings("WeakerAccess")
    public interface OnDrawableClickListener {

        void onDrawableClick();

    }

}