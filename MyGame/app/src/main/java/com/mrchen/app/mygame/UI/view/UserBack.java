package com.mrchen.app.mygame.UI.view;

import android.support.v7.util.SortedList;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.mrchen.app.mygame.UI.User;

import java.io.IOException;

import okhttp3.Response;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/06 14:05
 */

public abstract class UserBack extends AbsCallback<User> {

    @Override
    public User convertSuccess(Response response) throws Exception {
        Log.e("tripactivtiy", "我去");
        String string = StringConvert.create().convertSuccess(response);
        Log.e("tripactivtiy", string);
        User user = new Gson().fromJson(string, User.class);
        return user;
    }
}
