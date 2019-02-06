package com.riningan.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.riningan.widget.circularprogressbar.R;


/**
 * Created by Vadim Akhmarov on 19.07.2017.
 * Project CircularProgressBarProject
 * Classname CircularProgressBar
 * Version 1.0
 * Copyright All rights reserved.
 */

@SuppressWarnings("unused")
public class CircularProgressBar extends View {
    private static final int START_ANGLE = 270;
    private static final int STOP_ANGLE = 360;

    public enum StateEnum {
        Loading, ProgressDirect, ProgressInvert
    }

    private StateEnum mState = StateEnum.Loading;

    private float mProgressValue = 0;

    private float mProgressWidth;
    private int mProgressColor;

    private float mBackgroundWidth;
    private int mBackgroundColor;

    private RectF mRectF;
    private Paint mBackgroundPaint;
    private Paint mProgressPaint;

    private ObjectAnimator mLoadingAnimator;

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, 0, 0);
        int state = typedArray.getInt(R.styleable.CircularProgressBar_cpb_state, 1);
        if (state == 1) {
            mState = StateEnum.Loading;
            mProgressValue = 0;
        } else {
            if (state == 2) {
                mState = StateEnum.ProgressDirect;
            } else {
                mState = StateEnum.ProgressInvert;
            }
            mProgressValue = typedArray.getFloat(R.styleable.CircularProgressBar_cpb_progress_value, mProgressValue);
        }
        mProgressWidth = typedArray.getDimension(R.styleable.CircularProgressBar_cpb_progress_width, getResources().getDimension(R.dimen.default_cpb_progress_width));
        mBackgroundWidth = typedArray.getDimension(R.styleable.CircularProgressBar_cpb_background_width, getResources().getDimension(R.dimen.default_cpb_background_width));
        mProgressColor = typedArray.getInt(R.styleable.CircularProgressBar_cpb_progress_color, Color.BLACK);
        mBackgroundColor = typedArray.getInt(R.styleable.CircularProgressBar_cpb_background_color, Color.GRAY);

        typedArray.recycle();

        mRectF = new RectF();

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(mBackgroundWidth);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);

        if (mState == StateEnum.Loading) {
            mLoadingAnimator = ObjectAnimator.ofFloat(this, "progress", 100);
            mLoadingAnimator.setDuration(2000);
            mLoadingAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            mLoadingAnimator.setInterpolator(new LinearInterpolator());
            mLoadingAnimator.start();
        } else {
            mLoadingAnimator = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(mRectF, mBackgroundPaint);
        if (mState == StateEnum.Loading) {
            if (mProgressValue <= 50) {
                float angle = 360 * mProgressValue / 50;
                canvas.drawArc(mRectF, START_ANGLE, angle, false, mProgressPaint);
            } else {
                float angle = 360 * (mProgressValue - 50) / 50;
                canvas.drawArc(mRectF, START_ANGLE + angle, STOP_ANGLE - angle, false, mProgressPaint);
            }
        } else if (mState == StateEnum.ProgressDirect) {
            float angle = 360 * mProgressValue / 100;
            canvas.drawArc(mRectF, START_ANGLE, angle, false, mProgressPaint);
        } else {
            float angle = 360 * mProgressValue / 100 - 360;
            canvas.drawArc(mRectF, START_ANGLE, angle, false, mProgressPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        float highStroke = (mProgressWidth > mBackgroundWidth) ? mProgressWidth : mBackgroundWidth;
        mRectF.set(0 + highStroke / 2, 0 + highStroke / 2, min - highStroke / 2, min - highStroke / 2);
    }


    public StateEnum getState() {
        return mState;
    }

    public void setState(StateEnum state) {
        mState = state;
        stopLoadingAnimation();
        if (mState == StateEnum.Loading) {
            mProgressValue = 0;
            mLoadingAnimator = ObjectAnimator.ofFloat(this, "progress", 100);
            mLoadingAnimator.setDuration(2000);
            mLoadingAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            mLoadingAnimator.setInterpolator(new LinearInterpolator());
            mLoadingAnimator.start();
        }
        requestLayout();
        invalidate();
    }


    public float getProgressWidth() {
        return mProgressWidth;
    }

    public void setProgressWidth(float progressWidth) {
        mProgressWidth = progressWidth;
        mProgressPaint.setStrokeWidth(progressWidth);
        requestLayout();
        invalidate();
    }


    public float getBackgroundWidth() {
        return mBackgroundWidth;
    }

    public void setBackgroundWidth(float backgroundWidth) {
        mBackgroundWidth = backgroundWidth;
        mBackgroundPaint.setStrokeWidth(backgroundWidth);
        requestLayout();
        invalidate();
    }


    @ColorInt
    public int getProgressColor() {
        return mProgressColor;
    }

    public void setProgressColor(@ColorInt int progressColor) {
        mProgressColor = progressColor;
        mProgressPaint.setColor(progressColor);
        invalidate();
        requestLayout();
    }


    @ColorInt
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        mBackgroundColor = backgroundColor;
        mBackgroundPaint.setColor(backgroundColor);
        invalidate();
        requestLayout();
    }


    public float getProgress() {
        return mProgressValue;
    }

    public void setProgress(float progress) {
        mProgressValue = (progress <= 100) ? progress : 100;
        invalidate();
    }

    public void setProgressWithAnimation(float progress) {
        setProgressWithAnimation(progress, StateEnum.ProgressDirect);
    }

    public void setProgressWithAnimation(float progress, StateEnum state) {
        setProgressWithAnimation(progress, 1500);
    }

    public void setProgressWithAnimation(float progress, int duration) {
        setProgressWithAnimation(progress, 1500, StateEnum.ProgressDirect);
    }

    public void setProgressWithAnimation(float progress, int duration, StateEnum state) {
        if (mState != state) {
            setState(StateEnum.ProgressDirect);
        }
        if (mState == StateEnum.Loading) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    public void stopLoadingAnimation() {
        if (mLoadingAnimator != null) {
            mLoadingAnimator.end();
            mLoadingAnimator = null;
        }
    }
}

