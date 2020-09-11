package com.hjc.reader.ui.search;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.activity.BaseMvmFragmentActivity;
import com.hjc.baselib.event.EventManager;
import com.hjc.baselib.event.MessageEvent;
import com.hjc.baselib.widget.text.DeleteEditText;
import com.hjc.reader.R;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ActivitySearchBinding;
import com.hjc.reader.ui.search.child.SearchHistoryFragment;
import com.hjc.reader.ui.search.child.SearchResultFragment;
import com.hjc.reader.viewmodel.search.SearchViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;


/**
 * @Author: HJC
 * @Date: 2019/8/21 14:13
 * @Description: 搜索页面
 */
@Route(path = RoutePath.URL_SEARCH)
public class SearchActivity extends BaseMvmFragmentActivity<ActivitySearchBinding, SearchViewModel> {

    private SearchHistoryFragment mSearchHistoryFragment;
    private SearchResultFragment mSearchResultFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchViewModel getViewModel() {
        return new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        EventManager.register(this);

        mSearchHistoryFragment = SearchHistoryFragment.newInstance();
        mSearchResultFragment = SearchResultFragment.newInstance();

        showFragment(mSearchHistoryFragment);
    }

    @Override
    public void addListeners() {
        mBindingView.ivBack.setOnClickListener(this);
        mBindingView.tvSearch.setOnClickListener(this);

        mBindingView.etSearch.setOnSearchClickListener(new DeleteEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                search();
            }

            @Override
            public void onSearchClear() {
                showFragment(mSearchHistoryFragment);
            }
        });
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.fl_content;
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;

            case R.id.tv_search:
                search();
                break;
        }
    }

    /**
     * 搜索
     */
    private void search() {
        String keyword = Objects.requireNonNull(mBindingView.etSearch.getText()).toString().trim();
        if (StringUtils.isEmpty(keyword)) {
            ToastUtils.showShort("请输入关键字");
            return;
        }
        KeyboardUtils.hideSoftInput(this);
        mViewModel.saveHistoryData(keyword);

        EventManager.sendStickyEvent(new MessageEvent(EventCode.SEARCH_RESULT, keyword));
        showFragment(mSearchResultFragment);
    }

    /**
     * 点击历史搜索标签或者热门搜索标签回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerEvent(MessageEvent<String> messageEvent) {
        if (messageEvent.getCode() == EventCode.CLICK_TAG) {
            String keyword = messageEvent.getData();
            mBindingView.etSearch.setText(keyword);
            mBindingView.etSearch.requestFocus();
            mBindingView.etSearch.setSelection(keyword.length());

            search();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }
}
