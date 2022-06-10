package com.example.ynote.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppUtil {
    /**
     * 第一次启动
     * @param activity
     * @return
     */
    public static boolean isFirstStart(Activity activity){
        // 定义一个setting记录APP是几次启动

        //SharedPreferences:存储结果 Context.MODE_PRIVATE私有模式:仅本程序可读写
        SharedPreferences setting =activity.getSharedPreferences("FirstStart", Context.MODE_PRIVATE);
        //读取FirstStart文件的FIRSR值, 没读到写true
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {// 第一次
            //写入FIRST:false
            setting.edit().putBoolean("FIRST", false).apply();
            return true;
        } //不是第一次
        else return false;
    }
}
