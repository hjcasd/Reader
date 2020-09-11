package com.hjc.reader.ui.wan.child;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.WanBannerBean;
import com.hjc.reader.bean.response.WanListBean;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.FragmentWanBinding;
import com.hjc.reader.ui.wan.adapter.MyBannerAdapter;
import com.hjc.reader.ui.wan.adapter.WanListAdapter;
import com.hjc.reader.utils.helper.AccountManager;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.wan.WanViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import java.util.List;


/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 玩安卓页面
 */
public class WanFragment extends BaseMvmLazyFragment<FragmentWanBinding, WanViewModel> {

    private Banner banner;

    private List<WanBannerBean.DataBean> mBannerList;

    private WanListAdapter mAdapter;

    //页码
    private int mPage = 0;

    public static WanFragment newInstance() {
        return new WanFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wan;
    }

    @Override
    protected WanViewModel getViewModel() {
        return new ViewModelProvider(this).get(WanViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        View headerView = View.inflate(mContext, R.layout.layout_header_banner, null);
        banner = headerView.findViewById(R.id.banner);
        banner.addBannerLifecycleObserver(this);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mBindingView.rvList.setLayoutManager(manager);

        mAdapter = new WanListAdapter();
        mBindingView.rvList.setAdapter(mAdapter);

        mAdapter.addHeaderView(headerView);
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
    }

    @Override
    protected void initData() {
        mViewModel.getBannerData();
        mViewModel.loadArticleList(mPage, true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getBannerLiveData().observe(this, data -> {
            banner.setAdapter(new MyBannerAdapter(data.getData()))
                    .setPageTransformer(new ZoomOutPageTransformer())
                    .setOnBannerListener((item, position) -> {
                        WanBannerBean.DataBean bean = (WanBannerBean.DataBean) item;
                        RouteManager.jumpToWeb(bean.getTitle(), bean.getUrl());
                    });
        });

        mViewModel.getListLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();
            mBindingView.smartRefreshLayout.finishLoadMore();

            if (mPage == 0) {
                mAdapter.setNewInstance(data);
            } else {
                mAdapter.addData(data);
            }
        });

        mViewModel.getArticleLiveData().observe(this, data -> {
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mViewModel.loadArticleList(mPage, false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                mViewModel.loadArticleList(mPage, false);
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
                    mAdapter.notifyDataSetChanged();
                    RouteManager.jump(RoutePath.URL_LOGIN);
                }
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }

    @Override
    protected void onRetryBtnClick(View v) {
        mPage = 0;
        mViewModel.loadArticleList(mPage, true);
        mViewModel.getBannerData();
    }
}
