package com.hjc.reader.ui.menu.child;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.CollectArticleBean;
import com.hjc.reader.databinding.FragmentCollectArticleBinding;
import com.hjc.reader.ui.menu.adapter.ArticleAdapter;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.menu.CollectArticleViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;


/**
 * @Author: HJC
 * @Date: 2019/3/11 11:00
 * @Description: 我的收藏下的文章页面
 */
public class CollectArticleFragment extends BaseMvmLazyFragment<FragmentCollectArticleBinding, CollectArticleViewModel> {

    private ArticleAdapter mAdapter;

    private int mPage = 0;

    public static CollectArticleFragment newInstance() {
        return new CollectArticleFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect_article;
    }

    @Override
    protected CollectArticleViewModel getViewModel() {
        return new ViewModelProvider(this).get(CollectArticleViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mBindingView.rvArticle.setLayoutManager(manager);

        mAdapter = new ArticleAdapter();
        mBindingView.rvArticle.setAdapter(mAdapter);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
    }

    @Override
    public void initData() {
        mViewModel.loadCollectArticleList(mPage, true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getCollectArticleLiveData().observe(this, data -> {
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

        mViewModel.getPositionLiveData().observe(this, position -> {
            mAdapter.removeAt(position);
        });
    }

    @Override
    public void addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mViewModel.loadCollectArticleList(mPage, false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mViewModel.loadCollectArticleList(mPage, false);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<CollectArticleBean.DataBean.DatasBean> dataList = mAdapter.getData();
            CollectArticleBean.DataBean.DatasBean bean = dataList.get(position);
            RouteManager.jumpToWeb(bean.getTitle(), bean.getLink());
        });

        mAdapter.addChildClickViewIds(R.id.cb_collect);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.cb_collect){
                List<CollectArticleBean.DataBean.DatasBean> dataList = mAdapter.getData();
                CollectArticleBean.DataBean.DatasBean bean = dataList.get(position);
                mViewModel.unCollectArticle(bean.getId(), bean.getOriginId(), position);
            }
        });
    }

    @Override
    protected void onRetryBtnClick(View v) {
        mPage = 0;
        mViewModel.loadCollectArticleList(mPage, true);
    }

    @Override
    public void onSingleClick(View v) {

    }
}
