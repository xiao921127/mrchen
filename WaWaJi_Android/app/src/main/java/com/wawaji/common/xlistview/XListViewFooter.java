/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.wawaji.common.xlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wawaji.app.R;

public class XListViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;


    private View mContentView;
    //	private SimpleDraweeView mProgressBar;
    private ImageView mImageView;

//	private DraweeController mDraweeController; // 设置动态图片

    private RotateAnimation mRotateAnimation;

    public XListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public XListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setState(int state) {
//		mProgressBar.setVisibility(View.GONE);
        if (state == STATE_READY) {
            mImageView.setVisibility(View.VISIBLE);
            // mHintView.setText(R.string.release_to_refresh);
        } else if (state == STATE_LOADING) {
//			mImageView.setVisibility(View.INVISIBLE);
//			if (null == mDraweeController) {
//				mDraweeController = Fresco.newDraweeControllerBuilder()
//						.setAutoPlayAnimations(true)//自动播放动画
//						.setUri(Uri.parse(""))//路径
//						.build();
//			}
//			mProgressBar.setController(mDraweeController);
//			mProgressBar.setVisibility(View.VISIBLE);
            mImageView.clearAnimation();
            mImageView.setImageLevel(200);
            mImageView.startAnimation(mRotateAnimation);
        } else {
            mImageView.clearAnimation();
            mImageView.setVisibility(View.GONE);
//			mHintView.setVisibility(View.GONE);
            // mHintView.setText(R.string.xlistview_footer_hint_normal);
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0)
            return;
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        if (height < 150) {
            lp.bottomMargin = height;
        } else {
            lp.bottomMargin = 150;
        }
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        return lp.bottomMargin;
    }

    /**
     * normal status
     */
    public void normal() {
//		mProgressBar.setVisibility(View.GONE);
        mImageView.clearAnimation();
        mImageView.setVisibility(View.GONE);
    }

    /**
     * loading status
     */
    public void loading() {
        mImageView.setVisibility(View.VISIBLE);
        mImageView.clearAnimation();
        mImageView.startAnimation(mRotateAnimation);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.height = 0;
//		mProgressBar.setVisibility(View.GONE);
        mImageView.clearAnimation();
        mImageView.setVisibility(View.GONE);
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }

    @SuppressLint("InflateParams")
    private void initView(Context context) {
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.view_listview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = moreView.findViewById(R.id.head_content);
//		mProgressBar = (SimpleDraweeView) moreView.findViewById(R.id.progerssbar);
        mImageView = (ImageView) moreView.findViewById(R.id.arrow);

        mRotateAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(800);//设置动画持续时间
        mRotateAnimation.setRepeatCount(-1);//设置重复次数
        mRotateAnimation.setFillAfter(false);//动画执行完后是否停留在执行完的状态
        mRotateAnimation.setInterpolator(new LinearInterpolator());//设置动画速率为匀速
    }


    /**
     * 设置进程
     *
     * @param progress
     */
    public void setProgress(int progress) {
        mImageView.clearAnimation();
        if (progress >= 0) {
            mImageView.setImageLevel(progress);
        } else {
            mImageView.setImageLevel(0);
        }
    }

}