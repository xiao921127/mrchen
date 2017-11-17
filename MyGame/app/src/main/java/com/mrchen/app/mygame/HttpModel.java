package com.mrchen.app.mygame;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mrchen.app.mygame.UI.MyHomeActivity;
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
 * @since 2017/11/06 16:39
 */

public class HttpModel implements httpInterface.model{

    @Override
    public void onHttp(final httpInterface.onHttpListener listener, final Context context, String url) {

        OkGoClientManager.getInstance(context).getString(url, null, new UserBack() {
            @Override
            public void onSuccess(User user, Call call, Response response) {
                listener.httpLogout(user,context);
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
