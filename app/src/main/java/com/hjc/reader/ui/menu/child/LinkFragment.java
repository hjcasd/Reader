package com.hjc.reader.ui.menu.child;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.event.MessageEvent;
import com.hjc.reader.base.event.EventManager;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.CollectArticleBean;
import com.hjc.reader.model.response.CollectLinkBean;
import com.hjc.reader.ui.menu.adapter.LinkAdapter;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.widget.dialog.EditLinkDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

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
        getLinkList(true);
    }

    /**
     * 获取收藏的网址列表
     *
     * @param isShow 是否显示loading
     */
    private void getLinkList(boolean isShow) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getLinkList()
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<CollectLinkBean>(getChildFragmentManager(), isShow) {
                    @Override
                    public void onSuccess(CollectLinkBean result) {
                        smartRefreshLayout.finishRefresh();
                        if (result != null) {
                            parseLinkList(result);
                        } else {
                            ToastUtils.showShort("服务器异常,请稍后重试");
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
     * 解析收藏的网址列表数据
     *
     * @param collectLinkBean 网址列表对应的bean
     */
    private void parseLinkList(CollectLinkBean collectLinkBean) {
        List<CollectLinkBean.DataBean> dataList = collectLinkBean.getData();
        if (dataList != null && dataList.size() > 0) {
            mAdapter.setNewData(dataList);
        } else {
            ToastUtils.showShort("暂无收藏,快去收藏吧");
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> getLinkList(false));

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<CollectLinkBean.DataBean> dataList = mAdapter.getData();
            CollectLinkBean.DataBean bean = dataList.get(position);
            SchemeUtils.jumpToWeb(mContext, bean.getLink(), bean.getName());
        });

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
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
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerEvent(MessageEvent<CollectLinkBean.DataBean> messageEvent) {
        if (messageEvent.getCode() == EventCode.D) {
            CollectLinkBean.DataBean srcBean = messageEvent.getData();
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
                .subscribe(new BaseProgressObserver<CollectArticleBean>(getChildFragmentManager(), true) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        if (result != null) {
                            if (result.getErrorCode() == 0) {
                                mAdapter.remove(position);
                                ToastUtils.showShort("删除网址成功");
                            } else {
                                ToastUtils.showShort(result.getErrorMsg());
                            }
                        } else {
                            ToastUtils.showShort("服务器异常,请稍后重试");
                        }
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
