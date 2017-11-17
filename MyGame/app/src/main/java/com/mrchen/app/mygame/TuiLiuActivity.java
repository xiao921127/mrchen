package com.mrchen.app.mygame;

import android.os.Bundle;

import com.mrchen.app.mygame.base.TotalActivity;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TuiLiuActivity extends TotalActivity {

    @Bind(R.id.video_view)
    TXCloudVideoView videoView;

    TXLivePushConfig  mLivePushConfig;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_tui_liu;
    }

    @Override
    protected void initView() {
        TXLivePusher mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePusher.setConfig(mLivePushConfig);

        String rtmpUrl = "rtmp://2157.livepush.myqcloud.com/live/xxxxxx";
        mLivePusher.startPusher(rtmpUrl);
        mLivePusher.startCameraPreview(videoView);
    }

}
