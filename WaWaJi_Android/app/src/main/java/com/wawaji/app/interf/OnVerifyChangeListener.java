package com.wawaji.app.interf;

/**
 * 发送验证码回调监听
 *
 * @version V1.0 <发送验证码回调>
 * @author admin
 * @since  2016/07/26 15:35
 */
public interface OnVerifyChangeListener {

    void showVoiceDialog(boolean is_show); // 显示语音验证码弹框

    void showImageDialog(String image); // 显示图片验证码弹框

    void onEncode(String key, String server_time); // 加密处理

    void onVerifySuccess(); // 请求成功

    void onVerifyFailure(); // 网络请求失败
}
