package com.hjc.reader.bean.request;


import com.hjc.baselib.http.bean.BaseRequest;

public class UpdateRequest extends BaseRequest {
    private String appType;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
}
