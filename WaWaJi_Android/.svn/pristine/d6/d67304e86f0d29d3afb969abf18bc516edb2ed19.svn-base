package com.wawaji.common.https.api;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * 请求参数
 * @author admin
 * @since 2016/6/8
 */
@SuppressWarnings("WeakerAccess")
public class RequestParams {

    // Json数据
    private JSONObject json = null;

    /**
     * 构造方法
     */
    public RequestParams() {
        if (null == json) {
            json = new JSONObject();
        }
    }

    /**
     * 添加参数
     *
     * @param name 参数名
     * @param value 参数
     */
    public void put(String name, int value) {
        try {
            json.put(name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加参数
     *
     * @param name 参数名
     * @param value 参数
     */
    public void put(String name, double value) {
        try {
            json.put(name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加参数
     *
     * @param name 参数名
     * @param value 参数
     */
    public void put(String name, long value) {
        try {
            json.put(name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加参数
     *
     * @param name 参数名
     * @param value 参数
     */
    public void put(String name, boolean value) {
        try {
            json.put(name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加参数
     *
     * @param name 参数名
     * @param value 参数
     */
    public void put(String name, String value) {
        if(TextUtils.isEmpty(value)){
            return;
        }
        try {
            json.put(name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加参数
     *
     * @param name 参数名
     * @param object 参数
     */
    public void put(String name, Object object) {
        try {
            json.put(name, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取json字符串
     *
     * @return str
     */
    public String getParamsStr() {
        return json.toString();
    }

    /**
     * 获取json对象
     * @return JSONObject
     */
    public JSONObject getParams(){
        return json;
    }

}