package com.hjc.reader.ui.douban;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hjc.reader.R;
import com.hjc.reader.adapter.MyViewPagerAdapter;
import com.hjc.reader.base.fragment.BaseFragment;
import com.hjc.reader.ui.douban.child.BookFragment;
import com.hjc.reader.ui.douban.child.MovieFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/21 16:10
 * @Description: 豆瓣模块
 */
public class Tab3Fragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private String titles[] = new String[]{"电影", "书籍"};

    public static Tab3Fragment newInstance() {
        Tab3Fragment fragment = new Tab3Fragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab3;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        List<Fragment> fragments = new ArrayList<>();

        MovieFragment movieFragment = MovieFragment.newInstance();
        BookFragment bookFragment = BookFragment.newInstance();

        fragments.add(movieFragment);
        fragments.add(bookFragment);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void addListeners() {
    }

    @Override
    public void onSingleClick(View v) {

    }
}
