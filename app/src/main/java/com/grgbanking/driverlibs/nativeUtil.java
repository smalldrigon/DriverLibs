package com.grgbanking.driverlibs;

/**
 * Author: gongxiaobiao
 * Date: on 2020/4/8 8:50
 * Email: 904430803@qq.com
 * Description:
 */
class nativeUtil {
    static  {

            System.loadLibrary("hello_jni_world");

    }

      public native void jniIntArratToString(int[] intParams);
}