package com.hjc.reader.widget;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.KeyboardUtils;
import com.hjc.baselib.dialog.BaseFragmentDialog;
import com.hjc.baselib.event.EventManager;
import com.hjc.baselib.event.MessageEvent;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.CollectLinkBean;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.databinding.FragmentEditLinkBinding;
import com.hjc.reader.viewmodel.menu.EditLinkViewModel;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:28
 * @Description: 编辑网址的dialog
 */
public class EditLinkDialog extends BaseFragmentDialog<FragmentEditLinkBinding, EditLinkViewModel> {

    private CollectLinkBean.DataBean mData;

    public static EditLinkDialog newInstance(CollectLinkBean.DataBean bean) {
        EditLinkDialog dialog = new EditLinkDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", bean);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public int getStyleRes() {
        return R.style.Dialog_Base;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit_link;
    }

    @Override
    protected EditLinkViewModel getViewModel() {
        return new ViewModelProvider(this).get(EditLinkViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mData = (CollectLinkBean.DataBean) bundle.getSerializable("data");
            if (mData != null) {
                mBindingView.setEditLinkViewModel(mViewModel);

                mBindingView.etName.setText(mData.getName());
                mBindingView.etLink.setText(mData.getLink());
            }
        }
    }

    @Override
    public void addListeners() {
        mBindingView.setOnClickListener(this);

        mViewModel.getEditLinkLiveData().observe(this, data ->{
            KeyboardUtils.hideSoftInput(mBindingView.etName);
            dismiss();
            EventManager.sendEvent(new MessageEvent(EventCode.EDIT_LINK_CODE, data));
        });
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                KeyboardUtils.hideSoftInput(mBindingView.etName);
                dismiss();
                break;

            case R.id.tv_complete:
                mViewModel.editLink(mData.getId());
                break;
        }
    }
}
