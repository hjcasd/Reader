package com.hjc.learn.main.ui.search.child

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.TextView
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
            val view = View.inflate(mContext, R.layout.main_view_search_tag, null)
            val tvTag = view.findViewById<TextView>(R.id.tv_tag)
            tvTag.text = bean.name
            mBindingView.flHot.addView(view)

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
        val viewList = mutableListOf<View>()
        for (history in list) {
            val textView = LayoutInflater.from(mContext).inflate(R.layout.main_view_history_tag, mBindingView.flHistory, false) as TextView
            textView.text = history.name
            viewList.add(textView)
        }
        mBindingView.flHistory.setChildren(viewList)

        //view加载完成时回调
        mBindingView.flHistory.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                mBindingView.flHistory.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val lineCount: Int = mBindingView.flHistory.lineCount
                val twoLineViewCount: Int = mBindingView.flHistory.twoLineViewCount
                if (lineCount > 2) {
                    initExpandView(list, twoLineViewCount)
                }
            }

        })

        mBindingView.flHistory.setOnTagClickListener { view, position ->
            val keyword = list[position].name
            EventManager.sendEvent(MessageEvent(EventCode.CODE_CLICK_TAG, keyword))
        }
    }

    /**
     * 点击展开
     */
    private fun initExpandView(list: List<History>, twoLineViewCount: Int) {
        val viewList = mutableListOf<View>()
        for (i in 0 until twoLineViewCount) {
            val textView = LayoutInflater.from(mContext).inflate(R.layout.main_view_history_tag, mBindingView.flHistory, false) as TextView
            textView.text = list[i].name
            viewList.add(textView)
        }

        val imageView = getMoreImageView(R.mipmap.main_icon_search_close)
        imageView.setOnClickListener { initShrinkView(list, twoLineViewCount) }
        viewList.add(imageView)

        mBindingView.flHistory.setChildren(viewList)

        mBindingView.flHistory.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mBindingView.flHistory.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val lineCount: Int = mBindingView.flHistory.lineCount
                val viewCount: Int = mBindingView.flHistory.twoLineViewCount
                if (lineCount > 2) {
                    initShrinkView(list, viewCount - 1)
                }
            }
        })
    }

    /**
     * 点击收缩
     */
    private fun initShrinkView(list: List<History>, twoLineViewCount: Int) {
        val viewList = mutableListOf<View>()
        for (history in list) {
            val textView = LayoutInflater.from(mContext).inflate(R.layout.main_view_history_tag, mBindingView.flHistory, false) as TextView
            textView.text = history.name
            viewList.add(textView)
        }

        val imageView = getMoreImageView(R.mipmap.main_icon_search_close)
        imageView.setOnClickListener { initExpandView(list, twoLineViewCount) }
        viewList.add(imageView)

        mBindingView.flHistory.setChildren(viewList)
    }

    /**
     * 获取伸缩图标
     */
    private fun getMoreImageView(resId: Int): ImageView {
        val imageView = LayoutInflater.from(mContext).inflate(R.layout.main_view_more_img, mBindingView.flHistory, false) as ImageView
        imageView.setImageResource(resId)
        return imageView
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
        }
    }

    private fun showClearHistoryDialog() {
        val builder = AlertDialog.Builder(mContext)
        builder.setMessage("确认清除全部历史记录吗？")
        builder.setCancelable(false)
        builder.setPositiveButton("确定") { dialog: DialogInterface, _: Int ->
            mViewModel?.clearHistoryData()
            mBindingView.clHistory.visibility = View.GONE
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