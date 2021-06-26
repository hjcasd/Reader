package com.hjc.learn.gank.utils

import java.text.SimpleDateFormat
import java.util.*

object TranslateUtils {

    fun getTranslateTime(time: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA)
        // 在主页面中设置当天时间
        val nowTime = Date()
        val currDate = sdf.format(nowTime)
        val currentMilliseconds = nowTime.time // 当前日期的毫秒值
        val timeMilliseconds: Long
        val date: Date? = try {
            // 将给定的字符串中的日期提取出来
            sdf.parse(time)
        } catch (e: Exception) {
            e.printStackTrace()
            return time
        }
        if (date != null) {
            timeMilliseconds = date.time
            val timeDifferent = currentMilliseconds - timeMilliseconds
            if (timeDifferent < 60000) { // 一分钟之内
                return "刚刚"
            }
            if (timeDifferent < 3600000) { // 一小时之内
                val longMinute = timeDifferent / 60000
                val minute = (longMinute % 100).toInt()
                return minute.toString() + "分钟之前"
            }
            val l = (24 * 60 * 60 * 1000).toLong() // 每天的毫秒数
            if (timeDifferent < l) { // 小于一天
                val longHour = timeDifferent / 3600000
                val hour = (longHour % 100).toInt()
                return hour.toString() + "小时之前"
            }
            val currYear = currDate.substring(0, 4)
            val year = time.substring(0, 4)
            return if (year != currYear) {
                time.substring(0, 10)
            } else time.substring(5, 10)
        }
        return time
    }
}