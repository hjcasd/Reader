package com.hjc.reader.ui.menu;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.hjc.baselib.activity.BaseMvmActivity;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.R;
import com.hjc.reader.adapter.MyViewPagerAdapter;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ActivityCollectBinding;
import com.hjc.reader.ui.menu.child.CollectArticleFragment;
import com.hjc.reader.ui.menu.child.CollectLinkFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: HJC
 * @Date: 2019/3/11 10:58
 * @Description: 我的收藏页面
 */
@Route(path = RoutePath.URL_COLLECT)
public class CollectActivity extends BaseMvmActivity<ActivityCollectBinding, CommonViewModel> {

    private String[] titles = new String[]{"文章", "网址"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected CommonViewModel getViewModel() {
        return null;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(mBindingView.toolBar)
                .init();
    }

    @Override
    public void initView() {
        setSupportActionBar(mBindingView.toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<Fragment> fragments = new ArrayList<>();

        CollectArticleFragment articleFragment = CollectArticleFragment.newInstance();
        CollectLinkFragment urlFragment = CollectLinkFragment.newInstance();

        fragments.add(articleFragment);
        fragments.add(urlFragment);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mBindingView.viewPager.setAdapter(adapter);
        mBindingView.viewPager.setOffscreenPageLimit(3);
        mBindingView.tabLayout.setupWithViewPager(mBindingView.viewPager);
    }

    @Override
    public void addListeners() {
        mBindingView.toolBar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void onSingleClick(View v) {

    }
}
