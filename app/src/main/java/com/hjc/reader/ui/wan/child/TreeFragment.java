package com.hjc.reader.ui.wan.child;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.databinding.FragmentTreeBinding;
import com.hjc.reader.ui.wan.adapter.TreeListAdapter;
import com.hjc.reader.viewmodel.wan.TreeViewModel;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:30
 * @Description: 知识体系页面
 */
public class TreeFragment extends BaseMvmLazyFragment<FragmentTreeBinding, TreeViewModel> {

    private TreeListAdapter mAdapter;

    public static TreeFragment newInstance() {
        return new TreeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tree;
    }

    @Override
    protected TreeViewModel getViewModel() {
        return new ViewModelProvider(this).get(TreeViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mBindingView.rvTree.setLayoutManager(manager);

        mAdapter = new TreeListAdapter();
        mBindingView.rvTree.setAdapter(mAdapter);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInRight);
    }

    @Override
    public void initData() {
        mViewModel.loadTreeList(true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getListLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();
            mAdapter.setNewInstance(data);
        });
    }

    @Override
    public void addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshListener(refreshLayout -> mViewModel.loadTreeList(false));
    }

    @Override
    public void onSingleClick(View v) {

    }

    @Override
    protected void onRetryBtnClick(View v) {
        mViewModel.loadTreeList(true);
    }
}
