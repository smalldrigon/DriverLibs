package com.gdmcmc.simplecharge.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates

/**
 *
 *@fileName BaseApplication
 *@data on 2019/3/21  14:32
 *@author  xiaobiaogong
 *@email 904430803@qq.con
 *@describe TODO
 **/
open class BaseApplication :Application(){

    private var refWatch: RefWatcher?=null
    
    companion object {
        var context:Context by Delegates.notNull()
        private set
        fun getRefWatcher(context: Context): RefWatcher?{
            val baseApplication = context.applicationContext as BaseApplication
            return baseApplication.refWatch
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        
        initConfig()
        
        registerActivityLifecycleCallbacks(mactivityLifecycleCallbacks)
    }

    private fun initConfig() {
            //初始化腾讯x5内核
//        QbSdk.initX5Environment(this,object :QbSdk.PreInitCallback{
//            override fun onCoreInitFinished() {
//                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
//
//            }
//
//            override fun onViewInitFinished(p0: Boolean) {
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                "x5内核加载$p0".logI()
//             }
//
//        })
        
    }


}

 object mactivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {
        
        
    }

    override fun onActivityResumed(activity: Activity?) {
     }

    override fun onActivityStarted(activity: Activity?) {
     }

    override fun onActivityDestroyed(activity: Activity?) {
     }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
     }

    override fun onActivityStopped(activity: Activity?) {
     }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
     }

}
