package com.wawaji.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.wawaji.app.AppApplication;


/**
 * 输入法键盘工具类
 *
 * @author admin
 * @since 2017/02/22 09:33
 * @version V1.0 <控制输入法键盘>
 */
public final class KeyboardUtils {

    // 私有化构造方法
    private KeyboardUtils() {
        // This utility class is not publicly instantiable
    }

    /**
     * 隐藏输入法
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (null == view) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示输入法
     */
    public static void showSoftInput(EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) edit.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, 0);
    }

    /**
     * 如果输入法打开则关闭，如果没打开则打开
     */
    @SuppressWarnings("unused")
    public static void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) AppApplication.sContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

}
