package com.wawaji.common.https.api;


import com.wawaji.app.entity.UserEntity;
import com.wawaji.app.entity.VerifyEntity;
import com.wawaji.common.https.ApiHttpClient;
import com.wawaji.common.https.RequestHandler;

/**
 * 网络请求接口
 *
 * @author admin
 * @since 2016/6/8
 */
@SuppressWarnings("SameParameterValue")
public class RequestApi {

    /**
     * 配置接口
     */
    public static void config(RequestHandler handler, String tag) {
//        RequestParams params = new RequestParams();
//        params.put("target", AppConfig.APP_TARGET_OS);
//        params.put("v", DeviceUtils.getVersion());
        ApiHttpClient.doGet(RequestConfig.CONFIG, null, handler, tag);
    }

    /**
     * 手机号密码登录
     *
     * @param phone 手机号
     * @param pwd   密码
     */
    public static void loginPhonePwd(String phone, String pwd, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("pwd", pwd);
        ApiHttpClient.doGet(RequestConfig.LOGIN_PHONE_PWD, params.getParams(), handler, tag);
    }

    /**
     * 手机号登录或注册
     *
     * @param mobile 手机号
     * @param verify 验证码
     */
    public static void userPhoneLogin(String mobile, String verify, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("verify", verify);
        ApiHttpClient.doGet(RequestConfig.USER_PHONE_LOGIN, params.getParams(), handler, tag);
    }

    /**
     * 发送短信验证码
     *
     * @param entity   短信验证码
     * @param listener 请求回调
     * @param tag      标签
     */
    public static void sendPhoneVerfy(VerifyEntity entity, RequestHandler listener, String tag) {
        RequestParams params = new RequestParams();
        params.put("phone", entity.phone);
        params.put("is_voice", entity.isShowVoice);
        params.put("verify_token", entity.token);
        params.put("phone_verify", entity.verify);
        params.put("sync_type", entity.sysnType);
        params.put("type", entity.type);
        ApiHttpClient.doGet(RequestConfig.SEND_SMS, params.getParams(), listener, tag);
    }

    /**
     * 刷新验证码
     *
     * @param listener 请求回调
     * @param tag      标签
     */
    public static void refreshImage(RequestHandler listener, String tag) {
//        ApiHttpClient.doGet(InterfaceConfig.REFRESH_IMAGE, null, listener, tag);
    }

    /**
     * 首页数据
     *
     * @param page 页数
     */
    public static void homeData(int page, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        RequestParams params_page = new RequestParams();
//        RequestParams session = new RequestParams();
//        params.put("cid", "");
//        params.put("sort", "");
//        session.put("uid", "9");
//        session.put("sid", "999");
//        params.put("session", session.getParams());
        params_page.put("page", page);
        params.put("pagination", params_page.getParams());
        ApiHttpClient.doGet(RequestConfig.HOME_DATA, params.getParams(), handler, tag);
    }

    /**
     * 我的娃娃数据
     *
     * @param page 页数
     */
    public static void myData(int page, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        RequestParams params_page = new RequestParams();
        params_page.put("page", page);
        RequestParams params_id = new RequestParams();
        UserEntity entity = UserEntity.getCurUser();
        params_id.put("uid", entity.uid);
        params_id.put("sid", entity.sid);
        params.put("session", params_id.getParams());
        params.put("pagination", params_page.getParams());
        ApiHttpClient.doGet(RequestConfig.WINNERLIST, params.getParams(), handler, tag);
    }

    /**
     * 发送弹幕消息
     *
     * @param msg    消息
     * @param roomid 房间ID
     */
    public static void sendBarrage(String msg, String roomid, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        RequestParams params_session = new RequestParams();
        UserEntity entity = new UserEntity();
        params_session.put("uid", entity.uid);
        params_session.put("sid", entity.sid);
        params.put("roomid", roomid);
        params.put("session", params_session.getParams());
        params.put("msg", msg);
        ApiHttpClient.doGet(RequestConfig.SEND_MSG, params.getParams(), handler, tag);
    }
    public static void PlayGameOver(String roomid, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        RequestParams params_session = new RequestParams();
        UserEntity entity = new UserEntity();
        params_session.put("uid", entity.uid);
        params_session.put("sid", entity.sid);
        params.put("roomid", roomid);
        ApiHttpClient.doGet(RequestConfig.GAME_OVER, params.getParams(), handler, tag);
    }

    /**
     * 我的娃娃详情
     *
     * @param id id
     */
    public static void playDatails(String id, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        RequestParams params_session = new RequestParams();
        UserEntity entity = new UserEntity();
        params_session.put("uid", entity.uid);
        params_session.put("sid", entity.sid);
        params.put("id", id);
        params.put("session", params_session.getParams());
        ApiHttpClient.doGet(RequestConfig.WAWA_DETAIL, params.getParams(), handler, tag);
    }

    /**
     * 房间详情
     *
     * @param roomid 房间id
     */
    public static void roomDetail(String roomid, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        params.put("roomid", roomid);
        ApiHttpClient.doGet(RequestConfig.ROOM_DETAIL, params.getParams(), handler, tag);
    }

    /**
     * 开始游戏
     *
     * @param roomid 房间id
     */
    public static void startGame(String roomid, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        params.put("roomid", roomid);
        ApiHttpClient.doGet(RequestConfig.START_GAME, params.getParams(), handler, tag);
    }

    /**
     * 退出登录接口
     *
     * @param handler 回调线程
     * @param tag     标签
     */
    public static void userLogout(RequestHandler handler, String tag) {
        ApiHttpClient.doGet(RequestConfig.USER_LOGOUT, null, handler, tag);
    }

    /**
     * 用户资料刷新
     */
    public static void refreshInfo(RequestHandler handler, String tag) {
        ApiHttpClient.doGet(RequestConfig.REFRESH_INFO, null, handler, tag);
    }

    /**
     * 刷新房间
     *
     * @param roomid 房间id
     * @param time   时间戳
     */
    public static void refreshRoom(String roomid, String time, RequestHandler handler, String tag) {
        RequestParams params = new RequestParams();
        params.put("roomid", roomid);
        params.put("time", time);
        ApiHttpClient.doGet(RequestConfig.REFRESH_ROOM, params.getParams(), handler, tag);
    }

    /**
     * 取消请求
     *
     * @param tag 标签
     */
    public static void cancelRequestByTag(String tag) {
        ApiHttpClient.cancelRequestByTag(tag);
    }

    /**
     * 取消所有网络请求
     */
    public static void cancelAllRequest() {
        ApiHttpClient.cancelAllRequest();
    }


}