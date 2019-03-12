package com.hjc.reader.utils.image;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hjc.reader.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Glide封装类
 */
public class ImageLoader {


    /**
     * 加载图片(默认方式)
     *
     * @param imageView 控件id
     * @param url       图片地址
     * @param type      默认图片类型
     */
    public static void loadImage(ImageView imageView, String url, int type) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(getDefaultPic(type))
                .error(getDefaultPic(type))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView);
    }


    /**
     * 加载圆形图片
     *
     * @param imageView 控件id
     * @param url       图片地址
     * @param type      默认图片类型
     */
    public static void loadCircleImage(ImageView imageView, String url, int type) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(getDefaultPic(type))
                .error(getDefaultPic(type))
                .transforms(new CircleCrop())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param imageView 控件id
     * @param url       图片地址
     * @param radius    圆角大小
     * @param type      默认图片类型
     */
    public static void loadRoundImage(ImageView imageView, String url, int radius, int type) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(getDefaultPic(type))
                .error(getDefaultPic(type))
                .transforms(new CenterCrop(), new RoundedCorners(radius))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView);
    }

    /**
     * 加载图片指定大小
     *
     * @param imageView 控件id
     * @param url       图片地址
     * @param width     控件宽度
     * @param height    控件高度
     * @param type      默认图片类型
     */
    public static void loadSizeImage(ImageView imageView, String url, int width, int height, int type) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(getDefaultPic(type))
                .error(getDefaultPic(type))
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView);
    }

    /**
     * 加载高斯模糊图片
     *
     * @param imageView 控件id
     * @param url       图片地址
     * @param radius    模糊级数 最大25
     * @param sampling  采样率
     */
    public static void loadBlurImage(ImageView imageView, String url, int radius, int sampling) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(getDefaultPic(4))
                .error(getDefaultPic(4))
                .transforms(new BlurTransformation(radius, sampling))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView);
    }

    /**
     * 设置默认图片
     *
     * @param type 图片类型
     * @return 图片id
     */
    private static int getDefaultPic(int type) {
        switch (type) {
            case 0:
                return R.mipmap.img_default;
            case 1:// 默认妹子占位图
                return R.mipmap.img_default_meizi;
            case 2:// 默认书籍占位图
                return R.mipmap.img_default_book;
            case 3:// 默认电影占位图
                return R.mipmap.img_default_movie;
            case 4:// 默认高斯模糊占位图
                return R.mipmap.img_default_blur;
            default:
                return R.mipmap.img_default;
        }
    }
}
