package com.hjc.reader.ui.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.http.helper.RxSchedulers;
import com.hjc.reader.utils.AppUtils;
import com.hjc.reader.widget.TitleBar;

import butterknife.BindView;
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
public class ScanCodeActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        generateWithLogo();
    }

    /**
     * 生成带logo的二维码
     */
    private void generateWithLogo() {
        Observable
                .just("https://www.pgyer.com/nyQL")
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
                            ivLogo.setImageBitmap(bitmap);
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
        ivLogo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                recognition();
                return false;
            }
        });

        titleBar.setOnViewClickListener(new TitleBar.onViewClick() {
            @Override
            public void leftClick(View view) {
                finish();
            }

            @Override
            public void rightClick(View view) {

            }
        });
    }

    /**
     * 识别二维码
     */
    private void recognition() {
        Drawable drawable = ivLogo.getDrawable();
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
