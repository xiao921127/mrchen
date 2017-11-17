package com.mrchen.app.mygame.UI;

import java.util.ArrayList;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/06 11:22
 */

public class User {
    public Status status;
    public DataBean data;
    public String login_tips_box;
    public User(Status status, DataBean data,String login_tips_box) {
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
        public ArrayList<Slider> slider;
        public ArrayList<WawaList> wawa_list;
        public String errorCode;
        public String errorDesc;
        public String img;
        public String verify_token;
        public String is_show_voice;
        public String tips;
        public String successDesc;
        public String money;
        public String orderInfo;
        public String status;
        public String ordernumber;
        public String baseacturl;
        public String service_qq;
        public String isapplecheck;
        public String jpruleurl;
        public String jprulehtml;
        public String delaytime;
        public BarButton bar_button;
        public String other;
        public String nickname;
        public String username;
        public String avatar;
        public String wmoney;
        public String score;
        public String mobile;
        public String uid;
        public String vip;
        public String sex;
        public String age;
        public String summary;
        public String sid;

        public DataBean(ArrayList<Slider> slider,ArrayList<WawaList> wawa_list,String errorCode
        ,String errorDesc,String img,String verify_token,String is_show_voice,String tips,String successDesc
        ,String money,String orderInfo,String status,String ordernumber,String baseacturl,String service_qq
        ,String isapplecheck,String jpruleurl,String jprulehtml,String delaytime,BarButton bar_button
        ,String other,String nickname,String username,String avatar,String wmoney,String score,String mobile
        ,String uid,String vip,String sex,String age,String summary,String sid) {
            this.slider=slider;
            this.wawa_list=wawa_list;
            this.errorCode=errorCode;
            this.errorDesc=errorDesc;
            this.img=img;
            this.verify_token=verify_token;
            this.is_show_voice=is_show_voice;
            this.tips=tips;
            this.successDesc=successDesc;
            this.money=money;
            this.orderInfo=orderInfo;
            this.status=status;
            this.ordernumber=ordernumber;
            this.baseacturl=baseacturl;
            this.service_qq=service_qq;
            this.isapplecheck=isapplecheck;
            this.jpruleurl=jpruleurl;
            this.jprulehtml=jprulehtml;
            this.delaytime=delaytime;
            this.bar_button=bar_button;
            this.other=other;
            this.nickname=nickname;
            this.username=username;
            this.avatar=avatar;
            this.wmoney=wmoney;
            this.score=score;
            this.mobile=mobile;
            this.uid=uid;
            this.vip=vip;
            this.sex=sex;
            this.age=age;
            this.summary=summary;
            this.sid=sid;
        }
        public class BarButton{
            public String title;
            public String img;
            public String type;
            public String ygpid;
            public String url;
            public String islogin;
            public BarButton(String title,String img,String type,String ygpid,String url,String islogin){
                this.title=title;
                this.img=img;
                this.type=type;
                this.ygpid=ygpid;
                this.url=url;
                this.islogin=islogin;
            }
        }
        public class Slider {
            public String id;
            public String img;
            public String title;
            public String url;
            public String ygpid;
            public String type;
            public String islogin;
            public Slider(String id,String img,String title,String url,String ygpid,String type,String islogin){
                this.id=id;
                this.img=img;
                this.title=title;
                this.url=url;
                this.ygpid=ygpid;
                this.type=type;
                this.islogin=islogin;
            }
        }
        public class WawaList {
            public String id;
            public String img;
            public String price;
            public String title;
            public String playing;
            public WawaList(String id,String img,String price,String title,String playing){
                this.id=id;
                this.img=img;
                this.price=price;
                this.title=title;
                this.playing=playing;
            }
        }
    }
}



