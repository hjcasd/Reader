package com.hjc.reader.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankRecommendBean {

    private boolean error;
    private ResultsBean results;
    private List<String> category;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public static class ResultsBean {
        private List<GankDayBean> Android;
        private List<GankDayBean> App;
        private List<GankDayBean> iOS;
        @SerializedName("前端")
        private List<GankDayBean> front;
        @SerializedName("休息视频")
        private List<GankDayBean> rest;
        @SerializedName("拓展资源")
        private List<GankDayBean> extra;
        @SerializedName("瞎推荐")
        private List<GankDayBean> recommend;
        @SerializedName("福利")
        private List<GankDayBean> welfare;

        public List<GankDayBean> getAndroid() {
            return Android;
        }

        public void setAndroid(List<GankDayBean> android) {
            Android = android;
        }

        public List<GankDayBean> getApp() {
            return App;
        }

        public void setApp(List<GankDayBean> app) {
            App = app;
        }

        public List<GankDayBean> getiOS() {
            return iOS;
        }

        public void setiOS(List<GankDayBean> iOS) {
            this.iOS = iOS;
        }

        public List<GankDayBean> getRest() {
            return rest;
        }

        public void setRest(List<GankDayBean> rest) {
            this.rest = rest;
        }

        public List<GankDayBean> getExtra() {
            return extra;
        }

        public void setExtra(List<GankDayBean> extra) {
            this.extra = extra;
        }

        public List<GankDayBean> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<GankDayBean> recommend) {
            this.recommend = recommend;
        }

        public List<GankDayBean> getWelfare() {
            return welfare;
        }

        public void setWelfare(List<GankDayBean> welfare) {
            this.welfare = welfare;
        }

        public List<GankDayBean> getFront() {
            return front;
        }

        public void setFront(List<GankDayBean> front) {
            this.front = front;
        }
    }
}
