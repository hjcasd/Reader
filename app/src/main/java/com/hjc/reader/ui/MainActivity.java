package com.hjc.reader.ui;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjc.baselib.activity.BaseMvmFragmentActivity;
import com.hjc.baselib.event.EventManager;
import com.hjc.baselib.event.MessageEvent;
import com.hjc.baselib.utils.helper.ActivityManager;
import com.hjc.baselib.utils.permission.PermissionCallBack;
import com.hjc.baselib.utils.permission.PermissionManager;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.LoginBean;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ActivityMainBinding;
import com.hjc.reader.ui.film.Tab3Fragment;
import com.hjc.reader.ui.gank.Tab2Fragment;
import com.hjc.reader.ui.wan.Tab1Fragment;
import com.hjc.reader.utils.helper.AccountManager;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.MainViewModel;
import com.yanzhenjie.permission.runtime.Permission;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @Author: HJC
 * @Date: 2020/8/24 15:58
 * @Description: 主界面
 */
@Route(path = RoutePath.URL_MAIN)
public class MainActivity extends BaseMvmFragmentActivity<ActivityMainBinding, MainViewModel> {

    private Tab1Fragment mTab1Fragment;
    private Tab2Fragment mTab2Fragment;
    private Tab3Fragment mTab3Fragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarView(R.id.status_view)
                .init();
    }

    @Override
    protected MainViewModel getViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mBindingView.setMainViewModel(mViewModel);

        EventManager.register(this);
        requestPermission();

        mTab1Fragment = Tab1Fragment.newInstance();
        mTab2Fragment = Tab2Fragment.newInstance();
        mTab3Fragment = Tab3Fragment.newInstance();

        setCurrentItem(0);

        mViewModel.setUserName();
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        PermissionManager manager = new PermissionManager(this);
        manager.requestPermissionInActivity(new PermissionCallBack() {
            @Override
            public void onGranted() {
                ToastUtils.showShort("申请存储权限成功");
            }

            @Override
            public void onDenied() {
                ToastUtils.showShort("申请存储权限失败");
            }
        }, Permission.Group.STORAGE);
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
            case 1:
                isTwo = true;
                showFragment(mTab2Fragment);
                break;
            case 2:
                isThree = true;
                showFragment(mTab3Fragment);
                break;
            default:
                isOne = true;
                showFragment(mTab1Fragment);
                break;
        }
        mBindingView.ivTab1.setSelected(isOne);
        mBindingView.ivTab2.setSelected(isTwo);
        mBindingView.ivTab3.setSelected(isThree);
    }


    @Override
    protected void addListeners() {
        mBindingView.setOnClickListener(this);
        mBindingView.drawerLeft.setOnClickListener(this);

        mBindingView.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                ObjectAnimator.ofFloat(mBindingView.ivMenu, "rotation", 0f, -180f).setDuration(1000).start();
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                ObjectAnimator.ofFloat(mBindingView.ivMenu, "rotation", -180f, 0f).setDuration(1000).start();
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
    protected void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                mBindingView.drawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.iv_search:
                RouteManager.jump(RoutePath.URL_SEARCH);
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

            case R.id.fl_account:
                mBindingView.drawerLayout.closeDrawer(GravityCompat.START);
                boolean isLogin = AccountManager.getInstance().isLogin();
                if (isLogin) {
                    showExitDialog();
                } else {
                    RouteManager.jump(RoutePath.URL_LOGIN);
                }
                break;

            case R.id.ll_home_page:
                mBindingView.drawerLayout.closeDrawer(GravityCompat.START);
                RouteManager.jumpToWeb("项目主页", "https://github.com/hjcasd/Reader");
                break;

            case R.id.ll_scan:
                mBindingView.drawerLayout.closeDrawer(GravityCompat.START);
                RouteManager.jump(RoutePath.URL_SCAN_CODE);
                break;

            case R.id.ll_collect:
                mBindingView.drawerLayout.closeDrawer(GravityCompat.START);
                if (AccountManager.getInstance().isLogin()) {
                    RouteManager.jump(RoutePath.URL_COLLECT);
                } else {
                    RouteManager.jump(RoutePath.URL_LOGIN);
                }
                break;

            case R.id.ll_exit:
                ActivityManager.finishAllActivities();
                break;
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认退出账号吗？");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", (dialog, which) -> {
            mViewModel.logout();
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }


    /**
     * 登录后的逻辑处理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerEvent(MessageEvent<LoginBean> messageEvent) {
        if (messageEvent.getCode() == EventCode.LOGIN_CODE) {
            String username = AccountManager.getInstance().getUsername();
            mBindingView.drawerLeft.tvUsername.setText(username);
        }
    }

}