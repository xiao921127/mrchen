package com.wawaji.app.entity;

import com.wawaji.common.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 房间详情实体
 * <p>
 * Created by admin on 2017/11/7.
 */

public class RoomDetailsEntity implements BaseEntity {

//    public DollsEntity entity = new DollsEntity();

    public ArrayList<PlayerRecordEntity> records = new ArrayList<>();//最近抓中记录

    public ArrayList<PlayerRecordEntity> barrageList = new ArrayList<>();//弹幕列表

    public ArrayList<UserEntity> userList=new ArrayList<>();//玩家列表

    public String roomid;//房间id

    public String wid;//

    public String title;

    public String price;//每次花费

    public String video_url_1;//摄像头1地址

    public String video_url_2;//摄像头2地址

    public String token;//操作设备所需token

    public String url;//操作使用的websocket服务器地址

    public String camera_url;//视频推流地址

    public String count;//房间人数

    public String winner;//1 抓中2未抓中0:等着看吧

    public String ttl;//剩余时间

    public String isself;//当前游戏的用户是否自己

    public String musicurl;//背景音乐

    @Override
    public void fromJson(String json) throws JSONException {
        JSONObject jb = new JSONObject(json);

        token = jb.optString("token");
        url = jb.optString("url");
        camera_url = jb.optString("camera_url");
        count = jb.optString("count");
        winner = jb.optString("winner");
        ttl = jb.optString("ttl");
        isself = jb.optString("isself");
        musicurl = jb.optString("musicurl");

        JSONObject wawainfo = jb.optJSONObject("wawainfo");
        if (null != wawainfo) {
            roomid = wawainfo.optString("roomid");
            wid = wawainfo.optString("wid");
            title = wawainfo.optString("title");
            price = wawainfo.optString("price");
            video_url_1 = wawainfo.optString("video_url_1");
            video_url_2 = wawainfo.optString("video_url_2");
        }

        JSONArray winnerlist = jb.optJSONArray("winnerlist");
        if (null != winnerlist) {
            int length = winnerlist.length();
            for (int i = 0; i < length; i++) {
                PlayerRecordEntity entity = new PlayerRecordEntity();
                entity.fromJson(winnerlist.optString(i));
                records.add(entity);
            }
        }

        JSONArray msg = jb.optJSONArray("msg");
        if (null != msg) {
            int length1 = msg.length();
            for (int i = 0; i < length1; i++) {
                PlayerRecordEntity entity = new PlayerRecordEntity();
                entity.fromJson(msg.optString(i));
                barrageList.add(entity);
            }
        }

        JSONArray user = jb.optJSONArray("users");
        if (null != user) {
            int length2 = user.length();
            for (int i = 0; i < length2; i++) {
                UserEntity entity = new UserEntity();
                entity.fromJson(user.optString(i));
                userList.add(entity);
            }
        }
    }
}
