package com.hjc.reader.ui.search;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.HotKeyBean;
import com.hjc.reader.model.response.WanListBean;
import com.hjc.reader.ui.search.adapter.SearchAdapter;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.widget.SearchEditText;
import com.nex3z.flowlayout.FlowLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/8/21 14:13
 * @Description: 搜索页面
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    SearchEditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_clear_history)
    ImageView ivClearHistory;
    @BindView(R.id.fl_history)
    FlowLayout flHistory;
    @BindView(R.id.cl_history)
    ConstraintLayout clHistory;
    @BindView(R.id.fl_hot)
    FlowLayout flHot;
    @BindView(R.id.cl_hot)
    ConstraintLayout clHot;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private SearchAdapter mAdapter;

    /**
     * 页码
     */
    private int mPage = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvList.setLayoutManager(manager);

        mAdapter = new SearchAdapter(null);
        rvList.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getHotKeyData();
        getHistoryData();
    }

    /**
     * 获取热门搜索数据
     */
    private void getHotKeyData() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getHotKey()
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<HotKeyBean>(getSupportFragmentManager()) {
                    @Override
                    public void onSuccess(HotKeyBean result) {
                        if (result != null) {
                            if (result.getErrorCode() == 0) {
                                parseHotKeyData(result);
                            } else {
                                ToastUtils.showShort(result.getErrorMsg());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        super.onFailure(errorMsg);
                        smartRefreshLayout.finishLoadMore();
                    }
                });
    }

    /**
     * 解析热门搜索数据
     *
     * @param result 热门搜索对应的bean
     */
    private void parseHotKeyData(HotKeyBean result) {
        List<HotKeyBean.DataBean> list = result.getData();
        if (list != null && list.size() > 0) {
            clHot.setVisibility(View.VISIBLE);
            initHotTags(list);
        } else {
            clHot.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化热门搜索tag
     *
     * @param list 标签集合
     */
    private void initHotTags(List<HotKeyBean.DataBean> list) {
        flHot.removeAllViews();
        for (HotKeyBean.DataBean bean : list) {
            View view = View.inflate(SearchActivity.this, R.layout.view_navigation_tag, null);
            TextView tvTag = view.findViewById(R.id.tv_tag);
            tvTag.setText(bean.getName());
            flHot.addView(tvTag);

            tvTag.setOnClickListener(v -> {
                etSearch.setText(tvTag.getText().toString());
                etSearch.requestFocus();
                etSearch.setSelection(etSearch.getText().length());
                search(true);
            });
        }
    }

    /**
     * 获取历史搜索数据
     */
    private void getHistoryData() {
        Set<String> historySet = SPUtils.getInstance().getStringSet("history");
        if (historySet != null && historySet.size() > 0) {
            clHistory.setVisibility(View.VISIBLE);
            initHistoryTags(historySet);
        } else {
            clHistory.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化历史搜索tag
     *
     * @param set 标签集合
     */
    private void initHistoryTags(Set<String> set) {
        flHistory.removeAllViews();
        for (String text : set) {
            View view = View.inflate(SearchActivity.this, R.layout.view_navigation_tag, null);
            TextView tvTag = view.findViewById(R.id.tv_tag);
            tvTag.setText(text);
            flHistory.addView(tvTag);

            tvTag.setOnClickListener(v -> {
                etSearch.setText(tvTag.getText().toString());
                etSearch.requestFocus();
                etSearch.setSelection(etSearch.getText().length());
                search(true);
            });
        }
    }

    @Override
    public void addListeners() {
        ivBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        ivClearHistory.setOnClickListener(this);

        etSearch.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                mPage = 0;
                search(true);
            }

            @Override
            public void onSearchClear() {
                clHistory.setVisibility(View.VISIBLE);
                getHistoryData();
                clHot.setVisibility(View.VISIBLE);
                smartRefreshLayout.setVisibility(View.GONE);
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            search(false);
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<WanListBean.DataBean.DatasBean> dataList = adapter.getData();
            WanListBean.DataBean.DatasBean bean = dataList.get(position);
            String title = bean.getTitle();
            String link = bean.getLink();
            SchemeUtils.jumpToWeb(SearchActivity.this, link, title);
        });
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;

            case R.id.tv_search:
                mPage = 0;
                search(true);
                break;

            case R.id.iv_clear_history:
                showClearHistoryDialog();
                break;

            default:
                break;
        }
    }

    /**
     * 搜索
     *
     * @param isShow 是否显示loading
     */
    private void search(boolean isShow) {
        String keyword = etSearch.getText().toString().trim();
        if (StringUtils.isEmpty(keyword)) {
            ToastUtils.showShort("请输入搜索内容");
            return;
        }

        Set<String> historySet = SPUtils.getInstance().getStringSet("history");
        if (historySet == null || historySet.size() == 0) {
            historySet = new HashSet<>();
            historySet.add(keyword);
            SPUtils.getInstance().put("history", historySet);
        } else {
            historySet.add(keyword);
            SPUtils.getInstance().put("history", historySet);
        }
        RetrofitHelper.getInstance().getWanAndroidService()
                .search(mPage, keyword)
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<WanListBean>(getSupportFragmentManager(), isShow) {
                    @Override
                    public void onSuccess(WanListBean result) {
                        smartRefreshLayout.finishLoadMore();
                        if (result != null) {
                            if (result.getErrorCode() == 0) {
                                parseSearchData(result);
                            } else {
                                ToastUtils.showShort(result.getErrorMsg());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        super.onFailure(errorMsg);
                        smartRefreshLayout.finishLoadMore();
                    }
                });
    }

    /**
     * 解析搜索结果
     *
     * @param result 搜索对应的bean
     */
    private void parseSearchData(WanListBean result) {
        clHistory.setVisibility(View.GONE);
        clHot.setVisibility(View.GONE);
        smartRefreshLayout.setVisibility(View.VISIBLE);

        WanListBean.DataBean dataBean = result.getData();
        List<WanListBean.DataBean.DatasBean> dataList = dataBean.getDatas();
        if (dataList != null && dataList.size() > 0) {
            if (mPage == 0) {
                mAdapter.setNewData(dataList);
            } else {
                mAdapter.addData(dataList);
            }
        } else {
            if (mPage == 0) {
                ToastUtils.showShort("暂无搜索数据");
            } else {
                ToastUtils.showShort("没有更多数据了");
            }
        }
    }

    private void showClearHistoryDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认清除全部历史记录吗？");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", (dialog, which) -> {
            SPUtils.getInstance().remove("history");
            clHistory.setVisibility(View.GONE);
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
