package com.hjc.reader.application;

import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.hjc.reader.constant.AppConstants;
import com.hjc.reader.model.db.DaoMaster;
import com.hjc.reader.model.db.DaoSession;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * @Author: HJC
 * @Date: 2019/1/4 14:46
 * @Description: application (v1)
 */
public class App extends MultiDexApplication {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initUtils();
        initBugly();
        initGreenDao();
    }

    private void initUtils() {
        Utils.init(this);
        if (AppConstants.isDebug) {
            LogUtils.Config config = LogUtils.getConfig();
            config.setLogSwitch(true);
            config.setGlobalTag("tag");
        }
    }

    private void initBugly() {
        // 获取当前包名
        String packageName = getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(this, "e0a1ba856b", true, strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 初始化GreenDao
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "reader.db");
        SQLiteDatabase db = helper.getWritableDatabase();

        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /**
     * 获取DaoSession
     */
    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
