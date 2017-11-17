package com.wawaji.common.https;


import com.android.volley.RequestQueue;

/**
 * Volley堆栈
 *
 * @version V1.0 <描述当前版本功能>
 * @author admin
 * @since  2016/6/13
 */
@SuppressWarnings("WeakerAccess")
public class StaticVariable {

    private static RequestQueue mRequestQueue;

    public static void clear() {
        ApiHttpClient.cancelAllRequest();
        mRequestQueue = null;
    }

    public static synchronized RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static void setRequestQueue(RequestQueue paramRequestQueue) {
        mRequestQueue = paramRequestQueue;
    }
}
