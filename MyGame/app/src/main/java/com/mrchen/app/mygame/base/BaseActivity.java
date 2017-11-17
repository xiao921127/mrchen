package com.mrchen.app.mygame.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mrchen.app.mygame.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/01 09:12
 */

public class BaseActivity extends AppCompatActivity {
    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
//    private static final String LAYOUT_TABLAYOUT = "TabLayout";
//    protected TranslateAnimation mShowAction;
//    protected TranslateAnimation mShowActionV;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        setImmersionStatus();
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }
        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }
        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }
//        if (name.equals(LAYOUT_TABLAYOUT)) {
//            view = new AutoRelativeLayout(context, attrs);
//        }
        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏 SCREEN_ORIENTATION_LANDSCAPE(横屏)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
    }

    /**
     * 沉侵状态栏
     */
    public void setImmersionStatus(View view,int id) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
        // 生成一个状态栏大小的矩形
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.TRANSPARENT);
        // 添加 statusView 到布局中
        ViewGroup  rootView = (ViewGroup ) ((ViewGroup ) findViewById(id));
        rootView.addView(view, 0);// addView(ViewGroup view, index);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }
    /**
     * 界面跳转
     */
    public void Jump(Class<? extends Activity> start_class) {
        Intent intent = new Intent(BaseActivity.this, start_class);
        startActivity(intent);
    }

    /**
     * 服务器提示语
     *
     * @param text
     */
    public void Prompt(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }

    /**
     * 服务器提示语
     *
     * @param text
     */
    public void mPrompt(final Context context, final String text) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });

    }
}