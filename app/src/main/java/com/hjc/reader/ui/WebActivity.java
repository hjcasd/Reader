package com.hjc.reader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.utils.AppUtils;
import com.hjc.reader.widget.ProgressWebView;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/4 14:47
 * @Description: 含有WebView的Activity基类
 */
public class WebActivity extends BaseActivity {
    @BindView(R.id.web_view)
    ProgressWebView webView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    private String mTitle;
    private String mUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        initToolBar();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
    }

    private void initToolBar() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolBar.setOverflowIcon(ContextCompat.getDrawable(this, R.mipmap.icon_more));
        tvTitle.setSelected(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mTitle = bundle.getString("title");
                mUrl = bundle.getString("url");

                tvTitle.setText(mTitle);
                webView.loadUrl(mUrl);
            }
        }
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onSingleClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               finish();
                break;

            // 刷新页面
            case R.id.item_refresh:
                if (webView != null) {
                    webView.reload();
                }
                break;

            // 分享
            case R.id.item_share:
                AppUtils.share(this, mUrl);
                break;

            // 复制链接
            case R.id.item_copy:
                AppUtils.copy(this, mUrl);
                ToastUtils.showShort("复制成功");
                break;

            // 打开链接
            case R.id.item_open:
                AppUtils.openLink(this, mUrl);
                break;

            // 添加到收藏
            case R.id.item_collect:
                ToastUtils.showShort("添加到收藏");
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
