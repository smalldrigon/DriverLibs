cmake_minimum_required(VERSION 3.4.1)
#set(JNI src/main/java/jni)
#include_directories(${JNI})
add_library(JniFace SHARED com_grgbanking_driverlibs_nativeUtil.cpp)
#find_library(log-lib log)
target_link_libraries(JniFace
        android
        log
        )
