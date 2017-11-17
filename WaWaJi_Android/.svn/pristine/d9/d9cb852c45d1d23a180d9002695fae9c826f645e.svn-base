package com.wawaji.common.https;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.wawaji.app.AppApplication;
import com.wawaji.app.AppConfig;
import com.wawaji.app.entity.UserEntity;
import com.wawaji.common.https.api.RequestConfig;
import com.wawaji.common.utils.DeviceUtils;
import com.wawaji.common.utils.EncodeUtils;
import com.wawaji.common.utils.LogUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 网络请求类
 *
 * @author admin
 * @version 2.0<网络请求处理类>
 * @since 2016/6/12
 */
@SuppressWarnings({"unused", "StringBufferReplaceableByString"})
public final class ApiHttpClient {

    private static final String KEY_API_JSON = "&json="; // 拼接Json字段
    private static final String KEY_SIGN_JSON = "json="; // 拼接Json字段
    private static final String KEY_SIGN_SYMBOL = "&"; // 签名
    private static final String KEY_SIGN_URL = "s="; // 签名
    private static final String KEY_SIGN_NOW = "&now_time="; // 签名
    private static final String KEY_SIGN_SIGN = "&sign="; // 签名
    //    private static final String KEY_NO_LOGIN = "no-login"; // 没登录返回字段

    private static final String KEY_UPLOAD_IMAGE = "img"; // 上传图片参数
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png"); // 图片类型

//    // 私有化构造方法
//    private ApiHttpClient() {
//        // This utility class is not publicly instantiable
//    }

    /**
     * 得到网络接口地址
     *
     * @param api 接口地址
     * @return str
     */
    private static String getApiUrl(String api) {
        String url = AppApplication.get(AppConfig.APP_API_URL, null);
        if (TextUtils.isEmpty(url)) {
            url = RequestConfig.DEFAULT_API_URL;
        }
        url = url + RequestConfig.BASE_URL + api;
        return url;
    }

    /**
     * Get请求
     *
     * @param url    接口
     * @param params 参数
     */
    public static void doGet(String url, JSONObject params, RequestHandler listener, String tag) {
        doRequest(url, Request.Method.GET, params, listener, tag);
    }

    /**
     * Post请求
     *
     * @param url    接口
     * @param params 参数
     */
    public static void doPost(String url, JSONObject params, RequestHandler listener, String tag) {
        doRequest(url, Request.Method.POST, params, listener, tag);
    }

    /**
     * 请求网络
     */
    private static void doRequest(String url, int type, JSONObject params, final RequestHandler listener, String tag) {

        // 生成签名
        StringBuilder str_sign = new StringBuilder(); // 拼接地址

        StringBuilder str_url = new StringBuilder(); // 网络地址
        String api_url = getApiUrl(url); // 获得网络请求地址
        str_url.append(api_url);

        String test_url = api_url;
        // 添加Get请求参数
        if (null != params && Request.Method.GET == type) {
            try {
                test_url = api_url + KEY_API_JSON + params.toString();
                // 拼接所需参数
                String str = URLEncoder.encode(params.toString(), AppConfig.FORMAT_CODE); //先对中文进行UTF-8编码
                str_url.append(KEY_API_JSON);
                str_url.append(str);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            str_sign.append(KEY_SIGN_JSON);
            str_sign.append(params.toString());
            str_sign.append(KEY_SIGN_SYMBOL);
        }
        str_sign.append(KEY_SIGN_URL);
        str_sign.append(url);
        str_sign.append(KEY_SIGN_NOW);
        long time = System.currentTimeMillis() / 1000;
        str_sign.append(time);
        String sign = EncodeUtils.MD5.hexdigest(str_sign.toString());
        str_url.append(KEY_SIGN_SIGN);
        str_url.append(sign);
        // 网络地址
        api_url = str_url.toString();
        // 创建网络请求
        StrRequest request = new StrRequest(type, api_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("MrChen", "@@@@@" + s);
                if (null != listener) {
                    listener.onSuccess(s);
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (null != listener) {
                    listener.onFailure();
                }
            }

        });

        // 添加Post请求参数
        if (null != params && Request.Method.POST == type) {
            test_url = api_url + KEY_API_JSON + params.toString();
            request.addParams(params);
        }

        // 添加用户和设备参数
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConfig.HEADER_VERSION_KEY, DeviceUtils.getVersionCode());
            jsonObject.put(AppConfig.HEADER_TARGET_KEY, AppConfig.APP_TARGET_OS);
            jsonObject.put(AppConfig.HEADER_DEVICE_KEY, DeviceUtils.getDeviceId());
            jsonObject.put(AppConfig.HEADER_SCREEN_KEY, AppApplication.getDisplaySize());
            jsonObject.put(AppConfig.HEADER_CHANNEL_KEY, DeviceUtils.getAppMetaData(AppConfig.META_DATA));
            jsonObject.put(AppConfig.HEADER_TIME_KEY, time);
            if (UserEntity.isLogin()) {
                UserEntity user = UserEntity.getCurUser();
                jsonObject.put("uid", user.uid);
                jsonObject.put("sid", user.sid);
                test_url = test_url + "&uid=" + user.uid;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addHeader(AppConfig.HEADER_LOGIN_KEY, jsonObject.toString());
        LogUtils.d("test_url:" + test_url);

        // 设置请求相关参数
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        request.setTag(tag);
        // 把这个请求加入请求队列
        addRequest(request);
    }

    /**
     * 上传图片
     *
     * @param url      接口地址
     * @param params   参数
     * @param urls     图片地址
     * @param listener 接口回调
     */
    public static void uploadImages(String url, JSONObject params, String[] urls, final RequestHandler listener) {
        StringBuilder str_url = new StringBuilder(); // 网络地址
        String api_url = getApiUrl(url); // 获得网络请求地址
        str_url.append(api_url);

        // 添加请求参数
        if (null != params) {
            try {
                // 判断是否登录
                JSONObject jsonObject = new JSONObject();
                if (UserEntity.isLogin()) {
                    UserEntity u = UserEntity.getCurUser();
                    jsonObject = new JSONObject();
                    jsonObject.put("uid", u.uid);
                    jsonObject.put("sid", u.sid);
                }
                params.put("session", jsonObject);
                String test_url = str_url.toString() + KEY_API_JSON + params.toString();
                String str = URLEncoder.encode(params.toString(), AppConfig.FORMAT_CODE); //先对中文进行UTF-8编码
                str_url.append(KEY_API_JSON);
                str_url.append(str);
                LogUtils.e("uploadimage url:" + test_url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        api_url = str_url.toString();

        // 设置请求
        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
        multipartBody.setType(MultipartBody.FORM);
        // 添加图片参数
        if (null != urls) {
            int len = urls.length;
            for (String url1 : urls) {
                File file = new File(url1);
                multipartBody.addFormDataPart(KEY_UPLOAD_IMAGE, file.getName()
                        , RequestBody.create(MEDIA_TYPE_PNG, file));
            }
        }

        // 构建请求体
        RequestBody requestBody = multipartBody.build();
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        requestBuilder.url(api_url); // 添加Url地址
        requestBuilder.post(requestBody);
        okhttp3.Request request = requestBuilder.build();
        int timeoutMs = 5 * 1000;
        // 设置请求参数
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .connectTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .writeTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .build();
        // 网络请求回调
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (null != listener) {
                    listener.onFailure();
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String str = response.body().string();
                if (null != listener) {
                    listener.onSuccess(str);
                }
            }

        });
    }

    /**
     * 添加Request请求到队列中，因为我们使用OkHttp作为Volley的传输层，所以增加一个HttpStack参数
     *
     * @param request 网络请求
     */
    private static void addRequest(Request<?> request) {
        if (null != request) {
            if (null == StaticVariable.getRequestQueue()) {
                StaticVariable.setRequestQueue(Volley.newRequestQueue(AppApplication.sContext));
                Volley.newRequestQueue(AppApplication.sContext, new OkHttpStack(new OkHttpClient()));
            }
            StaticVariable.getRequestQueue().add(request);
        }
    }

    /**
     * 根据标签取消指定Request
     *
     * @param tag 标签
     */
    public static void cancelRequestByTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            if (StaticVariable.getRequestQueue() != null) {
                StaticVariable.getRequestQueue().cancelAll(tag);
            }
        }
    }

    /**
     * 取消所有Request
     */
    @SuppressWarnings("WeakerAccess")
    public static void cancelAllRequest() {
        RequestQueue localRequestQueue = StaticVariable.getRequestQueue();
        if (localRequestQueue != null) {
            localRequestQueue.cancelAll(new RequestQueue.RequestFilter() {

                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
            localRequestQueue.stop();
        }
    }

    /**
     * OkHttpStack堆栈
     * <p/>
     * An {@link com.android.volley.toolbox.HttpStack HttpStack}
     * implementation which uses OkHttp as its transport.
     */
    @SuppressWarnings("deprecation")
    private static class OkHttpStack extends HurlStack {

        private final OkHttpClient mClient;

        OkHttpStack(OkHttpClient client) {
            this.mClient = client;
        }

        private static HttpEntity entityFromOkHttpResponse(okhttp3.Response response) throws IOException {
            BasicHttpEntity entity = new BasicHttpEntity();
            ResponseBody body = response.body();

            entity.setContent(body.byteStream());
            entity.setContentLength(body.contentLength());
            entity.setContentEncoding(response.header("Content-Encoding"));

            if (body.contentType() != null) {
                entity.setContentType(body.contentType().type());
            }
            return entity;
        }

        @SuppressWarnings("deprecation")
        private static void setConnectionParametersForRequest
                (okhttp3.Request.Builder builder, Request<?> request)
                throws IOException, AuthFailureError {
            switch (request.getMethod()) {
                case Request.Method.DEPRECATED_GET_OR_POST:
                    byte[] postBody = request.getPostBody();
                    if (postBody != null) {
                        builder.post(RequestBody.create
                                (MediaType.parse(request.getPostBodyContentType()), postBody));
                    }
                    break;

                case Request.Method.GET:
                    builder.get();
                    break;

                case Request.Method.DELETE:
                    builder.delete();
                    break;

                case Request.Method.POST:
                    builder.post(createRequestBody(request));
                    break;

                case Request.Method.PUT:
                    builder.put(createRequestBody(request));
                    break;

                case Request.Method.HEAD:
                    builder.head();
                    break;

                case Request.Method.OPTIONS:
                    builder.method("OPTIONS", null);
                    break;

                case Request.Method.TRACE:
                    builder.method("TRACE", null);
                    break;

                case Request.Method.PATCH:
                    builder.patch(createRequestBody(request));
                    break;

                default:
                    throw new IllegalStateException("Unknown method type.");
            }
        }

        private static RequestBody createRequestBody(Request request) throws AuthFailureError {
            final byte[] body = request.getBody();
            if (body == null) return null;

            return RequestBody.create(MediaType.parse(request.getBodyContentType()), body);
        }

        private static ProtocolVersion parseProtocol(final Protocol protocol) {
            switch (protocol) {
                case HTTP_1_0:
                    return new ProtocolVersion("HTTP", 1, 0);
                case HTTP_1_1:
                    return new ProtocolVersion("HTTP", 1, 1);
                case SPDY_3:
                    return new ProtocolVersion("SPDY", 3, 1);
                case HTTP_2:
                    return new ProtocolVersion("HTTP", 2, 0);
            }

            throw new IllegalAccessError("Unkwown protocol");
        }

        @Override
        public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
                throws IOException, AuthFailureError {
            int timeoutMs = request.getTimeoutMs();
            OkHttpClient client = mClient.newBuilder()
                    .readTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                    .connectTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                    .writeTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                    .build();

            okhttp3.Request.Builder okHttpRequestBuilder = new okhttp3.Request.Builder();
            Map<String, String> headers = request.getHeaders();
            for (final String name : headers.keySet()) {
                okHttpRequestBuilder.addHeader(name, headers.get(name));
            }

            for (final String name : additionalHeaders.keySet()) {
                okHttpRequestBuilder.addHeader(name, additionalHeaders.get(name));
            }

            setConnectionParametersForRequest(okHttpRequestBuilder, request);

            okhttp3.Request okhttp3Request = okHttpRequestBuilder.url(request.getUrl()).build();
            okhttp3.Response okHttpResponse = client.newCall(okhttp3Request).execute();

            StatusLine responseStatus = new BasicStatusLine
                    (
                            parseProtocol(okHttpResponse.protocol()),
                            okHttpResponse.code(),
                            okHttpResponse.message()
                    );

            BasicHttpResponse response = new BasicHttpResponse(responseStatus);
            response.setEntity(entityFromOkHttpResponse(okHttpResponse));

            Headers responseHeaders = okHttpResponse.headers();
            for (int i = 0, len = responseHeaders.size(); i < len; i++) {
                final String name = responseHeaders.name(i), value = responseHeaders.value(i);
                if (name != null) {
                    response.addHeader(new BasicHeader(name, value));
                }
            }

            return response;
        }
    }

}