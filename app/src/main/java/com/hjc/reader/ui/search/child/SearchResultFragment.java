package com.hjc.reader.ui.search.child;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.baselib.event.EventManager;
import com.hjc.baselib.event.MessageEvent;
import com.hjc.baselib.fragment.BaseMvmFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.WanListBean;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.FragmentSearchResultBinding;
import com.hjc.reader.ui.search.adapter.SearchAdapter;
import com.hjc.reader.utils.helper.AccountManager;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.search.SearchResultViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @Author: HJC
 * @Date: 2020/9/11 10:55
 * @Description: 搜索结果页面
 */
public class SearchResultFragment extends BaseMvmFragment<FragmentSearchResultBinding, SearchResultViewModel> {

    private SearchAdapter mAdapter;

    private int mPage = 0;

    private String mKeyword;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected SearchResultViewModel getViewModel() {
        return new ViewModelProvider(this).get(SearchResultViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mBindingView.rvList.setLayoutManager(manager);

        mAdapter = new SearchAdapter();
        mBindingView.rvList.setAdapter(mAdapter);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        EventManager.register(this);
    }

    /**
     * 点击搜索后的逻辑
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handlerEvent(MessageEvent<String> messageEvent) {
        if (messageEvent.getCode() == EventCode.SEARCH_RESULT) {
            mKeyword = messageEvent.getData();
            mPage = 0;
            mBindingView.rvList.scrollToPosition(0);
            mViewModel.loadSearchData(mPage, mKeyword, true);
        }
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getSearchLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();
            mBindingView.smartRefreshLayout.finishLoadMore();

            if (data != null) {
                if (mPage == 0) {
                    mAdapter.setNewInstance(data);
                } else {
                    mAdapter.addData(data);
                }
            }
        });

        mViewModel.getArticleLiveData().observe(this, data -> {
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mViewModel.loadSearchData(mPage, mKeyword, false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mViewModel.loadSearchData(mPage, mKeyword, false);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<WanListBean.DataBean.DatasBean> dataList = mAdapter.getData();
            WanListBean.DataBean.DatasBean bean = dataList.get(position);
            RouteManager.jumpToWeb(bean.getTitle(), bean.getLink());
        });

        mAdapter.addChildClickViewIds(R.id.cb_collect);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.cb_collect) {
                List<WanListBean.DataBean.DatasBean> dataList = mAdapter.getData();
                WanListBean.DataBean.DatasBean bean = dataList.get(position);

                //收藏或取消收藏前判断是否已登录
                if (AccountManager.getInstance().isLogin()) {
                    if (!bean.isCollect()) {
                        mViewModel.collectArticle(bean);
                    } else {
                        mViewModel.unCollectArticle(bean);
                    }
                } else {
                    bean.setCollect(false);
                    adapter.notifyItemChanged(position);
                    RouteManager.jump(RoutePath.URL_LOGIN);
                }
            }
        });
    }

    @Override
    protected void onRetryBtnClick(View v) {
        mPage = 0;
        mViewModel.loadSearchData(mPage, mKeyword, true);
    }

    @Override
    protected void onSingleClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }
}
