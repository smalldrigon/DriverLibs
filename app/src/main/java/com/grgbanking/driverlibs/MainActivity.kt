package com.grgbanking.driverlibs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import com.example.hardwaredemo.Contants
import com.grgbanking.baselibrary.util.LogUtil
import com.grgbanking.baselibrary.util.SystemUtils
import com.grgbanking.driverlibs.util.BitmapUtil
import com.grgbanking.huitong.driver_libs.DriverManagers
import com.grgbanking.huitong.driver_libs.database.DatabaseInstance
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_CardReader
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_FingerPrints
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_ScanGun
import com.grgbanking.huitong.driver_libs.scan_qr_code.Driver_ScanQrCodeImpl_serialport
import com.grgbanking.huitong.driver_libs.scan_qr_code.Driver_ScanQrCodeImpl_usb
import com.grgbanking.huitong.driver_libs.util.FileUtil
import com.grgbanking.huitong.driver_libs.util.InstallSilent
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main1.*
import java.io.*

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
//        println(SystemUtils.getIMEI(this))
        openWifi()
    }

    fun openWifi() {
        val managers = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        managers.setWifiEnabled(true)

    }

    fun setText(str: String) {
        runOnUiThread {
            tv_resulr.text = "结果$str"
        }
    }


    fun initData() {

//        mIDriver_CardReader = Driver_DeCardReaderImpl(this)
        Handler().postDelayed({
            mIDriver_CardReader?.open(applicationContext)
        }, 1000L)
//        mIDriver_FingerPrints = Driver_FingerRecongnitionImpl()
        mIDriver_FingerPrints?.open(this)
        mIDriver_ScanGun =
            Driver_ScanQrCodeImpl_serialport()

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

    var inputStr: StringBuilder = StringBuilder()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        println("${event}")
        Log.i("gong", "设备name${event!!.device.name}")
        Log.i("gong", "设备  productId${event!!.device.productId}")
        Log.i("gong", "设备 vendorId ${event!!.device.vendorId}")
//        inputStr.append(event!!.device.name)
//        setText("输入事件$inputStr.toString()")

//        (mIDriver_ScanGun as Driver_ScanQrCodeImpl_serialport)?.startScan()

        mIDriver_ScanGun?.startScan(
            event
        ) { usbInputType, barcode ->
            when (usbInputType) {
                Driver_ScanQrCodeImpl_usb.INPUT_RQCODE -> {
                    Log.i("gong", "扫码：$barcode")
                    if (barcode != null) setText("扫码：$barcode")
                }
                Driver_ScanQrCodeImpl_usb.INPUT_ICCARD -> {
                    if (barcode != null) setText("读ic卡：$barcode")
                }
                Driver_ScanQrCodeImpl_usb.UNDEFINE_INPUT_TYPE -> {
                    setText("未定义输入类型：$barcode")
                }
            }
        }
        return false
    }

    var receiver: BroadcastReceiver? = null
    fun initView() {
         object :Thread(){
            override fun run() {
                super.run()

                try {
                    val p = Runtime.getRuntime().exec("su")
                    val os = DataOutputStream(p.outputStream)
                    /*
                    Log.d(TAG, "BYear: " + c.get(Calendar.YEAR));
                    Log.d(TAG, "BMonth: " + c.get(Calendar.MONTH) + 1);
                    Log.d(TAG, "BDay: " + c.get(Calendar.DAY_OF_MONTH));
                    Log.d(TAG, "BHour: " + c.get(Calendar.HOUR));
                    Log.d(TAG, "BMinute: " + c.get(Calendar.MINUTE));
                    Log.d(TAG, "BSecond: " + c.get(Calendar.SECOND));
                    */
                    /*
                    Log.d(TAG, "AYear: " + c.get(Calendar.YEAR));
                    Log.d(TAG, "AMonth: " + c.get(Calendar.MONTH) + 1);
                    Log.d(TAG, "ADay: " + c.get(Calendar.DAY_OF_MONTH));
                    Log.d(TAG, "AHour: " + c.get(Calendar.HOUR));
                    Log.d(TAG, "AMinute: " + c.get(Calendar.MINUTE));
                    Log.d(TAG, "ASecond: " + c.get(Calendar.SECOND));
                    */



                    val command1 = "date -s ${StringBuilder("20191212110909").insert(8,".")}" +
                            "\n"
                    os.writeBytes(command1)
                    os.flush()
                    os.writeBytes("exit\n")
                    os.flush()
                    p.waitFor()

                } catch (e: IOException) {
                    e.printStackTrace()

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
//        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
//        var broadIntent = Intent("com.grgbanking.driverlibs")
//        sendBroadcast(broadIntent)

//        localBroadcastManager.sendBroadcast(broadIntent)


//        receiver = MyBroadCast()
//        val filter = IntentFilter()
//        filter.addAction("com.grgbanking.driverlibs")
//        localBroadcastManager.registerReceiver(receiver!!, filter)
//        Log.i("gong", "执行")
//        localBroadcastManager.sendBroadcast(broadIntent)



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
            saveDate()
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
            iv_result.setImageBitmap(BitmapUtil.createBitmap(list))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_sockettest.setOnClickListener {

            startActivity(Intent(this@MainActivity, GreenDaoTestActivity::class.java))

//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }

        btn_mqtt.setOnClickListener {


            startActivity(Intent(this@MainActivity, MQTTTestActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_gate_tj_auto.setOnClickListener {

            startActivity(Intent(this@MainActivity, ThreeRollerAutoGatesActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_fingertest.setOnClickListener {

            startActivity(Intent(this@MainActivity, FingerTestActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_readfile.setOnClickListener {

            startActivity(Intent(this@MainActivity, ReadTxtFileActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_twoscreentest.setOnClickListener {

            startActivity(Intent(this@MainActivity, TwoScreenTestActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_openwebview.setOnClickListener {
            val str = FileUtil.readFile(
                File(
                    "${Environment.getExternalStorageDirectory()}${File.separator}source.txt"
                )
            )
            var arrayList = arrayListOf<String>()
            var res = str!!.split(",").toList().map {
                arrayList.add(it)
            }
            var intent = Intent(this, WebviewTestActivity::class.java)
            intent.putStringArrayListExtra("data", arrayList)
            startActivity(intent)

        }
        btn_jnitest.setOnClickListener {

            startActivity(Intent(this@MainActivity, NativeActivirt::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_MeterialDesign.setOnClickListener {

            startActivity(Intent(this@MainActivity, MeterialDesignActivity::class.java))
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }
        btn_execcommand.setOnClickListener {

            InstallSilent.execRootCommand(listOf("cd /dev","ls -l"),false,true)
//            iv_result.setImageBitmap(BitmapUtil.createEmptyBitmap(400,400))
        }

    }

    var list = arrayListOf<String>("1233")
    override fun onDestroy() {
        super.onDestroy()
        DriverManagers.Builder().setIDatabase(DatabaseInstance.getInstance(this, "")).build()


    }
        var ss:Notification? = null

    fun saveDate() {
        Log.i("gong", "saveDate")
        var manager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val fileOutputStream = openFileOutput("ss", Context.MODE_PRIVATE)
//        fileOutputStream.write(1)
        val whrite = BufferedWriter(OutputStreamWriter(fileOutputStream))
        whrite.write("ssss")
        whrite.flush()
        val uri = Uri.parse("")
        externalCacheDir
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            var  notifyChannel =   NotificationChannel("com.grgbanking.driverlibs",
                    "NOTIFICATION_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notifyChannel.setLightColor(Color.GREEN);
            notifyChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager.createNotificationChannel(notifyChannel);

             ss =  NotificationCompat.Builder(this, notifyChannel.id)
//            .setVibrate(longArrayOf(1, 1, 1, 1))
                .setSmallIcon(R.mipmap.ic_launcher)
//                 .setContentIntent(Pe)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("fadsfasd")
                .setContentText("dsddd")

                .build()
        } else {
            ss = NotificationCompat.Builder(this)
//            .setVibrate(longArrayOf(1, 1, 1, 1))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("fadsfasd")
                .setContentText("dsddd")
                .build()

        }


        manager.notify(1, ss)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_test, menu)
        return super.onCreateOptionsMenu(menu)

    }


}
