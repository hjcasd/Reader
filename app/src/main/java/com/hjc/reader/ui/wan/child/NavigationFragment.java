package com.hjc.reader.ui.wan.child;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.WanNavigationBean;
import com.hjc.reader.ui.wan.adapter.NavigationAdapter;
import com.hjc.reader.ui.wan.adapter.NavigationContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 导航数据页面
 */
public class NavigationFragment extends BaseLazyFragment {

    @BindView(R.id.rv_navigation)
    RecyclerView rvNavigation;
    @BindView(R.id.rv_navigation_content)
    RecyclerView rvNavigationContent;

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
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvNavigation.setLayoutManager(manager);
        mNavigationAdapter = new NavigationAdapter(null);
        rvNavigation.setAdapter(mNavigationAdapter);

        contentManager = new LinearLayoutManager(mContext);
        rvNavigationContent.setLayoutManager(contentManager);
        mNavigationContentAdapter = new NavigationContentAdapter(null);
        rvNavigationContent.setAdapter(mNavigationContentAdapter);
    }

    @Override
    public void initData() {
        getListData();
    }

    /**
     * 获取导航数据
     */
    private void getListData() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getNavigationList()
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<WanNavigationBean>(getChildFragmentManager()) {
                    @Override
                    public void onSuccess(WanNavigationBean result) {
                        if (result != null) {
                            if (result.getErrorCode() == 0) {
                                parseListData(result);
                            } else {
                                ToastUtils.showShort(result.getErrorMsg());
                            }
                        }
                    }
                });
    }

    /**
     * 解析导航数据
     *
     * @param wanNavigationBean 导航数据对应的bean
     */
    private void parseListData(WanNavigationBean wanNavigationBean) {
        List<WanNavigationBean.DataBean> dataList = wanNavigationBean.getData();
        List<String> chapterList = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            for (WanNavigationBean.DataBean bean : dataList) {
                chapterList.add(bean.getName());
            }
            mNavigationAdapter.setNewData(chapterList);
            mNavigationAdapter.setSelection(0);
            mNavigationContentAdapter.setNewData(dataList);
        }
    }

    @Override
    public void addListeners() {
        //点击侧边栏菜单,滑动到指定位置
        mNavigationAdapter.setOnSelectListener(position -> contentManager.scrollToPositionWithOffset(position, 0));

        //侧边栏菜单随右边列表一起滚动
        rvNavigationContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = contentManager.findFirstVisibleItemPosition();
                if (oldPosition != firstPosition) {
                    rvNavigation.smoothScrollToPosition(firstPosition);
                    mNavigationAdapter.setSelection(firstPosition);
                    oldPosition = firstPosition;
                }
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
