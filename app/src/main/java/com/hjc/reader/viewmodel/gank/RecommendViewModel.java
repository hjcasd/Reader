package com.hjc.reader.viewmodel.gank;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.GankDayBean;
import com.hjc.reader.bean.response.GankRecommendBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.ArrayList;
import java.util.List;

public class RecommendViewModel extends CommonViewModel {

    // 每日推荐列表数据
    private MutableLiveData<List<GankDayBean>> recommendLiveData = new MutableLiveData<>();

    public RecommendViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取每日推荐数据
     *
     * @param isFirst 是否第一次加载
     */
    public void getRecommendData(boolean isFirst) {
        RetrofitHelper.getInstance().getGankIOService()
                .getRecommendData("2016", "11", "24")
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<GankRecommendBean>(this, isFirst) {
                    @Override
                    public void onSuccess(GankRecommendBean result) {
                        if (!result.isError()) {
                            GankRecommendBean.ResultsBean resultsBean = result.getResults();
                            if (resultsBean != null) {
                                parseRecommendData(resultsBean);
                            }
                        } else {
                            ToastUtils.showShort("获取数据失败");
                        }
                    }
                });
    }

    /**
     * 解析推荐数据
     *
     * @param resultsBean 推荐数据对应的bean
     */
    private void parseRecommendData(GankRecommendBean.ResultsBean resultsBean) {
        List<GankDayBean> dataList = new ArrayList<>();

        //福利区
        List<GankDayBean> welfareList = resultsBean.getWelfare();
        if (welfareList != null && welfareList.size() > 0) {
            GankDayBean bean = new GankDayBean();
            bean.setTitle("福利");
            dataList.add(bean);
            dataList.addAll(welfareList);
        }

        //Android区
        List<GankDayBean> androidList = resultsBean.getAndroid();
        if (androidList != null && androidList.size() > 0) {
            GankDayBean bean = new GankDayBean();
            bean.setTitle("Android");
            dataList.add(bean);
            dataList.addAll(androidList);
        }

        //IOS区
        List<GankDayBean> iosList = resultsBean.getiOS();
        if (iosList != null && iosList.size() > 0) {
            GankDayBean bean = new GankDayBean();
            bean.setTitle("IOS");
            dataList.add(bean);
            dataList.addAll(iosList);
        }

        //Web区
        List<GankDayBean> webList = resultsBean.getFront();
        if (iosList != null && webList.size() > 0) {
            GankDayBean bean = new GankDayBean();
            bean.setTitle("前端");
            dataList.add(bean);
            dataList.addAll(webList);
        }

        //休息视频
        List<GankDayBean> restList = resultsBean.getRest();
        if (restList != null && restList.size() > 0) {
            GankDayBean bean = new GankDayBean();
            bean.setTitle("休息视频");
            dataList.add(bean);
            dataList.addAll(restList);
        }

        //拓展资源
        List<GankDayBean> extraList = resultsBean.getExtra();
        if (extraList != null && extraList.size() > 0) {
            GankDayBean bean = new GankDayBean();
            bean.setTitle("拓展资源");
            dataList.add(bean);
            dataList.addAll(extraList);
        }

        List<GankDayBean> recommendList = resultsBean.getRecommend();
        if (recommendList != null && recommendList.size() > 0) {
            GankDayBean bean = new GankDayBean();
            bean.setTitle("推荐");
            dataList.add(bean);
            dataList.addAll(recommendList);
        }

        recommendLiveData.setValue(dataList);
    }


    public MutableLiveData<List<GankDayBean>> getRecommendLiveData() {
        return recommendLiveData;
    }

}
