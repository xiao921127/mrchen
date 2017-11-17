package com.wawaji.common.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wawaji.app.AppApplication;
import com.wawaji.app.R;


/**
 * Toast弹框
 *
 * @version V1.0 <显示Toast消息>
 * @author admin
 * @since 2016/7/8
 */

@SuppressWarnings("WeakerAccess")
public class AppToast {

    private static Toast mToast; // Toast

    private static long showTime = 0; // 上次显示时间

    @SuppressLint("StaticFieldLeak")
    private static TextView mText;

    //静态方法
    public static void showText(CharSequence text) {
        if(TextUtils.isEmpty(text)){
            return;
        }

        if(null == mToast){
            LayoutInflater layoutInflater = (LayoutInflater) AppApplication.sContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            FrameLayout root = new FrameLayout(AppApplication.sContext);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            root.setLayoutParams(params);
            //由layout文件创建一个View对象
            View layout = layoutInflater.inflate(R.layout.view_toast, root, false);

            //实例化TextView对象
            mText = (TextView) layout.findViewById(R.id.tv_my_toast);
            mToast = new Toast(AppApplication.sContext);
            mToast.setView(layout);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mText.setText(text);
        long curTime = System.currentTimeMillis();
        if (showTime + 800 <= curTime){
            showTime = curTime;
            mToast.show();
        }
    }
}
