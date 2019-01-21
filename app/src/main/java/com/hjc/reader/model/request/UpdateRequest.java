package com.hjc.reader.model.request;


import com.hjc.reader.http.bean.BaseRequest;

public class UpdateRequest extends BaseRequest {
    private String appType;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
}
