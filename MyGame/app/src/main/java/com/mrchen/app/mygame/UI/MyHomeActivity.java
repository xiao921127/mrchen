package com.mrchen.app.mygame.UI;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.mrchen.app.mygame.HttpModel;
import com.mrchen.app.mygame.OnHttpPresenter;
import com.mrchen.app.mygame.R;
import com.mrchen.app.mygame.UI.adapter.MyAdapter;
import com.mrchen.app.mygame.UI.adapter.MyAdapterOne;
import com.mrchen.app.mygame.UI.mvp.AdpterCeShi;
import com.mrchen.app.mygame.UI.view.UserBack;
import com.mrchen.app.mygame.base.BaseActivity;
import com.mrchen.app.mygame.uitl.OkGoClientManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MyHomeActivity extends BaseActivity{

    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<User.DataBean.WawaList> listData=new ArrayList<User.DataBean.WawaList>();
    private int refreshTime = 0;
    private int times = 0;

//    private ArrayList<String> listData;
//    private int refreshTime = 0;
//    private int times = 0;

    private OnHttpPresenter onhttp=new OnHttpPresenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home);
        ButterKnife.bind(this);
        setImmersionStatus(new View(this), R.id.rootview);
        initView();
    }
    private void initView() {
        String url= "home/m?act=home_data&json={\"cid\":\"\",\"sort\":\"\",\"" +
                "session\":{\"uid\":\"9\",\"sid\":\"999\"},\"pagination\":{\"page\":1}}";
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header,(ViewGroup)findViewById(android.R.id.content), false);
        mRecyclerView.addHeaderView(header);
//        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                refreshTime ++;
//                times = 0;
//                new Handler().postDelayed(new Runnable(){
//                    public void run() {
//                        listData.clear();
//                        for(int i = 0; i < 4 ;i++){
//                            listData.add("item" + i + "after " + refreshTime + " times of refresh");
//                        }
//                        mAdapter.notifyDataSetChanged();
//                        mRecyclerView.refreshComplete();
//                    }
//
//                }, 1000);            //refresh data here
//            }
//
//            @Override
//            public void onLoadMore() {
//                Log.e("myOkGo","1111111111111");
//                if(times < 2){
//                    Log.e("myOkGo","2222222222221");
//                    new Handler().postDelayed(new Runnable(){
//                        public void run() {
//                            Log.e("myOkGo","333333333333333");
//                            mRecyclerView.loadMoreComplete();
////                            for(int i = 0; i < 25 ;i++){
////                                listData.add("item" + (i + listData.size()) );
////                            }
//                            mRecyclerView.loadMoreComplete();
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    }, 1000);
//                } else {
//                    Log.e("myOkGo","44444444444444444");
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                            Log.e("myOkGo","5555555555555555");
////                            for(int i = 0; i < 9 ;i++){
////                                listData.add("item" + (1 + listData.size() ) );
////                            }
//                            mRecyclerView.setNoMore(true);
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    }, 1000);
//                }
//                times ++;
//            }
//        });
//
//        listData = new  ArrayList<String>();
//        for(int i = 0; i < 10 ;i++){
//            listData.add("item" + i);
//        }
//        mAdapter = new MyAdapterOne(listData);
//
//        mRecyclerView.setAdapter(mAdapter);

        onhttp.getByOkGo(url,this,mRecyclerView);

        winnerlist();
}
    /**
     * 验证码
     */
    public void onOkGo(){
        String s="home/m?act=sendSms&json={\"type\":\"login\",\"is_voice\":0,\"phone\":\"18996206802\"}";
        OkGoClientManager.getInstance(this).getString(s, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                if (user.status.code.equals("1")){
                    Log.e("myOkGo",user.data.errorDesc);
                }else {
                    Log.e("myOkGo",user.data.errorDesc);
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 退出登录
     */
    public void logout(){
        String s="home/m?act=user_logout&json={\"session\":{\"uid\":9,\"sid\":999}}";
        OkGoClientManager.getInstance(this).getString(s, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                if (user.status.code.equals("1")){
                    Log.e("myOkGo",user.data.successDesc);
                }else {
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 登录注册
     */
    public void login(){
        String s="home/m?act=user_phone_login&json={\"invite\":\"\",\"verify\":\"1234\",\"mobile\":\"15310243344\"}";
        OkGoClientManager.getInstance(this).getString(s, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                if (user.status.code.equals("1")){

                }else {
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 三方登录
     */
    public void sync_login(){
        String s="home/m?act=sync_login&json={\"openid\":\"fdkjlkdjglfdg\",\"type\":\"QQ\",\"access_token\":\"4132412341324\",\"unionid\":\"zzzzzzzzz\",\"nickname\":\"风情\",\"avatar\":\"" +
                "http://static.wowozhe.com/Uploads/Avatar/2016-03-25/56f4ddfac6d6c.jpg\"}";
        OkGoClientManager.getInstance(this).getString(s, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                if (user.status.code.equals("1")){

                }else {
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 在线充值下订单
     */
    public void pay_recharge(){
        String s="home/m?act=pay_recharge&json={\"session\":{\"uid\":\"59\",\"sid\":\"59\"}," +
                "\"money\":\"10\",\"buyplatform\":\"APPLE\",\"pay_other\":\"alipay\"}&t=17298";
        OkGoClientManager.getInstance(this).getString(s, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                if (user.status.code.equals("1")){

                }else {
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 获取config配置信息
     */
    public void config(){
        String s="home/m?act=config&json={\"session\":{\"uid\":\"1477629\",\"sid\":\"dgbtww\"},\"pagination\":{\"page\":1}}";
        OkGoClientManager.getInstance(this).getString(s, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                if (user.status.code.equals("1")){

                }else {
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 用户资料刷新
     */
    public void refresh_info(){
        String s="home/m?act=refresh_info&json={\"session\":{\"uid\":\"100032\",\"sid\":\"100032\"}}\n";
        OkGoClientManager.getInstance(this).getString(s, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                if (user.status.code.equals("1")){

                }else {
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * slogin通过 uid sid 获取用户信息
     */
    public void slogin(){
        String s="home/m?act=slogin&json={\"session\":{\"uid\":\"100032\",\"sid\":\"100032\"}}";
        OkGoClientManager.getInstance(this).getString(s, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                if (user.status.code.equals("1")){

                }else {
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void login_phone_pwd(){
        String s="home/m?act=login_phone_pwd&json={\"phone\":\"13136595140\",\"pwd\":\"123456\"}";
        OkGoClientManager.getInstance(this).getString(s, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                if (user.status.code.equals("1")){

                }else {
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void winnerlist(){
        String s="home/m?act=winnerlist&json={\"session\":{\"uid\":\"732705\",\"sid\":\"100037\"},\"pagination\":{\"page\":1}}";
        OkGoClientManager.getInstance(this).getStringOne(s, null, new UserOneBack(){
            /**
             * 对返回数据进行操作的回调， UI线程
             *
             * @param userOne
             * @param call
             * @param response
             */
            @Override
            public void onSuccess(UserOne userOne, Call call, Response response) {
                if (userOne.status.code.equals("1")){
                    if (userOne.data.size()>0){
                        Log.e("MrChen",userOne.data.get(0).id);
                    }else {
                        Log.e("MrChen","没有数据");
                    }
                }else {
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("MrChen","!!!!!!!!!!!"+e.getMessage());
                Toast.makeText(MyHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}