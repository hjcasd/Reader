package com.hjc.reader.ui.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.activity.BaseMvmActivity;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.R;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ActivityScanCodeBinding;
import com.hjc.reader.utils.AppUtils;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Observable;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/8/19 17:44
 * @Description: 扫码下载页面
 */
@Route(path = RoutePath.URL_SCAN_CODE)
public class ScanCodeActivity extends BaseMvmActivity<ActivityScanCodeBinding, CommonViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    protected CommonViewModel getViewModel() {
        return null;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        generateQrCodeWithLogo();
    }

    /**
     * 生成带logo的二维码
     */
    private void generateQrCodeWithLogo() {
        Observable.just("https://www.pgyer.com/nyQL")
                .map(s -> {
                    Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo);
                    return QRCodeEncoder.syncEncodeQRCode(s, BGAQRCodeUtil.dp2px(ScanCodeActivity.this, 180),
                            Color.parseColor("#000000"), logoBitmap);
                })
                .compose(RxSchedulers.ioToMain())
                .subscribe(new DefaultObserver<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            mBindingView.ivLogo.setImageBitmap(bitmap);
                        } else {
                            ToastUtils.showShort("生成带logo的二维码失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void addListeners() {
        mBindingView.ivLogo.setOnLongClickListener(v -> {
            recognitionQrCode();
            return false;
        });

        mBindingView.titleBar.setOnViewLeftClickListener(view -> {
            finish();
        });
    }

    /**
     * 识别二维码
     */
    private void recognitionQrCode() {
        Drawable drawable = mBindingView.ivLogo.getDrawable();
        Observable
                .just(drawable)
                .map(drawable1 -> {
                    Bitmap bitmap = ImageUtils.drawable2Bitmap(drawable1);
                    return QRCodeDecoder.syncDecodeQRCode(bitmap);
                })
                .compose(RxSchedulers.ioToMain())
                .subscribe(new DefaultObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        if (StringUtils.isEmpty(s)) {
                            ToastUtils.showShort("解析二维码失败");
                        } else {
                            AppUtils.openLink(ScanCodeActivity.this, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
