package com.hjc.reader.utils.helper;

import com.blankj.utilcode.util.SPUtils;

/**
 * @Author: HJC
 * @Date: 2019/2/20 15:39
 * @Description: 账户管理类
 */
public class AccountManager {
    private static AccountManager mInstance;

    private static final String IS_LOGIN = "isLogin";
    private static final String USERNAME = "username";

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        if (mInstance == null) {
            synchronized (AccountManager.class) {
                if (mInstance == null) {
                    mInstance = new AccountManager();
                }
            }
        }
        return mInstance;
    }

    public void init(boolean isLogin, String username){
        AccountManager.getInstance().setLogin(isLogin);
        AccountManager.getInstance().setUsername(username);
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public String getUsername() {
        String username = SPUtils.getInstance().getString(USERNAME);
        return username;
    }

    public void setUsername(String username) {
        SPUtils.getInstance().put(USERNAME, username);
    }


    /**
     * 用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        boolean isLogin = SPUtils.getInstance().getBoolean(IS_LOGIN);
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        SPUtils.getInstance().put(IS_LOGIN, isLogin);
    }

    /**
     * 清除账户信息
     */
    public void clear(){
        AccountManager.getInstance().setLogin(false);
        AccountManager.getInstance().setUsername("");
    }
}
