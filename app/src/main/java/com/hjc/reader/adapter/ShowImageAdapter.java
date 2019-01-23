package com.hjc.reader.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.hjc.reader.R;
import com.hjc.reader.base.adapter.BasePagerAdapter;

import java.util.List;

public class ShowImageAdapter extends BasePagerAdapter<String> {
    public ShowImageAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_vp_show_image;
    }

    @Override
    protected void initView(View itemView, int position) {
        PhotoView photoView = itemView.findViewById(R.id.photo_view);
        ProgressBar pbLoading = itemView.findViewById(R.id.pb_loading);

        pbLoading.setVisibility(View.VISIBLE);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.img_default_meizi)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.img_default_meizi);

        Glide.with(mContext)
                .load(mDataList.get(position))
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade(600))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ToastUtils.showShort("资源加载异常");
                        pbLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pbLoading.setVisibility(View.GONE);

                        /**这里应该是加载成功后图片的高*/
                        int height = photoView.getHeight();
                        int wHeight = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
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
