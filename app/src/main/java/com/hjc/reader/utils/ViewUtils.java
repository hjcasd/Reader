package com.hjc.reader.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.hjc.reader.model.ImageViewInfo;


/**
 * @Author: HJC
 * @Date: 2019/1/30 17:46
 * @Description: 动画执行类
 */
public class ViewUtils {
    private static final long ANIM_TIME = 300;

    public static void startExitViewScaleAnim(Context context, View target, ImageViewInfo imageViewInfo, Animator.AnimatorListener animatorListener) {
        if (imageViewInfo.getRect() == null) {
            LogUtils.e("rect is null");
            return;
        }
        int width = imageViewInfo.getRect().width();
        int height = imageViewInfo.getRect().height();
        int localX = imageViewInfo.getRect().left;
        int localY = imageViewInfo.getRect().top;

        float windowScale = getWindowScale(context);
        float imgScale = getImgScale(width, height);

        float pivotX;
        float pivotY;
        float animImgStartHeight;
        float animImgStartWidth;
        float originalScale = getCurrentPicOriginalScale(context, imageViewInfo);
        if (imgScale >= windowScale) {
            animImgStartHeight = getWindowHeight(context) * originalScale;
            pivotX = localX / (1 - originalScale);
            pivotY = (localY - (animImgStartHeight - height) / 2) / (1 - originalScale);
        } else {
            animImgStartWidth = getWindowWidth(context) * originalScale;
            pivotX = (localX - (animImgStartWidth - width) / 2) / (1 - originalScale);
            pivotY = localY / (1 - originalScale);
        }
        target.setPivotX(pivotX);
        target.setPivotY(pivotY);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(target, View.SCALE_X, target.getScaleX(), originalScale);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(target, View.SCALE_Y, target.getScaleY(), originalScale);
        ObjectAnimator animatorTransX = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, target.getTranslationX() + (getWindowWidth(context) / 2 * (1 - target.getScaleX()) - target.getPivotX() * (1 - target.getScaleX())), 0);
        ObjectAnimator animatorTransY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, target.getTranslationY() + (getWindowHeight(context) / 2 * (1 - target.getScaleY()) - target.getPivotY() * (1 - target.getScaleY())), 0);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY, animatorTransX, animatorTransY);
        set.setDuration(ANIM_TIME);
        if (animatorListener != null) {
            set.addListener(animatorListener);
        }
        set.start();
    }

    public static void startEnterViewScaleAnim(Context context, View target, ImageViewInfo imageViewInfo, Animator.AnimatorListener animatorListener) {
        if (imageViewInfo.getRect() == null) {
            LogUtils.e("rect is null");
            return;
        }

        int width = imageViewInfo.getRect().width();
        int height = imageViewInfo.getRect().height();
        int localX = imageViewInfo.getRect().left;
        int localY = imageViewInfo.getRect().top;

        float windowScale = getWindowScale(context);
        float imgScale = getImgScale(width, height);

        float pivotX;
        float pivotY;
        float animImgStartHeight;
        float animImgStartWidth;
        float originalScale = getCurrentPicOriginalScale(context, imageViewInfo);
        if (imgScale >= windowScale) {
            animImgStartHeight = getWindowHeight(context) * originalScale;
            pivotX = localX / (1 - originalScale);
            pivotY = (localY - (animImgStartHeight - height) / 2) / (1 - originalScale);
        } else {
            animImgStartWidth = getWindowWidth(context) * originalScale;
            pivotX = (localX - (animImgStartWidth - width) / 2) / (1 - originalScale);
            pivotY = localY / (1 - originalScale);
        }
        target.setPivotX(pivotX);
        target.setPivotY(pivotY);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(target, View.SCALE_X, originalScale, 1.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(target, View.SCALE_Y, originalScale, 1.0f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
        set.setDuration(ANIM_TIME);
        if (animatorListener != null) {
            set.addListener(animatorListener);
        }
        set.start();
    }

    public static void startEnterViewAlphaAnim(Context context, View target, ImageViewInfo imageViewInfo) {
        float originalScale = getCurrentPicOriginalScale(context, imageViewInfo);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(ANIM_TIME);
        valueAnimator.setFloatValues(originalScale, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                target.setBackgroundColor(Color.parseColor(getBlackAlphaBg((float) animation.getAnimatedValue())));
            }
        });
        valueAnimator.start();
    }

    /**
     * 计算小图与全屏大图时候的缩放度，用于起始动画
     */
    private static float getCurrentPicOriginalScale(Context context, ImageViewInfo imageViewInfo) {
        float mScale;

        int width = imageViewInfo.getRect().width();
        int height = imageViewInfo.getRect().height();

        float imgScale = getImgScale(width, height);
        float mWindowScale = getWindowScale(context);

        if (imgScale >= mWindowScale) {
            mScale = width * 1.0f / getWindowWidth(context);
        } else {
            mScale = height * 1.0f / getWindowHeight(context);
        }
        return mScale;
    }

    private static float getImgScale(float width, float height) {
        return width * 1.0f / height;
    }

    private static int getWindowHeight(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
    }

    private static int getWindowWidth(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    }

    private static float getWindowScale(Context context) {
        return getWindowWidth(context) * 1.0f / getWindowHeight(context);
    }

    public static String getBlackAlphaBg(float alpha) {
        if (alpha >= 1) {
            alpha = 1f;
        }
        if (alpha <= 0) {
            alpha = 0.0f;
        }
        String colorAlpha = Integer.toHexString((int) (255 * alpha));
        if (colorAlpha.length() == 1) {
            colorAlpha = "0" + colorAlpha;
        }
        if (colorAlpha.length() == 0) {
            colorAlpha = "00";
        }
        return "#" + colorAlpha + "000000";
    }


    /**
     * 计算View坐标
     *
     * @param imageView 图片控件
     */
    public static Rect computeBound(ImageView imageView) {
        if (imageView == null || imageView.getDrawable() == null) {
            return null;
        }
        Drawable d = imageView.getDrawable();
        Rect result = new Rect();
        imageView.getGlobalVisibleRect(result);
        Rect tDrawableRect = d.getBounds();
        android.graphics.Matrix drawableMatrix = imageView.getImageMatrix();

        float[] values = new float[9];
        if (drawableMatrix != null) {
            drawableMatrix.getValues(values);
        }

        result.left = result.left + (int) values[android.graphics.Matrix.MTRANS_X];
        result.top = result.top + (int) values[android.graphics.Matrix.MTRANS_Y];
        result.right = (int) (result.left + tDrawableRect.width() * (values[android.graphics.Matrix.MSCALE_X] == 0 ? 1.0f : values[android.graphics.Matrix.MSCALE_X]));
        result.bottom = (int) (result.top + tDrawableRect.height() * (values[android.graphics.Matrix.MSCALE_Y] == 0 ? 1.0f : values[android.graphics.Matrix.MSCALE_Y]));

        return result;
    }

}
