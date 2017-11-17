package com.wawaji.app.contract;

import com.wawaji.app.entity.PlayEntity;
import com.wawaji.app.interf.OnRequestChangeListener;
import com.wawaji.common.base.BaseModel;
import com.wawaji.common.base.BasePresenter;
import com.wawaji.common.base.BaseView;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/13 11:31
 */

public interface PlayDetailsContract {

    /**
     * 视图
     */
    interface View extends BaseView {

        /**
         * 设置相关数据
         *
         */
        void onBarrage(PlayEntity playDetailsEntity);
    }

    /**
     * 控制器
     */
    interface Presenter extends BasePresenter {

        /**
         * 获取相关数据
         *
         * @param id 接口需要的id
         */
        void getPlayDetails(String id);

    }

    /**
     * 模型
     */
    interface Model extends BaseModel {

        /**
         * 获取房间详情
         *
         * @param id   房间id
         * @param listener 请求回调
         */
        void getPlayDetails(String id,OnRequestChangeListener<PlayEntity> listener);
    }
}
