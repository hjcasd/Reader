package com.hjc.reader.ui.menu;

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
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.JokeBean;
import com.hjc.reader.ui.menu.adapter.JokeAdapter;
import com.hjc.reader.widget.TitleBar;
import com.hjc.reader.widget.dialog.ShareJokeDialog;
import com.hjc.reader.widget.helper.LinearItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/3/11 11:00
 * @Description: 段子页面
 */
public class JokeActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_joke)
    RecyclerView rvJoke;

    private JokeAdapter mAdapter;
    private int mPage = 1;

    public static JokeActivity newInstance() {
        return new JokeActivity();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_joke;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvJoke.setLayoutManager(manager);

        mAdapter = new JokeAdapter(null);
        rvJoke.setAdapter(mAdapter);

        rvJoke.addItemDecoration(new LinearItemDecoration(this, LinearItemDecoration.VERTICAL_LIST, R.drawable.shape_rv_divider));

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getJokeList(true);
    }

    /**
     * 获取段子列表
     *
     * @param isShow 是否显示loading
     */
    private void getJokeList(boolean isShow) {
        RetrofitHelper.getInstance().getQSBKService()
                .getJokeList(mPage)
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<JokeBean>(getSupportFragmentManager(), isShow) {
                    @Override
                    public void onSuccess(JokeBean result) {
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
                        if (result != null) {
                            parseJokeList(result);
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

    private void parseJokeList(JokeBean jokeBean) {
        List<JokeBean.ItemsBean> dataList = jokeBean.getItems();
        if (dataList != null && dataList.size() > 0) {
            if (mPage == 1) {
                mAdapter.setNewData(dataList);
            } else {
                mAdapter.addData(dataList);
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
                mPage++;
                getJokeList(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                getJokeList(false);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<JokeBean.ItemsBean> dataList = mAdapter.getData();
            JokeBean.ItemsBean bean = dataList.get(position);

            ShareJokeDialog.newInstance(bean.getContent()).showDialog(getSupportFragmentManager());
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
