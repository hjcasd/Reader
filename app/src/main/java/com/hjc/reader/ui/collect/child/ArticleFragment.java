package com.hjc.reader.ui.collect.child;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.CollectArticleBean;
import com.hjc.reader.ui.collect.adapter.ArticleAdapter;
import com.hjc.reader.utils.SchemeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
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
                .subscribe(new DefaultObserver<CollectArticleBean>() {
                    @Override
                    public void onNext(CollectArticleBean collectArticleBean) {
                        if (collectArticleBean != null) {
                            parseArticleData(collectArticleBean);
                        } else {
                            ToastUtils.showShort("服务器异常,请稍后重试");
                            smartRefreshLayout.finishRefresh(1000);
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

    /**
     * 解析搜藏文章列表数据
     *
     * @param collectArticleBean 收藏的文章对应的bean
     */
    private void parseArticleData(CollectArticleBean collectArticleBean) {
        CollectArticleBean.DataBean dataBean = collectArticleBean.getData();
        List<CollectArticleBean.DataBean.DatasBean> dataList = dataBean.getDatas();
        if (dataList != null && dataList.size() > 0) {
            if (mPage == 0) {
                mAdapter.setNewData(dataList);
                smartRefreshLayout.finishRefresh(1000);
            } else {
                mAdapter.addData(dataList);
                smartRefreshLayout.finishLoadMore(1000);
            }
        } else {
            if (mPage == 0) {
                smartRefreshLayout.finishRefresh(1000);
                ToastUtils.showShort("暂无收藏,快去收藏吧");
            } else {
                smartRefreshLayout.finishLoadMore(1000);
                ToastUtils.showShort("没有更多收藏了");
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
                List<CollectArticleBean.DataBean.DatasBean> dataList = adapter.getData();
                CollectArticleBean.DataBean.DatasBean bean = dataList.get(position);
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
