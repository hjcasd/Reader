package com.hjc.reader.ui.gank.child;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.GankDayBean;
import com.hjc.reader.databinding.FragmentWelfareBinding;
import com.hjc.reader.ui.gank.adapter.WelfareAdapter;
import com.hjc.reader.viewmodel.gank.WelfareViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.vansz.glideimageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 福利社区页面
 */
public class WelfareFragment extends BaseMvmLazyFragment<FragmentWelfareBinding, WelfareViewModel> {

    private WelfareAdapter mAdapter;

    private Transferee transferee;

    private int mPage = 1;

    public static WelfareFragment newInstance() {
        return new WelfareFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected WelfareViewModel getViewModel() {
        return new ViewModelProvider(this).get(WelfareViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        mBindingView.rvWelfare.setLayoutManager(manager);

        mAdapter = new WelfareAdapter();
        mBindingView.rvWelfare.setAdapter(mAdapter);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
    }

    @Override
    public void initData() {
        transferee = Transferee.getDefault(mContext);

        mViewModel.loadWelfareList(mPage, true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getWelfareLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();
            mBindingView.smartRefreshLayout.finishLoadMore();

            if (data != null) {
                if (mPage == 1) {
                    mAdapter.setNewInstance(data);
                } else {
                    mAdapter.addData(data);
                }
            }
        });
    }

    @Override
    public void addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mViewModel.loadWelfareList(mPage, false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                mViewModel.loadWelfareList(mPage, false);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<GankDayBean> dataList = mAdapter.getData();
            List<String> imagesList = new ArrayList<>();
            for (GankDayBean bean : dataList) {
                imagesList.add(bean.getUrl());
            }

            TransferConfig config = TransferConfig.build()
                    .setSourceUrlList(imagesList) // 资源 url 集合, String 格式
                    .setMissPlaceHolder(R.mipmap.img_default_meizi) // 资源加载前的占位图
                    .setErrorPlaceHolder(R.mipmap.img_default_meizi) // 资源加载错误后的占位图
                    .setProgressIndicator(new ProgressPieIndicator()) // 资源加载进度指示器, 可以实现 IProgressIndicator 扩展
                    .setIndexIndicator(new NumberIndexIndicator()) // 资源数量索引指示器，可以实现 IIndexIndicator 扩展
                    .setImageLoader(GlideImageLoader.with(mContext)) // 图片加载器，可以实现 ImageLoader 扩展
                    .setBackgroundColor(Color.parseColor("#000000")) // 背景色
                    .setDuration(300) // 开启、关闭、手势拖拽关闭、显示、扩散消失等动画时长
                    .setOffscreenPageLimit(2) // 第一次初始化或者切换页面时预加载资源的数量，与 justLoadHitImage 属性冲突，默认为 1
                    .setNowThumbnailIndex(position) // 缩略图在图组中的索引
                    .enableJustLoadHitPage(true) // 是否只加载当前显示在屏幕中的的资源，默认关闭
                    .enableDragClose(true) // 是否开启下拉手势关闭，默认开启
                    .enableDragHide(false) // 下拉拖拽关闭时，是否先隐藏页面上除主视图以外的其他视图，默认开启
                    .enableDragPause(false) // 下拉拖拽关闭时，如果当前是视频，是否暂停播放，默认关闭
                    .enableHideThumb(false) // 是否开启当 transferee 打开时，隐藏缩略图, 默认关闭
                    .enableScrollingWithPageChange(false) // 是否启动列表随着页面的切换而滚动你的列表，默认关闭
                    .bindRecyclerView(mBindingView.rvWelfare, R.id.iv_pic);  // 绑定一个 RecyclerView， 所有绑定方法只能调用一个

            transferee.apply(config).show();
        });
    }

    @Override
    protected void onRetryBtnClick(View v) {
        mPage = 1;
        mViewModel.loadWelfareList(mPage, true);
    }

    @Override
    public void onSingleClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        transferee.destroy();
    }
}
