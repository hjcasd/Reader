package com.hjc.reader.ui.gank.adapter;


import android.annotation.SuppressLint;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.adapter.ImageAdapter;
import com.hjc.reader.bean.response.GankDayBean;
import com.hjc.reader.databinding.ItemGankImageOneBinding;
import com.hjc.reader.databinding.ItemGankImageOnlyBinding;
import com.hjc.reader.databinding.ItemGankImageThreeBinding;
import com.hjc.reader.databinding.ItemGankTextBinding;
import com.hjc.reader.databinding.ItemGankTitleBinding;
import com.hjc.reader.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends BaseDelegateMultiAdapter<GankDayBean, BaseViewHolder> {

    public RecommendAdapter() {
        super();
        setMultiTypeDelegate(new RecommendMultiTypeDelegate());
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, GankDayBean item) {
        if (item == null) {
            return;
        }
        switch (helper.getItemViewType()) {
            case GankDayBean.TYPE_TEXT:
                initType1(helper, item);
                break;

            case GankDayBean.TYPE_IMAGE_ONE:
                initType2(helper, item);
                break;

            case GankDayBean.TYPE_IMAGE_THREE:
                initType3(helper, item);
                break;

            case GankDayBean.TYPE_TITLE:
                initType4(helper, item);
                break;

            case GankDayBean.TYPE_IMAGE_ONLY:
                initType5(helper, item);
                break;
        }
    }

    /**
     * 无图片布局
     */
    private void initType1(BaseViewHolder helper, GankDayBean item) {
        ItemGankTextBinding binding = helper.getBinding();
        if (binding != null) {
            String translateTime = AppUtils.getTranslateTime(item.getPublishedAt());
            item.setPublishedAt(translateTime);
            binding.setGankDayBean(item);
        }
    }

    /**
     * 一张图片布局
     */
    private void initType2(BaseViewHolder helper, GankDayBean item) {
        ItemGankImageOneBinding binding = helper.getBinding();
        if (binding != null) {
            String translateTime = AppUtils.getTranslateTime(item.getPublishedAt());
            item.setPublishedAt(translateTime);
            binding.setGankDayBean(item);
        }
    }

    /**
     * 三张图片布局
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initType3(BaseViewHolder helper, GankDayBean item) {
        ItemGankImageThreeBinding binding = helper.getBinding();
        if (binding != null) {
            String translateTime = AppUtils.getTranslateTime(item.getPublishedAt());
            item.setPublishedAt(translateTime);
            binding.setGankDayBean(item);

            GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
            binding.rvPic.setLayoutManager(manager);

            List<String> imgList = new ArrayList<>();
            //多个取前3个
            for (int i = 0; i < 3; i++) {
                imgList.add(item.getImages().get(i));
            }
            ImageAdapter adapter = new ImageAdapter(imgList);
            binding.rvPic.setAdapter(adapter);

            //解决嵌套RecyclerView时,当点击item内部的RecyclerView后,外部RecyclerView的点击事件不生效的问题
            binding.rvPic.setOnTouchListener((v, event) -> helper.itemView.onTouchEvent(event));
        }
    }

    /**
     * 标题布局
     */
    private void initType4(BaseViewHolder helper, GankDayBean item) {
        ItemGankTitleBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setGankDayBean(item);
        }
    }

    /**
     * 只有图片布局
     */
    private void initType5(BaseViewHolder helper, GankDayBean item) {
        ItemGankImageOnlyBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setGankDayBean(item);
        }
    }

    // 代理类
    final static class RecommendMultiTypeDelegate extends BaseMultiTypeDelegate<GankDayBean> {

        public RecommendMultiTypeDelegate() {
            // 绑定 item 类型
            addItemType(GankDayBean.TYPE_TEXT, R.layout.item_gank_text);
            addItemType(GankDayBean.TYPE_IMAGE_ONE, R.layout.item_gank_image_one);
            addItemType(GankDayBean.TYPE_IMAGE_THREE, R.layout.item_gank_image_three);
            addItemType(GankDayBean.TYPE_TITLE, R.layout.item_gank_title);
            addItemType(GankDayBean.TYPE_IMAGE_ONLY, R.layout.item_gank_image_only);
        }

        @Override
        public int getItemType(@NotNull List<? extends GankDayBean> data, int position) {
            GankDayBean bean = data.get(position);
            String title = bean.getTitle();
            if (!StringUtils.isEmpty(title)) {
                return GankDayBean.TYPE_TITLE;
            } else {
                List<String> images = bean.getImages();
                if (images == null || images.size() == 0) {
                    if (bean.getType().equals("福利")) {
                        return GankDayBean.TYPE_IMAGE_ONLY;
                    } else {
                        return GankDayBean.TYPE_TEXT;
                    }
                } else {
                    if (images.size() < 3) {
                        return GankDayBean.TYPE_IMAGE_ONE;
                    } else {
                        return GankDayBean.TYPE_IMAGE_THREE;
                    }
                }
            }
        }
    }
}
