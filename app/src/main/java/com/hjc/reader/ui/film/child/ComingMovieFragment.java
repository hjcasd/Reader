package com.hjc.reader.ui.film.child;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.MovieComingBean;
import com.hjc.reader.ui.film.adapter.ComingMovieAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/2/13 11:29
 * @Description: 即将上映电影页面
 */
public class ComingMovieFragment extends BaseLazyFragment {
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private ComingMovieAdapter mAdapter;

    public static ComingMovieFragment newInstance() {
        return new ComingMovieFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coming_movie;
    }

    @Override
    public void initView() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rvList.setLayoutManager(manager);

        mAdapter = new ComingMovieAdapter(null);
        rvList.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    public void initData() {
        getComingMovieData(true);
    }

    /**
     * 获取即将上映电影数据
     *
     * @param isShow 是否显示loading
     */
    private void getComingMovieData(boolean isShow) {
        RetrofitHelper.getInstance().getMTimeService()
                .getComingFilm()
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<MovieComingBean>(getChildFragmentManager(), isShow) {
                    @Override
                    public void onSuccess(MovieComingBean result) {
                        smartRefreshLayout.finishRefresh();
                        if (result != null) {
                            parseTopData(result);
                        } else {
                            ToastUtils.showShort("未获取到数据");
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        super.onFailure(errorMsg);
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }

    /**
     * 解析即将上映电影数据
     *
     * @param result 电影数据对应的bean
     */
    private void parseTopData(MovieComingBean result) {
        List<MovieComingBean.ComingItemBean> movieList = result.getMoviecomings();
        if (movieList != null) {
            mAdapter.setNewData(movieList);
        } else {
            ToastUtils.showShort("数据解析异常");
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> getComingMovieData(false));
    }

    @Override
    public void onSingleClick(View v) {

    }
}
