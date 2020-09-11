package com.hjc.reader.viewmodel.film;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.MovieDetailBean;
import com.hjc.reader.http.RetrofitHelper;

public class MovieDetailViewModel extends CommonViewModel {

    // 电影详情页数据
    private MutableLiveData<MovieDetailBean.DataBean> movieDetailLiveData = new MutableLiveData<>();

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取电影详情页数据
     *
     * @param id 电影id
     */
    public void getMovieDetailData(int id) {
        RetrofitHelper.getInstance().getMTimeTicketService()
                .getDetailFilm(id)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<MovieDetailBean>(this) {
                    @Override
                    public void onSuccess(MovieDetailBean result) {
                        MovieDetailBean.DataBean bean = result.getData();
                        if (bean != null && bean.getBasic() != null) {
                            movieDetailLiveData.setValue(bean);
                        } else {
                            ToastUtils.showShort("获取数据失败");
                        }
                    }
                });
    }

    public MutableLiveData<MovieDetailBean.DataBean> getMovieDetailLiveData() {
        return movieDetailLiveData;
    }

}
