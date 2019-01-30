package com.hjc.reader.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

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
import com.hjc.reader.R;
import com.hjc.reader.base.adapter.BasePagerAdapter;
import com.hjc.reader.model.ImageViewInfo;

import java.util.List;

public class GalleyAdapter extends BasePagerAdapter<ImageViewInfo> {
    private PhotoCallback mCallback;

    public GalleyAdapter(Context context, List<ImageViewInfo> list, PhotoCallback callback) {
        super(context, list);
        this.mCallback = callback;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_vp_galley;
    }

    @Override
    protected void initView(View itemView, int position) {
        PhotoView photoView = itemView.findViewById(R.id.photo_view);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.img_default_meizi)
                .error(R.mipmap.img_default_meizi);

        Glide.with(mContext)
                .load(mDataList.get(position).getUrl())
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

        photoView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (mCallback != null) {
                    mCallback.onPhotoClick(view);
                }
            }
        });
    }


    public interface PhotoCallback {
        void onPhotoClick(View view);
    }
}
