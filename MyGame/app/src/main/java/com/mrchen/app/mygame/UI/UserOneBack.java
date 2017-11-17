package com.mrchen.app.mygame.UI;

import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;

import okhttp3.Response;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/10 13:42
 */

public abstract class UserOneBack extends AbsCallback<UserOne> {

    @Override
    public UserOne convertSuccess(Response response) throws Exception {
        Log.e("MrChen", "我去");
        String string = StringConvert.create().convertSuccess(response);
        Log.e("MrChen", string);
        UserOne user = new Gson().fromJson(string, UserOne.class);
        return user;
    }
}
