package com.hjc.reader.ui.gank.child;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.GankDayBean;
import com.hjc.reader.databinding.FragmentGankBinding;
import com.hjc.reader.ui.gank.adapter.FilterAdapter;
import com.hjc.reader.ui.gank.adapter.GankAdapter;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.gank.GankViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 干货定制页面
 */
public class GankFragment extends BaseMvmLazyFragment<FragmentGankBinding, GankViewModel> {

    private TextView tvTypeName;
    private LinearLayout llFilter;

    private GankAdapter mAdapter;

    private int page = 1;
    private String type = "All";

    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected GankViewModel getViewModel() {
        return new ViewModelProvider(this).get(GankViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        View headerView = View.inflate(mContext, R.layout.layout_header_filter, null);
        tvTypeName = headerView.findViewById(R.id.tv_type_name);
        llFilter = headerView.findViewById(R.id.ll_filter);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mBindingView.rvGank.setLayoutManager(manager);

        mAdapter = new GankAdapter();
        mBindingView.rvGank.setAdapter(mAdapter);

        mAdapter.addHeaderView(headerView);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInRight);
    }

    @Override
    public void initData() {
        mViewModel.loadGankList(type, page, true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getGankLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();
            mBindingView.smartRefreshLayout.finishLoadMore();

            if (data != null){
                if (page == 1) {
                    mAdapter.setNewInstance(data);
                } else {
                    mAdapter.addData(data);
                }
            }
        });
    }

    @Override
    public void addListeners() {
        llFilter.setOnClickListener(this);

        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mViewModel.loadGankList(type, page, false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mViewModel.loadGankList(type, page, false);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<GankDayBean> dataList = mAdapter.getData();
            GankDayBean bean = dataList.get(position);
            RouteManager.jumpToWeb(bean.getDesc(), bean.getUrl());
        });
    }

    @Override
    protected void onRetryBtnClick(View v) {
        page = 1;
        mViewModel.loadGankList(type, page, true);
    }

    @Override
    public void onSingleClick(View v) {
        if (v.getId() == R.id.ll_filter) {
            showFilterDialog();
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

        String[] titles = new String[]{"全部", "Android", "IOS", "App", "前端", "后端", "推荐"};
        List<String> list = new ArrayList<>(Arrays.asList(titles));

        FilterAdapter adapter = new FilterAdapter(list);
        rvFilter.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view1, position) -> {
            bottomSheetDialog.dismiss();
            switch (position) {
                case 0:
                    type = "All";
                    break;

                case 1:
                    type = "Android";
                    break;

                case 2:
                    type = "iOS";
                    break;

                case 3:
                    type = "app";
                    break;

                case 4:
                    type = "frontend";
                    break;

                case 5:
                    type = "backend";
                    break;

                case 6:
                    type = "promote";
                    break;
            }
            tvTypeName.setText(titles[position]);
            mBindingView.smartRefreshLayout.autoRefresh();
        });

        bottomSheetDialog.show();
    }
}
