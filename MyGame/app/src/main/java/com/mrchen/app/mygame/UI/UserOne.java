package com.mrchen.app.mygame.UI;

import android.app.Activity;

import java.util.ArrayList;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/10 13:36
 */

public class UserOne {

    public Status status;
    public ArrayList<DataBean> data;
    public String login_tips_box;
    public UserOne(Status status,ArrayList<DataBean> data, String login_tips_box) {
            this.status = status;
            this.data = data;
            this.login_tips_box=login_tips_box;
        }
    public class Status {
        public String code;
        public String msg;

        public Status(String code,String msg) {
            this.code=code;
            this.msg=msg;
        }
    }
    public class DataBean {
        public String id;
        public String wid;
        public String title;
        public String img;
        public String ctime;
        public DataBean(String id,String wid,String title,String img,String ctime){
            this.id=id;
            this.wid=wid;
            this.title=title;
            this.img=img;
            this.ctime=ctime;
        }
    }
}
