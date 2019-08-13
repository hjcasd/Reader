package com.hjc.reader.ui.film.child;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.MovieHotBean;
import com.hjc.reader.model.response.MovieItemBean;
import com.hjc.reader.ui.film.adapter.HotMovieAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 热映电影页面
 */
public class HotMovieFragment extends BaseLazyFragment {
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private HotMovieAdapter mAdapter;

    public static HotMovieFragment newInstance() {
        return new HotMovieFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hot_movie;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvList.setLayoutManager(manager);

        mAdapter = new HotMovieAdapter(null);
        rvList.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
    }

    @Override
    public void initData() {
        getHotMovieData(true);
    }

    /**
     * 获取热映榜电影数据
     *
     * @param isShow 是否显示loading
     */
    private void getHotMovieData(boolean isShow) {
        RetrofitHelper.getInstance().getMTimeService()
                .getHotFilm()
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<MovieHotBean>(getChildFragmentManager(), isShow) {
                    @Override
                    public void onSuccess(MovieHotBean result) {
                        smartRefreshLayout.finishRefresh();
                        if (result != null) {
                            parseMovieData(result);
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
     * 解析豆瓣电影数据
     *
     * @param result 电影数据对应的bean
     */
    private void parseMovieData(MovieHotBean result) {
        List<MovieItemBean> movieList = result.getMs();
        if (movieList != null && movieList.size() > 0) {
            mAdapter.setNewData(movieList);
        } else {
            ToastUtils.showShort("数据解析异常");
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> getHotMovieData(false));
    }

    @Override
    public void onSingleClick(View v) {

    }
}
