package com.grgbanking.driverlibs

import android.app.AlarmManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import kotlinx.android.synthetic.main.layout_meterial_design_activity.*

class MeterialDesignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meterial_design_activity)
//        setSupportActionBar(toolbar)
       val service =  getSystemService(Context.ALARM_SERVICE) as AlarmManager
        SystemClock.elapsedRealtime()
//        service.sete(AlarmManager.ela)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_test, menu)
        return super.onCreateOptionsMenu(menu)

    }
}
