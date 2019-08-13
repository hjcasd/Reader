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
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.WanTreeBean;
import com.hjc.reader.ui.wan.adapter.TreeListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:30
 * @Description: 知识体系页面
 */
public class TreeFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_tree)
    RecyclerView rvTree;

    private TreeListAdapter mAdapter;

    public static TreeFragment newInstance() {
        return new TreeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tree;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvTree.setLayoutManager(manager);

        mAdapter = new TreeListAdapter(null);
        rvTree.setAdapter(mAdapter);

        //添加列表动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    public void initData() {
        getListData(true);
    }

    /**
     * 获取知识体系数据
     *
     * @param isShow 是否显示loading
     */
    private void getListData(boolean isShow) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getTreeList()
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<WanTreeBean>(getChildFragmentManager(), isShow) {
                    @Override
                    public void onSuccess(WanTreeBean result) {
                        smartRefreshLayout.finishRefresh();
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
                    }
                });
    }

    /**
     * 解析知识体系数据
     *
     * @param result 知识体系数据对应的bean
     */
    private void parseListData(WanTreeBean result) {
        List<WanTreeBean.DataBean> dataList = result.getData();
        if (dataList != null && dataList.size() > 0) {
            mAdapter.setNewData(dataList);
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getListData(false);
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
