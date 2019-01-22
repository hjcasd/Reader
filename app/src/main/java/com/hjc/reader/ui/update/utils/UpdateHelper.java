package com.hjc.reader.ui.update.utils;

import android.os.Bundle;

import com.hjc.reader.R;
import com.hjc.reader.http.RetrofitClient;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.CommonObserver;
import com.hjc.reader.model.request.UpdateRequest;
import com.hjc.reader.model.response.VersionBean;
import com.hjc.reader.ui.update.UpdateDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

/**
 * 检查更新逻辑封装
 */
public class UpdateHelper {
    public static void check(RxFragment fragment) {
        UpdateRequest request = new UpdateRequest();
        request.setAppType("1");
        RetrofitClient.getInstance().getAPI()
                .checkVersion(request)
                .compose(RxHelper.bind(fragment))
                .subscribe(new CommonObserver<VersionBean>() {
                    @Override
                    public void onSuccess(VersionBean result) {
                        String newVersion = result.getNewVersion();
                        String lowVersion = result.getLowVersion();

                        int flag1 = ApkUtils.compareVersion(ApkUtils.getVersionName(fragment.getContext()), lowVersion);
                        int flag2 = ApkUtils.compareVersion(ApkUtils.getVersionName(fragment.getContext()), newVersion);

//                        //强制更新
//                        if (flag1 == -1) {
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("version", result);
//                            bundle.putBoolean("isForceUpdate", true);
//                            UpdateDialog.newInstance(bundle)
//                                    .setAnimStyle(R.style.ActionSheetDialogAnimation)
//                                    .showDialog(fragment.getChildFragmentManager());
//                            return;
//                        }
                        //需要更新
                        if (flag2 == -1) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("version", result);
                            bundle.putBoolean("isForceUpdate", false);
                            UpdateDialog.newInstance(bundle)
                                    .setAnimStyle(R.style.ActionSheetDialogAnimation)
                                    .showDialog(fragment.getFragmentManager());
                        }
                    }
                });
    }

    public static void check(RxAppCompatActivity activity) {
        UpdateRequest request = new UpdateRequest();
        request.setAppType("1");
        RetrofitClient.getInstance().getAPI()
                .checkVersion(request)
                .compose(RxHelper.bind(activity))
                .subscribe(new CommonObserver<VersionBean>() {
                    @Override
                    public void onSuccess(VersionBean result) {
                        String newVersion = result.getNewVersion();
                        String lowVersion = result.getLowVersion();

                        int flag1 = ApkUtils.compareVersion(ApkUtils.getVersionName(activity), lowVersion);
                        int flag2 = ApkUtils.compareVersion(ApkUtils.getVersionName(activity), newVersion);

                        //强制更新
                        if (flag1 == -1) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("version", result);
                            bundle.putBoolean("isForceUpdate", true);
                            UpdateDialog.newInstance(bundle)
                                    .setAnimStyle(R.style.ActionSheetDialogAnimation)
                                    .showDialog(activity.getSupportFragmentManager());
                            return;
                        }
                        //需要更新
                        if (flag2 == -1) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("version", result);
                            bundle.putBoolean("isForceUpdate", false);
                            UpdateDialog.newInstance(bundle)
                                    .setAnimStyle(R.style.ActionSheetDialogAnimation)
                                    .showDialog(activity.getSupportFragmentManager());
                        }
                    }
                });
    }

    public static void check(RxFragmentActivity activity) {
        UpdateRequest request = new UpdateRequest();
        request.setAppType("1");
        RetrofitClient.getInstance().getAPI()
                .checkVersion(request)
                .compose(RxHelper.bind(activity))
                .subscribe(new CommonObserver<VersionBean>() {
                    @Override
                    public void onSuccess(VersionBean result) {
                        String newVersion = result.getNewVersion();
                        String lowVersion = result.getLowVersion();

                        int flag1 = ApkUtils.compareVersion(ApkUtils.getVersionName(activity), lowVersion);
                        int flag2 = ApkUtils.compareVersion(ApkUtils.getVersionName(activity), newVersion);

                        //强制更新
                        if (flag1 == -1) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("version", result);
                            bundle.putBoolean("isForceUpdate", true);
                            UpdateDialog.newInstance(bundle)
                                    .setAnimStyle(R.style.ActionSheetDialogAnimation)
                                    .showDialog(activity.getSupportFragmentManager());
                            return;
                        }
                        //需要更新
                        if (flag2 == -1) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("version", result);
                            bundle.putBoolean("isForceUpdate", false);
                            UpdateDialog.newInstance(bundle)
                                    .setAnimStyle(R.style.ActionSheetDialogAnimation)
                                    .showDialog(activity.getSupportFragmentManager());
                        }
                    }
                });
    }
}
