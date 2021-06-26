package com.hjc.learn.main.ui.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainFragmentBinding
import com.hjc.learn.main.viewmodel.MainViewModel
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.EventCode
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteGankPath
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_common.router.path.RouteWanPath
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author: HJC
 * @Date: 2021/2/2 20:39
 * @Description: 测试fragment
 */
@Route(path = RouteMainPath.Fragment.URL_MAIN_FRAGMENT)
class MainFragment : BaseFragment<MainFragmentBinding, MainViewModel>() {

    private lateinit var mTab1Fragment: Fragment
    private lateinit var mTab2Fragment: Fragment
    private lateinit var mTab3Fragment: Fragment

    private var mCurrentFragment = Fragment()

    override fun getLayoutId(): Int {
        return R.layout.main_fragment
    }

    override fun createViewModel(): MainViewModel {
        return ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun getImmersionBar(): ImmersionBar? {
        return ImmersionBar.with(this)
            .statusBarView(R.id.status_view)
    }

    override fun initData(savedInstanceState: Bundle?) {
        EventManager.register(this)

        mTab1Fragment = ARouter.getInstance().build(RouteWanPath.Fragment.URL_WAN_FRAGMENT).navigation() as Fragment
        mTab2Fragment = ARouter.getInstance().build(RouteGankPath.Fragment.URL_GANK_FRAGMENT).navigation() as Fragment
        mTab3Fragment = ARouter.getInstance().build(RouteMainPath.Fragment.URL_TEST).navigation() as Fragment

        setCurrentItem(0)
    }

    /**
     * 切换页面
     *
     * @param position 位置
     */
    private fun setCurrentItem(position: Int) {
        var isOne = false
        var isTwo = false
        var isThree = false
        when (position) {
            1 -> {
                isTwo = true
                showFragment(mTab2Fragment)
            }
            2 -> {
                isThree = true
                showFragment(mTab3Fragment)
            }
            else -> {
                isOne = true
                showFragment(mTab1Fragment)
            }
        }
        mBindingView.ivTab1.isSelected = isOne
        mBindingView.ivTab2.isSelected = isTwo
        mBindingView.ivTab3.isSelected = isThree
    }

    override fun addListeners() {
        mBindingView.onClickListener = this
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.iv_menu -> {
                EventManager.sendEvent(MessageEvent(EventCode.OPEN_DRAWER, null))
            }

            R.id.iv_tab1 -> setCurrentItem(0)
            R.id.iv_tab2 -> setCurrentItem(1)
            R.id.iv_tab3 -> setCurrentItem(2)

            R.id.iv_search -> {
                RouteManager.jump(RouteMainPath.Activity.URL_SEARCH)
            }
        }
    }

    /**
     * 显示fragment
     */
    private fun showFragment(fragment: Fragment) {
        if (mCurrentFragment !== fragment) {
            val fm = childFragmentManager
            val ft = fm.beginTransaction()
            ft.hide(mCurrentFragment)
            mCurrentFragment = fragment
            if (!fragment.isAdded) {
                ft.add(R.id.fl_content, fragment).show(fragment).commit()
            } else {
                ft.show(fragment).commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveEvent(messageEvent: MessageEvent<Int>) {
        if (messageEvent.getCode() == EventCode.DRAWER_OPENED) {
            ObjectAnimator.ofFloat(mBindingView.ivMenu, "rotation", -180f, 0f).setDuration(500).start()

        } else if (messageEvent.getCode() == EventCode.DRAWER_CLOSED) {
            ObjectAnimator.ofFloat(mBindingView.ivMenu, "rotation", 0f, -180f).setDuration(500).start()
        }
    }
}