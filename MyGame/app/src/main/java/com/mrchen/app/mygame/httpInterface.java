package com.mrchen.app.mygame;
import android.content.Context;
import com.mrchen.app.mygame.UI.User;
/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/06 16:34
 */

public interface httpInterface {

    interface model {
        void onHttp(onHttpListener listener,Context context,String url);
    }
    interface onHttpListener{
        void httpLogout(User s, Context context);
    }
}
