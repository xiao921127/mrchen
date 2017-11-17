package com.mrchen.app.mygame.uitl;

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.mrchen.app.mygame.UI.User;
import com.mrchen.app.mygame.UI.UserOneBack;
import com.mrchen.app.mygame.UI.view.UserBack;

import java.util.Calendar;
import java.util.HashMap;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/06 14:00
 */
public class OkGoClientManager {
    private static OkGoClientManager mInstance;
    public static final String mBaseUrl = "http://192.168.1.111:93/";//客户
    private Context context;
    HashMap<String,String> verifymap;
    private OkGoClientManager(Context context) {
        this.context = context;
    }

    public static OkGoClientManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OkGoClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkGoClientManager(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public void getString(String url,HashMap<String, String> params,UserBack userback){
        url=mBaseUrl+url;
        OkGo.get(url)                            // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(userback);
    }
    public void getStringOne(String url,HashMap<String, String> params,UserOneBack userback){
        String urls=mBaseUrl+url;
        Log.e("MrChen",urls);
        OkGo.get(urls)                            // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(userback);
    }
}
