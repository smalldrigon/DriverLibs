package com.grgbanking.driverlibsdemo.data;

import java.text.SimpleDateFormat;

public class LogBean {
    public static final String SDAS = "sdas";
    public String mTime;
    public String mLog;
    public String mWho;
 
    public LogBean(long time, String log) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        mTime = format.format(time);
        mLog = log;
    }
    private String ss (){
         
        return SDAS;
    }
}