package com.hjc.reader.ui.update.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.hjc.reader.utils.AppUtils;

/**
 * 应用安装监听
 */
public class InstallBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            LogUtils.e("111111111");
            AppUtils.deleteApkFile();
        }

        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            LogUtils.e("222222222");
            AppUtils.deleteApkFile();
        }

        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
            LogUtils.e("333333333");
            AppUtils.deleteApkFile();
        }
    }
}