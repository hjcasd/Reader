package com.hjc.baselib.loadsir;

import com.hjc.baselib.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @Author: HJC
 * @Date: 2020/5/15 11:13
 * @Description: loadSir 错误页面
 */
public class ErrorCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.default_layout_error;
    }
}
