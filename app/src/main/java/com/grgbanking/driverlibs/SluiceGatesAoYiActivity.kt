package com.grgbanking.driverlibs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.hardwaredemo.Contants

import com.grgbanking.huitong.driver_libs.DriverManagers
import com.grgbanking.huitong.driver_libs.database.DatabaseInstance
import com.grgbanking.huitong.driver_libs.database.EntyType
import com.grgbanking.huitong.driver_libs.gate_machine.DevReturn
import com.grgbanking.huitong.driver_libs.gate_machine.TJZNGateDev_Passage_Num
import com.grgbanking.huitong.driver_libs.interfaces.IGateMachineActionCallBack
import com.grgbanking.huitong.driver_libs.util.LogUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_sluicegates_activity.*
import kotlinx.android.synthetic.main.layout_sluicegates_activity.tv_result1
import kotlinx.android.synthetic.main.layout_sluicegates_activity.tvn_close2
import kotlinx.android.synthetic.main.layout_sluicegates_activity.tvn_init2
import kotlinx.android.synthetic.main.layout_sluicegates_activity.tvn_open2
import kotlinx.android.synthetic.main.layout_sluicegates_activity.tvn_open2_aways
import kotlinx.android.synthetic.main.layout_sluicegates_activity.tvn_open2_right
import kotlinx.android.synthetic.main.layout_sluicegates_activity.tvn_open2_right_aways
import kotlinx.android.synthetic.main.layout_sluicegates_activity.tvn_passed2
import kotlinx.android.synthetic.main.layout_sluicegatesaoyi_activity.*

class SluiceGatesAoYiActivity : AppCompatActivity(), IGateMachineActionCallBack {
    override fun openLeft(res: Boolean) {

        setText1("左开门$res")

    }

    override fun openRight(res: Boolean) {
        setText1("右开门$res")
    }

    override fun closeLeft(res: Boolean) {
        setText1("左关门$res")
    }

    override fun closeRight(res: Boolean) {
        setText1("又关门$res")
    }

    override fun passLeftTimeout() {
        setText1("左通过超时")
    }

    override fun passRightTimeout() {
        setText1("右通过超时")
    }

    override fun passLeftSuccess() {
        setText1("左过闸成功")
    }

    override fun passRightSuccess() {
        setText1("右过闸成功")
    }

    var disposedCopyfIle: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sluicegatesaoyi_activity)
        initView()
    }


    var mHhandle: Int = 0

    fun setText1(str: String) {
        runOnUiThread {
            Log.i("gong",str)
            tv_result1.text = "结果:$str"
         val left =    DatabaseInstance.mDatabaseInstance.countTotal(EntyType.LEFTPASS)
         val right =    DatabaseInstance.mDatabaseInstance.countTotal(EntyType.RIGHTPASS)
         val rightunpass =    DatabaseInstance.mDatabaseInstance.countTotal(EntyType.RIGHTUNPASS)
         val leftunpass =    DatabaseInstance.mDatabaseInstance.countTotal(EntyType.LEFTUNPASS)
            tv_result2.text="左过闸成功$left-右过闸成功$right--左过闸超时$leftunpass--右过闸超时$rightunpass"
        }


    }

    private fun initView() {
        DriverManagers.Builder().setContext(this)
//            .setFingerPrints(DriverManagers.FINGERPRINTS_TYPE_FPC1011)
            .setScanGun(DriverManagers.SACNGUN_TYPE_ZD7100)
//            .setCardReader(DriverManagers.CARD_READER_TYPE_T10)
            .setGateMachine(DriverManagers.GATEMACHINE_TYPE_M820)
            .setIDatabase(DatabaseInstance.getInstance(this, ""))
//            .setmDriver_GateMachineTest(DriverManagers.GATEMACHINE_TYPE_TJZN)
            .build()
        tvn_open2.setOnClickListener {
            //打开1
            var devreturn = DevReturn()
//            testAction {
//                println( "执行线程名称${Thread.currentThread().name}")
            DriverManagers.instance.driver_GateMachine.openGateLeftOnce(devreturn)
            DriverManagers.instance.driver_GateMachine.timeout = 5
//                devreturn
//            }.subscribe {
//                println( "结果线程名称${Thread.currentThread().name}")
//                setText1("打开门$devreturn  mHandle$mHhandle")
//            }
        }
        tvn_open2_aways.setOnClickListener {
            //打开1
            var devreturn = DevReturn()
//            testAction {
//                println( "执行线程名称${Thread.currentThread().name}")
            DriverManagers.instance.driver_GateMachine.openGateLeftAways(devreturn)
//                devreturn
//            }.subscribe {
//                println( "结果线程名称${Thread.currentThread().name}")
//                setText1("打开门$devreturn  mHandle$mHhandle")
//            }
        }
        tvn_open2_right.setOnClickListener {
            //打开1
            var devreturn = DevReturn()
//            testAction {
//                println( "执行线程名称${Thread.currentThread().name}")
            DriverManagers.instance.driver_GateMachine.openGateRightOnce(devreturn)
//                devreturn
//            }.subscribe {
//                println( "结果线程名称${Thread.currentThread().name}")
//                setText1("打开门$devreturn  mHandle$mHhandle")
//            }
        }

        tvn_open2_right_aways.setOnClickListener {
            //打开1
            var devreturn = DevReturn()
//            testAction {
//                println( "执行线程名称${Thread.currentThread().name}")
            DriverManagers.instance.driver_GateMachine.openGateRightAways(devreturn)
//                devreturn
//            }.subscribe {
//                println( "结果线程名称${Thread.currentThread().name}")
//                setText1("打开门$devreturn  mHandle$mHhandle")
//            }
        }



        tvn_close2.setOnClickListener {
            //关闭1


            var devreturn = DevReturn()
//            DriverManagers.instance.driver_GateMachine.closeGate(mHhandle, devreturn)
//            setText1("关闭门$devreturn")

//            testAction {
            DriverManagers.instance.driver_GateMachine.closeGate(devreturn)
//                devreturn
//            }.subscribe {
//                setText1("关闭门$devreturn")
//            }


        }
        tvn_passed2.setOnClickListener {

            var passageNum = TJZNGateDev_Passage_Num()
            var devreturn = DevReturn()

            DriverManagers.instance.driver_GateMachine.getPassageNum(passageNum, devreturn)
            setText1("通过人数$passageNum \n操作返回$devreturn")

            testActionAny {
                DriverManagers.instance.driver_GateMachine.getPassageNum(passageNum, devreturn)
                passageNum
            }.subscribe {
                setText1("通过人数${passageNum as TJZNGateDev_Passage_Num}  \n操作返回$devreturn")
            }


        }
        tvn_init2.setOnClickListener {

            var devreturn = DevReturn()
//            DriverManagers.instance.driver_GateMachine.setConfigFileLoadDir(
//                Contants.CONFIG_FILE_PATH
//            )
//            DriverManagers.instance.driver_GateMachine.setDriverLogDir(
//                Contants.CONFIG_FILE_PATH
//            )
//
//
//             mHhandle = DriverManagers.instance.driver_GateMachine.openLogicDevice(DriverManagers.GATEMACHINE_TYPE_M810)
//            DriverManagers.instance.driver_GateMachine.setCommPara(mHhandle, devreturn)
//            DriverManagers.instance.driver_GateMachine.init(mHhandle, devreturn)

//            mHhandle = DriverManagers.instance.driver_GateMachine.openLogicDevice(DriverManagers.GATEMACHINE_TYPE_M810,Contants.CONFIG_FILE_PATH,Contants.CONFIG_FILE_PATH)
//            setText1("初始化$devreturn")

            testActionAny {
                DriverManagers.instance.driver_GateMachine.iGateMachineActionCallBack = this
                mHhandle = DriverManagers.instance.driver_GateMachine.openLogicDevice(
                    DriverManagers.GATEMACHINE_TYPE_M820, Contants.CONFIG_FILE_PATH, Contants.CONFIG_FILE_PATH
                )
                devreturn
            }.subscribe {
                setText1("初始化$devreturn")
            }

        }

    }

    fun testAction(action: () -> DevReturn): Observable<DevReturn> {
        return Observable.create<DevReturn> { it.onNext(action()) }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    //    fun test2Action (action:()->DevReturn,action1:()->DevReturn):Observable<DevReturn>{
//        return Observable.zip(Observable.create {  })<DevReturn> {
//            it.onNext(action())
//        }.observeOn(Schedulers.io())
//            .subscribeOn(AndroidSchedulers.mainThread())
//    }
    fun testActionAny(action: () -> Any): Observable<Any> {
        return Observable.create<Any> { it.onNext(action()) }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

}

