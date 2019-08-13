package com.hjc.reader.ui.menu.child;

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
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.CollectArticleBean;
import com.hjc.reader.ui.menu.adapter.ArticleAdapter;
import com.hjc.reader.utils.SchemeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

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
        return new ArticleFragment();
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
        getArticleList(true);
    }

    /**
     * 获取文章列表数据
     *
     * @param isShow 是否显示loading
     */
    private void getArticleList(boolean isShow) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getArticleList(mPage)
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<CollectArticleBean>(getChildFragmentManager(), isShow) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
                        if (result != null) {
                            parseArticleData(result);
                        } else {
                            ToastUtils.showShort("服务器异常,请稍后重试");
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        super.onFailure(errorMsg);
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
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
            } else {
                mAdapter.addData(dataList);
            }
        } else {
            if (mPage == 0) {
                ToastUtils.showShort("暂无收藏,快去收藏吧");
            } else {
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
                getArticleList(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                getArticleList(false);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<CollectArticleBean.DataBean.DatasBean> dataList = adapter.getData();
            CollectArticleBean.DataBean.DatasBean bean = dataList.get(position);
            String title = bean.getTitle();
            String link = bean.getLink();
            SchemeUtils.jumpToWeb(mContext, link, title);
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<CollectArticleBean.DataBean.DatasBean> dataList = mAdapter.getData();
            CollectArticleBean.DataBean.DatasBean bean = dataList.get(position);
            unCollectArticle(bean.getId(), bean.getOriginId(), position);
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
                .subscribe(new BaseProgressObserver<CollectArticleBean>(getChildFragmentManager()) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        parseUnCollectData(result, position);
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
