package com.grgbanking.huitong.driver_libs.interfaces;

import com.grgbanking.huitong.driver_libs.gate_machine.TJZNGateDev_Passage_Num;

/**
 * Author: gongxiaobiao
 * Date: on 2019/12/18 15:03
 * Email: 904430803@qq.com
 * Description: 闸机过闸，开门，超时等回调方法
 */
public interface IGateMachineActionCallBack {
    //左开门结果
    public void openLeft(boolean res);

    //右开门结果
    public void openRight(boolean res);

    //左关门结果
    public void closeLeft(boolean res);

    //右关门结果
    public void closeRight(boolean res);

    //通过超时
    public void passLeftTimeout();

    public void passRightTimeout();

    public void passLeftSuccess();

    //
    public void passRightSuccess();
   // 获取通过人数对象
//    public TJZNGateDev_Passage_Num getPassgerNum();

}