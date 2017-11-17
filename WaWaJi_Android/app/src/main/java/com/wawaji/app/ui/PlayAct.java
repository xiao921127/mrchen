package com.wawaji.app.ui;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.app.contract.PlayContract;
import com.wawaji.app.entity.PlayerRecordEntity;
import com.wawaji.app.entity.RoomDetailsEntity;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.presenter.PlayPresenter;
import com.wawaji.app.ui.adapter.PlayerRecordAdapter;
import com.wawaji.app.ui.dialog.CaughtDialog;
import com.wawaji.app.ui.dialog.CaughtNoDialog;
import com.wawaji.app.ui.dialog.InsufficientAmountDialog;
import com.wawaji.app.ui.dialog.Popup;
import com.wawaji.common.base.BaseActivity;
import com.wawaji.common.notification.Notification;
import com.wawaji.common.utils.ImageSetting;
import com.wawaji.common.utils.LogUtils;
import com.wawaji.common.xlistview.XListView;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * 游戏页面
 * <p>
 * Created by admin on 2017/10/23.
 */

public class PlayAct extends BaseActivity implements PlayContract.View, View.OnTouchListener {

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

    @BindView(R.id.iv_actionbar_left)
    ImageView ivActionbarLeft;//返回按钮

    @BindView(R.id.iv_actionbar_right)
    ImageView ivActionbarRight;//游戏记录

    @BindView(R.id.vv_play_play)
    TXCloudVideoView vvPlayPlay;// 游戏画面2

    @BindView(R.id.vv_play_camera)
    TXCloudVideoView vvPlayCamera;//玩家摄像头

    @BindView(R.id.iv_play_msg)
    ImageView ivPlayMsg;//发送消息

    @BindView(R.id.iv_play_start)
    ImageView ivPlayStart;//开始游戏

    @BindView(R.id.tv_play_coins)
    TextView tvPlayCoins;//我的金币

    @BindView(R.id.lv_play_record)
    XListView lvPlayRecord;//最近抓中记录

    @BindView(R.id.barrage)
    DanmakuView barrage;//弹幕

    @BindView(R.id.vv_play_play_one)
    TXCloudVideoView vvPlayPlayOne;//游戏画面1

    @BindView(R.id.layout)
    RelativeLayout layout;//根布局

    @BindView(R.id.switch_img)
    ImageView switchImg;//切换摄像头按钮

    @BindView(R.id.tv_play_need_coins)
    TextView tvPlayNeedCoins;//玩一次消费金币

    @BindView(R.id.controller)
    RelativeLayout controller;//控制按钮布局

    @BindView(R.id.rl_play_bottom)
    LinearLayout rlPlayBottom;//开始游戏布局

    @BindView(R.id.left)
    Button left;//左键

    @BindView(R.id.front)
    Button front;//前键

    @BindView(R.id.after)
    Button after;//后键

    @BindView(R.id.right)
    Button right;//右键

    @BindView(R.id.go)
    ImageView go;//下抓键

    @BindView(R.id.count_down)
    TextView countDown;//倒计时

    @BindView(R.id.play_sv)
    ScrollView playSv;

    @BindView(R.id.tv_play_players)
    TextView mtv_players;//房间人数

    @BindView(R.id.iv_play_player1)
    ImageView miv_player1;//用户1

    @BindView(R.id.iv_play_player2)
    ImageView miv_player2;//用户2

    @BindView(R.id.iv_play_player3)
    ImageView miv_player3;//用户3

    @BindView(R.id.iv_play_caught_record)
    ImageView miv_caught_record;//抓中记录图标

    @BindView(R.id.rl_play_record_container)
    RelativeLayout mrl_record_container;//抓中记录容器

    @BindView(R.id.prompt_rl)
    RelativeLayout promptRl;//下抓提示语和提示时间布局

    @BindView(R.id.prompt_time)
    TextView promptTime;//计时

    @BindView(R.id.caught_prompt)
    TextView caughtPrompt;//下抓提示语

    private ArrayList<PlayerRecordEntity> mRecordList = new ArrayList<>();

    private PlayerRecordAdapter mRecordAdapter;

    private PlayPresenter mPresenter = new PlayPresenter(this);

    private TXLivePushConfig mLivePushConfig;//推流参数

    private TXLivePusher mLivePusher;//推流器

    private TXLivePlayer mLivePlayer;//播放器

    private TXLivePlayer mLivePlayerOne;//播放器摄像头2

    private String roomid;//房间id

    private DanmakuContext danmakuContext;//弹幕

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    private boolean Camera = true;

    public static boolean socketSwitch=true;//true表示在游戏中不支持断开长连接；false表示已经结束游戏可，可以断开长连接了

    public static String s = "";//长连接需要的控制前后左右下抓的参数

    public static String t = "";//长连接需要控制的时间

    public static String token = "";//长连接需要的参数token

    private Vibrator vibrator;

//    private UserEntity entity = UserEntity.getCurUser();

    private double price;//开始一次游戏所需要的娃娃币

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                mPresenter.refreshRoom(roomid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        setImmersionStatus(R.id.layout);

        //测试使用；实际使用不在这个位置
//        caughtDialog=new CaughtDialog(this);
//        caughtDialog.showDialog();
//        caughtNoDialog=new CaughtNoDialog(this);
//        caughtNoDialog.showDialog();
    }

    @Override
    protected void setView() {

        left.setOnTouchListener(this);
        front.setOnTouchListener(this);
        after.setOnTouchListener(this);
        right.setOnTouchListener(this);
        go.setOnTouchListener(this);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        mPresenter.PlayContent(this);//向PlayPresenter传入全局变量context

        setActivityName(PlayAct.class.getSimpleName());
        ivActionbarLeft.setImageResource(R.drawable.ic_back);
        ivActionbarRight.setImageResource(R.drawable.ic_details);

        //创建推流器对象
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePusher.setConfig(mLivePushConfig);
        ////////////////////////////////////////////
        /**
         * 摄像头2
         */
        ViewGroup.LayoutParams layoutParamsOne = vvPlayPlayOne.getLayoutParams();
        //设置游戏画面高度
        layoutParamsOne.height = (int) (AppApplication.getDisplayHeight() * 0.65);
        vvPlayPlayOne.setLayoutParams(layoutParamsOne);
        //创建播放器对象
        mLivePlayerOne = new TXLivePlayer(this);
        //设置player播放界面view
        mLivePlayerOne.setPlayerView(vvPlayPlayOne);
        //设置铺满or适应
        //RENDER_MODE_FILL_SCREEN	将图像等比例铺满整个屏幕，多余部分裁剪掉，此模式下画面不会留黑边，但可能因为部分区域被裁剪而显示不全。
        // RENDER_MODE_ADJUST_RESOLUTION	将图像等比例缩放，适配最长边，缩放后的宽和高都不会超过显示区域，居中显示，画面可能会留有黑边。
        mLivePlayerOne.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);

        ////////////////////////////////////////////

        ViewGroup.LayoutParams layoutParams = vvPlayPlay.getLayoutParams();
        //设置游戏画面高度
        layoutParams.height = (int) (AppApplication.getDisplayHeight() * 0.65);
        vvPlayPlay.setLayoutParams(layoutParams);
        //创建播放器对象
        mLivePlayer = new TXLivePlayer(this);
        //设置player播放界面view
        mLivePlayer.setPlayerView(vvPlayPlay);
        //设置铺满or适应
        //RENDER_MODE_FILL_SCREEN	将图像等比例铺满整个屏幕，多余部分裁剪掉，此模式下画面不会留黑边，但可能因为部分区域被裁剪而显示不全。
        // RENDER_MODE_ADJUST_RESOLUTION	将图像等比例缩放，适配最长边，缩放后的宽和高都不会超过显示区域，居中显示，画面可能会留有黑边。
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        //播放器参数
        TXLivePlayConfig mPlayConfig = new TXLivePlayConfig();
//        //自动模式
//        mPlayConfig.setAutoAdjustCacheTime(true);
//        mPlayConfig.setMinAutoAdjustCacheTime(1);
//        mPlayConfig.setMaxAutoAdjustCacheTime(5);

        //极速模式
        mPlayConfig.setAutoAdjustCacheTime(true);
        mPlayConfig.setMinAutoAdjustCacheTime(1);
        mPlayConfig.setMaxAutoAdjustCacheTime(1);
//
//        //流畅模式
//        mPlayConfig.setAutoAdjustCacheTime(false);
//        mPlayConfig.setCacheTime(5);
        //设置播放器参数
        mLivePlayer.setConfig(mPlayConfig);

        //设置播放器参数
        mLivePlayerOne.setConfig(mPlayConfig);

        //PLAY_TYPE_LIVE_RTMP	     传入的URL为RTMP直播地址
        //PLAY_TYPE_LIVE_FLV	     传入的URL为FLV直播地址
        //PLAY_TYPE_LIVE_RTMP_ACC    低延迟链路地址（仅适合于连麦场景）
        //PLAY_TYPE_VOD_HLS	3	     传入的URL为HLS(m3u8)播放地址
//        mLivePlayer.startPlay(flvUrl, PLAY_TYPE_LIVE_FLV); //推荐FLV
//        mLivePlayer.startPlay("rtmp://live.hkstv.hk.lxdns.com/live/hks", TXLivePlayer.PLAY_TYPE_LIVE_RTMP); //推荐FLV
        mRecordAdapter = new PlayerRecordAdapter(this, mRecordList);
        lvPlayRecord.setAdapter(mRecordAdapter);
        lvPlayRecord.setMode(XListView.Mode.DISABLED);

        Bundle bundle = getIntent().getExtras();
        if (null != bundle && bundle.containsKey("roomid")) {
            roomid = bundle.getString("roomid");
            mPresenter.getRoomDetails(roomid);
        }

        /////////////////////
        /**弹幕*/
        barrage.enableDanmakuDrawingCache(true);
        barrage.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                barrage.start();
//                AddBarrage();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        barrage.prepare(parser, danmakuContext);


        /////////////////////////////////

        if (UserEntity.isLogin()) {
            UserEntity entity = UserEntity.getCurUser();
            tvPlayCoins.setText(entity.wmoney + "");
        }

        mPresenter.refreshRoom(roomid);
    }

    @Override
    protected void setListener() {
        //设置推流器监听
        mLivePusher.setPushListener(mPushListener);
        //设置播放器监听
        mLivePlayer.setPlayListener(mPlayListener);

        //设置播放器监听
        mLivePlayerOne.setPlayListener(mPlayListener);
    }

    @OnClick({R.id.iv_actionbar_left, R.id.iv_actionbar_right, R.id.iv_play_msg, R.id.iv_play_start, R.id.switch_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.iv_actionbar_right:
                break;
            case R.id.iv_play_msg:
                //发送弹幕消息
                Popup.newsPopup(layout, this, mPresenter, roomid);
                break;
            case R.id.iv_play_start:
                //启动推流,开始游戏按钮
                onStartGame();
                break;
            case R.id.switch_img:
                if (Camera) {
                    vvPlayPlayOne.setVisibility(View.VISIBLE);
                    vvPlayPlay.setVisibility(View.GONE);
                    Camera = false;
                } else {
                    vvPlayPlayOne.setVisibility(View.GONE);
                    vvPlayPlay.setVisibility(View.VISIBLE);
                    Camera = true;
                }
                break;
        }
    }

    /**
     * 推流器事件监听
     */
    private ITXLivePushListener mPushListener = new ITXLivePushListener() {
        @Override
        public void onPushEvent(int event, Bundle bundle) {
            String msg = bundle.getString(TXLiveConstants.EVT_DESCRIPTION);
            LogUtils.e("onPushEvent ：" + event + " msg ：" + msg);
            if (event < 0) {
                Notification.showToastMsg(msg);
//                if(event == TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL || event == TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL){
////                    stopPublishRtmp();
//                }
            }
        }

        @Override
        public void onNetStatus(Bundle bundle) {
//            LogUtils.e("Push status, CPU使用:" + bundle.getString(TXLiveConstants.NET_STATUS_CPU_USAGE)
//                    + ", 分辨率:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH)
//                    + "*" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT)
//                    + ", 速度:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps"
//                    + "，抖动:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_JITTER)
//                    + ", 帧率:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS)
//                    + ", 音频:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps"
//                    + ", 视频:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps"
//                    + "，缓冲:" + bundle.getInt(TXLiveConstants.NET_STATUS_CACHE_SIZE
//                    + ",服务器IP:" + bundle.getString(TXLiveConstants.NET_STATUS_SERVER_IP)));
        }
    };

    /**
     * 播放器监听事件
     */
    private ITXLivePlayListener mPlayListener = new ITXLivePlayListener() {
        @Override
        public void onPlayEvent(int event, Bundle bundle) {
            String msg = bundle.getString(TXLiveConstants.EVT_DESCRIPTION);
            LogUtils.e("onPlayEvent ：" + event + " msg ：" + msg);
            if (event < 0) {
                Notification.showToastMsg(msg);
            }
        }

        @Override
        public void onNetStatus(Bundle bundle) {
//            LogUtils.e("Play status, CPU使用:" + bundle.getString(TXLiveConstants.NET_STATUS_CPU_USAGE)
//                    + ", 分辨率:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH)
//                    + "*" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT)
//                    + ", 速度:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps"
//                    + "，抖动:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_JITTER)
//                    + ", 帧率:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS)
//                    + ", 音频:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps"
//                    + ", 视频:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps"
//                    + "，缓冲:" + bundle.getInt(TXLiveConstants.NET_STATUS_CACHE_SIZE
//                    + ",服务器IP:" + bundle.getString(TXLiveConstants.NET_STATUS_SERVER_IP)));
        }
    };

    /**
     * 结束推流，注意做好清理工作
     */
    public void stopRtmpPublish() {
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
    }

    @Override
    public void startAct(Class<?> cls, Bundle bundle) {
        startIntent(cls, bundle);
    }

    /**
     * 房间详情
     *
     * @param entity 数据
     */
    @Override
    public void setRoomDetails(RoomDetailsEntity entity) {
        if (null != entity) {
//            String count = entity.count;
//            if (!TextUtils.isEmpty(count)) {
//                //设置房间人数
//                count = AppApplication.getStringById(R.string.users).replace("0", count);
//                mtv_players.setText(count);
//            }
            ArrayList<UserEntity> users = entity.userList;
            showUser(users);
            if (null != entity.records) {
                //显示抓中记录
                mRecordList.clear();
                mRecordList.addAll(entity.records);
                mRecordAdapter.notifyDataSetChanged();
                miv_caught_record.setVisibility(View.VISIBLE);
                mrl_record_container.setVisibility(View.VISIBLE);
            } else {
                //隐藏抓中记录
                miv_caught_record.setVisibility(View.GONE);
                mrl_record_container.setVisibility(View.GONE);
            }
            if (null != entity.price) {
                //设置娃娃价格
                tvPlayNeedCoins.setText(String.format(AppApplication.getStringById(R.string.expense), entity.price));
                price = Double.parseDouble(entity.price);
            }
        }
        if (entity.video_url_1 != null && entity.video_url_1.length() > 0) {
            //播放器1
            mLivePlayer.startPlay(entity.video_url_1, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐FLV
        } else {
        }
        if (entity.video_url_2 != null && entity.video_url_2.length() > 0) {
            //播放器2
            mLivePlayerOne.startPlay(entity.video_url_2, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐FLV
        } else {
        }
    }

    /**
     * 显示房间
     *
     * @param users 用户列表
     */
    private void showUser(ArrayList<UserEntity> users) {
        if (null != users) {
            int length = users.size();
            if (length == 1) {
                miv_player1.setVisibility(View.VISIBLE);
                miv_player2.setVisibility(View.GONE);
                miv_player3.setVisibility(View.GONE);
                ImageSetting.onImageSetting(PlayAct.this, users.get(0).avatar, miv_player1);
            } else if (length == 2) {
                miv_player1.setVisibility(View.VISIBLE);
                miv_player2.setVisibility(View.VISIBLE);
                miv_player3.setVisibility(View.GONE);
                ImageSetting.onImageSetting(PlayAct.this, users.get(0).avatar, miv_player1);
                ImageSetting.onImageSetting(PlayAct.this, users.get(1).avatar, miv_player2);
            } else if (length >= 3) {
                miv_player1.setVisibility(View.VISIBLE);
                miv_player2.setVisibility(View.VISIBLE);
                miv_player3.setVisibility(View.VISIBLE);
                ImageSetting.onImageSetting(PlayAct.this, users.get(0).avatar, miv_player1);
                ImageSetting.onImageSetting(PlayAct.this, users.get(1).avatar, miv_player2);
                ImageSetting.onImageSetting(PlayAct.this, users.get(2).avatar, miv_player3);
            }
        }
    }

    @Override
    public void startGame(RoomDetailsEntity entity) {
        /**开始游戏*/
        //娃娃币不足提示弹框
        controller.setVisibility(View.VISIBLE);
        rlPlayBottom.setVisibility(View.GONE);
        String rtmpUrl = entity.camera_url;
        Log.e("mrchen2", rtmpUrl);
        if (!TextUtils.isEmpty(rtmpUrl)) {
            mLivePusher.startPusher(rtmpUrl);
            mLivePusher.startCameraPreview(vvPlayCamera);
        }
        token = entity.token;
        Log.e("ceshi", token);
        if (entity.url != null && entity.url.length() > 0) {
            mPresenter.PlayScoket(entity.url);//建立长连接开始游戏
        }
        onScroll(true);
        caughtPrompt.setText("游戏开始...");
    }

    /**
     * 发送弹幕消息
     *
     * @param s 消息
     */
    @Override
    public void onBarrage(String s) {
        Log.e("MrChen", "发送弹幕消息");
    }

    /**
     * 游戏结束休息一会
     *
     * @param entity
     */
    @Override
    public void PlayGameOver(RoomDetailsEntity entity) {
        controller.setVisibility(View.GONE);
        rlPlayBottom.setVisibility(View.VISIBLE);
    }

    /**
     * 刷新房间
     *
     * @param entity
     */
    @Override
    public void PlayRefreshRoom(RoomDetailsEntity entity) {
        handler.postDelayed(runnable, 500);//每隔0.5s执行
        String ttl=entity.ttl;
        countDown.setText("（" + ttl + "）");
        promptTime.setText(ttl+"“");
        String count = entity.count;
        if (!TextUtils.isEmpty(count)) {
            //设置房间人数
            count = AppApplication.getStringById(R.string.users).replace("0", count);
            mtv_players.setText(count);
        }
        for (int i = 0; i < entity.barrageList.size(); i++) {
            addDanmaku(entity.barrageList.get(i).msg, false);
        }
        if (entity.isself.equals("1")) {
            //当前用户是自己
            switch (entity.winner) {
                case "1":
                    //抓中
                    if (mPresenter.Eject) {
                        mPresenter.onClick(2);
                        socketSwitch=false;//断开长连接索引
                    }
                    break;
                case "2":
                    //未抓中
                    if (mPresenter.Eject) {
                        socketSwitch=false;//断开长连接索引
                    }
                    break;
            }

        }
    }
    ////////////////////////////////////////////////////////////////////

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(barrage.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        barrage.addDanmaku(danmaku);
    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (barrage != null && barrage.isPrepared()) {
            barrage.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (barrage != null && barrage.isPrepared() && barrage.isPaused()) {
            barrage.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);//关闭线程
        mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        mLivePlayerOne.stopPlay(true); // true代表清除最后一帧画面
        vvPlayPlay.onDestroy();
        vvPlayPlayOne.onDestroy();
        if (barrage != null) {
            barrage.release();
            barrage = null;
        }
    }

    /**
     * 按钮按下和放开事件监听
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.front:
                onGameData(event, getString(R.string.front));
                break;
            case R.id.after:
                onGameData(event, getString(R.string.after));
                break;
            case R.id.left:
                onGameData(event, getString(R.string.left));
                break;
            case R.id.right:
                onGameData(event, getString(R.string.right));
                break;
            case R.id.go:
                onGameData(event, getString(R.string.go));
                caughtPrompt.setText("已下抓，等待游戏结果...");
                break;
        }
        return true;
    }

    /**
     * 设置相关的游戏操作参数
     *
     * @param event  控件按住和放开判断
     * @param string 按住状态上下左右值
     */
    public void onGameData(MotionEvent event, String string) {
        playSv.requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                s = "";
                t = "";
                vibrator.cancel();
                break;
            case MotionEvent.ACTION_DOWN:
                s = string;
                t = getString(R.string.control_time);
                vibrator.vibrate(20000);
                break;
        }
    }

    /**
     * 设置ScrollView禁止滑动和允许滑动
     *
     * @param b true表示禁止，false表示允许
     */
    public void onScroll(final boolean b) {
        playSv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return b;
            }
        });
    }

    /**
     * 开始游戏
     */
    public void onStartGame() {
        promptRl.setVisibility(View.VISIBLE);
        if (UserEntity.isLogin()) {
            UserEntity entity = UserEntity.getCurUser();
            if (entity.wmoney < price) {
                mPresenter.onClick(1);//游戏币不足，弹框提示
            } else {
                mPresenter.startGame(roomid);//游戏币充足,建立服务请求
            }
        } else {
            startAct(LoginAct.class, null);
        }
        socketSwitch=true;
    }

    /**
     * 结束游戏
     */
    public void onEndGame() {
        mPresenter.PlayGameOver(roomid);
        controller.setVisibility(View.GONE);
        rlPlayBottom.setVisibility(View.VISIBLE);
        promptRl.setVisibility(View.GONE);
    }
}
