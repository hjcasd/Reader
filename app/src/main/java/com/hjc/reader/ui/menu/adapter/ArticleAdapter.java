package com.hjc.reader.ui.menu.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.CollectArticleBean;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

import io.reactivex.observers.DefaultObserver;

public class ArticleAdapter extends BaseQuickAdapter<CollectArticleBean.DataBean.DatasBean, BaseViewHolder> {
    public ArticleAdapter(@Nullable List<CollectArticleBean.DataBean.DatasBean> data) {
        super(R.layout.item_rv_article, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectArticleBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, item.getNiceDate());
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_chapter, item.getChapterName());

        helper.addOnClickListener(R.id.cb_collect);

//        CheckBox cbCollect = helper.getView(R.id.cb_collect);
//        int position = helper.getAdapterPosition();
//
//        cbCollect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                unCollectArticle(mContext, item.getId(), item.getOriginId(), position);
//            }
//        });
    }

//    /**
//     * @param context  上下文
//     * @param id       文章id
//     * @param originId 文章originId
//     */
//    private void unCollectArticle(Context context, int id, int originId, int position) {
//        RetrofitHelper.getInstance().getWanAndroidService()
//                .unCollectOrigin(id, originId)
//                .compose(RxHelper.bind((LifecycleProvider) context))
//                .subscribe(new DefaultObserver<CollectArticleBean>() {
//                    @Override
//                    public void onNext(CollectArticleBean collectArticleBean) {
//                        parseUnCollectData(collectArticleBean, position);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        ToastUtils.showShort("服务器异常,请稍后重试");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
//
//    /**
//     * 解析取消收藏文章是否成功
//     *
//     * @param collectArticleBean 返回结果对应的bean
//     */
//    private void parseUnCollectData(CollectArticleBean collectArticleBean, int position) {
//        if (collectArticleBean != null) {
//            if (collectArticleBean.getErrorCode() == 0) {
//                remove(position);
//                ToastUtils.showShort("已取消收藏");
//            } else {
//                ToastUtils.showShort(collectArticleBean.getErrorMsg());
//            }
//        } else {
//            ToastUtils.showShort("服务器异常,请稍后重试");
//        }
//    }
}
