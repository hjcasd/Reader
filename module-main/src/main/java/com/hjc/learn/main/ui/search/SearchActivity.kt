package com.hjc.learn.main.ui.search

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.*
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainActivitySearchBinding
import com.hjc.learn.main.ui.search.child.SearchHistoryFragment
import com.hjc.learn.main.ui.search.child.SearchResultFragment
import com.hjc.learn.main.viewmodel.search.SearchViewModel
import com.hjc.library_base.activity.BaseFragmentActivity
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.EventCode
import com.hjc.library_common.router.RoutePath
import com.hjc.library_widget.SearchEditText
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * @Author: HJC
 * @Date: 2019/8/21 14:13
 * @Description: 搜索页面
 */
@Route(path = RoutePath.Main.SEARCH)
class SearchActivity : BaseFragmentActivity<MainActivitySearchBinding, SearchViewModel>() {

    private lateinit var mSearchHistoryFragment: SearchHistoryFragment
    private lateinit var mSearchResultFragment: SearchResultFragment

    override fun getLayoutId(): Int {
        return R.layout.main_activity_search
    }

    override fun createViewModel(): SearchViewModel {
        return ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun initData(savedInstanceState: Bundle?) {
        EventManager.register(this)

        mSearchHistoryFragment = SearchHistoryFragment.newInstance()
        mSearchResultFragment = SearchResultFragment.newInstance()

        mBindingView.etSearch.requestFocus()

        showFragment(mSearchHistoryFragment)
    }

    override fun addListeners() {
        mBindingView.onClickListener = this

        mBindingView.ivBack.setOnClickListener(this)
        mBindingView.tvSearch.setOnClickListener(this)

        mBindingView.etSearch.setOnSearchClickListener(object : SearchEditText.OnSearchClickListener {
            override fun onSearchChanged(text: String) {

            }

            override fun onSearchClick(view: View?) {
                search()
            }

            override fun onSearchClear() {
                showFragment(mSearchHistoryFragment)
            }

        })

        mBindingView.clRoot.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            //获取当前界面可视部分
            window.decorView.getWindowVisibleDisplayFrame(rect)
            //获取屏幕的高度
            val screenHeight: Int = window.decorView.rootView.height
            //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
            val heightDifference = screenHeight - rect.bottom

            if (heightDifference > screenHeight * 0.3) {
                mBindingView.tvRecord.visibility = View.VISIBLE
                val layoutParams = mBindingView.tvRecord.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.bottomMargin = heightDifference + ConvertUtils.dp2px(10f)
                mBindingView.tvRecord.layoutParams = layoutParams
            } else {
                mBindingView.tvRecord.visibility = View.GONE
            }
        }
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                KeyboardUtils.hideSoftInput(this)
                finish()
            }
            R.id.tv_search -> search()

            R.id.tv_record -> {
                KeyboardUtils.hideSoftInput(this)
                showTestDialog()
            }
        }
    }

    private fun showTestDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("确认清除全部历史记录吗？")
        builder.setCancelable(false)
        builder.setPositiveButton("确定") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "取消"
        ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        builder.show()
    }

    override fun getFragmentContentId(): Int {
        return R.id.fl_content
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(this)
    }

    /**
     * 搜索
     */
    private fun search() {
        val keyword = Objects.requireNonNull(mBindingView.etSearch.text).toString().trim()
        if (StringUtils.isEmpty(keyword)) {
            ToastUtils.showShort("请输入关键字")
            return
        }
        KeyboardUtils.hideSoftInput(this)
        mViewModel?.saveHistoryData(keyword)
        EventManager.sendStickyEvent(MessageEvent(EventCode.SEARCH_RESULT_CODE, keyword))
        showFragment(mSearchResultFragment)
    }

    /**
     * 点击历史搜索标签或者热门搜索标签回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handlerEvent(messageEvent: MessageEvent<String?>) {
        when (messageEvent.getCode()) {
            EventCode.CLICK_TAG_CODE -> {
                val keyword = messageEvent.getData()
                keyword?.let {
                    mBindingView.etSearch.setText(it)
                    mBindingView.etSearch.requestFocus()
                    mBindingView.etSearch.setSelection(it.length)
                    search()
                }
            }

            EventCode.HIDE_KEYBOARD -> KeyboardUtils.hideSoftInput(this)

            else -> {
            }
        }
    }
}