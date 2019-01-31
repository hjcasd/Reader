package com.hjc.reader.ui.gank.child;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.GankIOBean;
import com.hjc.reader.ui.gank.adapter.FilterAdapter;
import com.hjc.reader.ui.gank.adapter.GankAdapter;
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
 * @Description: 干货定制页面
 */
public class GankFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_gank)
    RecyclerView rvGank;

    private TextView tvTypeName;
    private LinearLayout llFilter;

    private GankAdapter mAdapter;

    private int page = 1;
    private String type = "all";

    public static GankFragment newInstance() {
        GankFragment fragment = new GankFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    public void initView() {
        View headerView = View.inflate(mContext, R.layout.layout_header_filter, null);
        tvTypeName = headerView.findViewById(R.id.tv_type_name);
        llFilter = headerView.findViewById(R.id.ll_filter);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvGank.setLayoutManager(manager);

        mAdapter = new GankAdapter(null);
        rvGank.setAdapter(mAdapter);

        mAdapter.addHeaderView(headerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取分类数据
     */
    private void getGankData() {
        RetrofitHelper.getInstance().getGankIOService()
                .getGankIoData(type, 20, page)
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
     * 解析分类数据
     *
     * @param gankIOBean 分类对应的bean
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
        llFilter.setOnClickListener(this);

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getGankData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getGankData();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<GankIOBean.ResultsBean> dataList = adapter.getData();
                GankIOBean.ResultsBean bean = dataList.get(position);
                SchemeUtils.jumpToWeb(mContext, bean.getUrl(), bean.getDesc());
            }
        });
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.ll_filter:
                showFilterDialog();
                break;
        }
    }

    /**
     * 显示选择分类dialog
     */
    private void showFilterDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);

        View view = View.inflate(mContext, R.layout.dialog_filter, null);
        bottomSheetDialog.setContentView(view);

        RecyclerView rvFilter = view.findViewById(R.id.rv_filter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvFilter.setLayoutManager(manager);

        List<String> list = new ArrayList<>();
        String[] titles = new String[]{"全部", "Android", "IOS", "App", "前端", "休息视频", "拓展资源"};
        for (int i = 0; i < titles.length; i++) {
            list.add(titles[i]);
        }

        FilterAdapter adapter = new FilterAdapter(list);
        rvFilter.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bottomSheetDialog.dismiss();
                switch (position) {
                    case 0:
                        type = "all";
                        break;

                    case 1:
                        type = "Android";
                        break;

                    case 2:
                        type = "iOS";
                        break;

                    case 3:
                        type = "App";
                        break;

                    case 4:
                        type = "前端";
                        break;

                    case 5:
                        type = "休息视频";
                        break;

                    case 6:
                        type = "拓展资源";
                        break;

                    default:
                        type = "all";
                        break;
                }
                tvTypeName.setText(titles[position]);
                smartRefreshLayout.autoRefresh();
            }
        });
        bottomSheetDialog.show();
    }
}
