package com.hjc.reader.ui.film.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.hjc.baselib.activity.BaseMvmActivity;
import com.hjc.baselib.widget.bar.OnViewClickListener;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.MovieDetailBean;
import com.hjc.reader.bean.response.MovieItemBean;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ActivityMovieDetailBinding;
import com.hjc.reader.ui.film.adapter.RoleAdapter;
import com.hjc.reader.ui.film.adapter.StillsAdapter;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.film.MovieDetailViewModel;
import com.vansz.glideimageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: HJC
 * @Date: 2019/3/7 16:18
 * @Description: 电影详情页
 */
@Route(path = RoutePath.URL_MOVIE_DETAIL)
public class MovieDetailActivity extends BaseMvmActivity<ActivityMovieDetailBinding, MovieDetailViewModel> {

    @Autowired(name = "params")
    Bundle bundle;

    private Transferee transferee;

    private String movieName;
    private String movieUrl;

    private String trailerUrl;
    private String trailerTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected MovieDetailViewModel getViewModel() {
        return new ViewModelProvider(this).get(MovieDetailViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        transferee = Transferee.getDefault(this);

        if (bundle != null) {
            MovieItemBean bean = (MovieItemBean) bundle.getSerializable("bean");
            if (bean != null) {
                movieName = bean.getTCn();

                mBindingView.titleBar.setTitle(movieName);
                mBindingView.setMovieBean(bean);

                mViewModel.getMovieDetailData(bean.getId());
            }
        }
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getMovieDetailLiveData().observe(this, data -> {
            mBindingView.clContent.setVisibility(View.VISIBLE);

            MovieDetailBean.DataBean.BasicBean basicBean = data.getBasic();
            mBindingView.setBasicBean(basicBean);

            if (data.getRelated() != null) {
                movieUrl = data.getRelated().getRelatedUrl();
            }

            initActors(basicBean.getActors());
            initTrailer(basicBean.getVideo());
            initStills(basicBean.getStageImg());
        });
    }


    /**
     * 初始化演员数据
     *
     * @param actorList 演职员对应的集合
     */
    private void initActors(List<MovieDetailBean.DataBean.BasicBean.ActorsBean> actorList) {
        if (actorList != null && actorList.size() > 0) {
            mBindingView.clActor.setVisibility(View.VISIBLE);

            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mBindingView.rvRole.setLayoutManager(manager);

            RoleAdapter roleAdapter = new RoleAdapter(actorList);
            mBindingView.rvRole.setAdapter(roleAdapter);

            roleAdapter.setOnItemClickListener((adapter, view, position) -> {
                List<MovieDetailBean.DataBean.BasicBean.ActorsBean> dataList = roleAdapter.getData();
                List<String> imagesList = new ArrayList<>();
                for (MovieDetailBean.DataBean.BasicBean.ActorsBean listBean : dataList) {
                    imagesList.add(listBean.getImg());
                }

                TransferConfig config = TransferConfig.build()
                        .setSourceUrlList(imagesList)
                        .setMissPlaceHolder(R.mipmap.img_default_meizi)
                        .setErrorPlaceHolder(R.mipmap.img_default_meizi)
                        .setProgressIndicator(new ProgressPieIndicator())
                        .setIndexIndicator(new NumberIndexIndicator())
                        .setImageLoader(GlideImageLoader.with(this))
                        .setBackgroundColor(Color.parseColor("#000000"))
                        .setDuration(300)
                        .setOffscreenPageLimit(2)
                        .setNowThumbnailIndex(position)
                        .enableJustLoadHitPage(true)
                        .enableDragClose(true)
                        .enableDragHide(false)
                        .enableDragPause(false)
                        .enableHideThumb(false)
                        .enableScrollingWithPageChange(false)
                        .bindRecyclerView(mBindingView.rvRole, R.id.iv_role_pic);

                transferee.apply(config).show();
            });
        } else {
            mBindingView.clActor.setVisibility(View.GONE);
        }
    }


    /**
     * 初始化预告片
     *
     * @param bean 预告片对应的bean
     */
    private void initTrailer(MovieDetailBean.DataBean.BasicBean.VideoBean bean) {
        if (bean != null && !StringUtils.isEmpty(bean.getUrl())) {
            mBindingView.setVideoBean(bean);

            trailerUrl = bean.getHightUrl();
            trailerTitle = bean.getTitle();
        } else {
            mBindingView.clTrailer.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化剧照
     *
     * @param bean 剧照对应的bean
     */
    private void initStills(MovieDetailBean.DataBean.BasicBean.StageImgBean bean) {
        List<MovieDetailBean.DataBean.BasicBean.StageImgBean.ListBean> list = bean.getList();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBindingView.rvStills.setLayoutManager(manager);

        StillsAdapter stillsAdapter = new StillsAdapter(list);
        mBindingView.rvStills.setAdapter(stillsAdapter);

        stillsAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<MovieDetailBean.DataBean.BasicBean.StageImgBean.ListBean> dataList = stillsAdapter.getData();
            List<String> imagesList = new ArrayList<>();
            for (MovieDetailBean.DataBean.BasicBean.StageImgBean.ListBean listBean : dataList) {
                imagesList.add(listBean.getImgUrl());
            }

            TransferConfig config = TransferConfig.build()
                    .setSourceUrlList(imagesList)
                    .setMissPlaceHolder(R.mipmap.img_default_meizi)
                    .setErrorPlaceHolder(R.mipmap.img_default_meizi)
                    .setProgressIndicator(new ProgressPieIndicator())
                    .setIndexIndicator(new NumberIndexIndicator())
                    .setImageLoader(GlideImageLoader.with(this))
                    .setBackgroundColor(Color.parseColor("#000000"))
                    .setDuration(300)
                    .setOffscreenPageLimit(2)
                    .setNowThumbnailIndex(position)
                    .enableJustLoadHitPage(true)
                    .enableDragClose(true)
                    .enableDragHide(false)
                    .enableDragPause(false)
                    .enableHideThumb(false)
                    .enableScrollingWithPageChange(false)
                    .bindRecyclerView(mBindingView.rvStills, R.id.iv_pic);

            transferee.apply(config).show();
        });
    }


    @Override
    public void addListeners() {
        mBindingView.ivTrailer.setOnClickListener(this);

        mBindingView.titleBar.setOnViewClickListener(new OnViewClickListener() {
            @Override
            public void leftClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }

            @Override
            public void rightClick(View view) {
                RouteManager.jumpToWeb(movieName, movieUrl);
            }
        });
    }

    @Override
    public void onSingleClick(View v) {
        if (v.getId() == R.id.iv_trailer) {
            RouteManager.jumpToWeb(trailerTitle, trailerUrl);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        transferee.destroy();
    }
}
