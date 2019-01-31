package com.hjc.reader.ui.image;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.OnViewTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/31 10:10
 * @Description: 展示一张图片的页面
 */
public class ShowImageActivity extends BaseActivity {
    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.tv_save_image)
    TextView tvSaveImage;

    private String imgUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_image;
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
            imgUrl = bundle.getString("imgUrl", "");

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.img_default_meizi)
                    .error(R.mipmap.img_default_meizi);

            Glide.with(this)
                    .load(imgUrl)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade(600))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ToastUtils.showShort("资源加载异常");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            /**这里应该是加载成功后图片的高*/
                            int height = photoView.getHeight();
                            int wHeight = getWindowManager().getDefaultDisplay().getHeight();
                            if (height > wHeight) {
                                photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            return false;
                        }
                    })
                    .into(photoView);
        }
    }

    @Override
    public void addListeners() {
        tvSaveImage.setOnClickListener(this);
        photoView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                finish();
            }
        });
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save_image:
                ToastUtils.showShort("保存");
                break;
        }
    }
}
