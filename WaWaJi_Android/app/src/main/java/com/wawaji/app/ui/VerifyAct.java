package com.wawaji.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wawaji.app.ActivityTaskManager;
import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.contract.VerifyContract;
import com.wawaji.app.entity.VerifyEntity;
import com.wawaji.app.presenter.VerifyPresenter;
import com.wawaji.app.ui.widget.SecurityCodeView;
import com.wawaji.common.base.BaseActivity;
import com.wawaji.common.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 验证码页面
 * <p>
 * Created by m on 2017/3/28.
 */

public class VerifyAct extends BaseActivity implements VerifyContract.View, View.OnClickListener
        , SecurityCodeView.InputCompleteListener {

    @BindView(R.id.tv_actionbar_title)
    TextView mtv_title;//标题2

    @BindView(R.id.iv_actionbar_left)
    ImageView miv_back;//返回

    @BindView(R.id.tv_verify_title)
    TextView mtv_phone;

    @BindView(R.id.sv_verify_code)
    SecurityCodeView msv_code;

    @BindView(R.id.tv_verify_retrieve)
    TextView mtv_retrieve;//重新获取验证码(15s)

    @BindView(R.id.tv_verify_getcode)
    TextView mtv_get;//获取验证码

    @BindView(R.id.tv_verify_register)
    TextView mtv_register;

    private String mPhone;

    private String mInvite = "";

//    private SmsObserver smsObserver;

    private VerifyContract.Presenter mPresenter = new VerifyPresenter(this);

    private int mType = 0;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_verify);
        ButterKnife.bind(this);
        setImmersionStatus(R.id.layout);
    }

    @Override
    protected void setView() {
        setActivityName(VerifyAct.class.getSimpleName());
        mtv_title.setText(AppApplication.getStringById(R.string.enter_verify_code));
        miv_back.setImageResource(R.drawable.ic_back);
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("phone")) {
            mPhone = bundle.getString("phone");
        }
        if (bundle.containsKey("type")) {
            mType = bundle.getInt("type");
        }
        if (bundle.containsKey("invite")) {
            mInvite = bundle.getString("invite");
        }
        if (mType == 1) {
            mtv_register.setText(AppApplication.getStringById(R.string.confirm_login));
        }
        String htmlText = "<font color=#A1A3A9>验证码已发送手机：</font><font>" + CommonUtils.phoneFormat(mPhone) + "</font>";
        mtv_phone.setText(Html.fromHtml(htmlText));
        mPresenter.start();
        mtv_register.setClickable(false);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 200);

    }

    @Override
    protected void setListener() {
        mtv_retrieve.setOnClickListener(this);
        mtv_get.setOnClickListener(this);
        miv_back.setOnClickListener(this);
        mtv_register.setOnClickListener(this);
        msv_code.setInputCompleteListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
//            case R.id.tv_verify_retrieve:
//                break;//重新获取
            case R.id.tv_verify_getcode:
                mPresenter.onSendverify((new VerifyEntity(mPhone)));
                break;//获取
            case R.id.tv_verify_register:
                submit();
                break;
        }
    }

    @Override
    public void onTimerCancel() {
        mtv_retrieve.setVisibility(View.GONE);
        mtv_get.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTimer(String text) {
        if (mtv_retrieve.getVisibility() == View.GONE) {
            mtv_retrieve.setVisibility(View.VISIBLE);
            mtv_get.setVisibility(View.GONE);
        }
        mtv_retrieve.setText(text);
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
    public void closeSelf() {
        ActivityTaskManager.getInstance().closeAllActivityExceptOne(HomeAct.class.getSimpleName());
        finish();
    }

    /**
     * 检测input状态
     */
    private void checkInputStatus() {
        if (msv_code.getEditContent().length() != 4) {
            mtv_register.setClickable(false);
            mtv_register.setBackgroundResource(R.drawable.shape_verify_selected);
        } else {
            mtv_register.setClickable(true);
            mtv_register.setBackgroundResource(R.drawable.shape_verify_normal);
        }
    }

    @Override
    public void inputComplete() {
        checkInputStatus();
        submit();
    }

    @Override
    public void deleteContent(boolean isDelete) {
        checkInputStatus();
    }

    private void submit() {
        String text = msv_code.getEditContent();
        if (!TextUtils.isEmpty(text) && text.length() == 4) {
            mPresenter.onRegister(mPhone, text, mInvite);
        }
    }
}









