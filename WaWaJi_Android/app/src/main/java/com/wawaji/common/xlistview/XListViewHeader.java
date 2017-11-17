package com.wawaji.common.xlistview;

/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wawaji.app.R;


public class XListViewHeader extends LinearLayout {

    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    //	private SimpleDraweeView mProgressBar;
    private int mState = STATE_NORMAL;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

//	private DraweeController mDraweeController; // 设置动态图片

    private RotateAnimation mRotateAnimation;

    public XListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public XListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @SuppressLint("InflateParams")
    private void initView(Context context) {

        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.view_listview_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
//		mProgressBar = (SimpleDraweeView) findViewById(R.id.xlistview_header_progressbar);

        mArrowImageView.setImageLevel(0);

        mRotateAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(800);//设置动画持续时间
        mRotateAnimation.setRepeatCount(-1);//设置重复次数
        mRotateAnimation.setFillAfter(false);//动画执行完后是否停留在执行完状态
        mRotateAnimation.setInterpolator(new LinearInterpolator());//设置动画速率为匀速
    }

    public void setProgress(float progress) {
        if (progress >= 0) {
            mArrowImageView.setImageLevel((int) progress);
        } else {
            mArrowImageView.setImageLevel(0);
        }
    }

    public void setState(int state) {
        if (state == mState) return;

        if (state == STATE_REFRESHING) {
            mArrowImageView.clearAnimation();
//			mArrowImageView.setVisibility(View.INVISIBLE);
//			mProgressBar.setVisibility(View.VISIBLE);
//			if (null == mDraweeController) {
//				mDraweeController = Fresco.newDraweeControllerBuilder()
//						.setAutoPlayAnimations(true)//自动播放动画
//						.setUri(Uri.parse(""))//路径
//						.build();
//			}
//			mProgressBar.setController(mDraweeController);
            mArrowImageView.setImageLevel(480);
            mArrowImageView.startAnimation(mRotateAnimation);
        }
//		else {
//			mArrowImageView.setVisibility(View.VISIBLE);
//			mProgressBar.setVisibility(View.INVISIBLE);
//		}

        switch (state) {
            case STATE_NORMAL:
//                if (mState == STATE_READY) {
//				mArrowImageView.startAnimation(mRotateDownAnim);
//                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                break;
            case STATE_READY:
                if (mState != STATE_READY) {
                    mArrowImageView.clearAnimation();
//				mArrowImageView.startAnimation(mRotateUpAnim);
                }
                break;
            case STATE_REFRESHING:
                break;
            default:
                break;
        }

        mState = state;
    }

    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        if (height < 300) {
            lp.height = height;
        } else {
            lp.height = 300;
        }
        mContainer.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return mContainer.getHeight();
    }

}
