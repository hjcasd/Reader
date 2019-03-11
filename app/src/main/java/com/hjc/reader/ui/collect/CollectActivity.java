package com.hjc.reader.ui.collect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.hjc.reader.R;
import com.hjc.reader.adapter.MyViewPagerAdapter;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.ui.collect.child.ArticleFragment;
import com.hjc.reader.ui.collect.child.JokeFragment;
import com.hjc.reader.ui.collect.child.URLFragment;
import com.hjc.reader.ui.wan.child.NavigationFragment;
import com.hjc.reader.ui.wan.child.TreeFragment;
import com.hjc.reader.ui.wan.child.WanFragment;
import com.hjc.reader.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author: HJC
 * @Date: 2019/3/11 10:58
 * @Description: 我的收藏页面
 */
public class CollectActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private String titles[] = new String[]{"文章", "网址", "段子"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).titleBar(toolBar).init();
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<Fragment> fragments = new ArrayList<>();

        ArticleFragment articleFragment = ArticleFragment.newInstance();
        URLFragment urlFragment = URLFragment.newInstance();
        JokeFragment jokeFragment = JokeFragment.newInstance();

        fragments.add(articleFragment);
        fragments.add(urlFragment);
        fragments.add(jokeFragment);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void addListeners() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
