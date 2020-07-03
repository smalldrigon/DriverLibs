package com.grgbanking.driverlibs

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.layout_natibeactivity.*

/**
 * Author: gongxiaobiao
 * Date: on 2020/4/8 8:48
 * Email: 904430803@qq.com
 * Description:
 */
class NativeActivirt :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_natibeactivity)
        ss()
        button2.setOnClickListener {
            Observable.create<View> {
               println(Thread.currentThread().name)
                var t = TextView(this, null)
                var params:LinearLayout.LayoutParams = LinearLayout.LayoutParams(100,100
                )
                t.setBackgroundColor(0xff0000)
                t.layoutParams = params
                container.addView(t)


            }.subscribeOn(AndroidSchedulers.mainThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe {
                    it.text as TextView
//                    it.text = "1"
                println("11111111111")
            }

        }
    }
    fun  ss(){
       println("=======${nativeUtil().getnumber()}")
    }
}