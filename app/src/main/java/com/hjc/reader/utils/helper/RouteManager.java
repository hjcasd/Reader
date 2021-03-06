package com.hjc.reader.utils.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.core.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.utils.ClickUtils;
import com.hjc.reader.R;
import com.hjc.reader.constant.RoutePath;

/**
 * @Author: HJC
 * @Date: 2020/8/24 14:55
 * @Description: 页面路由管理
 */
public class RouteManager {

    /**
     * 页面跳转
     *
     * @param path 要跳转页面对应的路由url
     */
    public static void jump(String path) {
        ARouter.getInstance()
                .build(path)
                .navigation();
    }

    /**
     * 页面跳转(bundle)
     *
     * @param path   要跳转页面对应的路由url
     * @param bundle 传递的参数
     */
    public static void jumpWithBundle(String path, Bundle bundle) {
        ARouter.getInstance()
                .build(path)
                .withBundle("params", bundle)
                .navigation();
    }

    /**
     * 页面跳转(scene)
     *
     * @param context 当前页面
     * @param path    要跳转页面对应的路由url
     * @param bundle  传递的参数
     * @param compat  转场动画
     */
    public static void jumpWithScene(Context context, String path, Bundle bundle,  ActivityOptionsCompat compat) {
        ARouter.getInstance()
                .build(path)
                .withBundle("params", bundle)
                .withOptionsCompat(compat)
                .navigation(context);
    }


    /**
     * 页面跳转
     *
     * @param context     对应页面
     * @param path        要跳转页面对应的路由url
     * @param bundle      传递的参数
     * @param requestCode code码
     */
    public static void jumpWithCode(Activity context, String path, Bundle bundle, int requestCode) {
        ARouter.getInstance()
                .build(path)
                .withBundle("params", bundle)
                .navigation(context, requestCode);
    }

    /**
     * 页面跳转(web)
     *
     * @param title 标题
     * @param url   链接地址
     */
    public static void jumpToWeb(String title, String url) {
        if (ClickUtils.isFastClick()) {
            ToastUtils.showShort("点的太快了,歇会呗!");
            return;
        }
        if (StringUtils.isEmpty(url)) {
            ToastUtils.showShort("链接地址不能为空");
            return;
        }

        ARouter.getInstance()
                .build(RoutePath.URL_WEB)
                .withString("title", title)
                .withString("url", url)
                .withTransition(R.anim.slide_enter_bottom, R.anim.slide_exit_bottom)
                .navigation();
    }
}
