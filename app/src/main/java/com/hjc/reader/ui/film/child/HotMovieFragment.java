package com.hjc.reader.ui.film.child;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.MovieItemBean;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.FragmentHotMovieBinding;
import com.hjc.reader.ui.film.adapter.HotMovieAdapter;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.film.HotMovieViewModel;

import java.util.List;


/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 热映电影页面
 */
public class HotMovieFragment extends BaseMvmLazyFragment<FragmentHotMovieBinding, HotMovieViewModel> {
    private HotMovieAdapter mAdapter;

    public static HotMovieFragment newInstance() {
        return new HotMovieFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hot_movie;
    }

    @Override
    protected HotMovieViewModel getViewModel() {
        return new ViewModelProvider(this).get(HotMovieViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mBindingView.rvList.setLayoutManager(manager);

        mAdapter = new HotMovieAdapter(null);
        mBindingView.rvList.setAdapter(mAdapter);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
    }

    @Override
    public void initData() {
        mViewModel.loadHotMovieData(true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getHotMovieLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();
            mAdapter.setNewInstance(data);
        });
    }

    @Override
    public void addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mViewModel.loadHotMovieData(false);
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<MovieItemBean> dataList = mAdapter.getData();
            MovieItemBean bean = dataList.get(position);

            ImageView ivCover = view.findViewById(R.id.iv_cover);

            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", bean);
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, ivCover, mContext.getResources().getString(R.string.transition_movie_img));
            RouteManager.jumpWithScene(mContext, RoutePath.URL_MOVIE_DETAIL, bundle, compat);
        });
    }

    @Override
    protected void onRetryBtnClick(View v) {
        mViewModel.loadHotMovieData(true);
    }

    @Override
    public void onSingleClick(View v) {

    }

}
