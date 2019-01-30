package com.hjc.reader.ui.gank.adapter;

import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.hjc.reader.R;
import com.hjc.reader.adapter.ImageAdapter;
import com.hjc.reader.model.ImageViewInfo;
import com.hjc.reader.model.response.GankDayBean;
import com.hjc.reader.utils.ViewUtils;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.utils.image.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecommendAdapter extends BaseQuickAdapter<GankDayBean, BaseViewHolder> {
    public final int TYPE_TEXT = 1;  //只有文字
    public final int TYPE_IMAGE_ONE = 2;  //一张图片加文字
    public final int TYPE_IMAGE_THREE = 3;  //三张图片加文字
    public final int TYPE_TITLE = 4;  //标题栏
    public final int TYPE_IMAGE_ONLY = 5;  //只有一张图片

    public RecommendAdapter(@Nullable List<GankDayBean> data) {
        super(data);

        setMultiTypeDelegate(new MultiTypeDelegate<GankDayBean>() {
            @Override
            protected int getItemType(GankDayBean resultsBean) {
                String title = resultsBean.getTitle();
                if (!StringUtils.isEmpty(title)) {
                    return TYPE_TITLE;
                } else {
                    List<String> images = resultsBean.getImages();
                    if (images == null) {
                        if (resultsBean.getType().equals("福利")) {
                            return TYPE_IMAGE_ONLY;
                        } else {
                            return TYPE_TEXT;
                        }
                    } else {
                        if (images.size() >= 1 && images.size() < 3) {
                            return TYPE_IMAGE_ONE;
                        } else {
                            return TYPE_IMAGE_THREE;
                        }
                    }
                }
            }
        });

        getMultiTypeDelegate()
                .registerItemType(TYPE_TEXT, R.layout.item_rv_gank_text)
                .registerItemType(TYPE_IMAGE_ONE, R.layout.item_rv_gank_image_one)
                .registerItemType(TYPE_IMAGE_THREE, R.layout.item_rv_gank_image_three)
                .registerItemType(TYPE_TITLE, R.layout.item_rv_gank_title)
                .registerItemType(TYPE_IMAGE_ONLY, R.layout.item_rv_gank_image_only);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankDayBean item) {
        switch (helper.getItemViewType()) {
            case TYPE_TEXT:
                initType1(helper, item);
                break;

            case TYPE_IMAGE_ONE:
                initType2(helper, item);
                break;

            case TYPE_IMAGE_THREE:
                initType3(helper, item);
                break;

            case TYPE_TITLE:
                initType4(helper, item);
                break;

            case TYPE_IMAGE_ONLY:
                initType5(helper, item);
                break;
        }
    }

    /**
     * 无图片布局
     */
    private void initType1(BaseViewHolder helper, GankDayBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvAuthor = helper.getView(R.id.tv_author);
        TextView tvTime = helper.getView(R.id.tv_time);

        tvTitle.setText(item.getDesc());
        tvAuthor.setText(item.getWho());
        String translateTime = getTranslateTime(item.getPublishedAt());
        tvTime.setText(translateTime);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SchemeUtils.jumpToWeb(mContext, item.getUrl(), item.getDesc());
            }
        });
    }

    /**
     * 一张图片布局
     */
    private void initType2(BaseViewHolder helper, GankDayBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvAuthor = helper.getView(R.id.tv_author);
        TextView tvTime = helper.getView(R.id.tv_time);
        ImageView ivPic = helper.getView(R.id.iv_pic);

        tvTitle.setText(item.getDesc());
        tvAuthor.setText(item.getWho());
        String translateTime = getTranslateTime(item.getPublishedAt());
        tvTime.setText(translateTime);
        ImageLoader.loadImage(ivPic, item.getImages().get(0), 0);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SchemeUtils.jumpToWeb(mContext, item.getUrl(), item.getDesc());
            }
        });
    }

    /**
     * 三张图片布局
     */
    private void initType3(BaseViewHolder helper, GankDayBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvAuthor = helper.getView(R.id.tv_author);
        TextView tvTime = helper.getView(R.id.tv_time);
        RecyclerView rvPic = helper.getView(R.id.rv_pic);

        tvTitle.setText(item.getDesc());
        tvAuthor.setText(item.getWho());
        String translateTime = getTranslateTime(item.getPublishedAt());
        tvTime.setText(translateTime);

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rvPic.setLayoutManager(manager);

        List<String> imgList = new ArrayList<>();
        //多个取前3个
        for (int i = 0; i < 3; i++) {
            imgList.add(item.getImages().get(i));
        }
        ImageAdapter adapter = new ImageAdapter(imgList);
        rvPic.setAdapter(adapter);

        //解决嵌套RecyclerView时,当点击item内部的RecyclerView后,外部RecyclerView的点击事件不生效的问题
        rvPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return helper.itemView.onTouchEvent(event);
            }
        });

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SchemeUtils.jumpToWeb(mContext, item.getUrl(), item.getDesc());
            }
        });
    }

    /**
     * 标题布局
     */
    private void initType4(BaseViewHolder helper, GankDayBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        tvTitle.setText(item.getTitle());
    }

    /**
     * 仅有一张图片布局
     */
    private void initType5(BaseViewHolder helper, GankDayBean item) {
        ImageView ivPic = helper.getView(R.id.iv_pic);
        ImageLoader.loadImage(ivPic, item.getUrl(), 1);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ImageViewInfo> list = new ArrayList<>();
                ImageViewInfo viewInfo = new ImageViewInfo(item.getUrl());
                list.add(viewInfo);
                Rect rect = ViewUtils.computeBound(ivPic);
                list.get(0).setRect(rect);
                SchemeUtils.jumpToImage(mContext, list);
            }
        });
    }

    private String getTranslateTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf.format(nowTime);
        long currentMilliseconds = nowTime.getTime();// 当前日期的毫秒值
        long timeMilliseconds;
        Date date;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }
        if (date != null) {
            timeMilliseconds = date.getTime();
            long timeDifferent = currentMilliseconds - timeMilliseconds;

            if (timeDifferent < 60000) {// 一分钟之内

                return "刚刚";
            }
            if (timeDifferent < 3600000) {// 一小时之内
                long longMinute = timeDifferent / 60000;
                int minute = (int) (longMinute % 100);
                return minute + "分钟之前";
            }
            long l = 24 * 60 * 60 * 1000; // 每天的毫秒数
            if (timeDifferent < l) {// 小于一天
                long longHour = timeDifferent / 3600000;
                int hour = (int) (longHour % 100);
                return hour + "小时之前";
            }
            if (timeDifferent >= l) {
                String currYear = currDate.substring(0, 4);
                String year = time.substring(0, 4);
                if (!year.equals(currYear)) {
                    return time.substring(0, 10);
                }
                return time.substring(5, 10);
            }
        }
        return time;
    }
}
