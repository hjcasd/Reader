package com.hjc.reader.viewmodel.menu;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.CollectArticleBean;
import com.hjc.reader.bean.response.CollectLinkBean;
import com.hjc.reader.http.RetrofitHelper;

public class EditLinkViewModel extends CommonViewModel {

    // 收藏地址数据
    private MutableLiveData<CollectLinkBean.DataBean> editLinkLiveData = new MutableLiveData<>();

    private MutableLiveData<String> nameData = new MutableLiveData<>();
    private MutableLiveData<String> linkData = new MutableLiveData<>();


    public EditLinkViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 编辑收藏的网址
     *
     * @param id   网址id
     */
    public void editLink(int id) {
        String name = nameData.getValue();
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入网址名称");
            return;
        }
        String link = linkData.getValue();
        if (StringUtils.isEmpty(link)) {
            ToastUtils.showShort("请输入网址链接");
            return;
        }
        RetrofitHelper.getInstance().getWanAndroidService()
                .editLink(id, name, link)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<CollectArticleBean>(this) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        if (result.getErrorCode() == 0) {
                            CollectLinkBean.DataBean bean = new CollectLinkBean.DataBean();
                            bean.setName(name);
                            bean.setLink(link);
                            editLinkLiveData.setValue(bean);
                            ToastUtils.showShort("编辑成功");
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }


    public MutableLiveData<CollectLinkBean.DataBean> getEditLinkLiveData() {
        return editLinkLiveData;
    }

    public MutableLiveData<String> getNameData() {
        return nameData;
    }

    public MutableLiveData<String> getLinkData() {
        return linkData;
    }

}
