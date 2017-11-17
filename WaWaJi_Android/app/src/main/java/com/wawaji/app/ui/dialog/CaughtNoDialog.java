package com.wawaji.app.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.wawaji.app.AppApplication;
import com.wawaji.app.R;
import com.wawaji.common.base.dialog.BaseDialog;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/14 17:36
 */

public class CaughtNoDialog extends BaseDialog implements View.OnClickListener{

    private ImageView continue_dialog;
    private ImageView rest_dialog;

    public CaughtNoDialog(Context context) {
        super(context);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continue_dialog:
                //继续游戏
                if (null != mOnButtonClick) {
                    mOnButtonClick.onClickCancel();
                }
                break;
            case R.id.rest_dialog:
                //休息一会
                if (null != mOnButtonClick) {
                    mOnButtonClick.onClickSure();
                }
                break;
        }
    }

    @Override
    public void initView() {
        continue_dialog=(ImageView)mDialog.findViewById(R.id.continue_dialog);
        rest_dialog=(ImageView)mDialog.findViewById(R.id.rest_dialog);
    }

    @Override
    public void setListener() {
        continue_dialog.setOnClickListener(this);
        rest_dialog.setOnClickListener(this);
    }

    @Override
    protected void initDialog() {
        int width = AppApplication.getDisplayWidth() * 1;
        setWidth(width);
        setContentView(R.layout.view_dialog_caught_no);
        setGravity(Gravity.CENTER);
        mDialog.setCanceledOnTouchOutside(false);
    }
}
