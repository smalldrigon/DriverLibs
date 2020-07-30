package com.grgbanking.driverlibs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadCast :BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
       if (intent!!.action=="com.hhh.hhh"){
           Log.i("gong","收到hhh")
       }else{
           Log.i("gong","收到${intent.action}")

       }

    }
}