package com.hjc.learn.main.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.KeyboardUtils
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainFragmentEditLinkBinding
import com.hjc.learn.main.viewmodel.collect.EditLinkViewModel
import com.hjc.library_base.dialog.BaseFragmentDialog
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.EventCode
import com.hjc.library_net.entity.WanCollectLinkBean

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:28
 * @Description: 编辑网址的dialog
 */
class EditLinkDialog : BaseFragmentDialog<MainFragmentEditLinkBinding, EditLinkViewModel>() {

    private var mData: WanCollectLinkBean? = null

    companion object {
        fun newInstance(bean: WanCollectLinkBean): EditLinkDialog {
            val dialog = EditLinkDialog()
            val bundle = Bundle()
            bundle.putSerializable("data", bean)
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun getStyleRes(): Int {
        return R.style.Base_Dialog
    }

    override fun getLayoutId(): Int {
        return R.layout.main_fragment_edit_link
    }

    override fun createViewModel(): EditLinkViewModel {
        return ViewModelProvider(this)[EditLinkViewModel::class.java]
    }

    override fun initData(savedInstanceState: Bundle?) {
        val bundle = arguments
        bundle?.let {
            mData = it.getSerializable("data") as? WanCollectLinkBean
            mData?.let { result ->
                mBindingView.editLinkViewModel = mViewModel
                mBindingView.etName.setText(result.name)
                mBindingView.etLink.setText(result.link)
            }
        }
    }

    override fun addListeners() {
        mBindingView.onClickListener = this

        mViewModel?.run {
            editLinkLiveData.observe(this@EditLinkDialog) { data ->
                KeyboardUtils.hideSoftInput(mBindingView.etName)
                EventManager.sendEvent(MessageEvent(EventCode.CODE_EDIT_LINK, data))
                dismiss()
            }
        }
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.tv_cancel -> {
                KeyboardUtils.hideSoftInput(mBindingView.etName)
                dismiss()
            }
            R.id.tv_complete -> mData?.let {
                mViewModel?.editLink(it.id)
            }
        }
    }

}