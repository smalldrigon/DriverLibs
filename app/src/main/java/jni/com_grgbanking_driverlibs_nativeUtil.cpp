//
// Created by lenovo on 2020/4/8.
//
#include "com_grgbanking_driverlibs_nativeUtil.h"
#include "string"
#include <stdio.h>
#include <stdlib.h>
#include <jni.h>
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG, fmt, ##args)

 using namespace std;
/**
 * [1,2,3] -> 1,2,3
 *
 * @param env JNIEnv
 * @param intArrayParams 输入的int数组
 * @return 用逗号拼接的字符串
 */
JNIEXPORT void JNICALL Java_com_grgbanking_driverlibs_nativeUtil_test
        (JNIEnv *, jobject, jintArray){

}

JNIEXPORT jint JNICALL Java_com_grgbanking_driverlibs_nativeUtil_getnumber
        (JNIEnv *, jobject){
    return 123;
}