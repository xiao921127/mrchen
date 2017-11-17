package com.mrchen.app.mygame.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mrchen.app.mygame.R;
import com.mrchen.app.mygame.base.BaseActivity;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeUIActivity extends BaseActivity {

    @Bind(R.id.video_view)
    TXCloudVideoView videoView;

    TXLivePlayer mLivePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ui);
        ButterKnife.bind(this);
        setImmersionStatus(new View(this),R.id.rootview);
        initView();

    }

    private void initView() {
        //mPlayerView即step1中添加的界面view
        TXCloudVideoView mView = (TXCloudVideoView) findViewById(R.id.video_view);
        //创建player对象
        mLivePlayer = new TXLivePlayer(this);
        //关键player对象与界面view
        mLivePlayer.setPlayerView(videoView);
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        onPlay();
    }
    public void onPlay() {

        String flvUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP); //推荐FLV
    }


}
