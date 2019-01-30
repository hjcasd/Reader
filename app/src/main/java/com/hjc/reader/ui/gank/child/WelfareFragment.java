package com.hjc.reader.ui.gank.child;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.ImageViewInfo;
import com.hjc.reader.model.response.GankIOBean;
import com.hjc.reader.ui.gank.adapter.WelfareAdapter;
import com.hjc.reader.utils.ViewUtils;
import com.hjc.reader.utils.SchemeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 福利社区页面
 */
public class WelfareFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_welfare)
    RecyclerView rvWelfare;

    private WelfareAdapter mAdapter;
    private int page = 1;
    private GridLayoutManager mGridLayoutManager;

    public static WelfareFragment newInstance() {
        WelfareFragment fragment = new WelfareFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    public void initView() {
        mGridLayoutManager = new GridLayoutManager(mContext, 2);
        rvWelfare.setLayoutManager(mGridLayoutManager);

        mAdapter = new WelfareAdapter(null);
        rvWelfare.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取福利数据
     */
    private void getWelfareData() {
        RetrofitHelper.getInstance().getGankIOService()
                .getGankIoData("福利", 20, page)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<GankIOBean>() {
                    @Override
                    public void onNext(GankIOBean gankIOBean) {
                        if (gankIOBean != null) {
                            parseWelfareData(gankIOBean);
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
     * 解析福利数据
     * @param gankIOBean 福利对应的bean
     */
    private void parseWelfareData(GankIOBean gankIOBean) {
        List<GankIOBean.ResultsBean> resultList = gankIOBean.getResults();
        if (resultList != null && resultList.size() > 0) {
            if (page == 1) {
                mAdapter.setNewData(resultList);
                smartRefreshLayout.finishRefresh(1000);
            } else {
                mAdapter.addData(resultList);
                smartRefreshLayout.finishLoadMore(1000);
            }
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getWelfareData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getWelfareData();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArrayList<ImageViewInfo> list = new ArrayList<>();
                List<GankIOBean.ResultsBean> dataList = adapter.getData();
                for (GankIOBean.ResultsBean bean : dataList) {
                    ImageViewInfo viewInfo = new ImageViewInfo(bean.getUrl());
                    list.add(viewInfo);
                }

                //获取view位置坐标信息
                int firstVisibleItemPosition = mGridLayoutManager.findFirstVisibleItemPosition();
                for (int i = firstVisibleItemPosition; i < list.size(); i++) {
                    View itemView = mGridLayoutManager.findViewByPosition(i);
                    if (itemView != null) {
                        ImageView imageView = itemView.findViewById(R.id.iv_pic);
                        Rect rect = ViewUtils.computeBound(imageView);
                        list.get(i).setRect(rect);
                    }
                }
                SchemeUtils.jumpToGalley(mContext, list, position);
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
