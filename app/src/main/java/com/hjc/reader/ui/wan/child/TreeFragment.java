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
import com.hjc.reader.model.response.WanTreeBean;
import com.hjc.reader.ui.wan.adapter.TreeListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

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
        TreeFragment fragment = new TreeFragment();
        return fragment;
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
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取知识体系数据
     */
    private void getListData() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getTreeList()
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<WanTreeBean>() {
                    @Override
                    public void onNext(WanTreeBean wanTreeBean) {
                        if (wanTreeBean != null) {
                            if (wanTreeBean.getErrorCode() == 0) {
                                parseListData(wanTreeBean);
                            } else {
                                ToastUtils.showShort(wanTreeBean.getErrorMsg());
                            }
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
     * 解析知识体系数据
     * @param wanTreeBean 知识体系数据对应的bean
     */
    private void parseListData(WanTreeBean wanTreeBean) {
        List<WanTreeBean.DataBean> dataList = wanTreeBean.getData();
        if (dataList != null && dataList.size() > 0) {
            mAdapter.setNewData(dataList);
            smartRefreshLayout.finishRefresh(1000);
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getListData();
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
