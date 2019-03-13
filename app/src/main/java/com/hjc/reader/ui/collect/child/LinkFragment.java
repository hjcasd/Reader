package com.hjc.reader.ui.collect.child;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.event.Event;
import com.hjc.reader.base.event.EventManager;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.ImageViewInfo;
import com.hjc.reader.model.response.CollectArticleBean;
import com.hjc.reader.model.response.CollectLinkBean;
import com.hjc.reader.ui.collect.adapter.LinkAdapter;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.widget.dialog.EditLinkDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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

    private int mCurrentPosition;

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

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
    }

    @Override
    public void initData() {
        EventManager.register(this);
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取收藏的网址列表
     */
    private void getLinkList() {
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
     *
     * @param collectLinkBean 网址列表对应的bean
     */
    private void parseLinkList(CollectLinkBean collectLinkBean) {
        List<CollectLinkBean.DataBean> dataList = collectLinkBean.getData();
        if (dataList != null && dataList.size() > 0) {
            mAdapter.setNewData(dataList);
            smartRefreshLayout.finishRefresh(1000);
        } else {
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
                List<CollectLinkBean.DataBean> dataList = mAdapter.getData();
                CollectLinkBean.DataBean bean = dataList.get(position);
                SchemeUtils.jumpToWeb(mContext, bean.getLink(), bean.getName());
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                mCurrentPosition = position;

                List<CollectLinkBean.DataBean> dataList = mAdapter.getData();
                CollectLinkBean.DataBean bean = dataList.get(position);

                String[] items = {"编辑", "删除"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setItems(items, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            EditLinkDialog.newInstance(bean).showDialog(getChildFragmentManager());
                            break;

                        case 1:
                            deleteLink(bean.getId(), position);
                            break;

                        default:
                            break;
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerEvent(Event<CollectLinkBean.DataBean> event) {
        if (event.getCode() == EventCode.D) {
            CollectLinkBean.DataBean srcBean = event.getData();
            List<CollectLinkBean.DataBean> dataList = mAdapter.getData();
            CollectLinkBean.DataBean desBean = dataList.get(mCurrentPosition);

            desBean.setName(srcBean.getName());
            desBean.setLink(srcBean.getLink());
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 删除收藏的网址
     *
     * @param id 网址id
     */
    private void deleteLink(int id, int position) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .deleteLink(id)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<CollectArticleBean>() {
                    @Override
                    public void onNext(CollectArticleBean bean) {
                        if (bean != null) {
                            if (bean.getErrorCode() == 0) {
                                mAdapter.remove(position);
                                ToastUtils.showShort("删除网址成功");
                            } else {
                                ToastUtils.showShort(bean.getErrorMsg());
                            }
                        } else {
                            ToastUtils.showShort("服务器异常,请稍后重试");
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

    @Override
    public void onSingleClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }
}
