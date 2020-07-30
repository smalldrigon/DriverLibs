package com.grgbanking.driverlibs

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder

class ServiceTestActivity : AppCompatActivity() {


    private val serviceConnection :ServiceConnection= object :ServiceConnection{


        override fun onServiceDisconnected(name: ComponentName?) {


        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_test)
        bindService(Intent(),serviceConnection, Context.BIND_AUTO_CREATE)
    }
}
