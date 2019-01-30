package com.hjc.reader.ui;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.hjc.reader.R;
import com.hjc.reader.adapter.GalleyAdapter;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.model.ImageViewInfo;
import com.hjc.reader.utils.ViewUtils;
import com.hjc.reader.widget.FixedViewPager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/23 14:32
 * @Description: 查看图片页面(可单张 / 多张)
 */
public class GalleyActivity extends BaseActivity implements ViewPager.OnPageChangeListener, GalleyAdapter.PhotoCallback {

    @BindView(R.id.view_pager)
    FixedViewPager viewPager;
    @BindView(R.id.tv_page_count)
    TextView tvPageCount;
    @BindView(R.id.tv_save_image)
    TextView tvSaveImage;
    @BindView(R.id.cl_root)
    ConstraintLayout clRoot;

    //第几张图片
    private int currentPosition;

    //1.查看多张图片,可滑动 2.查看单张图片
    private int type;
    private ArrayList<ImageViewInfo> viewList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_galley;
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .fullScreen(true)
                .hideBar(BarHide.FLAG_HIDE_BAR)
                .init();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt("type", 1);
            currentPosition = bundle.getInt("position", 0);
            viewList = bundle.getParcelableArrayList("viewList");
        }
        if (type == 1) {
            tvPageCount.setVisibility(View.VISIBLE);
            tvPageCount.setText((currentPosition + 1) + " / " + viewList.size());
        } else {
            tvPageCount.setVisibility(View.GONE);
        }

        GalleyAdapter adapter = new GalleyAdapter(this, viewList, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition);

        startEnterAnim();
    }

    @Override
    public void addListeners() {
        tvSaveImage.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save_image:
                ToastUtils.showShort("保存");
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        tvPageCount.setText((currentPosition + 1) + " / " + viewList.size());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPhotoClick(View view) {
        startExitAnim();
    }

    private void startEnterAnim() {
        ViewUtils.startEnterViewScaleAnim(this, viewPager, viewList.get(currentPosition), null);
        ViewUtils.startEnterViewAlphaAnim(this, clRoot, viewList.get(currentPosition));
    }

    private void startExitAnim() {
        clRoot.setBackgroundColor(Color.parseColor(ViewUtils.getBlackAlphaBg(0)));
        ViewUtils.startExitViewScaleAnim(this, viewPager, viewList.get(currentPosition), new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
