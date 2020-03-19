package com.grgbanking.driverlibs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.grgbanking.huitong.driver_libs.bean.LeftPass
import com.grgbanking.huitong.driver_libs.bean.LeftPassDao
import com.grgbanking.huitong.driver_libs.database.DatabaseInstance
import com.grgbanking.huitong.driver_libs.database.EntyType
import kotlinx.android.synthetic.main.layout_greendaotest_activity.*
import java.text.SimpleDateFormat
import java.util.*

class GreenDaoTestActivity : AppCompatActivity() {
    var times = 3;
    var mInstance: DatabaseInstance? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_greendaotest_activity)
        init()
    }


    fun init() {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val format1 = SimpleDateFormat("yyyy-MM-dd")

        mInstance = DatabaseInstance.getInstance(this, "")
        btn_add.setOnClickListener {
//            println(format1.parse(Date().toString()))

            if (times >= 0) {
                mInstance!!.insert(EntyType.LEFTPASS, LeftPass("$times", null, ("2020-03-18 00:00:01")))
                times--
            } else {
                times--
                mInstance!!.insert(EntyType.LEFTPASS, LeftPass("$times", null, Date().toString()))
            }

        }

        btn_querrytoday.setOnClickListener {
            val res = mInstance!!.countToday(EntyType.LEFTPASS)

            setResult(res)
        }

        btn_querrytotal.setOnClickListener {
            val res = mInstance!!.countTotal(EntyType.LEFTPASS)
            (mInstance!!.querry(EntyType.LEFTPASS) as List<LeftPass>).forEach {
                print("打印时间：")
                println(it.timeStamp.toString())
            }
            setResult(res)
        }
    }

    fun setResult(str: Long) {
        tv_result.text = str.toString()
    }

}
