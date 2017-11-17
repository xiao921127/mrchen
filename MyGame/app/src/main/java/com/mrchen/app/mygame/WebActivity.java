package com.mrchen.app.mygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private Button btnStart;
    private TextView tvOutput;
    private WebSocket mSocket;


    private Button bt,bt1,bt2,bt3,bt4;

    public static String s="";
    public static String t="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        btnStart = (Button) findViewById(R.id.start);
        tvOutput = (TextView) findViewById(R.id.output);
        btnStart.setOnClickListener(this);

        bt=(Button)findViewById(R.id.bt);
        bt.setOnTouchListener(this);
        bt1=(Button)findViewById(R.id.bt_1);
        bt1.setOnTouchListener(this);
        bt2=(Button)findViewById(R.id.bt_2);
        bt2.setOnTouchListener(this);
        bt3=(Button)findViewById(R.id.bt_3);
        bt3.setOnTouchListener(this);
        bt4=(Button)findViewById(R.id.bt_4);
        bt4.setOnTouchListener(this);

    }




    private void start() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        Request request = new Request.Builder().url("ws://118.31.115.17:12345").build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();
    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start://开始连接
                start();
                break;
        }
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.bt:
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        s="";
                        t="0";
                        break;
                    case MotionEvent.ACTION_DOWN:
                        s="w";
                        t="300";
                        break;
                }
                break;
            case R.id.bt_1:
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        s="";
                        t="0";
                        break;
                    case MotionEvent.ACTION_DOWN:
                        s="s";
                        t="300";
                        break;
                }
                break;
            case R.id.bt_2:
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        s="";
                        t="0";
                        break;
                    case MotionEvent.ACTION_DOWN:
                        s="a";
                        t="300";
                        break;
                }
                break;
            case R.id.bt_3:
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        s="";
                        t="0";
                        break;
                    case MotionEvent.ACTION_DOWN:
                        s="d";
                        t="300";
                        break;
                }
                break;
            case R.id.bt_4:
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        s="";
                        t="0";
                        break;
                    case MotionEvent.ACTION_DOWN:
                        s="e";
                        t="300";
                        break;
                }
                break;
        }

        return false;
    }


    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
            String openid = "1";
            //连接成功后，发送登录信息
//            String message = "{\"type\":\"login\",\"user_id\":\""+openid+"\"}";
            String message = "{\"act\":\"op\",\"token\":\"12345678\",\"id\":\"3\",\"do\":\"\",\"t\":\"0\",\"uid\":\"100036\"}";
            mSocket.send(setJson(s,t));
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
            Log.e("my_Socket",text+"!!!!!!!!!!");
            output("receive text:" + text);
            //收到服务器端发送来的信息后，每隔25秒发送一次心跳包
            final String message = "{\"act\":\"op\",\"token\":\"12345678\",\"id\":\"3\",\"do\":\"a\",\"t\":\"300\",\"uid\":\"100036\"}";
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mSocket.send(setJson(s,t));
//                    mSocket.close(500,"再见");
                }
            },500);
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
        }
    }

    private void output(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("my_Socket",text+"@@@@@@@@");
                tvOutput.setText(tvOutput.getText().toString() + "\n\n" + text);
            }
        });
    }

///////////////////////////////////
    public static String setJson(String s,String t) {
        String json = "{\"act\":\"op\",\"token\":\"12345678\",\"id\":\"3\",\"do\":\""+s+"\",\"t\":\""+t+"\",\"uid\":\"100036\"}";
        Log.e("ceshi", json);
        return json;
    }
}
