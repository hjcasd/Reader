package com.hjc.reader.ui.collect.child;

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
import com.hjc.reader.model.response.CollectArticleBean;
import com.hjc.reader.model.response.CollectLinkBean;
import com.hjc.reader.ui.collect.adapter.LinkAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/3/11 11:00
 * @Description: 网址页面
 */
public class LinkFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_url)
    RecyclerView rvUrl;

    private LinkAdapter mAdapter;

    public static LinkFragment newInstance() {
        LinkFragment fragment = new LinkFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_link;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvUrl.setLayoutManager(manager);

        mAdapter = new LinkAdapter(null);
        rvUrl.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取收藏的网址列表
     */
    private void getLinkList(){
        RetrofitHelper.getInstance().getWanAndroidService()
                .getLinkList()
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<CollectLinkBean>() {
                    @Override
                    public void onNext(CollectLinkBean collectLinkBean) {
                        if (collectLinkBean != null) {
                            parseLinkList(collectLinkBean);
                        } else {
                            ToastUtils.showShort("服务器异常,请稍后重试");
                            smartRefreshLayout.finishRefresh(1000);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("服务器异常,请稍后重试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 解析收藏的网址列表数据
     * @param collectLinkBean 网址列表对应的bean
     */
    private void parseLinkList(CollectLinkBean collectLinkBean){
        List<CollectLinkBean.DataBean> dataList = collectLinkBean.getData();
        if (dataList != null && dataList.size() > 0){
            mAdapter.setNewData(dataList);
            smartRefreshLayout.finishRefresh(1000);
        }else{
            smartRefreshLayout.finishRefresh(1000);
            ToastUtils.showShort("暂无收藏,快去收藏吧");
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getLinkList();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
