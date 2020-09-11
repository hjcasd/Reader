package com.hjc.reader.ui.gank;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hjc.baselib.fragment.BaseMvmFragment;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.R;
import com.hjc.reader.adapter.MyViewPagerAdapter;
import com.hjc.reader.databinding.FragmentTab2Binding;
import com.hjc.reader.ui.gank.child.GankFragment;
import com.hjc.reader.ui.gank.child.RecommendFragment;
import com.hjc.reader.ui.gank.child.WelfareFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HJC
 * @Date: 2019/1/21 16:10
 * @Description: 干货模块
 */
public class Tab2Fragment extends BaseMvmFragment<FragmentTab2Binding, CommonViewModel> {
    private String[] titles = new String[]{"每日推荐", "福利社区", "干货定制"};

    public static Tab2Fragment newInstance() {
        return new Tab2Fragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab2;
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

        RecommendFragment recommendFragment = RecommendFragment.newInstance();
        WelfareFragment welfareFragment = WelfareFragment.newInstance();
        GankFragment gankFragment = GankFragment.newInstance();

        fragments.add(recommendFragment);
        fragments.add(welfareFragment);
        fragments.add(gankFragment);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        mBindingView.viewPager.setAdapter(adapter);
        mBindingView.viewPager.setOffscreenPageLimit(3);
        mBindingView.tabLayout.setupWithViewPager(mBindingView.viewPager);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onSingleClick(View v) {

    }
}
