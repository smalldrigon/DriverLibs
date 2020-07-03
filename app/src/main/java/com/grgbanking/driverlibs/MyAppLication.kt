package com.grgbanking.driverlibs

import android.os.Environment
import android.support.multidex.MultiDex
import com.gdmcmc.simplecharge.base.BaseApplication
   import com.grgbanking.baselibrary.util.DamonDiskLogAdapter
import com.grgbanking.baselibrary.util.LogUtil
import com.grgbanking.driverlibs.util.CrashHandler
  import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.PrettyFormatStrategy
import java.io.File


/**
 * Author: gongxiaobiao
 * Date: on 2019/8/15 15:11
 * Email: 904430803@qq.com
 * Description:
 */
class MyAppLication : BaseApplication() {
    var mMainApplication: MyAppLication? = null
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
//        if (LeakCanary.isInAnalyzerProcess(this)) {
        // This process is dedicated to LeakCanary for heap analysis.
//             You should not init your app in this process.
//            return
//        }
//        LeakCanary.install(this)
        mMainApplication = this

//        Driver_CardReader.open_Driver_CardReader(this)


        //从assets中复制配置文件和C动态库到手机中，再初始化
//        FileAssetsUtil.getInstance(this).copyAssetsToData("ca10","/ca10").setFileOperateCallback(object :
//            FileAssetsUtil.FileOperateCallback{
//            override fun onSuccess() {
//                runLinuxServer()
//            }
//            override fun onFailed(error: String?) {
//                "复制失败$error".logI()
//                throw FileNotFoundException("复制初始化文件失败")
//            }
//        })
//        FileAssetsUtil.getInstance(this).copyAssetsToSD("GRGConfig","/app/GRGConfig")
//            .setFileOperateCallback(object :FileAssetsUtil.FileOperateCallback{
//            override fun onSuccess() {
//                "复制成功".logI()
//            }
//
//            override fun onFailed(error: String?) {
//                "复制失败$error".logI()
//            }
//
//        })


        val crashHandler = CrashHandler.getInstance()
        crashHandler.init(applicationContext)
///////////////////////////////初始化记录日志
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(0)         // (Optional) How many method line to show. Default 2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//            .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
            .tag("gong")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        LogUtil.initLogUtil(AndroidLogAdapter(formatStrategy))
//
        LogUtil.initLogUtil(
            DamonDiskLogAdapter(
                File(
                    Environment.getExternalStorageDirectory().getCanonicalPath() + "/"
                            + context.getResources().getString(R.string.app_name) + "/" +
                            "logs" + File.separator
                ), 1024 * 1 * 500, 100
            )
        )
//        Logger.addLogAdapter(object :AndroidLogAdapter(){
//            override fun isLoggable(priority: Int, tag: String?): Boolean {
//                return false
//
//            }
//        })

    }




}