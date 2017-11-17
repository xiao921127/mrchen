package com.mrchen.app.mygame;

import android.os.Bundle;
import android.widget.Button;

import com.mrchen.app.mygame.base.TotalActivity;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TengActivity extends TotalActivity {


    TXLivePlayer mLivePlayer;
    @Bind(R.id.video_view)
    TXCloudVideoView videoView;
    @Bind(R.id.tui)
    Button tui;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_teng;
    }

    @Override
    protected void initView() {
        //mPlayerView即step1中添加的界面view
        TXCloudVideoView mView = (TXCloudVideoView) findViewById(R.id.video_view);
        //创建player对象
        mLivePlayer = new TXLivePlayer(this);
        //关键player对象与界面view
        mLivePlayer.setPlayerView(videoView);
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        onPlay();
    }

    public void onPlay() {

        String flvUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP); //推荐FLV
    }
    @OnClick(R.id.tui)
    public void onViewClicked() {
        Jump(TuiLiuActivity.class);
    }
}
