package com.wawaji.app.interf;


import com.wawaji.app.entity.VerifyEntity;

/**
 * 图片验证码回调接口
 *
 * @version V1.0 <图片验证码回调接口>
 * @author admin
 * @since 2016/07/26 16:18
 */
public interface OnImageCodeChangeListener {

    void onImageSuccess(VerifyEntity entity);

    void onImageFailure();

}
