package com.wawaji.common.https;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.wawaji.common.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * String请求
 *
 * @author admin
 * @version V1.0 <网络请求>
 * @since 2016/6/24
 */
@SuppressWarnings("SameParameterValue")
public class StrRequest extends StringRequest {

    private final static String STR_REQUEST_DATE = "Date";
    private final static String STR_REQUEST_TIME = "server_time";

    private final Map<String, String> mHeader = new HashMap<>(); // 请求头文件
    private final Map<String, String> mParams = new HashMap<>(); // Post请求

    // 构造方法
    StrRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
//        mHeader.put("Charset", "utf-8");
        return mHeader;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        // 可用于添加Post请求中的参数
        return mParams;
    }

    /**
     * Post添加参数
     *
     * @param jsonObject 参数名
     */
    void addParams(JSONObject jsonObject) {
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.hasNext());
            String value = jsonObject.optString(key);
            mParams.put(key, value);
        }
    }

    /**
     * Header添加属性
     *
     * @param name  参数名
     * @param value 参数
     */
    void addHeader(String name, String value) {
        mHeader.put(name, value);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            parsed = new String(response.data);
        }
        String date = response.headers.get(STR_REQUEST_DATE);
        if (!TextUtils.isEmpty(date)) {
            String info = parsed;
            try {
                date = DateUtils.gmtToDate(date);
                JSONObject jb = new JSONObject(parsed);
                jb.put(STR_REQUEST_TIME, date);
                parsed = jb.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                parsed = info;
            }
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

}