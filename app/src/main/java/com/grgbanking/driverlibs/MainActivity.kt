package com.grgbanking.driverlibs

import android.app.NativeActivity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import com.grgbanking.baselibrary.util.SystemUtils
import com.grgbanking.driverlibs.util.BitmapUtil
import com.grgbanking.huitong.driver_libs.DriverManagers

import com.grgbanking.huitong.driver_libs.card_reader.Driver_DeCardReaderImpl
import com.grgbanking.huitong.driver_libs.database.DatabaseInstance
import com.grgbanking.huitong.driver_libs.fingerprints.Driver_FingerRecongnitionImpl
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_CardReader
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_FingerPrints
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_ScanGun
import com.grgbanking.huitong.driver_libs.scan_qr_code.Driver_ScanQrCodeImpl
import com.grgbanking.huitong.driver_libs.util.FileUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main1.*
import java.io.File
import java.lang.StringBuilder
 import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var disposedCopyfIle: Disposable? = null
    var mIDriver_CardReader: IDriver_CardReader? = null
    var mIDriver_FingerPrints: IDriver_FingerPrints? = null
    var mIDriver_ScanGun: IDriver_ScanGun? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        initData()
        println("----------")
        println(SystemUtils.getDeviceId(this))
//        println(SystemUtils.getDeviceId1(this))
        println(SystemUtils.getIMEI(this))
        openWifi()
    }

    fun openWifi(){
      val managers =  this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

                    managers.setWifiEnabled(true)

    }

    fun setText(str: String) {
       runOnUiThread{
           tv_resulr.text = "结果$str"
       }
    }




    fun initData() {

//        mIDriver_CardReader = Driver_DeCardReaderImpl(this)
        Handler().postDelayed({
            mIDriver_CardReader?.open(applicationContext)
        },1000L)
//        mIDriver_FingerPrints = Driver_FingerRecongnitionImpl()
        mIDriver_FingerPrints?.open(this)
        mIDriver_ScanGun = Driver_ScanQrCodeImpl()

//
//        DriverManagers.Builder().setContext(this)
//            .setFingerPrints(DriverManagers.FINGERPRINTS_TYPE_FPC1011)
//            .setScanGun(DriverManagers.SACNGUN_TYPE_ZD7100)
//            .setCardReader(DriverManagers.CARD_READER_TYPE_T10)
//            .setGateMachine(DriverManagers.GATEMACHINE_TYPE_M810)
//             .build()







//        try {
//            mIDriver_CardReader?.open(this)
//        }catch (e:Exception){
//            Toast.makeText(this,"读卡器打开失败",Toast.LENGTH_LONG).show()
//        }
//        try {
//            mIDriver_FingerPrints?.open(this)
//        }catch (e:Exception){
//            Toast.makeText(this,"指纹仪打开失败",Toast.LENGTH_LONG).show()
//        }
//        try {
//            mIDriver_ScanGun?.open(this)
//        }catch (e:Exception){
//            Toast.makeText(this,"扫码枪打开失败",Toast.LENGTH_LONG).show()
//        }

        initView()
    }
var inputStr:StringBuilder = StringBuilder()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        println("${event}")
        Log.i("gong", "设备name${event!!.device.name}")
        Log.i("gong", "设备  productId${event!!.device.productId}")
        Log.i("gong", "设备 vendorId ${event!!.device.vendorId}")
//        inputStr.append(event!!.device.name)
//        setText("输入事件$inputStr.toString()")
          mIDriver_ScanGun?.startScan(event
          ) { usbInputType, barcode ->
              when(usbInputType){
                  Driver_ScanQrCodeImpl.INPUT_RQCODE ->{
                      Log.i("gong", "扫码：$barcode")
                      if (barcode != null) setText("扫码：$barcode")
                  }
                  Driver_ScanQrCodeImpl.INPUT_ICCARD->{
                      if (barcode != null) setText("读ic卡：$barcode")
                  }
                  Driver_ScanQrCodeImpl.UNDEFINE_INPUT_TYPE->{
                        setText("未定义输入类型：$barcode")
                  }
              }
          }
        return false
    }

    fun initView() {

        btn_readIdCard.setOnClickListener {
            mIDriver_CardReader?.open(this)
            mIDriver_CardReader?.readIdCard(false, 2000L) {

                if (it != null) {
                    println(it.name)
                    setText("读取身份证：${it.name}")
                }
            }
        }
        btn_readfinger.setOnClickListener {
            mIDriver_FingerPrints?.getFeature(
                false,
                1000L,
                object : IDriver_FingerPrints.FingerCallBack {
                    override fun fingerResultFeature(feature: ByteArray?) {
                        if (feature != null) setText("识别指纹：${feature.toString()}")
                    }

                    override fun fingerResultBitmap(bitmap: Bitmap?) {
                     }

                })

        }

        btn_gatethree.setOnClickListener {
            startActivity(Intent(this@MainActivity, ThreeRollerAutoGatesActivity::class.java))
        }
        btn_gatebaizha.setOnClickListener {
            startActivity(Intent(this@MainActivity, SluiceGatesActivity::class.java))
        }
        btn_gatebaizha_aoyi.setOnClickListener {
            startActivity(Intent(this@MainActivity, SluiceGatesAoYiActivity::class.java))
        }
        btn_getframe.setOnClickListener {
//            startActivity(Intent(this@MainActivityRecord, MainMenuActivity::class.java))
//            startActivity(Intent(this@MainActivityRecord, GetFrameActivity::class.java))
//            startActivity(Intent(this@MainActivity, MainActivity2::class.java))
//            startActivity(Intent(this@MainActivity2, DemoMainActivity::class.java))
        }

        btn_createImg.setOnClickListener {
            list.add("21")
            iv_result.setImageBitmap(BitmapUtil.createBitmap( list))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_sockettest.setOnClickListener {

            startActivity(Intent(this@MainActivity,GreenDaoTestActivity::class.java))

//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }

        btn_mqtt.setOnClickListener {

            startActivity(Intent(this@MainActivity,MQTTTestActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_gate_tj_auto.setOnClickListener {

            startActivity(Intent(this@MainActivity,ThreeRollerAutoGatesActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_fingertest.setOnClickListener {

            startActivity(Intent(this@MainActivity,FingerTestActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_readfile.setOnClickListener {

            startActivity(Intent(this@MainActivity,ReadTxtFileActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_twoscreentest.setOnClickListener {

            startActivity(Intent(this@MainActivity,TwoScreenTestActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_openwebview.setOnClickListener {
            val str =   FileUtil.readFile(File(
                "${Environment.getExternalStorageDirectory()}${File.separator}source.txt"
            ))
            var arrayList  = arrayListOf<String>()
            var res = str!!.split(",").toList().map {
                    arrayList.add(it)
            }
            var intent = Intent(this,WebviewTestActivity::class.java)
            intent.putStringArrayListExtra("data", arrayList)
            startActivity(intent)

        }
        btn_jnitest.setOnClickListener {

            startActivity(Intent(this@MainActivity,NativeActivirt::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
    }
    var list = arrayListOf<String>("1233")
    override fun onDestroy() {
        super.onDestroy()
        DriverManagers.Builder().setIDatabase(DatabaseInstance.getInstance(this,"")).build()

    }


}
