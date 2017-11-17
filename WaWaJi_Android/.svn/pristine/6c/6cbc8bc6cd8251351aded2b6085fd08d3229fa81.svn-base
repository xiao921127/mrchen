package com.wawaji.app.interf;


import com.wawaji.common.https.entity.HttpResponse;

import java.util.ArrayList;

/**
 * 列表数据监听
 *
 * Created by m on 2017/1/17.
 */

public interface BaseListChangeListener<T> {

    void onRefresh(ArrayList<T> list, int more);

    void onLoad(ArrayList<T> list);

    void onFailure();

    void onError(HttpResponse result);

    void onNoMoreData();

}
