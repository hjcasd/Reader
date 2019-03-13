package com.hjc.reader.widget.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.reader.R;
import com.hjc.reader.base.dialog.BaseDialog;
import com.hjc.reader.base.event.Event;
import com.hjc.reader.base.event.EventManager;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.CollectArticleBean;
import com.hjc.reader.model.response.CollectLinkBean;
import com.hjc.reader.utils.AppUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:28
 * @Description: 编辑网址的dialog
 */
public class EditLinkDialog extends BaseDialog {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_link)
    EditText etLink;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

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
        return R.layout.dialog_edit_link;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mData = (CollectLinkBean.DataBean) bundle.getSerializable("data");

        etName.setText(mData.getName());
        etLink.setText(mData.getLink());
    }

    @Override
    public void addListeners() {
        tvCancel.setOnClickListener(this);
        tvComplete.setOnClickListener(this);
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                KeyboardUtils.hideSoftInput(etName);
                dismiss();
                break;

            case R.id.tv_complete:
                String name = etName.getText().toString().trim();
                if (StringUtils.isEmpty(name)) {
                    ToastUtils.showShort("请输入网址名称");
                    return;
                }
                String link = etLink.getText().toString().trim();
                if (StringUtils.isEmpty(link)) {
                    ToastUtils.showShort("请输入网址链接");
                    return;
                }
                KeyboardUtils.hideSoftInput(etName);
                editLink(mData.getId(), name, link);
                break;
        }
    }

    /**
     * 编辑收藏的网址
     *
     * @param id   网址id
     * @param name 网址标题
     * @param link 网址链接
     */
    private void editLink(int id, String name, String link) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .editLink(id, name, link)
                .compose(RxHelper.bind((LifecycleProvider) mContext))
                .subscribe(new DefaultObserver<CollectArticleBean>() {
                    @Override
                    public void onNext(CollectArticleBean bean) {
                        if (bean != null) {
                            if (bean.getErrorCode() == 0) {
                                mData.setName(name);
                                mData.setLink(link);
                                ToastUtils.showShort("编辑成功");
                                dismiss();
                                EventManager.sendEvent(new Event(EventCode.D, mData));
                            } else {
                                ToastUtils.showShort(bean.getErrorMsg());
                            }
                        } else {
                            ToastUtils.showShort("服务器异常,请稍后重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("服务器异常,请稍后重试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
