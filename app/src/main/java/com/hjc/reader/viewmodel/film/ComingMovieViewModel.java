package com.hjc.reader.viewmodel.film;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.MovieComingBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class ComingMovieViewModel extends CommonViewModel {

    // 即将上映电影数据
    private MutableLiveData<List<MovieComingBean.ComingItemBean>> comingMovieLiveData = new MutableLiveData<>();

    public ComingMovieViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取即将上映电影数据
     *
     * @param isFirst 是否第一次加载
     */
    public void loadComingMovieData(boolean isFirst) {
        RetrofitHelper.getInstance().getMTimeService()
                .getComingFilm()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<MovieComingBean>(this, isFirst) {
                    @Override
                    public void onSuccess(MovieComingBean result) {
                        List<MovieComingBean.ComingItemBean> dataList = result.getMoviecomings();
                        if (dataList != null) {
                            comingMovieLiveData.setValue(dataList);
                        } else {
                            ToastUtils.showShort("获取数据失败");
                        }
                    }
                });
    }


    public MutableLiveData<List<MovieComingBean.ComingItemBean>> getComingMovieLiveData() {
        return comingMovieLiveData;
    }

}
