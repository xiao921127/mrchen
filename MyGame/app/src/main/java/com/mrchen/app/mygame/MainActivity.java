package com.mrchen.app.mygame;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.mrchen.app.mygame.base.TotalActivity;
import com.mrchen.app.mygame.view.CarrouselLayout;

public class MainActivity extends TotalActivity {

    private CarrouselLayout carrousel;
    private int width;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        carrousel= (CarrouselLayout) findViewById(R.id.carrousel);
        carrousel.setRotationX(-25);
        carrousel.refreshLayout();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
        carrousel.setR(width/3)//设置R的大小
                .setAutoRotation(false)//是否自动切换
                .setAutoRotationTime(1500);//自动切换的时间  单位毫秒

    }
}
