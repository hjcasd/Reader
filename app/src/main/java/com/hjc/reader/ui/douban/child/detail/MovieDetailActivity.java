package com.hjc.reader.ui.douban.child.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.RoleBean;
import com.hjc.reader.model.response.DBMovieBean;
import com.hjc.reader.model.response.DBMovieDetailBean;
import com.hjc.reader.ui.douban.adapter.MovieAdapter;
import com.hjc.reader.ui.douban.adapter.RoleAdapter;
import com.hjc.reader.utils.FormatUtils;
import com.hjc.reader.utils.image.ImageLoader;
import com.hjc.reader.widget.TitleBar;
import com.hjc.reader.widget.dialog.LoadingDialog;
import com.hjc.reader.widget.helper.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DefaultObserver;

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
    @BindView(R.id.tv_show_date)
    TextView tvShowDate;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.cl_content)
    ConstraintLayout clContent;
    @BindView(R.id.tv_other_name)
    TextView tvOtherName;
    @BindView(R.id.tv_brief)
    TextView tvBrief;
    @BindView(R.id.rv_role)
    RecyclerView rvRole;

    private LoadingDialog loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void initView() {
        loadingDialog = LoadingDialog.newInstance();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            DBMovieBean.SubjectsBean bean = (DBMovieBean.SubjectsBean) intent.getSerializableExtra("bean");
            if (bean != null) {
                titleBar.setTitle(bean.getTitle());

                ImageLoader.loadImage(ivCover, bean.getImages().getLarge(), 3);
                tvDirector.setText(FormatUtils.formatName(bean.getDirectors()));
                tvMain.setText(FormatUtils.formatName(bean.getCasts()));
                tvType.setText(FormatUtils.formatGenres(bean.getGenres()));
                tvScore.setText(bean.getRating().getAverage() + "");

                loadingDialog.showDialog(getSupportFragmentManager());
                getDetailData(bean.getId());
            }
        }
    }

    /**
     * 获取电影详情页数据
     * @param id 电影id
     */
    private void getDetailData(String id) {
        RetrofitHelper.getInstance().getDouBanService()
                .getMovieDetail(id)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<DBMovieDetailBean>() {
                    @Override
                    public void onNext(DBMovieDetailBean dbMovieDetailBean) {
                        if (dbMovieDetailBean != null) {
                            parseDetailData(dbMovieDetailBean);
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
     * 解析电影详情页数据
     * @param dbMovieDetailBean 电影详情对应的bean
     */
    private void parseDetailData(DBMovieDetailBean dbMovieDetailBean) {
        if (dbMovieDetailBean != null){
            clContent.setVisibility(View.VISIBLE);

            tvShowDate.setText(dbMovieDetailBean.getYear());
            tvFrom.setText(FormatUtils.formatGenres(dbMovieDetailBean.getCountries()));
            tvOtherName.setText(FormatUtils.formatGenres(dbMovieDetailBean.getAka()));
            tvBrief.setText(dbMovieDetailBean.getSummary());

            initRoleList(dbMovieDetailBean.getDirectors(), dbMovieDetailBean.getCasts());

            loadingDialog.dismissDialog();
        }
    }

    private void initRoleList(List<DBMovieDetailBean.DirectorsBean> directors, List<DBMovieDetailBean.CastsBean> casts) {
        List<RoleBean> list = new ArrayList<>();
        for (int i = 0; i < directors.size(); i++) {
            RoleBean bean = new RoleBean();
            DBMovieDetailBean.DirectorsBean directorsBean = directors.get(i);
            bean.setId(directorsBean.getId());
            bean.setName(directorsBean.getName());
            bean.setType("导演");
            bean.setAlt(directorsBean.getAlt());
            bean.setAvatar(directorsBean.getAvatars().getLarge());
            list.add(bean);
        }

        for (int i = 0; i < casts.size(); i++) {
            RoleBean bean = new RoleBean();
            DBMovieDetailBean.CastsBean castsBean = casts.get(i);
            bean.setId(castsBean.getId());
            bean.setName(castsBean.getName());
            bean.setType("演员");
            bean.setAlt(castsBean.getAlt());
            bean.setAvatar(castsBean.getAvatars().getLarge());
            list.add(bean);
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRole.setLayoutManager(manager);

//        rvRole.addItemDecoration(new LinearItemDecoration(this, LinearItemDecoration.VERTICAL_LIST, R.drawable.shape_rv_divider));

        RoleAdapter adapter = new RoleAdapter(list);
        rvRole.setAdapter(adapter);
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
    }

    @Override
    public void onSingleClick(View v) {

    }

}
