package com.hjc.reader.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseFragmentActivity;
import com.hjc.reader.base.event.EventManager;
import com.hjc.reader.base.event.MessageEvent;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.BaseProgressObserver;
import com.hjc.reader.model.response.LoginBean;
import com.hjc.reader.ui.film.Tab3Fragment;
import com.hjc.reader.ui.gank.Tab2Fragment;
import com.hjc.reader.ui.menu.CollectActivity;
import com.hjc.reader.ui.menu.JokeActivity;
import com.hjc.reader.ui.wan.Tab1Fragment;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.utils.helper.AccountManager;
import com.hjc.reader.utils.helper.ActivityManager;
import com.hjc.reader.utils.permission.PermissionCallBack;
import com.hjc.reader.utils.permission.PermissionManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.fl_account)
    FrameLayout flAccount;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.ll_home_page)
    LinearLayout llHomePage;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.ll_joke)
    LinearLayout llJoke;
    @BindView(R.id.ll_exit)
    LinearLayout llExit;

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
        EventManager.register(this);
        requestPermission();

        mTab1Fragment = Tab1Fragment.newInstance();
        mTab2Fragment = Tab2Fragment.newInstance();
        mTab3Fragment = Tab3Fragment.newInstance();

        setCurrentItem(0);

        String username = AccountManager.getInstance().getUsername();
        if (!StringUtils.isEmpty(username)) {
            tvUsername.setText(username);
        } else {
            tvUsername.setText("请登录");
        }
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        PermissionManager manager = new PermissionManager(this);
        manager.requestStoragePermission(new PermissionCallBack() {
            @Override
            public void onGranted() {
                ToastUtils.showShort("申请存储权限成功");
            }

            @Override
            public void onDenied() {
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

        flAccount.setOnClickListener(this);
        llHomePage.setOnClickListener(this);
        llScan.setOnClickListener(this);
        llCollect.setOnClickListener(this);
        llJoke.setOnClickListener(this);
        llExit.setOnClickListener(this);

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
                ToastUtils.showShort("正在开发中");
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
                boolean isLogin = AccountManager.getInstance().isLogin();
                if (isLogin) {
                    logout();
                } else {
                    SchemeUtils.jumpToLogin(MainActivity.this);
                }
                break;

            case R.id.ll_home_page:
                ToastUtils.showShort("项目主页");
                break;

            case R.id.ll_scan:
                ToastUtils.showShort("扫码下载");
                break;

            case R.id.ll_collect:
                if (AccountManager.getInstance().isLogin()) {
                    startActivity(new Intent(this, CollectActivity.class));
                } else {
                    SchemeUtils.jumpToLogin(MainActivity.this);
                }
                break;

            case R.id.ll_joke:
                startActivity(new Intent(this, JokeActivity.class));
                break;

            case R.id.ll_exit:
                ActivityManager.finishAllActivities();
                break;
        }
    }

    private void logout() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .logout()
                .compose(RxHelper.bind(this))
                .subscribe(new BaseProgressObserver<LoginBean>(getSupportFragmentManager()) {
                    @Override
                    public void onSuccess(LoginBean result) {
                        if (result != null) {
                            ToastUtils.showShort("退出账号成功");
                            tvUsername.setText("请登录");

                            AccountManager.getInstance().clear();
                        } else {
                            ToastUtils.showShort("退出账号失败,请稍后重试");
                        }
                    }
                });
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
                isOne = true;
                showFragment(mTab1Fragment);
                break;
        }
        ivTab1.setSelected(isOne);
        ivTab2.setSelected(isTwo);
        ivTab3.setSelected(isThree);
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
        if (messageEvent.getCode() == EventCode.C) {
            String username = AccountManager.getInstance().getUsername();
            tvUsername.setText(username);
        }
    }
}
