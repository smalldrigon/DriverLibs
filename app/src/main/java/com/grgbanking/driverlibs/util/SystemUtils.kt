package com.grgbanking.baselibrary.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.telephony.TelephonyManager
import android.text.TextUtils
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*


/**
 * Author: gongxiaobiao
 * Date: on 2019/8/16 10:20
 * Email: 904430803@qq.com
 * Description: 系统工具类，获取系统相关信息，时间，分辨率等
 */

object SystemUtils {

    /**
     * @method
     * @description 根据参数格式返回当前系统时间
     * @date: 2019/8/16 10:22
     * @author: gongxiaobiao
     * @param
     * @return
     */
    fun getSystemTime(format: String): String {

        var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(Date())// new Date()为获取当前系统时间

    }

    //当前星期几
    fun getSystemWeekDay(): String {

        val today = Date()
        val c = Calendar.getInstance()
        c.time = today
        val weekday = c.get(Calendar.DAY_OF_WEEK)

        var res = ""
//        var  date =   Date();
//        var dateFm =   SimpleDateFormat("EEEE");
        when (weekday) {

            1 -> {
                res = "星期日"
            }
            2 -> {
                res = "星期一"
            }
            3 -> {
                res = "星期二"
            }
            4 -> {
                res = "星期三"
            }
            5 -> {
                res = "星期四"
            }
            6 -> {
                res = "星期五"
            }
            7 -> {
                res = "星期六"
            }
        }
        return res
    }


    /**
     * 产生三十二位随机数
     */
    fun getGuiRandom(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString().replace("-", "")
    }

    /**
     * 获取时间戳
     */
    fun getTimeStamp(): String {
        return System.currentTimeMillis().toString() + ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDeviceId(context: Context): String {

//    这个DEVICE_ID可以同通过下面的方法获取：
        if (ActivityCompat.checkSelfPermission(
                context,
                "Manifest.permission.READ_PHONE_STATE"
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            var tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var DEVICE_ID = tm.imei
            if (DEVICE_ID == null) DEVICE_ID = tm.meid
            return DEVICE_ID
        } else {
            return "00000000"
        }


    }


    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    fun copy(copyStr: String, context: Context): Boolean {
        try {
            //获取剪贴板管理器
            var cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 创建普通字符型ClipData
            var mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.primaryClip = mClipData;
            return true;
        } catch (e: Exception) {
            return false;
        }
    }

    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    fun getMacAddress(context: Context): String {
        var mac = "02:00:00:00:00:00"
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context)!!
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddress()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware()
        }
        return mac
    }

    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     * @return
     */
    private fun getMacFromHardware(): String {
        try {
            val all = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (nif.name != "wlan0") continue

                val macBytes = nif.getHardwareAddress() ?: return ""

                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b))
                }

                if (res1.length > 0) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "02:00:00:00:00:00"
    }


    /**
     * Android  6.0 之前（不包括6.0）
     * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     * @param context
     * @return
     */
    private fun getMacDefault(context: Context?): String? {
        var mac = "02:00:00:00:00:00"
        if (context == null) {
            return mac
        }

        val wifi = context.applicationContext
            .getSystemService(Context.WIFI_SERVICE) as WifiManager ?: return mac
        var info: WifiInfo? = null
        try {
            info = wifi.connectionInfo
        } catch (e: Exception) {
        }

        if (info == null) {
            return null
        }
        mac = info.macAddress
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH)
        }
        return mac
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     * @return
     */
    private fun getMacAddress(): String {
        var WifiAddress = "02:00:00:00:00:00"
        try {
            WifiAddress = BufferedReader(FileReader(File("/sys/class/net/wlan0/address"))).readLine()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return WifiAddress
    }


}