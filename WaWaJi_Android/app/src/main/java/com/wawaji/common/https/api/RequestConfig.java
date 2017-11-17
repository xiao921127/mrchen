package com.wawaji.common.https.api;

/**
 * 网络请求接口名
 * <p>
 * Created by admin on 2017/11/3.
 */

public class RequestConfig {

    // 默认域名
    public static String DEFAULT_API_URL = "http://192.168.1.111:87/"; // 接口域名

    // 基础地址
    public final static String BASE_URL = "home/m?&act=";

    // 配置接口
    static final String CONFIG = "config";

    //手机号密码登录
    static final String LOGIN_PHONE_PWD = "login_phone_pwd";

    //手机号登录或注册
    static final String USER_PHONE_LOGIN = "user_phone_login";

    //发送短信验证码
    static final String SEND_SMS = "sendSms";

    //第三方登录
    static final String SYNC_LOGIN = "sync_login";

    // 首页接口
    static final String HOME_DATA = "home_data";

    //MY接口
    static final String WINNERLIST = "winnerlist";

    //弹幕接口
    static final String SEND_MSG = "send_msg";

    //房间详情接口
    static final String ROOM_DETAIL = "room_detail";

    //开始游戏
    static final String START_GAME = "start_game";

    //退出登录
    static final String USER_LOGOUT = "user_logout";

    //我的娃娃接口
    static final String WAWA_DETAIL = "wawa_detail";

    //用户资料刷新
    static final String REFRESH_INFO = "refresh_info";

    //用户资料刷新
    static final String GAME_OVER= "game_over";

    //刷新房间
    static final String REFRESH_ROOM = "refresh_room";
}
