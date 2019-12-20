package com.grgbanking.huitong.driver_libs.interfaces;

import com.grgbanking.huitong.driver_libs.gate_machine.DevReturn;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/4 16:03
 * Email: 904430803@qq.com
 * Description: 闸机接口
 */
public abstract class IDriver_GateMachine_TJZN extends IDriver_GateMachine {
    /**
     * @param
     * @return
     * @method
     * @description 设置通行模式
     * @date: 2019/9/11 8:46
     * @author: gongxiaobiao
     */
    public abstract int setMode(int p_hDevHandle, int mode, DevReturn devReturn);


}
