package com.hjc.library_common.utils

import android.app.Application
import android.content.Context
import android.text.TextUtils
import com.hjc.library_common.global.AppConstants
import com.tencent.bugly.crashreport.CrashReport
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * @Author: HJC
 * @Date: 2019/11/2 15:40
 * @Description: 初始化Bugly工具类
 */
object BuglyUtils {

    /**
     * @param context 上下文
     * @param code Bugly App ID
     * @param isDebug 是否debug
     */
    fun init(context: Context, code: String, isDebug: Boolean) {
        if (context is Application) {
            // 获取当前包名
            val packageName = context.getPackageName()
            // 获取当前进程名
            val processName = getProcessName(android.os.Process.myPid())
            // 设置是否为上报进程
            val strategy = CrashReport.UserStrategy(context)
            strategy.isUploadProcess = processName == null || processName == packageName
            CrashReport.initCrashReport(context, code, isDebug, strategy)
        } else {
            throw UnsupportedOperationException("context must be application...")
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }

}
