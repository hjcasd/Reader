package com.hjc.reader.ui.douban.child;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.DBMovieBean;
import com.hjc.reader.ui.douban.adapter.MovieAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 电影页面
 */
public class MovieFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;

    private ConstraintLayout clTop;

    private MovieAdapter mAdapter;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    public void initView() {
        View headerView = View.inflate(mContext, R.layout.layout_header_top250, null);
        clTop = headerView.findViewById(R.id.cl_top);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvMovie.setLayoutManager(manager);

        mAdapter = new MovieAdapter(null);
        rvMovie.setAdapter(mAdapter);

        mAdapter.addHeaderView(headerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取豆瓣电影数据
     */
    private void getMovieData() {
        RetrofitHelper.getInstance().getDouBanService()
                .getMovieList()
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<DBMovieBean>() {
                    @Override
                    public void onNext(DBMovieBean dbMovieBean) {
                        if (dbMovieBean != null) {
                            parseMovieData(dbMovieBean);
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
     * 解析豆瓣电影数据
     *
     * @param dbMovieBean 电影数据对应的bean
     */
    private void parseMovieData(DBMovieBean dbMovieBean) {
        List<DBMovieBean.SubjectsBean> movieList = dbMovieBean.getSubjects();
        if (movieList != null && movieList.size() > 0) {

            rvMovie.setVisibility(View.VISIBLE);
            mAdapter.setNewData(movieList);
            smartRefreshLayout.finishRefresh(1000);
        }else{
            rvMovie.setVisibility(View.GONE);
        }
    }

    @Override
    public void addListeners() {
        clTop.setOnClickListener(this);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMovieData();
            }
        });
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.cl_top:
                startActivity(new Intent(mContext, MovieTopActivity.class));
                break;
        }
    }
}
