package com.hjc.reader.ui.gank.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.adapter.ImageAdapter;
import com.hjc.reader.bean.response.GankDayBean;
import com.hjc.reader.databinding.ItemGankImageOneBinding;
import com.hjc.reader.databinding.ItemGankImageThreeBinding;
import com.hjc.reader.databinding.ItemGankTextBinding;


import java.util.ArrayList;
import java.util.List;

public class GankAdapter extends BaseDelegateMultiAdapter<GankDayBean, BaseViewHolder> {

    public GankAdapter() {
        super();
        setMultiTypeDelegate(new GankMultiTypeDelegate());
    }

    @Override
    protected void onItemViewHolderCreated(@NonNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GankDayBean item) {
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
        }
    }

    private void initType1(BaseViewHolder helper, GankDayBean item) {
        ItemGankTextBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setGankDayBean(item);
        }
    }

    private void initType2(BaseViewHolder helper, GankDayBean item) {
        ItemGankImageOneBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setGankDayBean(item);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initType3(BaseViewHolder helper, GankDayBean item) {
        ItemGankImageThreeBinding binding = helper.getBinding();
        if (binding != null) {
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


    // 代理类
    final static class GankMultiTypeDelegate extends BaseMultiTypeDelegate<GankDayBean> {

        public GankMultiTypeDelegate() {
            // 绑定 item 类型
            addItemType(GankDayBean.TYPE_TEXT, R.layout.item_gank_text);
            addItemType(GankDayBean.TYPE_IMAGE_ONE, R.layout.item_gank_image_one);
            addItemType(GankDayBean.TYPE_IMAGE_THREE, R.layout.item_gank_image_three);
        }

        @Override
        public int getItemType(@NonNull List<? extends GankDayBean> data, int position) {
            GankDayBean bean = data.get(position);
            List<String> images = bean.getImages();
            if (images == null || images.size() == 0) {
                return GankDayBean.TYPE_TEXT;
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
