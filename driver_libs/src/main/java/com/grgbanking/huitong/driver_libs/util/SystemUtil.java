package com.grgbanking.huitong.driver_libs.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemUtil {
    //获取当前年月日
    public static String getYMD(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());
        return today;
    }
}
