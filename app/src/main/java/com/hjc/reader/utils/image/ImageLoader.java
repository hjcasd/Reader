package com.hjc.reader.utils.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hjc.reader.R;

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
                .diskCacheStrategy(DiskCacheStrategy.ALL);

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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(new CircleCrop());

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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(new CenterCrop(), new RoundedCorners(radius));

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
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

//    /**
//     * 加载本地图片文件
//     *
//     * @param context
//     * @param file
//     * @param imageView
//     */
//    public static void loadFileImage(Context context, File file, ImageView imageView) {
//        RequestOptions requestOptions = new RequestOptions()
//                .priority(Priority.HIGH)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop();
//
//        Glide.with(context)
//                .load(file)
//                .apply(requestOptions)
//                .into(imageView);
//    }
//
//    /**
//     * 加载高斯模糊
//     *
//     * @param context
//     * @param url
//     * @param imageView
//     * @param radius    模糊级数 最大25
//     */
//    public static void loadBlurImage(Context context, String url, ImageView imageView, int radius) {
//        RequestOptions requestOptions = new RequestOptions()
//                .override(300)
//                .transforms(new BlurTransformation(radius));
//
//        Glide.with(context)
//                .load(url)
//                .apply(requestOptions)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(imageView);
//    }

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
            case 1:// 妹子
                return R.mipmap.img_default_meizi;
            case 2:// 书籍
                return R.mipmap.img_default_book;
            case 3:// 电影
                return R.mipmap.img_default_movie;
            default:
                return R.mipmap.img_default;
        }
    }
}
