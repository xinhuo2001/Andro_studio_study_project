package com.example.ynote.Model;

import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class Date {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public Date() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }

    public Date(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }


    public Date(String dataStr) {
        String separator = "-";
        String[] dateList = dataStr.split(separator);
        if(dateList.length == 5) {
            int i = 0;
            year = Integer.parseInt(dateList[i++]);
            month = Integer.parseInt(dateList[i++]);
            day = Integer.parseInt(dateList[i++]);
            hour = Integer.parseInt(dateList[i++]);
            minute = Integer.parseInt(dateList[i++]);
        } else {
            year = 0;
            month = 0;
            day = 0;
            hour = 0;
            minute = 0;
            Log.d("yzf", "Date类数据创建失败");
        }

    }
    public String getDateString() {
        String ret = "";
        ret = ret + year + "-";
        ret = ret + month + "-";
        ret = ret + day + "-";
        ret = ret + hour + "-";
        ret = ret + minute;
        return ret;
    }
}
