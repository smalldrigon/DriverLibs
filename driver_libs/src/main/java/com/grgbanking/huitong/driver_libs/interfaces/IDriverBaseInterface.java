package com.grgbanking.huitong.driver_libs.interfaces;

import android.content.Context;


/**
 * Author: gongxiaobiao
 * Date: on 2019/9/4 16:01
 * Email: 904430803@qq.com
 * Description: 硬件开关接口申明
 */
public interface IDriverBaseInterface {
    int open(Context context);
    int close();


}
