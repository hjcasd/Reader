package com.hjc.reader.ui.wan.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.WanListBean;
import com.hjc.reader.ui.wan.adapter.TagListAdapter;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/2/13 10:24
 * @Description: 知识体系tag下文章列表页面
 */
public class TagListActivity extends BaseActivity {
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.rv_article)
    RecyclerView rvArticle;

    private TagListAdapter mAdapter;

    private int page = 0;
    private String name;
    private int id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tag_list;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvArticle.setLayoutManager(manager);

        mAdapter = new TagListAdapter(null);
        rvArticle.setAdapter(mAdapter);

        //添加列表动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            id = intent.getIntExtra("id", 0);

            titleBar.setTitle(name);
            smartRefreshLayout.autoRefresh();
        }
    }

    /**
     * 获取文章列表数据
     */
    private void getListData() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getWanList(page, id)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<WanListBean>() {
                    @Override
                    public void onNext(WanListBean wanListBean) {
                        if (wanListBean != null) {
                            if (wanListBean.getErrorCode() == 0) {
                                parseListData(wanListBean);
                            } else {
                                ToastUtils.showShort(wanListBean.getErrorMsg());
                            }
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
     * 解析文章列表数据
     *
     * @param wanListBean 文章列表对应的bean
     */
    private void parseListData(WanListBean wanListBean) {
        WanListBean.DataBean dataBean = wanListBean.getData();
        if (dataBean != null) {
            List<WanListBean.DataBean.DatasBean> dataList = dataBean.getDatas();

            if (dataList != null && dataList.size() > 0) {
                if (page == 0) {
                    mAdapter.setNewData(dataList);
                    smartRefreshLayout.finishRefresh(1000);
                } else {
                    mAdapter.addData(dataList);
                    smartRefreshLayout.finishLoadMore(1000);
                }
            } else {
                ToastUtils.showShort("没有更多数据了");
                smartRefreshLayout.finishLoadMore(1000);
            }
        }
    }

    @Override
    public void addListeners() {
        titleBar.setOnViewClickListener(new TitleBar.onViewClick() {
            @Override
            public void leftClick(View view) {
                finish();
            }

            @Override
            public void rightClick(View view) {

            }
        });

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getListData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getListData();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<WanListBean.DataBean.DatasBean> dataList = adapter.getData();
                WanListBean.DataBean.DatasBean bean = dataList.get(position);
                String title = bean.getTitle();
                String link = bean.getLink();
                SchemeUtils.jumpToWeb(TagListActivity.this, link, title);
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
