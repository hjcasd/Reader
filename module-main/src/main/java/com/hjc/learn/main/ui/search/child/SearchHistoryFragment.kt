package com.hjc.learn.main.ui.search.child

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainFragmentSearchHistoryBinding
import com.hjc.learn.main.entity.History
import com.hjc.learn.main.viewmodel.search.SearchHistoryViewModel
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_base.view.ILoadingView
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.EventCode
import com.hjc.library_common.impl.CommonLoadingViewImpl
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_net.entity.WanSearchBean

/**
 * @Author: HJC
 * @Date: 2020/9/11 10:55
 * @Description: 搜索记录页面
 */
@Route(path = RouteMainPath.Fragment.URL_SEARCH_HISTORY)
class SearchHistoryFragment : BaseFragment<MainFragmentSearchHistoryBinding, SearchHistoryViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.main_fragment_search_history
    }

    override fun createViewModel(): SearchHistoryViewModel {
        return ViewModelProvider(this)[SearchHistoryViewModel::class.java]
    }

    override fun createLoadingView(): ILoadingView {
        return CommonLoadingViewImpl(mContext)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel?.getHotKeyData()
        mViewModel?.getHistoryData()
    }

    override fun observeLiveData() {
        super.observeLiveData()

        mViewModel?.run {
            hotKeyLiveData.observe(this@SearchHistoryFragment) { data ->
                if (data != null) {
                    mBindingView.clHot.visibility = View.VISIBLE
                    initHotTags(data)
                } else {
                    mBindingView.clHot.visibility = View.GONE
                }
            }

            historyLiveData.observe(this@SearchHistoryFragment) { data ->
                if (data != null) {
                    mBindingView.clHistory.visibility = View.VISIBLE
                    initHistoryTags(data)
                } else {
                    mBindingView.clHistory.visibility = View.GONE
                }
            }
        }
    }

    /**
     * 初始化热门搜索tag
     *
     * @param list 标签集合
     */
    private fun initHotTags(list: List<WanSearchBean>) {
        mBindingView.flHot.removeAllViews()
        for (bean in list) {
            val view = View.inflate(mContext, R.layout.main_view_navigation_tag, null)
            val tvTag = view.findViewById<TextView>(R.id.tv_tag)
            tvTag.text = bean.name
            mBindingView.flHot.addView(tvTag)

            tvTag.setOnClickListener {
                val keyword = tvTag.text.toString()
                EventManager.sendEvent(MessageEvent(EventCode.CODE_CLICK_TAG, keyword))
            }
        }
    }

    /**
     * 初始化历史搜索tag
     *
     * @param list 标签集合
     */
    private fun initHistoryTags(list: List<History>) {
        mBindingView.flHistory.removeAllViews()
        for (history in list) {
            val view = View.inflate(mContext, R.layout.main_view_navigation_tag, null)
            val tvTag = view.findViewById<TextView>(R.id.tv_tag)
            tvTag.text = history.name
            mBindingView.flHistory.addView(tvTag)

            tvTag.setOnClickListener {
                val keyword = tvTag.text.toString()
                EventManager.sendEvent(MessageEvent(EventCode.CODE_CLICK_TAG, keyword))
            }
        }

        //view加载完成时回调
        mBindingView.flHistory.viewTreeObserver.addOnGlobalLayoutListener {
            val rowCount = mBindingView.flHistory.getRowCount()
            val maxLines = mBindingView.flHistory.maxLines
            if (rowCount > maxLines) {
                mBindingView.tvExpanded.visibility = View.VISIBLE
            }
        }
    }

    override fun addListeners() {
        mBindingView.onClickListener = this
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.iv_clear_history -> {
                EventManager.sendEvent(MessageEvent(EventCode.CODE_HIDE_KEYBOARD, null))
                showClearHistoryDialog()
            }

            R.id.tv_expanded -> {
                if (mBindingView.flHistory.maxLines == Int.MAX_VALUE) {
                    mBindingView.flHistory.maxLines = 2
                    mBindingView.tvExpanded.text = "展开"
                    val drawableLeft = ContextCompat.getDrawable(requireContext(), R.mipmap.main_icon_arrow_down)
                    mBindingView.tvExpanded.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
                } else {
                    mBindingView.flHistory.maxLines = Int.MAX_VALUE
                    mBindingView.tvExpanded.text = "收起"
                    val drawableLeft = ContextCompat.getDrawable(requireContext(), R.mipmap.main_icon_arrow_up)
                    mBindingView.tvExpanded.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
                }
            }
        }
    }

    private fun showClearHistoryDialog() {
        val builder = AlertDialog.Builder(mContext)
        builder.setMessage("确认清除全部历史记录吗？")
        builder.setCancelable(false)
        builder.setPositiveButton("确定") { dialog: DialogInterface, _: Int ->
            mViewModel?.clearHistoryData()
            mBindingView.clHistory.visibility = View.GONE
            mBindingView.tvExpanded.visibility = View.GONE
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "取消"
        ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        builder.show()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            mViewModel?.getHistoryData()
        }
    }
}