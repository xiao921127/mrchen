package com.mrchen.app.mygame.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;

import butterknife.ButterKnife;

public abstract class TotalActivity extends  BaseActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        ButterKnife.bind(this);
        initView();
    }

    abstract protected int provideContentViewId();

    abstract protected void initView();
}
