package com.hjc.learn.main.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainFragmentDrawerBinding
import com.hjc.learn.main.viewmodel.drawer.DrawerViewModel
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.EventCode
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_net.utils.AccountHelper
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author: HJC
 * @Date: 2021/2/2 20:39
 * @Description: 测试fragment
 */
@Route(path = RouteMainPath.Fragment.URL_DRAWER)
class DrawerFragment : BaseFragment<MainFragmentDrawerBinding, DrawerViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.main_fragment_drawer
    }

    override fun createViewModel(): DrawerViewModel {
        return ViewModelProvider(this)[DrawerViewModel::class.java]
    }

    override fun getImmersionBar(): ImmersionBar? {
        return ImmersionBar.with(this).transparentStatusBar()
    }

    override fun initData(savedInstanceState: Bundle?) {
        EventManager.register(this)

        mBindingView.drawerViewModel = mViewModel
        mViewModel?.setUserName()
    }

    override fun addListeners() {
        mBindingView.onClickListener = this
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            //登录注册
            R.id.fl_account -> {
                EventManager.sendEvent(MessageEvent(EventCode.CLOSE_DRAWER, 1))
            }

            //项目主页
            R.id.ll_home_page -> {
                EventManager.sendEvent(MessageEvent(EventCode.CLOSE_DRAWER, 2))
            }

            //扫描下载
            R.id.ll_scan -> {
                EventManager.sendEvent(MessageEvent(EventCode.CLOSE_DRAWER, 3))
            }

            //我的收藏
            R.id.ll_collect -> {
                EventManager.sendEvent(MessageEvent(EventCode.CLOSE_DRAWER, 4))
            }

            //退出应用
            R.id.ll_exit -> {
                EventManager.sendEvent(MessageEvent(EventCode.CLOSE_DRAWER, 5))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(this)
    }

    /**
     * 登录后的逻辑处理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveEvent(messageEvent: MessageEvent<*>) {
        if (messageEvent.getCode() === EventCode.LOGIN_CODE) {
            val username = AccountHelper.username
            mBindingView.tvUsername.text = username

        }else if(messageEvent.getCode() === EventCode.LOGIN_OUT_CODE){
            mBindingView.tvUsername.text = "登录/注册"
        }
    }
}