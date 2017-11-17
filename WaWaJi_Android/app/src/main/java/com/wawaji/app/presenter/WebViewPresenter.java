package com.wawaji.app.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.wawaji.app.ActivityTaskManager;
import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.config.StringConfig;
import com.wawaji.app.contract.WebViewContract;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.ui.HomeAct;
import com.wawaji.app.ui.LoginAct;
import com.wawaji.common.notification.Notification;
import com.wawaji.common.utils.CommonUtils;

/**
 * WebView 控制器
 *
 * @author admin
 * @version V1.0 <WebView>
 * @since 2016/07/18 10:32
 */
public class WebViewPresenter implements WebViewContract.Presenter {

    public static final String ACTION = "send.broadcast.action";
    private final WebViewContract.View mView; // WebView控件
    private final Handler mHandler = new Handler(Looper.getMainLooper()); // Handler线程
    private Context mContext;//上下文
    //    public int type = 0;
    private String mUrl;// 商品链接
    private String mItem_id;// 商品id
    private boolean mIs_refresh = false;//是否刷新webview

    /**
     * 构造方法
     *
     * @param view webview视图
     */
    public WebViewPresenter(WebViewContract.View view) {
        mView = view;
    }

    /**
     * 跳转个人中心
     */
    @android.webkit.JavascriptInterface
    public void jumpUcenter() {
        sendMessage(9);
    }

    /**
     * 跳转首页
     */
    @android.webkit.JavascriptInterface
    public void jumpIndex() {
        sendMessage(1);
    }

    private void sendMessage(int message) {
        Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.putExtra("message", message);
        mContext.sendBroadcast(intent);
        ActivityTaskManager.getInstance().closeAllActivityExceptOne(HomeAct.class.getSimpleName());
    }

    /**
     * 打开qq
     */
    @android.webkit.JavascriptInterface
    public void openQq(String qq_value) {
        if (CommonUtils.isAppInstalled(StringConfig.KEY_PACKAGE_QQ)) {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq_value;
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } else {
            Notification.showToastMsg(R.string.qq_uninstalled);
        }
    }

    /**
     * 拨打电话
     */
    @android.webkit.JavascriptInterface
    public void openPhone(String phone) {
        mPhone = phone;
        if (Build.VERSION.SDK_INT <= 22) {
            //小于等于22版本的直接
            call();
        } else {
            if (mView.requestPermission()) {
                call();
            }
        }
    }

    private String mPhone;//手机号

    @Override
    public void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + mPhone);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            mContext.startActivity(intent);
        }
    }

    /**
     * 关闭当前页面
     *
     * @param type 类型
     */
    @android.webkit.JavascriptInterface
    public void close(int type) {
        mView.closeActivity();
        mView.closeDialog();
    }

    /**
     * 跳转web页面
     *
     * @param url 链接
     */
    @android.webkit.JavascriptInterface
    public void jumpWeb(String url) {
        mView.onReload(url);
    }

    /**
     * 跳转登录页面
     *
     * @param param 参数
     */
    @android.webkit.JavascriptInterface
    public void open_login(String param) {
//        if (TextUtils.isEmpty(param)) {
//            mView.onStartAct(LoginAct.class);
//        } else {
//            Bundle bundle = new Bundle();
////            bundle.putInt("type", 3);
//            mView.onStartAct(LoginAct.class, bundle);
//        }
        mView.startAct(LoginAct.class, null);
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void onDestroy() {

    }

    /**
     * 修改昵称成功
     *
     * @param nickname 新的昵称
     */
    @android.webkit.JavascriptInterface
    public void editNicknameSuccess(final String nickname) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                UserEntity entity = UserEntity.getCurUser();
                entity.nickname = nickname;
                AppApplication.getInstance().setCurUser(entity);
                mView.closeActivity();
            }
        });
    }

    /**
     * 修改真实姓名成功
     *
     * @param name 真实姓名
     */
    @android.webkit.JavascriptInterface
    public void editNameSuccess(final String name) {
        UserEntity.getCurUser().nickname = name;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.closeActivity();
            }
        });
    }

//    /**
//     * 设置支付宝账号成功
//     *
//     * @param alipay 支付宝账号
//     */
//    @android.webkit.JavascriptInterface
//    public void editAlipaySuccess(final String alipay) {
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                UserEntity entity = UserEntity.getCurUser();
//                entity.alipay = alipay;
//                AppApplication.getInstance().setCurUser(entity);
//                mView.closeActivity();
//            }
//        });
//    }

//    private ShareDialog mShareDialog;
//
//    /**
//     * 获取分享内容
//     *
//     * @param title      分享标题
//     * @param content    分享内容
//     * @param url        分享链接
//     * @param share_img  分享图片
//     * @param share_type 分享类型
//     */
//    @android.webkit.JavascriptInterface
//    public void share_info(String title, String content, String url, String share_img, String share_type) {
//        boolean flag = mView.requestReadSdKaPermission();
//        if (!flag) {
//            return;
//        }
//        if (null == mShareDialog) {
//            mShareDialog = new ShareDialog(mContext, R.style.Dialog_Background_Transparency);
//            mShareDialog.setOnLoadingListener(this);
//            mShareDialog.setUMShareListener(mUMShareListener);
//        }
//        ShareEntity entity = new ShareEntity();
//        entity.content = content;
//        entity.url = url;
//        entity.title = title;
//        entity.img = share_img;
//        mShareDialog.setShareEntity(entity);
//        if (share_type.equalsIgnoreCase("all")) {
//            mShareDialog.showDialog();
//        } else if (share_type.equalsIgnoreCase(SHARE_MEDIA.QQ.toString())) {
//            onLoading();
//            mShareDialog.click(0);
//        } else if (share_type.equalsIgnoreCase(SHARE_MEDIA.QZONE.toString())) {
//            onLoading();
//            mShareDialog.click(1);
//        } else if (share_type.equalsIgnoreCase(SHARE_MEDIA.WEIXIN.toString())) {
//            onLoading();
//            mShareDialog.click(3);
//        } else if (share_type.equalsIgnoreCase(SHARE_MEDIA.SINA.toString())) {
//            onLoading();
//            mShareDialog.click(4);
//        } else {
//            onLoading();
//            mShareDialog.click(2);
//        }
//    }

//    /**
//     * 获取只分享图片内容
//     *
//     * @param share_img    分享图片
//     * @param share_type   分享类型
//     * @param img_original 源图片（大图）
//     */
//    @android.webkit.JavascriptInterface
//    public void share_info_new(String share_img, String share_type, String img_original) {
//        boolean flag = mView.requestReadSdKaPermission();
//        if (!flag) {
//            return;
//        }
//        if (null == mShareDialog) {
//            mShareDialog = new ShareDialog(mContext, R.style.Dialog_Background_Transparency);
//            mShareDialog.setOnLoadingListener(this);
//            mShareDialog.setUMShareListener(mUMShareListener);
//        }
//        ShareEntity entity = new ShareEntity();
//        entity.img = share_img;
//        entity.img_original = img_original;
//        entity.type = 1;
//        mShareDialog.setShareEntity(entity);
//        if (share_type.equalsIgnoreCase("all")) {
//            mShareDialog.showDialog();
//        } else if (share_type.equalsIgnoreCase(SHARE_MEDIA.QQ.toString())) {
//            onLoading();
//            mShareDialog.click(0);
//        } else if (share_type.equalsIgnoreCase(SHARE_MEDIA.QZONE.toString())) {
//            onLoading();
//            mShareDialog.click(1);
//        } else if (share_type.equalsIgnoreCase(SHARE_MEDIA.WEIXIN.toString())) {
//            onLoading();
//            mShareDialog.click(3);
//        } else if (share_type.equalsIgnoreCase(SHARE_MEDIA.SINA.toString())) {
//            onLoading();
//            mShareDialog.click(4);
//        } else {
//            onLoading();
//            mShareDialog.click(2);
//        }
//    }

    /**
     * 修改手机号成功
     *
     * @param phone 新的手机号
     */
    @android.webkit.JavascriptInterface
    public void editPhoneSuccess(String phone) {
        UserEntity.getCurUser().mobile = phone;
    }

//    /**
//     * 复制成功
//     *
//     * @param text    复制内容
//     * @param is_open 是否打开淘宝
//     * @param itemId  商品id
//     */
//    @android.webkit.JavascriptInterface
//    public void app_copy(String text, String is_open, String itemId) {
//
//        if (!TextUtils.isEmpty(text)) {
//            CommonUtils.setClipboardText(text);
//        }
//        if (is_open.equals("1")) {
//            if (UserEntity.isLogin()) {
//                if (CommonUtils.launchApp(StringConfig.KEY_PACKAGE_TAO)) {
//                    if (CommonUtils.launchApp(StringConfig.KEY_PACKAGE_TIAN)) {
//                        Notification.showToastMsg(R.string.taobao_and_tmall_uninstalled);
//                    }
//                }
//            } else {
//                Bundle bundle = new Bundle();
//                bundle.putString("itemId", itemId);
//                mView.onStartAct(LoginOverAct.class, bundle);
//            }
//        }
//    }

//    /**
//     * 填写邀请码成功
//     *
//     * @param invite_code 邀请码
//     */
//    @android.webkit.JavascriptInterface
//    public void fill_invite_code(String invite_code) {
//        if (!TextUtils.isEmpty(invite_code)) {
//            UserEntity.getCurUser().invite_code = invite_code;
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mView.closeActivity();
//                }
//            });
//        }
//    }

//    /**
//     * 绑定微信
//     *
//     * @param keyword 微信公众号
//     */
//    @android.webkit.JavascriptInterface
//    public void bindwechat(String keyword) {
//        mKeyword = keyword;
//        onLoading();
//        UMShareAPI.get(mContext).doOauthVerify((Activity) mContext, SHARE_MEDIA.WEIXIN, mUMAuthListener);
//    }

//    private String mKeyword;

//    /**
//     * 友盟授权回调
//     */
//    private UMAuthListener mUMAuthListener = new UMAuthListener() {
//
//        @Override
//        public void onStart(SHARE_MEDIA share_media) {
//
//        }
//
//        @Override
//        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//            //授权成功
//            SyncEntity entity = CommonUtils.getSyncEntity(share_media, map);
//            UserInfoUtil userInfoUtil = new UserInfoUtil(entity, SyncUserInfoListener);
//            userInfoUtil.startGetSysnInfo();
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//            WebViewPresenter.this.onComplete();
//            if (throwable.getMessage().contains("2008")) {
//                Notification.showToastMsg(R.string.app_uninstalled);
//            } else {
//                Notification.showToastMsg(R.string.auth_failure);
//            }
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA share_media, int i) {
//            WebViewPresenter.this.onComplete();
//        }
//    };
//
//    /**
//     * 第三方用户信息同步回调
//     */
//    private OnSyncUserInfoListener<SyncEntity> SyncUserInfoListener = new OnSyncUserInfoListener<SyncEntity>() {
//
//        @Override
//        public void onUserInfoSuccess(SyncEntity entity) {
//            new SyncModel().syscBind(entity, mSyscBindListener);
//        }
//
//        @Override
//        public void onUserInfoError() {
//            Notification.showToastMsg(R.string.get_sync_info_error);
//            onComplete();
//        }
//    };
//
//    /**
//     * 第三方绑定回调
//     */
//    private OnRequestChangeListener<String> mSyscBindListener = new OnRequestChangeListener<String>() {
//
//        @Override
//        public void onSuccess(String s) {
//            onComplete();
//            Notification.showToastMsg(R.string.bind_success);
//            mIs_refresh = true;
//            openwechat(mKeyword);
//        }
//
//        @Override
//        public void onFailure() {
//            onComplete();
//        }
//
//        @Override
//        public void onError() {
//            onComplete();
//        }
//    };


//    @Override
//    public void onLoading() {
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                mView.onLoading();
//            }
//        });
//    }
//
//    @Override
//    public void onComplete() {
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                mView.onComplete();
//            }
//        });
//    }
//
//    /**
//     * 友盟分享回调
//     */
//    private UMShareListener mUMShareListener = new UMShareListener() {
//
//        @Override
//        public void onStart(SHARE_MEDIA share_media) {
//
//        }
//
//        @Override
//        public void onResult(SHARE_MEDIA media) {
//            mView.executeJs("javascript:share_success()");
//            onComplete();
//            Notification.showToastMsg(/*media + */"分享成功啦");
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA media, Throwable throwable) {
//            onComplete();
//            Notification.showToastMsg(/*media + */"分享失败啦" + throwable.getMessage());
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA media) {
//            onComplete();
////        Notification.showToastMsg(media + " 分享取消了");
//        }
//    };


    /**
     * 跳转登录页面
     */
    private void checkLogin() {
        if (!UserEntity.isLogin()) {
            mView.startAct(LoginAct.class, null);
        }
    }


    @Override
    public void onClick(int type) {

    }
}