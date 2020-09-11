package com.hjc.reader.ui.film.child;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.MovieComingBean;
import com.hjc.reader.bean.response.MovieItemBean;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.FragmentComingMovieBinding;
import com.hjc.reader.ui.film.adapter.ComingMovieAdapter;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.film.ComingMovieViewModel;

import java.util.List;


/**
 * @Author: HJC
 * @Date: 2019/2/13 11:29
 * @Description: 即将上映电影页面
 */
public class ComingMovieFragment extends BaseMvmLazyFragment<FragmentComingMovieBinding, ComingMovieViewModel> {

    private ComingMovieAdapter mAdapter;

    public static ComingMovieFragment newInstance() {
        return new ComingMovieFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coming_movie;
    }

    @Override
    protected ComingMovieViewModel getViewModel() {
        return new ViewModelProvider(this).get(ComingMovieViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mBindingView.rvList.setLayoutManager(manager);

        mAdapter = new ComingMovieAdapter(null);
        mBindingView.rvList.setAdapter(mAdapter);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
    }

    @Override
    public void initData() {
        mViewModel.loadComingMovieData(true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getComingMovieLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();
            mAdapter.setNewInstance(data);
        });
    }

    @Override
    public void addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mViewModel.loadComingMovieData(false);
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<MovieComingBean.ComingItemBean> dataList = mAdapter.getData();
            MovieComingBean.ComingItemBean bean = dataList.get(position);

            ImageView ivCover = view.findViewById(R.id.iv_cover);

            MovieItemBean movieItemBean = new MovieItemBean();
            movieItemBean.setId(bean.getId());
            movieItemBean.setDN(bean.getDirector());
            movieItemBean.setTCn(bean.getTitle());
            movieItemBean.setMovieType(bean.getType());
            movieItemBean.setImg(bean.getImage());
            String actor1 = bean.getActor1();
            String actor2 = bean.getActor2();
            if (!TextUtils.isEmpty(actor2)) {
                actor1 = actor1 + " / " + actor2;
            }
            movieItemBean.setActors(actor1);

            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", movieItemBean);
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, ivCover, mContext.getResources().getString(R.string.transition_movie_img));
            RouteManager.jumpWithScene(mContext, RoutePath.URL_MOVIE_DETAIL, bundle, compat);
        });
    }

    @Override
    protected void onRetryBtnClick(View v) {
        mViewModel.loadComingMovieData(true);
    }

    @Override
    public void onSingleClick(View v) {

    }
}
