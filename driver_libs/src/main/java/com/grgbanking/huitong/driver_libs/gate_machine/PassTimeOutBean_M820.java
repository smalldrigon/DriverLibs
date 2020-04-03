package com.grgbanking.huitong.driver_libs.gate_machine;

/**
 * Author: gongxiaobiao
 * Date: on 2019/12/20 9:40
 * Email: 904430803@qq.com
 * Description:
 */
public class PassTimeOutBean_M820 extends PassTimeOutBean{
    private int openLeftTimes = -1;
    private int openRightTimes = -1;

    public PassTimeOutBean_M820(int openLeftTimes, int openRightTimes) {
        this.openLeftTimes = openLeftTimes;
        this.openRightTimes = openRightTimes;
    }

    public PassTimeOutBean_M820(){

    }
    public int getOpenLeftTimes() {
        return openLeftTimes;
    }

    public void setOpenLeftTimes(int openLeftTimes) {
        System.out.println("setOpenLeftTimes:"+openLeftTimes);
        this.openLeftTimes = openLeftTimes;
    }

    public int getOpenRightTimes() {
        return openRightTimes;
    }

    public void setOpenRightTimes(int openRightTimes) {
        System.out.println("setOpenRightTimes:"+openRightTimes);

        this.openRightTimes = openRightTimes;
    }
}