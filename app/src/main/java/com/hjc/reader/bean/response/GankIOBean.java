package com.hjc.reader.bean.response;

import java.util.List;

public class GankIOBean {
    private int status;
    private List<GankDayBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GankDayBean> getData() {
        return data;
    }

    public void setData(List<GankDayBean> data) {
        this.data = data;
    }
}
