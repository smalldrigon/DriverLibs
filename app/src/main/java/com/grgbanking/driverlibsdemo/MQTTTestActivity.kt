package com.grgbanking.driverlibsdemo


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView


internal class MQTTTestActivity : AppCompatActivity(), IGetMessageCallBack {

    private var textView: TextView? = null
    private var button: Button? = null
    private var serviceConnection: MyServiceConnection? = null
    private var mqttService: MQTTService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<View>(R.id.text) as TextView
        button = findViewById<View>(R.id.button) as Button

        serviceConnection = MyServiceConnection()
        serviceConnection!!.setIGetMessageCallBack(this@MQTTTestActivity)

        val intent = Intent(this, MQTTService::class.java)

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)


        button!!.setOnClickListener { MQTTService.publish("测试一下子") }
    }

    override fun setMessage(message: String) {
        textView!!.text = message
        mqttService = serviceConnection!!.mqttService
         mqttService!!.toCreateNotification(message)
    }

    override fun onDestroy() {
        unbindService(serviceConnection)
        super.onDestroy()
    }

}
