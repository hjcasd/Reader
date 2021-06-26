package com.hjc.learn.main.ui.scan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainActivityScanCodeBinding
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_common.utils.CommonUtils
import com.hjc.library_common.viewmodel.CommonViewModel
import com.hjc.library_net.utils.RxSchedulers
import com.hjc.library_widget.bar.OnViewLeftClickListener
import io.reactivex.Observable
import io.reactivex.observers.DefaultObserver

/**
 * @Author: HJC
 * @Date: 2019/8/19 17:44
 * @Description: 扫码下载页面
 */
@Route(path = RouteMainPath.URL_ACTIVITY_SCAN)
class ScanCodeActivity : BaseActivity<MainActivityScanCodeBinding, CommonViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.main_activity_scan_code
    }

    override fun createViewModel(): CommonViewModel? {
        return null
    }

    override fun initData(savedInstanceState: Bundle?) {
        generateQrCodeWithLogo()
    }

    /**
     * 生成带logo的二维码
     */
    private fun generateQrCodeWithLogo() {
        Observable.just("https://www.pgyer.com/nyQL")
            .map { s: String? ->
                val logoBitmap = BitmapFactory.decodeResource(resources, R.mipmap.main_logo)
                QRCodeEncoder.syncEncodeQRCode(
                    s, BGAQRCodeUtil.dp2px(this@ScanCodeActivity, 180.0f),
                    Color.parseColor("#000000"), logoBitmap
                )
            }
            .compose(RxSchedulers.ioToMain())
            .subscribe(object : DefaultObserver<Bitmap>() {
                override fun onNext(bitmap: Bitmap) {
                    mBindingView.ivLogo.setImageBitmap(bitmap)
                }

                override fun onError(e: Throwable) {
                    ToastUtils.showShort("生成带logo的二维码失败")
                }

                override fun onComplete() {}
            })
    }

    override fun addListeners() {
        mBindingView.ivLogo.setOnLongClickListener {
            recognitionQrCode()
            false
        }

        mBindingView.titleBar.setOnViewLeftClickListener(object : OnViewLeftClickListener {
            override fun leftClick(view: View?) {
                finish()
            }

        })
    }

    /**
     * 识别二维码
     */
    private fun recognitionQrCode() {
        val drawable = mBindingView.ivLogo.drawable
        Observable
            .just(drawable)
            .map { drawable1: Drawable? ->
                val bitmap = ImageUtils.drawable2Bitmap(drawable1)
                QRCodeDecoder.syncDecodeQRCode(bitmap)
            }
            .compose(RxSchedulers.ioToMain())
            .subscribe(object : DefaultObserver<String?>() {
                override fun onNext(s: String) {
                    if (StringUtils.isEmpty(s)) {
                        ToastUtils.showShort("解析二维码失败")
                    } else {
                        CommonUtils.openLink(this@ScanCodeActivity, s)
                    }
                }

                override fun onError(e: Throwable) {
                    ToastUtils.showShort("生成带logo的二维码失败")
                }
                override fun onComplete() {}
            })
    }

    override fun onSingleClick(v: View?) {

    }

}