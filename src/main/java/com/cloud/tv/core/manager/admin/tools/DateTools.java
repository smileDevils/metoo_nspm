package com.cloud.tv.core.manager.admin.tools;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateTools {

    public static String FORMAT_yyyyMMdd = "yyyyMMdd";
    public static String FORMAT_STANDARD = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static String TIME_000000 = "000000";
    public static String TIME_000 = "000";
    public static String TIME_235959 = "235959";
    public static long ONEDAY_TIME = 86400000L;


    public static String longToStr(long date, String format) {
        try {
            return dateToStr(new Date(date), format);
        } catch (Exception var4) {
            return null;
        }
    }

   // 字符串转时间戳
    public static long strToLong(String data, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(data).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1L;
        }
    }



    public static String dateToStr(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception var3) {
            return null;
        }
    }

    public static String longToDate(Long timestamp, String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = new Date(timestamp);
            return sdf.format(date);
        } catch (Exception var3) {
            return null;
        }
    }

    // 转换 10位时间戳
    public static long getTimesTamp10(){
        Date date = new Date();
        return date.getTime() / 1000;
    }
    public static long currentTimeMillis(){
        Long currencTimeMillis = System.currentTimeMillis();
        return currencTimeMillis;
    }

    public static int compare(Long time1, Long time2){
        int day = (int) ((time1 - time2) / ONEDAY_TIME);
        return day;
    }



    // 时间转时间戳

    // 时间戳转日期
}
