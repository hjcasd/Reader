package com.hjc.reader.ui.gank.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.hjc.reader.R;
import com.hjc.reader.adapter.ImageAdapter;
import com.hjc.reader.model.response.GankIOBean;
import com.hjc.reader.utils.image.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GankAdapter extends BaseQuickAdapter<GankIOBean.ResultsBean, BaseViewHolder> {
    private final int TYPE_TEXT = 1;
    private final int TYPE_IMAGE_ONE = 2;
    private final int TYPE_IMAGE_THREE = 3;

    public GankAdapter(@Nullable List<GankIOBean.ResultsBean> data) {
        super(data);

        setMultiTypeDelegate(new MultiTypeDelegate<GankIOBean.ResultsBean>() {
            @Override
            protected int getItemType(GankIOBean.ResultsBean resultsBean) {
                List<String> images = resultsBean.getImages();
                if (images == null || images.size() == 0) {
                    return TYPE_TEXT;
                }
                if (images.size() >= 1 && images.size() < 3) {
                    return TYPE_IMAGE_ONE;
                }
                return TYPE_IMAGE_THREE;
            }
        });

        getMultiTypeDelegate()
                .registerItemType(TYPE_TEXT, R.layout.item_rv_gank_type1)
                .registerItemType(TYPE_IMAGE_ONE, R.layout.item_rv_gank_type2)
                .registerItemType(TYPE_IMAGE_THREE, R.layout.item_rv_gank_type3);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankIOBean.ResultsBean item) {
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
        }
    }

    private void initType1(BaseViewHolder helper, GankIOBean.ResultsBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvAuthor = helper.getView(R.id.tv_author);
        TextView tvTime = helper.getView(R.id.tv_time);

        tvTitle.setText(item.getDesc());
        tvAuthor.setText(item.getWho());
        String translateTime = getTranslateTime(item.getPublishedAt());
        tvTime.setText(translateTime);
    }

    private void initType2(BaseViewHolder helper, GankIOBean.ResultsBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvAuthor = helper.getView(R.id.tv_author);
        TextView tvTime = helper.getView(R.id.tv_time);
        ImageView ivPic = helper.getView(R.id.iv_pic);

        tvTitle.setText(item.getDesc());
        tvAuthor.setText(item.getWho());
        String translateTime = getTranslateTime(item.getPublishedAt());
        tvTime.setText(translateTime);
        ImageLoader.loadImage(ivPic, item.getImages().get(0), 0);
    }

    private void initType3(BaseViewHolder helper, GankIOBean.ResultsBean item) {
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
        for(int i = 0; i < 3 ; i++){
            imgList.add(item.getImages().get(i));
        }
        ImageAdapter adapter = new ImageAdapter(imgList);
        rvPic.setAdapter(adapter);
    }

    public String getTranslateTime(String time) {
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
