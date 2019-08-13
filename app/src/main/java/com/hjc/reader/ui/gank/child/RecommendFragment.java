package com.hjc.reader.ui.gank.child;

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
import com.hjc.reader.model.response.GankDayBean;
import com.hjc.reader.model.response.GankRecommendBean;
import com.hjc.reader.ui.gank.adapter.RecommendAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 每日推荐页面
 */
public class RecommendFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    private RecommendAdapter mAdapter;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvRecommend.setLayoutManager(manager);

        mAdapter = new RecommendAdapter(null);
        rvRecommend.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
    }

    @Override
    public void initData() {
        getRecommendData(true);
    }

    /**
     * 获取推荐数据
     *
     * @param isShow 是否显示loading
     */
    private void getRecommendData(boolean isShow) {
        RetrofitHelper.getInstance().getGankIOService()
                .getRecommendData()
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<GankRecommendBean>(getChildFragmentManager(), isShow) {
                    @Override
                    public void onSuccess(GankRecommendBean result) {
                        smartRefreshLayout.finishRefresh();
                        if (result != null) {
                            parseRecommendData(result);
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
     * 解析推荐数据
     *
     * @param gankRecommendBean 推荐数据对应的bean
     */
    private void parseRecommendData(GankRecommendBean gankRecommendBean) {
        GankRecommendBean.ResultsBean resultsBean = gankRecommendBean.getResults();
        if (resultsBean != null) {
            List<GankDayBean> dataList = new ArrayList<>();
            //福利区
            List<GankDayBean> welfareList = resultsBean.getWelfare();
            if (welfareList != null && welfareList.size() > 0) {
                GankDayBean bean = new GankDayBean();
                bean.setTitle("福利");
                dataList.add(bean);
                dataList.addAll(welfareList);
            }

            //Android区
            List<GankDayBean> androidList = resultsBean.getAndroid();
            if (androidList != null && androidList.size() > 0) {
                GankDayBean bean = new GankDayBean();
                bean.setTitle("Android");
                dataList.add(bean);
                dataList.addAll(androidList);
            }

            //IOS区
            List<GankDayBean> iosList = resultsBean.getiOS();
            if (iosList != null && iosList.size() > 0) {
                GankDayBean bean = new GankDayBean();
                bean.setTitle("IOS");
                dataList.add(bean);
                dataList.addAll(iosList);
            }

            //Web区
            List<GankDayBean> webList = resultsBean.getFront();
            if (iosList != null && webList.size() > 0) {
                GankDayBean bean = new GankDayBean();
                bean.setTitle("前端");
                dataList.add(bean);
                dataList.addAll(webList);
            }

            //休息视频
            List<GankDayBean> restList = resultsBean.getRest();
            if (restList != null && restList.size() > 0) {
                GankDayBean bean = new GankDayBean();
                bean.setTitle("休息视频");
                dataList.add(bean);
                dataList.addAll(restList);
            }

            //拓展资源
            List<GankDayBean> extraList = resultsBean.getExtra();
            if (extraList != null && extraList.size() > 0) {
                GankDayBean bean = new GankDayBean();
                bean.setTitle("拓展资源");
                dataList.add(bean);
                dataList.addAll(extraList);
            }

            List<GankDayBean> recommendList = resultsBean.getRecommend();
            if (recommendList != null && recommendList.size() > 0) {
                GankDayBean bean = new GankDayBean();
                bean.setTitle("瞎推荐");
                dataList.add(bean);
                dataList.addAll(recommendList);
            }
            mAdapter.setNewData(dataList);
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> getRecommendData(false));
    }

    @Override
    public void onSingleClick(View v) {

    }
}
