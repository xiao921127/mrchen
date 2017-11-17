package com.wawaji.common.https.entity;

import android.text.TextUtils;

import com.wawaji.common.base.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 列表页数实体类
 *
 * @version V1.0 <列表页数>
 * @author admin
 * @since  2016/7/4
 */
@SuppressWarnings("WeakerAccess")
public class Paginated implements BaseEntity {

    public int more = 0; // 下一页数字

    public int total = 0; // 总页数

    public long stamp; // 时间戳

    @Override
    public void fromJson(String json) throws JSONException {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        JSONObject jb = new JSONObject(json);
        more = jb.optInt("more");
        total = jb.optInt("total");
        stamp = jb.optLong("timestamp");
    }
}
