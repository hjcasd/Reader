package com.hjc.reader.ui.douban.child;

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
import com.hjc.reader.model.response.CollectLinkBean;
import com.hjc.reader.model.response.JokeBean;
import com.hjc.reader.ui.douban.adapter.JokeAdapter;
import com.hjc.reader.widget.helper.LinearItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/3/11 11:00
 * @Description: 段子页面
 */
public class JokeFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_joke)
    RecyclerView rvJoke;

    private JokeAdapter mAdapter;

    private int mPage = 1;

    public static JokeFragment newInstance() {
        JokeFragment fragment = new JokeFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_joke;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvJoke.setLayoutManager(manager);

        mAdapter = new JokeAdapter(null);
        rvJoke.setAdapter(mAdapter);

        rvJoke.addItemDecoration(new LinearItemDecoration(mContext, LinearItemDecoration.VERTICAL_LIST, R.drawable.shape_rv_divider));

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取段子列表
     */
    private void getJokeList() {
        RetrofitHelper.getInstance().getQSBKService()
                .getJokeList(mPage)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<JokeBean>() {
                    @Override
                    public void onNext(JokeBean jokeBean) {
                        if (jokeBean != null) {
                            parseJokeList(jokeBean);
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

    private void parseJokeList(JokeBean jokeBean) {
        List<JokeBean.ItemsBean> dataList = jokeBean.getItems();
        if (dataList != null && dataList.size() > 0) {
            if (mPage == 1) {
                mAdapter.setNewData(dataList);
                smartRefreshLayout.finishRefresh(1000);
            } else {
                mAdapter.addData(dataList);
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
                getJokeList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                getJokeList();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }

}
