package com.hjc.reader.ui.wan.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.observer.BaseCommonObserver;
import com.hjc.reader.model.response.CollectArticleBean;
import com.hjc.reader.model.response.WanListBean;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.utils.helper.AccountManager;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

public class WanListAdapter extends BaseQuickAdapter<WanListBean.DataBean.DatasBean, BaseViewHolder> {

    public WanListAdapter(@Nullable List<WanListBean.DataBean.DatasBean> data) {
        super(R.layout.item_rv_wan_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WanListBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, item.getNiceDate());
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_chapter, item.getChapterName());

        CheckBox cbCollect = helper.getView(R.id.cb_collect);
        cbCollect.setChecked(item.isCollect());

        cbCollect.setOnClickListener(v -> {
            //收藏或取消收藏前判断是否已登录
            if (AccountManager.getInstance().isLogin()) {
                if (!item.isCollect()) {
                    collectArticle(mContext, item);
                } else {
                    unCollectArticle(mContext, item);
                }
            } else {
                item.setCollect(false);
                notifyDataSetChanged();
                SchemeUtils.jumpToLogin(mContext);
            }
        });
    }

    /**
     * 收藏文章
     *
     * @param context 上下文
     * @param item    对应item的bean
     */
    private void collectArticle(Context context, WanListBean.DataBean.DatasBean item) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .collectArticle(item.getId())
                .compose(RxHelper.bind((LifecycleProvider) context))
                .subscribe(new BaseCommonObserver<CollectArticleBean>() {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        parseCollectData(result, item);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        super.onFailure(errorMsg);
                        item.setCollect(false);
                        notifyDataSetChanged();
                    }
                });
    }

    /**
     * 解析收藏文章是否成功
     *
     * @param collectArticleBean 返回结果对应的bean
     * @param item               对应item的bean
     */
    private void parseCollectData(CollectArticleBean collectArticleBean, WanListBean.DataBean.DatasBean item) {
        if (collectArticleBean != null) {
            if (collectArticleBean.getErrorCode() == 0) {
                item.setCollect(true);
                notifyDataSetChanged();
                ToastUtils.showShort("收藏成功");
            }
        } else {
            ToastUtils.showShort("服务器异常,请稍后重试");
        }
    }


    /**
     * 取消收藏文章
     *
     * @param context 上下文
     * @param bean    对应item的bean
     */
    private void unCollectArticle(Context context, WanListBean.DataBean.DatasBean bean) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .unCollect(bean.getId())
                .compose(RxHelper.bind((LifecycleProvider) context))
                .subscribe(new BaseCommonObserver<CollectArticleBean>() {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        parseUnCollectData(result, bean);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        super.onFailure(errorMsg);
                        bean.setCollect(true);
                        notifyDataSetChanged();
                    }
                });
    }

    /**
     * 解析取消收藏文章是否成功
     *
     * @param collectArticleBean 返回结果对应的bean
     * @param item               对应item的bean
     */
    private void parseUnCollectData(CollectArticleBean collectArticleBean, WanListBean.DataBean.DatasBean item) {
        if (collectArticleBean != null) {
            if (collectArticleBean.getErrorCode() == 0) {
                item.setCollect(false);
                notifyDataSetChanged();
                ToastUtils.showShort("已取消收藏");
            } else {
                ToastUtils.showShort(collectArticleBean.getErrorMsg());
            }
        } else {
            ToastUtils.showShort("服务器异常,请稍后重试");
        }
    }
}
