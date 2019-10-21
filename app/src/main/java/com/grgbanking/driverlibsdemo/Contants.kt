package com.example.hardwaredemo

import android.os.Environment

object Contants{



    val BASE_URL: String= "http://120.77.93.85:1509"
    val SIMILARITY: Float= 0.8f
    val CHECKTICKET_SUCCESS: Int= 0
    val CHECKTICKET_ERROR: Int= -1
    const  val CHECKEDTICKETLIST = "checkedTicketslist"

    public   var CONFIG_FILE_PATH=  Environment.getExternalStorageDirectory().absolutePath+"/app/GRGConfig1"



    val SCAN_INTERVAL:Long = 1000L*2//扫码枪，读卡器，指纹的读取时间间隔
const val data  = "{\n" +
        "    \"data\": {\n" +
        "        \"guid\": \"6e8e2fcd-2c95-dfdf-57f7-8173888dd06b\",\n" +
        "        \"serialNumber\": \"XS1400201484\",\n" +
        "        \"ticketName\": \"儿童票(1-1.2米)\",\n" +
        "        \"singleTimes\": 1,\n" +
        "        \"remainTimes\": 92,\n" +
        "        \"textMsg\": \"请刷放行卡\",\n" +
        "        \"voiceFile\": \"请刷放行卡.wav\",\n" +
        "        \"state\": 5\n" +
        "    },\n" +
        "    \"error\": {\n" +
        "        \"code\": 0,\n" +
        "        \"message\": \"success\"\n" +
        "    }\n" +
        "}\n"
}