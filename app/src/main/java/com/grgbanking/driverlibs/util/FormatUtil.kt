package com.grgbanking.baselibrary.util

import java.text.DecimalFormat

/**
 * Author: gongxiaobiao
 * Date: on 2019/8/16 16:27
 * Email: 904430803@qq.com
 * Description: 格式化字符串工具类
 */
object FormatUtil{
    fun subString(source:String,lenth:Int):String{

        if (source.isEmpty())return "-"
        if (source.length<=lenth){
            return source
        }else{
            return source.substring(0,lenth)
        }
    }

    /**
     * @method
     * @description 格式化数字，取小数点后几位
     * @date: 2019/8/17 13:44
     * @author: gongxiaobiao
     * @param   "#0.00"
     * @return
     */
    fun subNumber(source:String,type:String):String{
        return DecimalFormat(type).format(source.toDouble())
    }
}