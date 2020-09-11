package com.hjc.reader.ui.gank.child;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.GankDayBean;
import com.hjc.reader.databinding.FragmentRecommendBinding;
import com.hjc.reader.ui.gank.adapter.RecommendAdapter;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.gank.RecommendViewModel;
import com.vansz.glideimageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 每日推荐页面
 */
public class RecommendFragment extends BaseMvmLazyFragment<FragmentRecommendBinding, RecommendViewModel> {

    private RecommendAdapter mAdapter;

    private Transferee transferee;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected RecommendViewModel getViewModel() {
        return new ViewModelProvider(this).get(RecommendViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mBindingView.rvRecommend.setLayoutManager(manager);

        mAdapter = new RecommendAdapter();
        mBindingView.rvRecommend.setAdapter(mAdapter);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
    }

    @Override
    public void initData() {
        transferee = Transferee.getDefault(mContext);

        mViewModel.getRecommendData(true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getRecommendLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();
            mAdapter.setNewInstance(data);
        });
    }

    @Override
    public void addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mViewModel.getRecommendData(false);
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<GankDayBean> dataList = mAdapter.getData();
            GankDayBean bean = dataList.get(position);

            int itemViewType = adapter.getItemViewType(position);
            if (itemViewType == GankDayBean.TYPE_IMAGE_ONLY) {
                List<String> imageList = new ArrayList<>();
                imageList.add(bean.getUrl());

                transferee.apply(TransferConfig.build()
                        .setImageLoader(GlideImageLoader.with(getContext()))
                        .setSourceUrlList(imageList)
                        .create()
                ).show();

            } else if (itemViewType == GankDayBean.TYPE_TEXT || itemViewType == GankDayBean.TYPE_IMAGE_ONE || itemViewType == GankDayBean.TYPE_IMAGE_THREE) {
                RouteManager.jumpToWeb(bean.getDesc(), bean.getUrl());
            }
        });
    }

    @Override
    protected void onRetryBtnClick(View v) {
        mViewModel.getRecommendData(true);
    }

    @Override
    public void onSingleClick(View v) {

    }
}
