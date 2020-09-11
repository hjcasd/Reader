package com.hjc.reader.ui.search.child;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.hjc.baselib.event.EventManager;
import com.hjc.baselib.event.MessageEvent;
import com.hjc.baselib.fragment.BaseMvmFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.db.History;
import com.hjc.reader.bean.response.HotKeyBean;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.databinding.FragmentSearchHistoryBinding;
import com.hjc.reader.viewmodel.search.SearchHistoryViewModel;

import java.util.List;

/**
 * @Author: HJC
 * @Date: 2020/9/11 10:55
 * @Description: 搜索记录页面
 */
public class SearchHistoryFragment extends BaseMvmFragment<FragmentSearchHistoryBinding, SearchHistoryViewModel> {

    public static SearchHistoryFragment newInstance() {
        return new SearchHistoryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_history;
    }

    @Override
    protected SearchHistoryViewModel getViewModel() {
        return new ViewModelProvider(this).get(SearchHistoryViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mViewModel.getHotKeyData();
        mViewModel.getHistoryData();
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getHotKeyLiveData().observe(this, data -> {
            if (data != null) {
                mBindingView.clHot.setVisibility(View.VISIBLE);
                initHotTags(data);
            } else {
                mBindingView.clHot.setVisibility(View.GONE);
            }
        });

        mViewModel.getHistoryLiveData().observe(this, data -> {
            if (data != null) {
                mBindingView.clHistory.setVisibility(View.VISIBLE);
                initHistoryTags(data);
            } else {
                mBindingView.clHistory.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 初始化热门搜索tag
     *
     * @param list 标签集合
     */
    private void initHotTags(List<HotKeyBean.DataBean> list) {
        mBindingView.flHot.removeAllViews();
        for (HotKeyBean.DataBean bean : list) {
            View view = View.inflate(mContext, R.layout.view_navigation_tag, null);
            TextView tvTag = view.findViewById(R.id.tv_tag);
            tvTag.setText(bean.getName());
            mBindingView.flHot.addView(tvTag);

            tvTag.setOnClickListener(v -> {
                String keyword = tvTag.getText().toString();
                EventManager.sendEvent(new MessageEvent(EventCode.CLICK_TAG, keyword));
            });
        }
    }

    /**
     * 初始化历史搜索tag
     *
     * @param list 标签集合
     */
    private void initHistoryTags(List<History> list) {
        mBindingView.flHistory.removeAllViews();
        for (History history : list) {
            View view = View.inflate(mContext, R.layout.view_navigation_tag, null);
            TextView tvTag = view.findViewById(R.id.tv_tag);
            tvTag.setText(history.getName());
            mBindingView.flHistory.addView(tvTag);

            tvTag.setOnClickListener(v -> {
                String keyword = tvTag.getText().toString();
                EventManager.sendEvent(new MessageEvent(EventCode.CLICK_TAG, keyword));
            });
        }
    }

    @Override
    protected void addListeners() {
        mBindingView.setOnClickListener(this);
    }

    @Override
    protected void onSingleClick(View v) {
        if (v.getId() == R.id.iv_clear_history) {
            showClearHistoryDialog();
        }
    }

    private void showClearHistoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("确认清除全部历史记录吗？");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", (dialog, which) -> {
            mViewModel.clearHistoryData();
            mBindingView.clHistory.setVisibility(View.GONE);
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mViewModel.getHistoryData();
        }
    }
}
