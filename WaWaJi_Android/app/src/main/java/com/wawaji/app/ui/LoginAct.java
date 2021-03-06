package com.wawaji.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.contract.LoginContract;
import com.wawaji.app.entity.VerifyEntity;
import com.wawaji.app.presenter.LoginPresenter;
import com.wawaji.app.ui.widget.ClearEditText;
import com.wawaji.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 登录注册页面
 * <p>
 * Created by admin on 2017/11/8.
 */

public class LoginAct extends BaseActivity implements LoginContract.View {

//    @BindView(R.id.tv_login_back)
//    TextView tvLoginBack;//返回按钮

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;//返回按钮

    @BindView(R.id.tv_login_title)
    TextView tvLoginTitle;//标题

    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;//注册按钮

    @BindView(R.id.et_login_phone)
    ClearEditText etLoginPhone;//手机号输入框

//    @BindView(R.id.et_login_password)
//    ClearEditText etLoginPassword;//密码输入框

    @BindView(R.id.tv_login_ok)
    TextView tvLoginOk;//登录按钮

    @BindView(R.id.tv_login_sms)
    TextView tvLoginSMS;//短信验证码登录

    @BindView(R.id.tv_login_protocol)
    TextView tvLoginProtocol;//用户协议

    @BindView(R.id.iv_login_wechat)
    ImageView ivLoginWechat;//微信登录

    @BindView(R.id.iv_login_qq)
    ImageView ivLoginQq;//qq登录

    @BindView(R.id.iv_login_sina)
    ImageView ivLoginSina;//新浪登录

    private LoginPresenter mPresenter = new LoginPresenter(this);

    private int type;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setImmersionStatus(R.id.layout);
    }

    @Override
    protected void setView() {
        setActivityName(LoginAct.class.getSimpleName());

        Bundle bundle = getIntent().getExtras();
        if (null != bundle && bundle.containsKey("type")) {
            type = bundle.getInt("type");
        }
        if (type == 1) {
            //注册
            String register = AppApplication.getStringById(R.string.register);
            tvLoginTitle.setText(register);
            tvLoginRegister.setVisibility(View.GONE);
//            etLoginPassword.setVisibility(View.GONE);
            tvLoginSMS.setVisibility(View.GONE);
            tvLoginOk.setText(register);
        } else if (type == 2) {
            //短信验证码登录
            String login = AppApplication.getStringById(R.string.login);
            tvLoginTitle.setText(login);
            tvLoginRegister.setVisibility(View.GONE);
//            etLoginPassword.setVisibility(View.GONE);
            tvLoginSMS.setVisibility(View.GONE);
        } else {
            tvLoginTitle.setText(R.string.login);
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.tv_login_ok, R.id.tv_login_sms, R.id.tv_login_protocol, R.id.iv_login_wechat
            , R.id.iv_login_qq, R.id.iv_login_sina, R.id.iv_login_back, R.id.tv_login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                //返回
                finish();
                break;
            case R.id.tv_login_register:
                //注册
                mPresenter.onClick(1);
                break;
            case R.id.tv_login_ok:
//                if (type == 0) {
//                    //手机号密码登录
//                    mPresenter.loginPhonePwd(etLoginPhone.getText().toString().trim(), "");
//                } else
                if (type == 1) {
                    //注册
                    VerifyEntity entity = new VerifyEntity();
                    entity.phone = etLoginPhone.getText().toString().trim();
                    entity.type = "register";
                    mPresenter.sendVerify(entity);
                } else if (type == 0 || type == 2) {
                    //短信验证码登录
                    VerifyEntity entity = new VerifyEntity();
                    entity.phone = etLoginPhone.getText().toString().trim();
                    entity.type = "login";
                    mPresenter.sendVerify(entity);
                }
                break;
            case R.id.tv_login_sms:
                //短信验证码登录
                mPresenter.onClick(2);
                break;
            case R.id.tv_login_protocol:
                mPresenter.onClick(3);
                break;
            case R.id.iv_login_wechat:
                break;
            case R.id.iv_login_qq:
                break;
            case R.id.iv_login_sina:
                break;
        }
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
    public void startAct(Class<?> cls, Bundle bundle) {
        startIntent(cls, bundle);
    }
}
