package com.wawaji.common.websocket;

import android.util.Log;

import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.ui.PlayAct;
import com.wawaji.common.https.api.RequestParams;

import java.util.Timer;
import java.util.TimerTask;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/15 16:00
 */

public class WebSocketListener extends okhttp3.WebSocketListener {

    private WebSocket mSocket;
    private UserEntity user = UserEntity.getCurUser();

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        mSocket = webSocket;
        //连接成功后，发送登录信息
        mSocket.send(setJson(PlayAct.token,PlayAct.s,PlayAct.t,"3",user.uid));
        output("连接成功！");

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        output("receive bytes:" + bytes.hex());
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        Log.e("my_Socket",text+"!!!!!!!!!!");//打印服务器返回的值
        if (PlayAct.socketSwitch){
            output("receive text:" + text);
            //收到服务器端发送来的信息后，每隔0.5秒发送一次心跳包
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mSocket.send(setJson(PlayAct.token,PlayAct.s,PlayAct.t,"3",user.uid));
                }
            },300);
        }else {
            webSocket.close(1000,"正常关闭");//1000表示正常关闭，0-999范围内的状态码不被使用。1000-2999范围内的状态码保留给本协议、其未来的修订和一个永久的和现成的公共规范中指定的扩展的定义。等等
        }
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        output("closed:" + reason);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        output("closing:" + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        output("failure:" + t.getMessage());
        //异常处理
    }

    private void output(final String text) {
        Log.e("my_Socket",text);
    }

    ///////////////////////////////////
    public static String setJson(String token,String s,String t,String id,String uid) {
        String json = "{\"act\":\"op\",\"token\":\""+token+"\",\"id\":\""+id+"\",\"do\":\""+s+"\",\"t\":\""+t+"\",\"uid\":\""+uid+"\"}";
//        RequestParams params = new RequestParams();
//        params.put("act", "op");
//        params.put("token", token);
//        params.put("id", id);
//        params.put("do", s);
//        params.put("t", t);
//        params.put("uid", uid);
//        String jsonOne=params.toString();
        Log.e("ceshi", json);
        return json;
    }

}
