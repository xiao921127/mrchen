package com.wawaji.app.ui.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.wawaji.app.R;
import com.wawaji.app.presenter.PlayPresenter;
import com.wawaji.common.notification.Notification;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/14 10:31
 */

public class Popup {

    private static View myView;
    private static PopupWindow myPopup;

    public static void newsPopup(View RelayoutPhoto, final Context context, final PlayPresenter mPresenter,final String roomId) {
        myView = LayoutInflater.from(context).inflate(
                R.layout.popup_news, null);
        final EditText news = (EditText) myView.findViewById(R.id.news_et);
        TextView send =(TextView) myView.findViewById(R.id.send_tv);
        myPopup = new PopupWindow(myView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        myPopup.update();
        myPopup.setFocusable(true);//pop的点击
        myPopup.setTouchable(true);
        myPopup.setBackgroundDrawable(new BitmapDrawable());
        if (myPopup.isShowing()) {
            myPopup.dismiss();
        } else {
            myPopup.showAtLocation(RelayoutPhoto, Gravity.CENTER, 0, 0);
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (news.getText().toString().length()>0){
                    mPresenter.Requestbarrage(news.getText().toString(),roomId);//发送弹幕消息掉接口传值，1.需要发送的消息；2.房间id
                    news.setText("");
                    myPopup.dismiss();
                }else {
                    Notification.showToastMsg(context.getString(R.string.send));
                }
            }
        });

    }
}
