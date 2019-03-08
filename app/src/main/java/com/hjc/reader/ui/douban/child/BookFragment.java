package com.hjc.reader.ui.douban.child;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.DBBookBean;
import com.hjc.reader.ui.douban.adapter.BookAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 书籍页面
 */
public class BookFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_book)
    RecyclerView rvBook;

    private BookAdapter mAdapter;
    private int start = 0;

    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    public void initView() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rvBook.setLayoutManager(manager);

        mAdapter = new BookAdapter(null);
        rvBook.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取书籍数据
     */
    private void getBookData() {
        RetrofitHelper.getInstance().getDouBanService()
                .getBookList("沟通", start, 18)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<DBBookBean>() {
                    @Override
                    public void onNext(DBBookBean dbBookBean) {
                        if (dbBookBean != null) {
                            parseBookData(dbBookBean);
                        } else {
                            ToastUtils.showShort("未获取到数据");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 解析书籍数据
     *
     * @param dbBookBean 书籍对应的bean
     */
    private void parseBookData(DBBookBean dbBookBean) {
        List<DBBookBean.BooksBean> bookList = dbBookBean.getBooks();
        if (bookList != null) {
            if (start == 0) {
                mAdapter.setNewData(bookList);
                smartRefreshLayout.finishRefresh(1000);
            } else {
                mAdapter.addData(bookList);
                smartRefreshLayout.finishLoadMore(1000);
            }
        }
    }

    @Override
    public void addListeners() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                start += 18;
                getBookData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                start = 0;
                getBookData();
            }
        });
    }

    @Override
    public void onSingleClick(View v) {

    }
}
