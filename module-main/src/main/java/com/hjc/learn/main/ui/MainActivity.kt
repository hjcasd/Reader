package com.hjc.learn.main.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainActivityBinding
import com.hjc.learn.main.viewmodel.MainViewModel
import com.hjc.library_base.activity.BaseFragmentActivity
import com.hjc.library_base.utils.ActivityHelper
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.AppConstants
import com.hjc.library_common.global.EventCode
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteLoginPath
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_net.utils.AccountHelper
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author: HJC
 * @Date: 2020/8/24 15:58
 * @Description: 主界面
 */
@Route(path = RouteMainPath.Activity.URL_MAIN_ACTIVITY)
class MainActivity : BaseFragmentActivity<MainActivityBinding, MainViewModel>() {

    private var flag = 0

    override fun getLayoutId(): Int {
        return R.layout.main_activity
    }

    override fun createViewModel(): MainViewModel {
        return ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun initData(savedInstanceState: Bundle?) {
        EventManager.register(this)

        val mainFragment = ARouter.getInstance().build(RouteMainPath.Fragment.URL_MAIN_FRAGMENT).navigation() as Fragment
        val drawerFragment = ARouter.getInstance().build(RouteMainPath.Fragment.URL_DRAWER).navigation() as Fragment

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_main, mainFragment)
            .add(R.id.fl_drawer, drawerFragment)
            .commit()
    }

    override fun addListeners() {
        mBindingView.drawerLayout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(view: View, v: Float) {}

            override fun onDrawerOpened(view: View) {
                EventManager.sendEvent(MessageEvent(EventCode.CODE_DRAWER_OPENED, null))
            }

            override fun onDrawerClosed(view: View) {
                EventManager.sendEvent(MessageEvent(EventCode.CODE_DRAWER_CLOSED, null))
                when (flag) {
                    1 -> {
                        flag = 0
                        if (AccountHelper.isLogin) {
                            showLogoutDialog()
                        } else {
                            RouteManager.jump(RouteLoginPath.URL_LOGIN)
                        }
                    }

                    2 -> {
                        flag = 0
                        RouteManager.jumpToWeb("项目主页", AppConstants.READER_URL)
                    }

                    3 -> {
                        flag = 0
                        RouteManager.jump(RouteMainPath.Activity.URL_SCAN)
                    }

                    4 -> {
                        flag = 0
                        RouteManager.jump(RouteMainPath.Activity.URL_COLLECT)
                    }

                    5 -> {
                        flag = 0
                        showExitDialog()
                    }
                }
            }

            override fun onDrawerStateChanged(i: Int) {}
        })
    }

    override fun onSingleClick(v: View?) {

    }

    override fun getFragmentContentId(): Int {
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(this)
    }

    /**
     * 退出登录dialog
     */
    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("确认退出账号吗？")
        builder.setCancelable(false)
        builder.setPositiveButton("确定") { dialog: DialogInterface, _: Int ->
            mViewModel?.logout()
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "取消"
        ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        builder.show()
    }

    /**
     * 退出应用弹框
     */
    private fun showExitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("确认退出应用吗？")
        builder.setCancelable(false)
        builder.setPositiveButton("确定") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            ActivityHelper.finishAllActivities()
        }
        builder.setNegativeButton(
            "取消"
        ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        builder.show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveEvent(event: MessageEvent<Int?>) {
        when(event.code){
            EventCode.CODE_CLOSE_DRAWER -> {
                mBindingView.drawerLayout.closeDrawer(GravityCompat.START)
                event.data?.let {
                    flag = it
                }
            }
            EventCode.CODE_OPEN_DRAWER -> mBindingView.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

}