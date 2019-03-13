package com.hjc.reader.ui.collect.child;

import android.content.Context;
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
import com.trello.rxlifecycle2.LifecycleProvider;

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

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
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
     * 解析收藏文章列表数据
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

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<CollectArticleBean.DataBean.DatasBean> dataList = mAdapter.getData();
                CollectArticleBean.DataBean.DatasBean bean = dataList.get(position);
                unCollectArticle(bean.getId(), bean.getOriginId(), position);
            }
        });
    }

    /**
     * @param id       文章id
     * @param originId 文章originId
     * @param position 位置
     */
    private void unCollectArticle(int id, int originId, int position) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .unCollectOrigin(id, originId)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<CollectArticleBean>() {
                    @Override
                    public void onNext(CollectArticleBean collectArticleBean) {
                        parseUnCollectData(collectArticleBean, position);
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
     * 解析取消收藏文章是否成功
     *
     * @param collectArticleBean 返回结果对应的bean
     */
    private void parseUnCollectData(CollectArticleBean collectArticleBean, int position) {
        if (collectArticleBean != null) {
            if (collectArticleBean.getErrorCode() == 0) {
                mAdapter.remove(position);
                ToastUtils.showShort("已取消收藏");
            } else {
                ToastUtils.showShort(collectArticleBean.getErrorMsg());
            }
        } else {
            ToastUtils.showShort("服务器异常,请稍后重试");
        }
    }

    @Override
    public void onSingleClick(View v) {

    }
}
