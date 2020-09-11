package com.hjc.reader.ui.film;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hjc.baselib.fragment.BaseMvmFragment;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.R;
import com.hjc.reader.adapter.MyViewPagerAdapter;
import com.hjc.reader.databinding.FragmentTab3Binding;
import com.hjc.reader.ui.film.child.ComingMovieFragment;
import com.hjc.reader.ui.film.child.HotMovieFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: HJC
 * @Date: 2019/1/21 16:10
 * @Description: 豆瓣模块
 */
public class Tab3Fragment extends BaseMvmFragment<FragmentTab3Binding, CommonViewModel> {

    private String[] titles = new String[]{"热映榜", "即将上映"};

    public static Tab3Fragment newInstance() {
        return new Tab3Fragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab3;
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
    public void initData(@Nullable Bundle savedInstanceState) {
        List<Fragment> fragments = new ArrayList<>();

        HotMovieFragment hotMovieFragment = HotMovieFragment.newInstance();
        ComingMovieFragment upcomingMovieFragment = ComingMovieFragment.newInstance();

        fragments.add(hotMovieFragment);
        fragments.add(upcomingMovieFragment);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        mBindingView.viewPager.setAdapter(adapter);
        mBindingView.viewPager.setOffscreenPageLimit(2);
        mBindingView.tabLayout.setupWithViewPager(mBindingView.viewPager);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onSingleClick(View v) {

    }
}
