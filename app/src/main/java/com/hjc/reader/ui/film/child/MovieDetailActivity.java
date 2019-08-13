package com.hjc.reader.ui.film.child;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.MovieDetailBean;
import com.hjc.reader.model.response.MovieItemBean;
import com.hjc.reader.ui.film.adapter.RoleAdapter;
import com.hjc.reader.ui.film.adapter.StillsAdapter;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.utils.image.ImageManager;
import com.hjc.reader.widget.TitleBar;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/3/7 16:18
 * @Description: 电影详情页
 */
public class MovieDetailActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.iv_blur)
    ImageView ivBlur;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_director)
    TextView tvDirector;
    @BindView(R.id.tv_main)
    TextView tvMain;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_runtime)
    TextView tvRuntime;
    @BindView(R.id.tv_show_date)
    TextView tvShowDate;
    @BindView(R.id.tv_from)
    TextView tvFrom;

    @BindView(R.id.cl_content)
    ConstraintLayout clContent;
    @BindView(R.id.tv_special)
    TextView tvSpecial;
    @BindView(R.id.tv_brief)
    TextView tvBrief;

    @BindView(R.id.cl_actor)
    ConstraintLayout clActor;
    @BindView(R.id.rv_role)
    RecyclerView rvRole;

    @BindView(R.id.cl_box)
    ConstraintLayout clBox;
    @BindView(R.id.tv_today_box)
    TextView tvTodayBox;
    @BindView(R.id.tv_total_box)
    TextView tvTotalBox;
    @BindView(R.id.tv_ranking)
    TextView tvRanking;

    @BindView(R.id.cl_trailer)
    ConstraintLayout clTrailer;
    @BindView(R.id.iv_trailer)
    ImageView ivTrailer;
    @BindView(R.id.rv_stills)
    RecyclerView rvStills;

    private String movieName;
    private String movieUrl;

    private String trailerUrl;
    private String trailerTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            MovieItemBean bean = (MovieItemBean) intent.getSerializableExtra("bean");
            if (bean != null) {
                movieName = bean.getTCn();
                titleBar.setTitle(movieName);

                ImageManager.loadImage(ivCover, bean.getImg(), 3);
                ImageManager.loadBlurImage(ivBlur, bean.getImg(), 25, 5);

                tvScore.setText(String.format("评分：%s", bean.getR()));
                tvDirector.setText(String.format("导演：%s", bean.getDN()));
                tvMain.setText(String.format("主演：%s", bean.getActors()));
                tvType.setText(String.format("类型：%s", bean.getMovieType()));

                getDetailData(bean.getId());
            }
        }
    }

    /**
     * 获取电影详情页数据
     *
     * @param id 电影id
     */
    private void getDetailData(int id) {
        RetrofitHelper.getInstance().getMTimeTicketService()
                .getDetailFilm(id)
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<MovieDetailBean>(getSupportFragmentManager(), true) {
                    @Override
                    public void onSuccess(MovieDetailBean result) {
                        if (result != null) {
                            parseDetailData(result);
                        } else {
                            ToastUtils.showShort("未获取到数据");
                        }
                    }
                });
    }

    /**
     * 解析电影详情页数据
     *
     * @param result 电影详情对应的bean
     */
    private void parseDetailData(MovieDetailBean result) {
        MovieDetailBean.DataBean bean = result.getData();
        if (bean != null && bean.getBasic() != null) {
            clContent.setVisibility(View.VISIBLE);
            MovieDetailBean.DataBean.BasicBean basicBean = bean.getBasic();

            tvRuntime.setText(String.format("片长：%s", basicBean.getMins()));
            tvShowDate.setText(String.format("上映日期：%s", basicBean.getReleaseDate()));
            tvFrom.setText(String.format("产地：%s", basicBean.getReleaseArea()));

            String commentSpecial = basicBean.getCommentSpecial();
            if (!StringUtils.isEmpty(commentSpecial)) {
                tvSpecial.setVisibility(View.VISIBLE);
                tvSpecial.setText(basicBean.getCommentSpecial());
            } else {
                tvSpecial.setVisibility(View.GONE);
            }
            tvBrief.setText(basicBean.getStory());

            if (bean.getRelated() != null) {
                movieUrl = bean.getRelated().getRelatedUrl();
            }

            initActors(basicBean.getActors());
            initBoxOffice(bean.getBoxOffice());
            initTrailer(basicBean.getVideo());
            initStills(basicBean.getStageImg());
        } else {
            ToastUtils.showShort("数据解析异常");
        }
    }

    /**
     * 初始化演员数据
     *
     * @param actorList 演职员对应bean
     */
    private void initActors(List<MovieDetailBean.DataBean.BasicBean.ActorsBean> actorList) {
        if (actorList != null && actorList.size() > 0) {
            clActor.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvRole.setLayoutManager(manager);

            RoleAdapter adapter = new RoleAdapter(actorList);
            rvRole.setAdapter(adapter);
        } else {
            clActor.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化票房数据
     *
     * @param bean 票房对应的bean
     */
    private void initBoxOffice(MovieDetailBean.DataBean.BoxOfficeBean bean) {
        if (bean != null && !StringUtils.isEmpty(bean.getTotalBoxDes())) {
            clBox.setVisibility(View.VISIBLE);
            tvTodayBox.setText(bean.getTodayBoxDes());
            tvTotalBox.setText(bean.getTotalBoxDes());
            String ranking = bean.getRanking() + "";
            tvRanking.setText(ranking);
        } else {
            clBox.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化预告片
     *
     * @param bean 预告片对应的bean
     */
    private void initTrailer(MovieDetailBean.DataBean.BasicBean.VideoBean bean) {
        if (bean != null && !StringUtils.isEmpty(bean.getUrl())) {
            clTrailer.setVisibility(View.VISIBLE);
            trailerUrl = bean.getHightUrl();
            trailerTitle = bean.getTitle();
            ImageManager.loadImage(ivTrailer, bean.getImg(), 3);
        } else {
            clTrailer.setVisibility(View.GONE);
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
        rvStills.setLayoutManager(manager);

        StillsAdapter adapter = new StillsAdapter(list);
        rvStills.setAdapter(adapter);
    }

    @Override
    public void addListeners() {
        ivTrailer.setOnClickListener(this);

        titleBar.setOnViewClickListener(new TitleBar.onViewClick() {
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
                SchemeUtils.jumpToWeb(MovieDetailActivity.this, movieUrl, movieName);
            }
        });
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.iv_trailer:
                SchemeUtils.jumpToWeb(MovieDetailActivity.this, trailerUrl, trailerTitle);
                break;
        }
    }
}
