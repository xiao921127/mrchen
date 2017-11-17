package com.wawaji.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.contract.SplashContract;
import com.wawaji.app.presenter.SplashPresenter;
import com.wawaji.common.base.BaseActivity;


/**
 * 开屏页面
 *
 * @author admin
 * @version V1.0 <开屏页面>
 * @since 2016/6/24
 */
public class SplashAct extends BaseActivity implements SplashContract.View {

    private SplashPresenter mPresenter = new SplashPresenter(this); // 控制器

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (null != mPresenter) {
//            mPresenter.onStop();
//            mPresenter = null;
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(null != mPresenter) {
//            mPresenter.start();
//        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return true;
//    }

    @Override
    protected void initView() {
        AppApplication.saveDisplaySize(this);
        View view = new View(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setBackgroundResource(R.drawable.ic_splash);
        setContentView(view);
    }

    @Override
    protected void setView() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setActivityName(SplashAct.class.getSimpleName());
        setIs_theme(false);
//        Bundle bundle = getIntent().getExtras();
//        if(null != bundle) {
//            mPresenter.setBundle(bundle);
//        }
    }

    @Override
    protected void setListener() {
    }

    @Override
    public void startAct(Class<?> cls, Bundle bundle) {
        startIntent(cls,bundle);
        finish();
        overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha);
    }
}