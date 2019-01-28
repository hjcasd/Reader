package com.hjc.reader.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hjc.reader.ui.GalleyActivity;
import com.hjc.reader.ui.WebActivity;

import java.util.ArrayList;

/**
 * @Author: HJC
 * @Date: 2019/1/28 15:54
 * @Description: 跳转逻辑封装类
 */
public class SchemeUtils {

    /**
     * @param context 上下文
     * @param url     页面地址
     * @param title   标题文字
     */
    public static void jumpToWeb(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 查看头像/单张图片
     *
     * @param context  上下文
     * @param imageUrl 图片地址
     */
    public static void jumpToImage(Context context, String imageUrl) {
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add(imageUrl);

        Intent intent = new Intent(context, GalleyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        bundle.putStringArrayList("imgList", imgList);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 查看多张图片
     *
     * @param context  上下文
     * @param imgList  图片地址集合
     * @param position 第几张图片
     */
    public static void jumpToGalley(Context context, ArrayList<String> imgList, int position) {
        Intent intent = new Intent(context, GalleyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        bundle.putInt("position", position);
        bundle.putStringArrayList("imgList", imgList);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
