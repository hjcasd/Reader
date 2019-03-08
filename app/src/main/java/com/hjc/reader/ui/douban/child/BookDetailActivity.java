package com.hjc.reader.ui.douban.child;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.model.response.DBBookBean;
import com.hjc.reader.utils.FormatUtils;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.utils.image.ImageLoader;
import com.hjc.reader.widget.TitleBar;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/3/7 16:18
 * @Description: 书籍详情页
 */
public class BookDetailActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.iv_blur)
    ImageView ivBlur;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_publish_time)
    TextView tvPublishTime;
    @BindView(R.id.tv_publisher)
    TextView tvPublisher;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_brief)
    TextView tvBrief;
    @BindView(R.id.tv_chapter)
    TextView tvChapter;

    private DBBookBean.BooksBean mData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mData = (DBBookBean.BooksBean) intent.getSerializableExtra("bean");
            if (mData != null) {
                titleBar.setTitle(mData.getTitle());

                ImageLoader.loadImage(ivCover, mData.getImages().getLarge(), 3);
                ImageLoader.loadBlurImage(ivBlur, mData.getImages().getMedium(), 25, 5);

                tvScore.setText(mData.getRating().getAverage() + "");
                tvCount.setText(mData.getRating().getNumRaters() + "人评分");

                tvAuthor.setText(FormatUtils.formatGenres(mData.getAuthor()));
                tvPublishTime.setText(mData.getPubdate());
                tvPublisher.setText(mData.getPublisher());

                tvSummary.setText(mData.getSummary());
                tvBrief.setText(mData.getAuthor_intro());
                tvChapter.setText(mData.getCatalog());
            }
        }
    }

    @Override
    public void addListeners() {
        titleBar.setOnViewClickListener(new TitleBar.onViewClick() {
            @Override
            public void leftClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }

            @Override
            public void rightClick(View view) {
                if (mData != null){
                    SchemeUtils.jumpToWeb(BookDetailActivity.this, mData.getAlt(), mData.getTitle());
                }
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
