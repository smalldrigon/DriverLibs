package com.grgbanking.driverlibsdemo.util;


import android.app.Activity;
import android.os.Process;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
* ClassName: ActivityUtil
* Author: gongxiaobiao
* Date: 2019/10/10 16:02
* Description:
*/


public class ActivityUtil {
    private ActivityUtil(){

    }
    private static ActivityUtil instance = new ActivityUtil();
    private static List<Activity> activities = new ArrayList<>();
    public static ActivityUtil getInstance(){
        return instance;
    }

    public void addActivity(Activity activity) {
        Log.i("activity","添加"+activity.getComponentName());
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activities.size(); i < size; i++) {
            if ( null!= activities.get(i)) {
                Log.i("activity","关闭"+activities.get(i).getComponentName());
                activities.get(i).finish();
            }
        }
        activities.clear();
        Process.killProcess(Process.myPid());
    }

//    public void killAppProcess(Activity currentactivity)
//    {
//        //注意：不能先杀掉主进程，否则逻辑代码无法继续执行，需先杀掉相关进程最后杀掉主进程
//        ActivityManager mActivityManager = (ActivityManager)currentactivity.this.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> mList = mActivityManager.getRunningAppProcesses();
//        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : mList)
//        {
//            if (runningAppProcessInfo.pid != android.os.Process.myPid())
//            {
//                android.os.Process.killProcess(runningAppProcessInfo.pid);
//            }
//        }
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
//    }

}
