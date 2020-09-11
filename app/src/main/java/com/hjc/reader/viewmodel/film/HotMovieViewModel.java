package com.hjc.reader.viewmodel.film;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.MovieHotBean;
import com.hjc.reader.bean.response.MovieItemBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class HotMovieViewModel extends CommonViewModel {

    // 热映榜电影数据
    private MutableLiveData<List<MovieItemBean>> hotMovieLiveData = new MutableLiveData<>();

    public HotMovieViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取热映榜电影数据
     *
     * @param isFirst 是否第一次加载
     */
    public void loadHotMovieData(boolean isFirst) {
        RetrofitHelper.getInstance().getMTimeService()
                .getHotFilm()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<MovieHotBean>(this, isFirst) {
                    @Override
                    public void onSuccess(MovieHotBean result) {
                        List<MovieItemBean> dataList = result.getMs();
                        if (dataList != null && dataList.size() > 0) {
                           hotMovieLiveData.setValue(dataList);
                        } else {
                            ToastUtils.showShort("获取数据失败");
                        }
                    }
                });
    }


    public MutableLiveData<List<MovieItemBean>> getHotMovieLiveData() {
        return hotMovieLiveData;
    }

}
