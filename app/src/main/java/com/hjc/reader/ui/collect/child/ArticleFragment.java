package com.hjc.reader.ui.collect.child;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.GankIOBean;
import com.hjc.reader.model.response.WanCollectBean;
import com.hjc.reader.model.response.WanListBean;
import com.hjc.reader.ui.collect.adapter.ArticleAdapter;
import com.hjc.reader.ui.gank.adapter.GankAdapter;
import com.hjc.reader.utils.SchemeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/3/11 11:00
 * @Description: 文章页面
 */
public class ArticleFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_article)
    RecyclerView rvArticle;

    private ArticleAdapter mAdapter;

    private int mPage = 0;

    public static ArticleFragment newInstance() {
        ArticleFragment fragment = new ArticleFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvArticle.setLayoutManager(manager);

        mAdapter = new ArticleAdapter(null);
        rvArticle.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取文章列表数据
     */
    private void getArticleList() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getArticleList(mPage)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<WanCollectBean>() {
                    @Override
                    public void onNext(WanCollectBean wanCollectBean) {
                        if (wanCollectBean != null) {
                            parseArticleData(wanCollectBean);
                        } else {
                            ToastUtils.showShort("未获取到数据");
                            smartRefreshLayout.finishRefresh(1000);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 解析文章数据
     *
     * @param wanCollectBean 文章收藏的对应的bean
     */
    private void parseArticleData(WanCollectBean wanCollectBean) {
        WanCollectBean.DataBean dataBean = wanCollectBean.getData();
        if (dataBean != null) {
            List<WanCollectBean.DataBean.DatasBean> dataList = dataBean.getDatas();
            if (dataList != null && dataList.size() > 0) {
                if (mPage == 0) {
                    mAdapter.setNewData(dataList);
                    smartRefreshLayout.finishRefresh(1000);
                } else {
                    mAdapter.addData(dataList);
                    smartRefreshLayout.finishLoadMore(1000);
                }
            }else{
                smartRefreshLayout.finishLoadMore(1000);
            }
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getArticleList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                getArticleList();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<WanCollectBean.DataBean.DatasBean> dataList = adapter.getData();
                WanCollectBean.DataBean.DatasBean bean = dataList.get(position);
                String title = bean.getTitle();
                String link = bean.getLink();
                SchemeUtils.jumpToWeb(mContext, link, title);
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
