package com.grgbanking.baselibrary.util

import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.Logger

/**
 * Author: gongxiaobiao
 * Date: on 2019/10/8 11:03
 * Email: 904430803@qq.com
 * Description: 封装logutil 依赖于 logger
 */
object LogUtil {
    fun initLogUtil(adapter: LogAdapter) {
        Logger.addLogAdapter(adapter)
    }


    fun e(name: String, vararg args: Any) {
        e("", name, args)
    }

    fun e(tag: String, name: String, vararg args: Any) {
        Logger.t(tag).e(getLineAndMethod() + name, args)
    }

    fun d(name: String, vararg args: Any) {
        d("", name, args)
    }

    fun d(tag: String, name: String, vararg args: Any) {
        Logger.t(tag).d("$name", args)
    }

    fun w(name: String, vararg args: Any) {
        w("", name, args)
    }

    fun w(tag: String, name: String, vararg args: Any) {
        Logger.t(tag).w(getLineAndMethod() + name, args)
    }

    fun v(name: String, vararg args: Any) {
        v("", name, args)
    }

    fun v(tag: String, name: String, vararg args: Any) {
        Logger.t(tag).v(getLineAndMethod() + name, args)
    }

    fun i(name: String, vararg args: Any) {
        i("", name, args)
    }

    fun i(tag: String, name: String, vararg args: Any) {
        Logger.t(tag).i(getLineAndMethod() + name, args)
    }

    fun wtf(name: String, vararg args: Any) {
        wtf("", name, args)
    }

    fun wtf(tag: String, name: String, vararg args: Any) {
        Logger.t(tag).wtf(getLineAndMethod() + name, args)
    }
    fun json(tag: String, name: String,   args: String){
        Logger.t(tag).json("$name$args")
    }
    fun json(name: String,   args: String){
         json("",name,args)
    }
    fun xml(tag: String, name: String,   args: String){
        Logger.t(tag).xml("$name$args")
    }
    fun xml(name: String,   args: String){
        xml("",name,args)
    }
    fun getLineAndMethod(): String {
        var s = Throwable().getStackTrace()[3];
        var str = s.getFileName() + "--" + s.getMethodName() + "第" + s.getLineNumber() + "行"
        return "$str"
    }
}