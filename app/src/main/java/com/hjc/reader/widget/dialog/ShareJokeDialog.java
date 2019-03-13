package com.hjc.reader.widget.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.reader.R;
import com.hjc.reader.base.dialog.BaseDialog;
import com.hjc.reader.utils.AppUtils;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:28
 * @Description: 复制和分享段子的dialog
 */
public class ShareJokeDialog extends BaseDialog {
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_share)
    TextView tvShare;

    private String mContent;

    public static ShareJokeDialog newInstance(String content) {
        ShareJokeDialog dialog = new ShareJokeDialog();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public int getStyleRes() {
        return R.style.Dialog_Base;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_share_joke;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mContent = bundle.getString("content", "");
    }

    @Override
    public void addListeners() {
        tvCopy.setOnClickListener(this);
        tvShare.setOnClickListener(this);
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()){
            case R.id.tv_copy:
                ToastUtils.showShort("复制成功");
                AppUtils.copy(mContext, mContent);
                dismiss();
                break;

            case R.id.tv_share:
                AppUtils.share(mContext, mContent);
                dismiss();
                break;
        }
    }

}
