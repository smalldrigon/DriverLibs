package com.grgbanking.huitong.driver_libs.interfaces;

import com.grgbanking.huitong.driver_libs.gate_machine.DevReturn;
import com.sun.jna.platform.win32.WinNT;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/4 16:03
 * Email: 904430803@qq.com
 * Description: 闸机接口
 */
public abstract class IDriver_GateMachine_M820 extends IDriver_GateMachine {
  /**
   * @method
   * @description 设置通行模式
   * @date: 2019/9/11 8:46
   * @author: gongxiaobiao
   * @param
   * @return
   */
  public abstract int setMode(int p_hDevHandle, int mode, DevReturn devReturn);

 public abstract

  int GetStatus(int p_hDevHandle, DevReturn devReturn);


}
