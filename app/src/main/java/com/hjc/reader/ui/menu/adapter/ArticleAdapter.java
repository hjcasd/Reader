package com.hjc.reader.ui.menu.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.hjc.reader.R;
import com.hjc.reader.model.response.CollectArticleBean;
import com.hjc.reader.utils.image.ImageManager;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<CollectArticleBean.DataBean.DatasBean, BaseViewHolder> {
    private final int TYPE_TEXT = 1;
    private final int TYPE_IMAGE = 2;

    public ArticleAdapter(@Nullable List<CollectArticleBean.DataBean.DatasBean> data) {
        super(data);

        setMultiTypeDelegate(new MultiTypeDelegate<CollectArticleBean.DataBean.DatasBean>() {
            @Override
            protected int getItemType(CollectArticleBean.DataBean.DatasBean bean) {
                String envelopePic = bean.getEnvelopePic();
                if (StringUtils.isEmpty(envelopePic)) {
                    return TYPE_TEXT;
                } else {
                    return TYPE_IMAGE;
                }
            }
        });

        getMultiTypeDelegate()
                .registerItemType(TYPE_TEXT, R.layout.item_article_text)
                .registerItemType(TYPE_IMAGE, R.layout.item_article_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectArticleBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, item.getNiceDate());
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_chapter, item.getChapterName());

        helper.addOnClickListener(R.id.cb_collect);

        if (helper.getItemViewType() == TYPE_IMAGE){
            ImageView ivPic = helper.getView(R.id.iv_pic);
            ImageManager.loadImage(ivPic, item.getEnvelopePic(), 0);
        }
    }
}
