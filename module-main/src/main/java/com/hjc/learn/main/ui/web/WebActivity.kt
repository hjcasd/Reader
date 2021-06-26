package com.hjc.learn.main.ui.web

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainActivityWebBinding
import com.hjc.learn.main.viewmodel.web.WebViewModel
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteLoginPath
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_common.utils.CommonUtils
import com.hjc.library_net.utils.AccountHelper

/**
 * @Author: HJC
 * @Date: 2021/1/8 14:22
 * @Description: Web页面
 */
@Route(path = RouteMainPath.URL_ACTIVITY_WEB)
class WebActivity : BaseActivity<MainActivityWebBinding, WebViewModel>() {

    @JvmField
    @Autowired(name = "title")
    var mTitle = ""

    @JvmField
    @Autowired(name = "url")
    var mUrl = ""

    override fun getLayoutId(): Int {
        return R.layout.main_activity_web
    }

    override fun createViewModel(): WebViewModel {
        return ViewModelProvider(this)[WebViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        setSupportActionBar(mBindingView.toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        mBindingView.toolbar.overflowIcon = ContextCompat.getDrawable(this, R.mipmap.main_icon_more)
        mBindingView.tvTitle.isSelected = true
    }

    override fun getImmersionBar(): ImmersionBar? {
        return ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.main_color)
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (!StringUtils.isEmpty(mTitle) && !StringUtils.isEmpty(mUrl)) {
            mBindingView.tvTitle.text = mTitle
            mBindingView.webLayout.getWebView().loadUrl(mUrl)
        }
    }

    override fun addListeners() {
        mBindingView.onClickListener = this
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.tv_close -> finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu_web_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()

            // 刷新页面
            R.id.item_refresh -> mBindingView.webLayout.getWebView().reload()

            // 分享
            R.id.item_share -> CommonUtils.share(this, mUrl)

            // 复制链接
            R.id.item_copy -> {
                CommonUtils.copy(this, mUrl)
                ToastUtils.showShort("复制成功")
            }

            // 打开链接
            R.id.item_open -> CommonUtils.openLink(this, mUrl)

            // 收藏链接
            R.id.item_collect -> {
                if (AccountHelper.isLogin) {
                    mViewModel?.collectLink(mTitle, mUrl)
                } else {
                    RouteManager.jump(RouteLoginPath.URL_LOGIN)
                }
            }

            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (mBindingView.webLayout.getWebView().canGoBack()) {
            mBindingView.webLayout.getWebView().goBack()
        } else {
            finish()
        }
    }

}