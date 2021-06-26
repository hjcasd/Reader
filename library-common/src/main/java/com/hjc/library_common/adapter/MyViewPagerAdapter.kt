package com.hjc.library_common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @Author: HJC
 * @Date: 2021/2/22 15:10
 * @Description: 玩安卓ViewPager Adapter
 */
class MyViewPagerAdapter(
    fm: FragmentManager,
    private val mFragments: List<Fragment>,
    private val mTitles: Array<String>
) : FragmentPagerAdapter(
    fm
) {
    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}