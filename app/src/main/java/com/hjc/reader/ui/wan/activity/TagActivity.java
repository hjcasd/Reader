package com.hjc.reader.ui.wan.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.baselib.activity.BaseMvmActivity;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.WanListBean;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ActivityTagBinding;
import com.hjc.reader.ui.wan.adapter.TagAdapter;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.wan.TagViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * @Author: HJC
 * @Date: 2020/8/27 17:13
 * @Description: 知识体系tag下文章列表页面
 */
@Route(path = RoutePath.URL_TAG)
public class TagActivity extends BaseMvmActivity<ActivityTagBinding, TagViewModel> {

    private TagAdapter mAdapter;

    private int mPage = 0;

    @Autowired(name = "params")
    Bundle bundle;

    private int cid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag;
    }

    @Override
    protected TagViewModel getViewModel() {
        return new ViewModelProvider(this).get(TagViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mBindingView.rvArticle.setLayoutManager(manager);

        mAdapter = new TagAdapter();
        mBindingView.rvArticle.setAdapter(mAdapter);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        if (bundle != null ){
            String name = bundle.getString("name");
            cid = bundle.getInt("id", 0);

            mBindingView.titleBar.setTitle(name);
            mViewModel.loadArticleList(mPage, cid, true);
        }
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getArticleLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();
            mBindingView.smartRefreshLayout.finishLoadMore();

            if (data != null){
                if (mPage == 0) {
                    mAdapter.setNewInstance(data);
                } else {
                    mAdapter.addData(data);
                }
            }
        });
    }

    @Override
    protected void addListeners() {
        mBindingView.titleBar.setOnViewLeftClickListener(view -> finish());

        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mViewModel.loadArticleList(mPage, cid, false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mViewModel.loadArticleList(mPage, cid, false);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<WanListBean.DataBean.DatasBean> dataList = (List<WanListBean.DataBean.DatasBean>) adapter.getData();
            WanListBean.DataBean.DatasBean bean = dataList.get(position);
            RouteManager.jumpToWeb(bean.getTitle(), bean.getLink());
        });
    }

    @Override
    protected void onSingleClick(View v) {

    }

    @Override
    protected void onRetryBtnClick(View v) {
        mPage = 0;
        mViewModel.loadArticleList(mPage, cid, false);
    }
}
