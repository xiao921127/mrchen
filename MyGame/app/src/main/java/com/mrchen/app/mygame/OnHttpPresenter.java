package com.mrchen.app.mygame;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.mrchen.app.mygame.UI.MyHomeActivity;
import com.mrchen.app.mygame.UI.SetUpActivity;
import com.mrchen.app.mygame.UI.User;
import com.mrchen.app.mygame.UI.adapter.MyAdapter;
import com.mrchen.app.mygame.UI.mvp.AdpterCeShi;
import com.mrchen.app.mygame.UI.view.UserBack;
import com.mrchen.app.mygame.uitl.OkGoClientManager;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/06 16:26
 */

public class OnHttpPresenter implements httpInterface.onHttpListener,AdpterCeShi{
    private static HttpModel httpModel; // 配置模型
    private static ArrayList<User.DataBean.WawaList> listData=new ArrayList<User.DataBean.WawaList>();

    MyAdapter myAdapter;
    Context context;
    XRecyclerView xRecyclerView;
    private int times = 0;

    // 构造方法
    public OnHttpPresenter() {
        httpModel=new HttpModel();
    }
    public void getByOkGo(String url, final Context context, XRecyclerView xRecyclerView) {
        httpModel.onHttp(this,context,url);
        this.context=context;
        this.xRecyclerView=xRecyclerView;
    }

    @Override
    public void httpLogout(User user, final Context context) {
        listData=user.data.wawa_list;
        if (user.status.code.equals("1")){
            ////////////////////////////////////////////
            xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                times = 0;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (myAdapter!=null){
                                myAdapter.notifyDataSetChanged();
                            }
                            xRecyclerView.refreshComplete();
                        }
                    }, 1000);//refresh data here
                }
                @Override
                public void onLoadMore() {

                    Log.e("myOkGo","上拉加载");

                    if (times < 2) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

                                xRecyclerView.loadMoreComplete();

                                xRecyclerView.loadMoreComplete();
                                if (myAdapter!=null){
                                    myAdapter.notifyDataSetChanged();
                                }
                            }
                        }, 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
//                            for (int i = 0; i < 9; i++) {
//                                listData.add("item" + (1 + listData.size()));
//                            }
                                xRecyclerView.setNoMore(true);
                                if (myAdapter!=null){
                                    myAdapter.notifyDataSetChanged();
                                }
                            }
                        }, 1000);
                    }
                times++;
                }
            });
            ///////////////////////////////////////////////////////
            myAdapter=new MyAdapter(listData,context,this);
            xRecyclerView.setAdapter(myAdapter);
            Log.e("myJieKou","成功了");
        }else {
            Log.e("myJieKou","成功了!!!");
        }
    }

    @Override
    public void jiekouff(String s) {
        Toast.makeText(context, String.valueOf(s), Toast.LENGTH_SHORT).show();
    }
}
