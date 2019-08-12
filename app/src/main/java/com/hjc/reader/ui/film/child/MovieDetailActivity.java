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
    @BindView(R.id.rv_role)
    RecyclerView rvRole;

    private String movieName;
    private String movieUrl;

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

            initRoleList(basicBean.getActors());

        } else {
            ToastUtils.showShort("数据解析异常");
        }
    }

    /**
     * 初始化演职员数据
     *
     * @param actorList 演职员对应bean
     */
    private void initRoleList(List<MovieDetailBean.DataBean.BasicBean.ActorsBean> actorList) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRole.setLayoutManager(manager);

        RoleAdapter adapter = new RoleAdapter(actorList);
        rvRole.setAdapter(adapter);
    }

    @Override
    public void addListeners() {
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

    }
}
