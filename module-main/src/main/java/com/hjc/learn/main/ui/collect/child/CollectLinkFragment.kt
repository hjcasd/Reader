package com.hjc.learn.main.ui.collect.child

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.hjc.learn.main.R
import com.hjc.learn.main.adapter.LinkAdapter
import com.hjc.learn.main.databinding.MainFragmentCollectLinkBinding
import com.hjc.learn.main.dialog.EditLinkDialog
import com.hjc.learn.main.viewmodel.collect.CollectLinkViewModel
import com.hjc.library_base.fragment.BaseLazyFragment
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.EventCode
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_net.entity.WanCollectLinkBean
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author: HJC
 * @Date: 2019/3/11 11:00
 * @Description: 我的收藏下的网址页面
 */
@Route(path = RouteMainPath.Fragment.URL_COLLECT_LINK)
class CollectLinkFragment : BaseLazyFragment<MainFragmentCollectLinkBinding, CollectLinkViewModel>() {

    private lateinit var mAdapter: LinkAdapter

    private var mCurrentPosition = 0

    override fun getLayoutId(): Int {
        return R.layout.main_fragment_collect_link
    }

    override fun createViewModel(): CollectLinkViewModel {
        return ViewModelProvider(this)[CollectLinkViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        initLoadSir(mBindingView.smartRefreshLayout)

        val manager = LinearLayoutManager(mContext)
        mBindingView.rvLink.layoutManager = manager

        mAdapter = LinkAdapter(null)
        mBindingView.rvLink.adapter = mAdapter

        mAdapter.setAnimationWithDefault(AnimationType.SlideInRight)
    }

    override fun initData() {
        EventManager.register(this)

        mViewModel?.loadCollectLinkList(true)
    }

    override fun observeLiveData() {
        super.observeLiveData()

        mViewModel?.run {
            collectLinkLiveData.observe(this@CollectLinkFragment) { data ->
                mAdapter.setNewInstance(data)
            }

            refreshData.observe(this@CollectLinkFragment) { result ->
                if (result) {
                    mBindingView.smartRefreshLayout.finishRefresh()
                }
            }

            positionLiveData.observe(this@CollectLinkFragment) { position -> mAdapter.removeAt(position) }
        }
    }

    override fun addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshListener { mViewModel?.loadCollectLinkList(false) }

        mAdapter.setOnItemClickListener { _, _, position ->
            val dataList = mAdapter.data
            val bean = dataList[position]
            RouteManager.jumpToWeb(bean.name, bean.link)
        }

        mAdapter.setOnItemLongClickListener { _, _, position ->
            mCurrentPosition = position

            val dataList = mAdapter.data
            val bean = dataList[position]
            val items = arrayOf("编辑", "删除")
            val builder = AlertDialog.Builder(mContext)
            builder.setItems(
                items
            ) { _: DialogInterface?, which: Int ->
                when (which) {
                    0 -> EditLinkDialog.newInstance(bean).showDialog(childFragmentManager)
                    1 -> mViewModel?.deleteLink(bean.id, position)
                }
            }
            builder.show()
            true
        }
    }

    override fun onSingleClick(v: View?) {

    }

     override fun onRetryBtnClick(v: View?) {
        mViewModel?.loadCollectLinkList(true)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveEvent(event: MessageEvent<WanCollectLinkBean?>) {
        if (event.code === EventCode.CODE_EDIT_LINK) {
            val srcBean= event.data
            val dataList = mAdapter.data
            val desBean = dataList[mCurrentPosition]

            srcBean?.let {
                desBean.name = it.name
                desBean.link = it.link
            }

            mAdapter.notifyItemChanged(mCurrentPosition)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(this)
    }

}