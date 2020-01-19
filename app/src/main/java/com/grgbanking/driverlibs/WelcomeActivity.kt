package com.grgbanking.driverlibs

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.hardwaredemo.Contants
import com.grgbanking.baselibrary.util.LogUtil

import com.grgbanking.huitong.driver_libs.util.FileAssetsUtil
import com.grgbanking.huitong.driver_libs.util.InstallSilent
 import io.reactivex.Observable
 import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import java.io.FileNotFoundException


/**
 * @ClassName:
 * @Description: 闸机欢迎， 可点击设置横竖屏
 * @Author: gongxiaobiao
 * @Date: 2019/8/14 15:30
 */
class WelcomeActivity : AppCompatActivity() {


    var disposables: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_welcome_activity)
        initData()
    }


    var disposedCopyfIle: Disposable? = null
    fun initData() {
        disposedCopyfIle = Observable.zip(copyData(), copyToSdCard(),
            BiFunction<Int, Int, Int> { t1, t2 -> t1 + t2 }).subscribe {
            println("启动")
             LogUtil.i(Contants.LOGTAG,"启动驱动服务")
            runLinuxServer()

        }
     }

    private fun copyData(): Observable<Int> {
        return Observable.create {
            //从assets中复制配置文件和C动态库到手机中，再初始化
            FileAssetsUtil.getInstance(this).copyAssetsToData("ca10", "/ca10").setFileOperateCallback(object :
                FileAssetsUtil.FileOperateCallback {
                override fun onSuccess() {
                    it.onNext(1)
                }

                override fun onFailed(error: String?) {
                    println("复制失败$error")
                    LogUtil.i(Contants.LOGTAG,"复制初始化文件失败$error")
                    throw FileNotFoundException("复制初始化文件失败")
                }
            })
        }
    }


    private fun copyToSdCard(): Observable<Int> {
        return Observable.create {
            //从assets中复制配置文件和C动态库到手机中，再初始化
            FileAssetsUtil.getInstance(this).copyAssetsToSD("GRGConfig1", "/app/GRGConfig1")
                .setCallback2SD(object : FileAssetsUtil.FileOperateCallback {
                    override fun onSuccess() {
                        it.onNext(2)
                    }

                    override fun onFailed(error: String?) {
                        println("复制失败$error")//.logI()
                        LogUtil.i(Contants.LOGTAG,"复制配置文件失败$error")
                    }

                })
        }
    }

    fun initView() {


    }


    fun initDevices(machineType: String) {
//        DriverManagers.Builder()
//                    .setContext(applicationContext)//先设置context
//                    .setCardReader(DriverManagers.CARD_READER_TYPE_T10)
//                    .setFingerPrints(DriverManagers.FINGERPRINTS_TYPE_FPC1011)
//                    .setScanGun(DriverManagers.SACNGUN_TYPE_ZD7100)
//                    .setGateMachine(machineType)
//                    .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables?.dispose()
        disposedCopyfIle?.dispose()
        disposable?.dispose()


    }

    var disposable: Disposable? = null
    fun runLinuxServer() {
//        val path = "cd /data/ca10\n"
//        val commandList = ArrayList<String>()
//        val commd1 = "export LD_LIBRARY_PATH=./\n"
//        val commd2 = "./GrgCommManager\n"
//        commandList.add(path)
//        commandList.add(commd1)
//        commandList.add(commd2)


        object :Thread(){
            override fun run() {
                super.run()
                println("执行子线程")
                val path = "cd /data/ca10\n"
                val commandList = ArrayList<String>()
                val commd1 = "export LD_LIBRARY_PATH=./\n"
                val commd2 = "./GrgCommManager\n"
                commandList.add(path)
                commandList.add(commd1)
                commandList.add(commd2)
                InstallSilent.execRootCommand(commandList, false, true)
            }
        }.start()

        startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
//                    finish()
//        disposable = Observable.create<Boolean> {
//            InstallSilent.execRootCommand(commandList, false, true)
//            Thread.sleep(3000L)
//            it.onNext(true)
//        }.observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe {
//                if (it) {

//                    DriverManagers.Builder().setContext(this)
//                        .setFingerPrints(DriverManagers.FINGERPRINTS_TYPE_FPC1011)
//                        .setScanGun(DriverManagers.SACNGUN_TYPE_ZD7100)
//                        .setCardReader(DriverManagers.CARD_READER_TYPE_T10)
//                        .setGateMachine(DriverManagers.GATEMACHINE_TYPE_M810)
//                        .setmDriver_GateMachineTest(DriverManagers.GATEMACHINE_TYPE_TJZN)
//                        .build()

//                    startActivity(Intent(this@WelcomeActivity, CameraActivity::class.java))
//                    finish()
//                } else {
//                    println("执行cmd失败")
//                }
//
//            }
//

    }

}