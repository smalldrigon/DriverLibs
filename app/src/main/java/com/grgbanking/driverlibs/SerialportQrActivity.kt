package com.grgbanking.driverlibs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.grgbanking.huitong.driver_libs.DriverManagers
import com.grgbanking.huitong.driver_libs.interfaces.IConnectType
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_ScanGun
import com.grgbanking.huitong.driver_libs.scan_qr_code.Driver_ScanQrCodeImpl_serialport
import com.kongqw.serialportlibrary.listener.CustomOnSerialPortDataListener
import kotlinx.android.synthetic.main.layout_serialport_activity.*

class SerialportQrActivity :AppCompatActivity(){
    var mIDriver_ScanGun :IDriver_ScanGun ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_serialport_activity)
            init()
        DriverManagers.Builder().setScanGun(IConnectType.SERIABLE_PORT).build()
    }
        fun init(){
            val map = mutableMapOf<String,String>(
                "dev/ttyS3" to "9600",
                "dev/ttyS1" to "115200"
            )
            btn_open.setOnClickListener {
                        mIDriver_ScanGun = DriverManagers.instance.driver_ScanGun
                (mIDriver_ScanGun as Driver_ScanQrCodeImpl_serialport).startScan(map,
                    object :CustomOnSerialPortDataListener{
                        override fun onDataReceived(bytes: ByteArray?) {
                            bytes?.let { it1 -> String(it1) }?.let { it2 -> setres("第一串口$it2") }
                        }

                        override fun onDataSent(bytes: ByteArray?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    },
                    object :CustomOnSerialPortDataListener{
                        override fun onDataReceived(bytes: ByteArray?) {
                            bytes?.let { it1 -> String(it1) }?.let { it2 -> setres("第二串口$it2") }                        }

                        override fun onDataSent(bytes: ByteArray?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    }
                    )
            }
            btn_pause.setOnClickListener {
                mIDriver_ScanGun?.pauseReadQrCode()
            }
            btn_restart.setOnClickListener {
                mIDriver_ScanGun?.restartReadQrCode()
            }

        }
    fun setres(ss:String){
        runOnUiThread {

        tv_res.setText(ss)
        }
    }
}