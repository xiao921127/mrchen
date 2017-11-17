package com.wawaji.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.contract.SettingsContract;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.presenter.SettingsPresenter;
import com.wawaji.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置页面
 * <p>
 * Created by admin on 2017/10/25.
 */

public class SettingsAct extends BaseActivity implements SettingsContract.View {

    @BindView(R.id.iv_actionbar_logo)
    ImageView ivActionbarLogo;//图标

    @BindView(R.id.tv_actionbar_title)
    TextView tvActionbarTitle;//标题

    @BindView(R.id.iv_actionbar_left)
    ImageView ivActionbarLeft;//退出按钮

    @BindView(R.id.rl_settings_msg)
    RelativeLayout rlSettingsMsg;

    @BindView(R.id.rl_settings_invite)
    RelativeLayout rlSettingsInvite;//邀请有奖

    @BindView(R.id.rl_settings_invite_code)
    RelativeLayout rlSettingsInviteCode;//输入邀请码

    @BindView(R.id.cb_settings_bgm)
    CheckBox cbSettingsBgm;

    @BindView(R.id.cb_settings_sound)
    CheckBox cbSettingsSound;

    @BindView(R.id.rl_settings_feedback)
    RelativeLayout rlSettingsFeedback;//意见反馈

    @BindView(R.id.rl_settings_about_us)
    RelativeLayout rlSettingsAboutUs;

    @BindView(R.id.tv_settings_exit)
    TextView tvSettingsExit;//退出按钮

    private SettingsPresenter mPresenter = new SettingsPresenter(this);

    @Override
    protected void initView() {
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setImmersionStatus(R.id.layout);
    }

    @Override
    protected void setView() {
        setActivityName(SettingsAct.class.getSimpleName());
        ivActionbarLeft.setImageResource(R.drawable.ic_back);
        ivActionbarLogo.setVisibility(View.GONE);
        tvActionbarTitle.setVisibility(View.VISIBLE);
        tvActionbarTitle.setText(AppApplication.getStringById(R.string.settings));

        if (UserEntity.isLogin()) {
            tvSettingsExit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setListener() {
    }

    @OnClick({R.id.iv_actionbar_logo, R.id.tv_actionbar_title, R.id.iv_actionbar_left, R.id.rl_settings_msg
            , R.id.rl_settings_invite, R.id.rl_settings_invite_code, R.id.rl_settings_feedback
            , R.id.rl_settings_about_us, R.id.tv_settings_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_actionbar_logo:
                break;
            case R.id.tv_actionbar_title:
                break;
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.rl_settings_msg:
                break;
            case R.id.rl_settings_invite:
                mPresenter.onClick(2);
                break;
            case R.id.rl_settings_invite_code:
                mPresenter.onClick(3);
                break;
            case R.id.rl_settings_feedback:
                break;
            case R.id.rl_settings_about_us:
                break;
            case R.id.tv_settings_exit:
                mPresenter.onClick(1);
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
    public void logoutSuccess() {
        tvSettingsExit.setVisibility(View.GONE);
    }

    @Override
    public void startAct(Class<?> cls, Bundle bundle) {
        startIntent(cls,bundle);
    }
}
