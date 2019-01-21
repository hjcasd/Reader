package com.hjc.reader.ui;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseFragmentActivity;
import com.hjc.reader.ui.gank.Tab2Fragment;
import com.hjc.reader.ui.douban.Tab3Fragment;
import com.hjc.reader.ui.wan.Tab1Fragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;

public class MainActivity extends BaseFragmentActivity {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.iv_search)
    ImageView ivSearch;

    @BindView(R.id.iv_tab1)
    ImageView ivTab1;
    @BindView(R.id.iv_tab2)
    ImageView ivTab2;
    @BindView(R.id.iv_tab3)
    ImageView ivTab3;

    private Tab2Fragment mTab2Fragment;
    private Tab1Fragment mTab1Fragment;
    private Tab3Fragment mTab3Fragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarView(R.id.status_view)
                .init();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        requestPermission();

        mTab1Fragment = Tab1Fragment.newInstance();
        mTab2Fragment = Tab2Fragment.newInstance();
        mTab3Fragment = Tab3Fragment.newInstance();

        setCurrentItem(0);
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);

        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        ToastUtils.showShort("申请存储权限成功");
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        ToastUtils.showShort("该应用需要存储权限,否则可能会导致应用异常");
                    } else {
                        ToastUtils.showShort("申请存储权限失败");
                    }
                });
    }

    @Override
    public void addListeners() {
        ivMenu.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        ivTab1.setOnClickListener(this);
        ivTab2.setOnClickListener(this);
        ivTab3.setOnClickListener(this);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                ObjectAnimator.ofFloat(ivMenu, "rotation", 0f, -180f).setDuration(1000).start();
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                ObjectAnimator.ofFloat(ivMenu, "rotation", -180f, 0f).setDuration(1000).start();
            }

            @Override
            public void onDrawerStateChanged(int i) {

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
            case R.id.iv_menu:
                drawerLayout.openDrawer(Gravity.START);
                break;

            case R.id.iv_search:
                break;

            case R.id.iv_tab1:
                setCurrentItem(0);
                break;

            case R.id.iv_tab2:
                setCurrentItem(1);
                break;

            case R.id.iv_tab3:
                setCurrentItem(2);
                break;
        }
    }

    /**
     * 切换页面
     *
     * @param position 位置
     */
    private void setCurrentItem(int position) {
        boolean isOne = false;
        boolean isTwo = false;
        boolean isThree = false;
        switch (position) {
            case 0:
                isOne = true;
                showFragment(mTab1Fragment);
                break;
            case 1:
                isTwo = true;
                showFragment(mTab2Fragment);
                break;
            case 2:
                isThree = true;
                showFragment(mTab3Fragment);
                break;
            default:
                isTwo = true;
                showFragment(mTab2Fragment);
                break;
        }
        ivTab1.setSelected(isOne);
        ivTab2.setSelected(isTwo);
        ivTab3.setSelected(isThree);
    }
}
