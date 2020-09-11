package com.hjc.reader.ui.menu.child;

import android.app.AlertDialog;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.baselib.event.EventManager;
import com.hjc.baselib.event.MessageEvent;
import com.hjc.baselib.fragment.BaseMvmLazyFragment;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.CollectLinkBean;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.databinding.FragmentCollectLinkBinding;
import com.hjc.reader.ui.menu.adapter.LinkAdapter;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.menu.CollectLinkViewModel;
import com.hjc.reader.widget.EditLinkDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * @Author: HJC
 * @Date: 2019/3/11 11:00
 * @Description: 我的收藏下的网址页面
 */
public class CollectLinkFragment extends BaseMvmLazyFragment<FragmentCollectLinkBinding, CollectLinkViewModel> {

    private LinkAdapter mAdapter;

    private int mCurrentPosition;

    public static CollectLinkFragment newInstance() {
        return new CollectLinkFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect_link;
    }

    @Override
    protected CollectLinkViewModel getViewModel() {
        return new ViewModelProvider(this).get(CollectLinkViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initView() {
        setLoadSir(mBindingView.smartRefreshLayout);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mBindingView.rvLink.setLayoutManager(manager);

        mAdapter = new LinkAdapter();
        mBindingView.rvLink.setAdapter(mAdapter);

        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInRight);
    }

    @Override
    public void initData() {
        EventManager.register(this);

        mViewModel.loadCollectLinkList(true);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getCollectLinkLiveData().observe(this, data -> {
            mBindingView.smartRefreshLayout.finishRefresh();

            if (data != null){
                mAdapter.setNewInstance(data);
            }
        });

        mViewModel.getPositionLiveData().observe(this, position -> {
            mAdapter.removeAt(position);
        });
    }

    @Override
    public void addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mViewModel.loadCollectLinkList(false);
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<CollectLinkBean.DataBean> dataList = mAdapter.getData();
            CollectLinkBean.DataBean bean = dataList.get(position);
            RouteManager.jumpToWeb(bean.getName(), bean.getLink());
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
                        mViewModel.deleteLink(bean.getId(), position);
                        break;

                    default:
                        break;
                }
            });
            builder.show();
            return true;
        });
    }

    @Override
    protected void onRetryBtnClick(View v) {
        mViewModel.loadCollectLinkList(true);
    }

    @Override
    public void onSingleClick(View v) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerEvent(MessageEvent<CollectLinkBean.DataBean> messageEvent) {
        if (messageEvent.getCode() == EventCode.EDIT_LINK_CODE) {
            CollectLinkBean.DataBean srcBean = messageEvent.getData();
            List<CollectLinkBean.DataBean> dataList = mAdapter.getData();
            CollectLinkBean.DataBean desBean = dataList.get(mCurrentPosition);

            desBean.setName(srcBean.getName());
            desBean.setLink(srcBean.getLink());
            mAdapter.notifyItemChanged(mCurrentPosition);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }
}
