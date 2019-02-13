package com.hjc.reader.ui.douban.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.DBMovieBean;
import com.hjc.reader.ui.douban.adapter.MovieTopAdapter;
import com.hjc.reader.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/2/13 11:29
 * @Description: 豆瓣电影Top250页面
 */
public class MovieTopActivity extends BaseActivity {
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.rv_top)
    RecyclerView rvTop;

    private MovieTopAdapter mAdapter;
    private int start = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_top;
    }

    @Override
    public void initView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        rvTop.setLayoutManager(manager);

        mAdapter = new MovieTopAdapter(null);
        rvTop.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取Top250电影数据
     */
    private void getTopData() {
        RetrofitHelper.getInstance().getDouBanService()
                .getMovieTop250(start, 21)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<DBMovieBean>() {
                    @Override
                    public void onNext(DBMovieBean dbMovieBean) {
                        if (dbMovieBean != null) {
                            parseTopData(dbMovieBean);
                        } else {
                            ToastUtils.showShort("未获取到数据");
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
     * 解析Top250电影数据
     * @param dbMovieBean  电影数据对应的bean
     */
    private void parseTopData(DBMovieBean dbMovieBean) {
        List<DBMovieBean.SubjectsBean> subjectsList = dbMovieBean.getSubjects();
        if (subjectsList != null) {
            if (start == 0) {
                mAdapter.setNewData(subjectsList);
                smartRefreshLayout.finishRefresh(1000);
            } else {
                mAdapter.addData(subjectsList);
                smartRefreshLayout.finishLoadMore(1000);
            }
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                start += 21;
                getTopData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                start = 0;
                getTopData();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("position---" + position);
            }
        });

        titleBar.setOnViewClickListener(new TitleBar.onViewClick() {
            @Override
            public void leftClick(View view) {
                finish();
            }

            @Override
            public void rightClick(View view) {

            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
