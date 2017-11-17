package com.wawaji.app.ui.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.common.avalidations.EditTextValidator;
import com.wawaji.common.avalidations.ImageCodeValidation;
import com.wawaji.common.avalidations.ValidationModel;
import com.wawaji.common.base.dialog.BaseDialog;
import com.wawaji.common.utils.CommonUtils;



/**
 * 图片验证码弹框
 *
 * @author admin
 * @version V1.0 <验证码>
 * @since 2016/07/25 17:28
 */
@SuppressWarnings("unused")
public class VerifyCodeDialog extends BaseDialog implements View.OnClickListener {

    private ImageView miv_image; // 验证码图片

    private EditText met_code; // 验证码输入框

    private Button mbt_code; // 确定

    private EditTextValidator mValidator; // 校验器

    private OnCodeClickListener mOnCodeListener; // 验证码点击监听

    private Bitmap mBitmap; // 验证码图片

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public VerifyCodeDialog(Context context) {
        super(context);
    }

    @Override
    protected void initDialog() {
        int width = (int) (AppApplication.getDisplayWidth() * 0.8);
        setWidth(width);
        setContentView(R.layout.view_dialog_verify);
        setGravity(Gravity.CENTER);
        mDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void initView() {
        miv_image = (ImageView) mDialog.findViewById(R.id.iv_dialog_verify_code);
        met_code = (EditText) mDialog.findViewById(R.id.et_dialog_verify_code);
        mbt_code = (Button) mDialog.findViewById(R.id.bt_dialog_verify_code);

        mValidator = new EditTextValidator(mbt_code)
                .add(new ValidationModel(met_code, new ImageCodeValidation()))
                .execute();
    }

    @Override
    public void setListener() {
        miv_image.setOnClickListener(this);
        mbt_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.bt_dialog_verify_code == id) {
            if (null != mOnCodeListener && mValidator.validate()) {
                String code = met_code.getText().toString();
                mOnCodeListener.onSure(code);
            }
        } else if (R.id.iv_dialog_verify_code == id) {
            if (null != mOnCodeListener) {
                miv_image.setClickable(false);
                mOnCodeListener.onRefreshImage();
            }
        }
    }

    @Override
    public void dismissDialog() {
        if (null != met_code) {
            met_code.setText("");
        }
        super.dismissDialog();
    }

    /**
     * 设置图片验证码弹框点击监听
     *
     * @param l 监听
     */
    public void setClickListener(OnCodeClickListener l) {
        mOnCodeListener = l;
    }

    /**
     * 设置验证码图片
     *
     * @param str 字符串
     */
    public void setCodeImage(String str) {
        if (null == miv_image) {
            return;
        }
        miv_image.setClickable(true);
        if (!TextUtils.isEmpty(str)) {
            if (null != mBitmap) {
                mBitmap.recycle();
                mBitmap = null;
            }
            try {
                mBitmap = CommonUtils.base64ToBitmap(str);
                miv_image.setImageBitmap(mBitmap);
                miv_image.setClickable(true);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 验证码点击监听
     */
    public interface OnCodeClickListener {

        void onRefreshImage();

        void onSure(String code);
    }

}