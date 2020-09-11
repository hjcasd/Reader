package com.hjc.reader.ui.wan.child;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.databinding.FragmentNavigationBinding;
import com.hjc.reader.ui.wan.adapter.NavigationAdapter;
import com.hjc.reader.ui.wan.adapter.NavigationContentAdapter;
import com.hjc.reader.viewmodel.wan.NavigationViewModel;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 导航数据页面
 */
public class NavigationFragment extends BaseMvmLazyFragment<FragmentNavigationBinding, NavigationViewModel> {

    private NavigationAdapter mNavigationAdapter;
    private NavigationContentAdapter mNavigationContentAdapter;

    private LinearLayoutManager contentManager;

    private int oldPosition = 0;

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected NavigationViewModel getViewModel() {
        return new ViewModelProvider(this).get(NavigationViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.clRoot);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mBindingView.rvNavigation.setLayoutManager(manager);
        mNavigationAdapter = new NavigationAdapter();
        mBindingView.rvNavigation.setAdapter(mNavigationAdapter);
        mNavigationAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);

        contentManager = new LinearLayoutManager(mContext);
        mBindingView.rvNavigationContent.setLayoutManager(contentManager);
        mNavigationContentAdapter = new NavigationContentAdapter();
        mBindingView.rvNavigationContent.setAdapter(mNavigationContentAdapter);
        mNavigationContentAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
    }

    @Override
    public void initData() {
        mViewModel.loadNavigationList(true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getNavigationLiveData().observe(this, data -> {
            mNavigationAdapter.setNewInstance(data);
            selectItem(0);
        });

        mViewModel.getNavigationContentLiveData().observe(this, data -> {
            mNavigationContentAdapter.setNewInstance(data);
        });
    }

    @Override
    public void addListeners() {
        //点击侧边栏菜单,滑动到指定位置
        mNavigationAdapter.setOnSelectListener(position -> {
            selectItem(position);
            contentManager.scrollToPositionWithOffset(position, 0);
        });

        //侧边栏菜单随右边列表一起滚动
        mBindingView.rvNavigationContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = contentManager.findFirstVisibleItemPosition();
                if (oldPosition != firstPosition) {
                    mBindingView.rvNavigation.smoothScrollToPosition(firstPosition);
                    selectItem(firstPosition);
                }
            }
        });
    }

    /**
     * 选中左边标题
     * @param position 位置索引
     */
    private void selectItem(int position) {
        if (position < 0 || mNavigationAdapter.getData().size() <= position) {
            return;
        }
        mNavigationAdapter.getData().get(oldPosition).setSelected(false);
        mNavigationAdapter.notifyItemChanged(oldPosition);
        oldPosition = position;
        mNavigationAdapter.getData().get(position).setSelected(true);
        mNavigationAdapter.notifyItemChanged(position);
    }

    @Override
    public void onSingleClick(View v) {

    }

    @Override
    protected void onRetryBtnClick(View v) {
        mViewModel.loadNavigationList(true);
    }
}
