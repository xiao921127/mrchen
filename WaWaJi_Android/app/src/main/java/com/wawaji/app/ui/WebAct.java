package com.wawaji.app.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wawaji.app.AppApplication;
import com.wawaji.app.AppConfig;
import com.wawaji.app.R;
import com.wawaji.app.contract.WebViewContract;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.presenter.WebViewPresenter;
import com.wawaji.common.base.BaseActivity;
import com.wawaji.common.notification.Notification;
import com.wawaji.common.utils.CommonUtils;
import com.wawaji.common.utils.DeviceUtils;
import com.wawaji.common.utils.KeyboardUtils;
import com.wawaji.common.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wawaji.common.https.api.RequestConfig.DEFAULT_API_URL;


/**
 * 网页页面
 *
 * @author admin
 * @version V1.0 <显示网页>
 * @since 2016/7/18
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class WebAct extends BaseActivity implements View.OnClickListener, WebViewContract.View {

    @BindView(R.id.iv_actionbar_left)
    ImageView miv_back; // 返回按钮

    @BindView(R.id.tv_actionbar_title)
    TextView mtv_title; // 标题

//    @BindView(R.id.iv_actionbar_right)
//    ImageView miv_right; // 右侧按钮

    @BindView(R.id.pb_webview_progress)
    ProgressBar mProgressBar; // 进度条

    @BindView(R.id.ll_webview_content)
    LinearLayout mll_content; // 页面主体

//    @BindView(R.id.tv_actionbar_back)
//    TextView mtv_back;//返回文字按钮

//    @BindView(R.id.tv_actionbar_close)
//    TextView mtv_close;//关闭按钮

//    @BindView(R.id.iv_content_animation)
//    SimpleDraweeView mSimpleDraweeView; // 动画

//    @BindView(R.id.ll_content_animation)
//    LinearLayout mll_animation; // 显示动画部分

//    @BindView(R.id.tv_content_promt)
//    TextView mtv_promt; // 显示没数据时提示

//    @BindView(R.id.ll_content_refresh)
//    LinearLayout mll_refresh; // 刷新

    private WebView mWebView; // 网页内容

//    private DraweeController mDraweeController; // 设置动态图片

    private final WebViewPresenter mPresenter = new WebViewPresenter(this);

    private boolean mIs_refresh = false;

    private boolean mCanGoBack = true;

    private boolean mIsJDPage = false;//是否是京东页面

    private String mJs_hide_banner;//隐藏页面顶部banner广告

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mWebView && mIs_refresh && isForeground(WebAct.class.getName())) {
            mIs_refresh = false;
            mWebView.loadUrl("javascript:if(app_load != undefined && typeof(app_load)=='function'){app_load();}");
        } else {
            mIs_refresh = true;
        }
    }

    /**
     * *
     * 判断某个界面是否在前台
     *
     * @param className 某个界面名称
     * @return 返回true在前台，false不在前台
     */
    public static boolean isForeground(String className) {
        if (TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) AppApplication.sContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equalsIgnoreCase(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mWebView) {
            WebSettings settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(false);
            mll_content.removeAllViews();
            mWebView.stopLoading();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        mPresenter.onDestroy();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        mPresenter.setContext(this);
    }

    @Override
    public void setView() {
        mtv_title.setText("");
        miv_back.setImageResource(R.drawable.ic_back);
//        miv_right.setVisibility(View.VISIBLE);
//        miv_right.setImageResource(R.drawable.ic_web_refresh);
//        int pading = CommonUtils.dip2px(12);
//        miv_right.setPadding(pading, pading, pading, pading);
        initWebView();

        // 把页面加入Activity管理类
        String aname = WebAct.class.getSimpleName();
        setActivityName(aname);

        // 网页加载进度条设置
        mProgressBar.setIndeterminate(false);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
    }


    /**
     * 初始化WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("WrongConstant")
    private void initWebView() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mWebView = new WebView(this);
        mll_content.addView(mWebView, params);

        WebSettings settings = mWebView.getSettings();
        // 修改ua使得web端正确判断
        String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + "; KeLeZhuan_android/"
                + DeviceUtils.getVersionCode());
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        // settings.setRenderPriority(RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启本地缓存
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            settings.setLoadsImagesAutomatically(false);
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mWebView.setScrollBarStyle(0);

        mWebView.addJavascriptInterface(mPresenter, "app");
        mWebView.setWebViewClient(new android.webkit.WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mIsJDPage && !TextUtils.isEmpty(mJs_hide_banner) && null != mHandler) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.loadUrl(mJs_hide_banner);
                        }
                    }, 500);
                }
                if (null != mWebView && !mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url) && !url.endsWith("#isself")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    startIntent(WebAct.class, bundle);
                    return true;
                }
                return false;
            }
        });
        mWebView.setWebChromeClient(new AppWebChromeClient());

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            String mUrl = bundle.getString("url");
            String domain = AppApplication.get(AppConfig.APP_API_URL, null);
            if (TextUtils.isEmpty(domain)) {
                domain = DEFAULT_API_URL;
            }
            mUrl = domain + mUrl;
            HashMap<String, String> extraHeaders = new HashMap<>();
            if (bundle.containsKey("canGoBack")) {
                mCanGoBack = bundle.getBoolean("canGoBack", true);
            }
            // 添加用户和设备参数
            JSONObject jsonObject = new JSONObject();
            String test_url = mUrl;
            try {
                jsonObject.put(AppConfig.HEADER_VERSION_KEY, DeviceUtils.getVersionCode());
                jsonObject.put(AppConfig.HEADER_TARGET_KEY, AppConfig.APP_TARGET_OS);
                jsonObject.put(AppConfig.HEADER_DEVICE_KEY, DeviceUtils.getDeviceId());
                jsonObject.put(AppConfig.HEADER_SCREEN_KEY, AppApplication.getDisplaySize());
                jsonObject.put(AppConfig.HEADER_CHANNEL_KEY, DeviceUtils.getAppMetaData(AppConfig.META_DATA));
                jsonObject.put(AppConfig.HEADER_TIME_KEY, System.currentTimeMillis() / 1000);
                if (UserEntity.isLogin()) {
                    UserEntity user = UserEntity.getCurUser();
                    jsonObject.put("uid", user.uid);
                    jsonObject.put("sid", user.sid);
                    test_url = test_url + "&uid=" + user.uid;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogUtils.e("test_url ：" + test_url);
            extraHeaders.put(AppConfig.HEADER_LOGIN_KEY, jsonObject.toString());
            mWebView.loadUrl(mUrl, extraHeaders);
        }
    }

    @Override
    public void setListener() {
        miv_back.setOnClickListener(this);
        mll_content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                if (mWebView.canGoBack() && mCanGoBack) {
                    mWebView.goBack();
                }
                break;
            case R.id.iv_actionbar_right:
                mWebView.reload();
                break;
        }
    }

    @Override
    public void closeActivity() {
        closeSelf();
    }

    @Override
    public void onLoading() {
        createDlg();
    }

    @Override
    public void onComplete() {
        closeDlg();
    }

    @Override
    public void onReload(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        startIntent(WebAct.class, bundle);
        finish();

    }

    @Override
    public void startAct(Class<?> cls, Bundle bundle) {
        startIntent(cls,bundle);
    }

    /**
     * 内部类 用于显示加载进度
     */
    private class AppWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (!mIsJDPage && !TextUtils.isEmpty(title)) {
//                mtv_title.setText(title);
                setText(mtv_title, title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                mProgressBar.setProgress(100); // 网页加载完成
            } else {
                mProgressBar.setProgress(newProgress);
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            Notification.showToastMsg(message);
            return true;
        }
    }

    @Override
    public void loadJs(String js) {
        mWebView.loadUrl("javascript:share_success()");
    }

    @Override
    public void closeDialog() {

    }

    @Override
    public void onRefresh() {
        mWebView.reload();
    }

    @Override
    public void executeJs(String js) {
        mWebView.loadUrl(js);
    }

    @Override
    public void closeSelf() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean requestPermission() {
        //打电话权限  true表示没有该权限
        boolean permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED;
        if (permission) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
        } else {
            return true;
        }
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean requestReadSdKaPermission() {
        //肚饿写Sd卡  true表示没有该权限
        boolean permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        if (permission) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (CommonUtils.checkGrantResults(grantResults)) {
                    mPresenter.call();
                } else {
                    Notification.showToastMsg(R.string.call_permission_request_cancel);
                }
                return;
            }
            default:
                break;

        }
    }

}