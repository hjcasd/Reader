package com.hjc.reader.ui.wan.child;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.BaseCommonObserver;
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.WanBannerBean;
import com.hjc.reader.model.response.WanListBean;
import com.hjc.reader.ui.wan.adapter.WanListAdapter;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.utils.image.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 玩安卓页面
 */
public class WanFragment extends BaseLazyFragment {
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private Banner banner;

    private List<WanBannerBean.DataBean> mBannerList;
    private WanListAdapter mAdapter;

    //页码
    private int page = 0;

    public static WanFragment newInstance() {
        return new WanFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wan;
    }

    @Override
    public void initView() {
        View headerView = View.inflate(mContext, R.layout.layout_header_banner, null);
        banner = headerView.findViewById(R.id.banner);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvList.setLayoutManager(manager);

        mAdapter = new WanListAdapter(null);
        rvList.setAdapter(mAdapter);

        mAdapter.addHeaderView(headerView);

        //添加列表动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    @Override
    public void initData() {
        getBannerData();
        getListData(true);
    }

    /**
     * 获取Banner数据
     */
    private void getBannerData() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getWanBannerList()
                .compose(RxHelper.bind(this))
                .subscribe(new BaseCommonObserver<WanBannerBean>() {
                    @Override
                    public void onSuccess(WanBannerBean result) {
                        if (result != null) {
                            if (result.getErrorCode() == 0) {
                                parseBannerData(result);
                            } else {
                                ToastUtils.showShort(result.getErrorMsg());
                            }
                        }
                    }
                });
    }

    /**
     * 解析Banner数据
     *
     * @param result banner对应的bean
     */
    private void parseBannerData(WanBannerBean result) {
        mBannerList = result.getData();

        List<String> imgList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        if (mBannerList != null && mBannerList.size() > 0) {
            for (WanBannerBean.DataBean bean : mBannerList) {
                imgList.add(bean.getImagePath());
                titleList.add(bean.getTitle());
            }
            banner.setImages(imgList)
                    .setImageLoader(new GlideImageLoader())
                    .setBannerAnimation(Transformer.Accordion)
                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setBannerTitles(titleList)
                    .setIndicatorGravity(BannerConfig.CENTER)
                    .start();
        }
    }

    /**
     * 获取文章列表数据
     *
     * @param isShow 是否显示loading
     */
    private void getListData(boolean isShow) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getWanList(page, null)
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<WanListBean>(getChildFragmentManager(), isShow) {
                    @Override
                    public void onSuccess(WanListBean result) {
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
                        if (result != null) {
                            if (result.getErrorCode() == 0) {
                                parseListData(result);
                            } else {
                                ToastUtils.showShort(result.getErrorMsg());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        super.onFailure(errorMsg);
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
                    }
                });
    }

    /**
     * 解析文章列表数据
     *
     * @param result 文章列表对应的bean
     */
    private void parseListData(WanListBean result) {
        WanListBean.DataBean dataBean = result.getData();
        if (dataBean != null) {
            List<WanListBean.DataBean.DatasBean> dataList = dataBean.getDatas();
            if (dataList != null && dataList.size() > 0) {
                if (page == 0) {
                    mAdapter.setNewData(dataList);
                } else {
                    mAdapter.addData(dataList);
                }
            }
        }
    }


    @Override
    public void addListeners() {
        banner.setOnBannerListener(position -> {
            WanBannerBean.DataBean bean = mBannerList.get(position);
            SchemeUtils.jumpToWeb(mContext, bean.getUrl(), bean.getTitle());
        });

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getListData(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getListData(false);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<WanListBean.DataBean.DatasBean> dataList = adapter.getData();
            WanListBean.DataBean.DatasBean bean = dataList.get(position);
            String title = bean.getTitle();
            String link = bean.getLink();
            SchemeUtils.jumpToWeb(mContext, link, title);
        });
    }


    @Override
    public void onSingleClick(View v) {

    }

    @Override
    public void onDestroyView() {
        if (banner != null) {
            banner.stopAutoPlay();
        }
        super.onDestroyView();
    }
}
