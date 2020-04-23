package com.grgbanking.driverlibs

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gdmcmc.simplecharge.base.BaseApplication
import com.grgbanking.huitong.driver_libs.gate_machine.DevReturn
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_readtxtfile_activity.*
import kotlinx.android.synthetic.main.layout_threerollergates_activity.*
import java.io.*


class ReadTxtFileActivity : AppCompatActivity() {


    var disposedCopyfIle: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_readtxtfile_activity)
        initView()
    }

    var mHhandle: Int = 0

    fun setText1(str: String) {

        runOnUiThread {
            tv_result.text = "结果:$str"
        }
    }

    private fun initView() {
        btn_readtxt.setOnClickListener {
          val str =   ReadTxtFile(Environment.getExternalStorageDirectory().canonicalPath + "/"
                    + BaseApplication.context.getResources().getString(R.string.app_name) + "/test1.txt")
            val res = str!!.split(",").toList()
        setText1(res.toString())
            System.out.println(res.contains("1235"))
            System.out.println(res.contains("1234"))
        }


    }

    fun testAction(action: () -> DevReturn): Observable<DevReturn> {
        return Observable.create<DevReturn> { it.onNext(action()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
//    fun test2Action (action:()->DevReturn,action1:()->DevReturn):Observable<DevReturn>{
//        return Observable.zip(Observable.create {  })<DevReturn> {
//            it.onNext(action())
//        }.observeOn(Schedulers.io())
//            .subscribeOn(AndroidSchedulers.mainThread())
//    }


    fun ReadTxtFile(strFilePath: String): String? {
        var result: String? = ""
        val fileName = File("$strFilePath")
        var fileReader: FileReader? = null
        var bufferedReader: BufferedReader? = null
        try {
            fileReader = FileReader(fileName)
            bufferedReader = BufferedReader(fileReader)
            try {
                var read: String? = null
                while ({ read = bufferedReader.readLine();read }() != null) {
                    result = result + read + ","
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close()
            }
            if (fileReader != null) {
                fileReader.close()
            }
        }
//        println("读取出来的文件内容是：\r\n$result")
        return result
    }


    fun testActionAny(action: () -> Any): Observable<Any> {
        return Observable.create<Any> { it.onNext(action()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}
