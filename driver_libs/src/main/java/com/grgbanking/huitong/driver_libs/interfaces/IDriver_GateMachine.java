package com.grgbanking.huitong.driver_libs.interfaces;

import com.grgbanking.huitong.driver_libs.gate_machine.DevReturn;
import com.grgbanking.huitong.driver_libs.gate_machine.TJZNGateDev_Passage_Num;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/4 16:03
 * Email: 904430803@qq.com
 * Description: 闸机接口
 */
public interface IDriver_GateMachine extends IDriverBaseInterface {
  /**
   * @method
   * @description 设置日志路径
   * @date: 2019/9/12 15:08
   * @author: gongxiaobiao
   * @param
   * @return
   */
  public int setDriverLogDir(String logPath);

  /**
   * @method
   * @description 设置配置文件路径
   * @date: 2019/9/12 15:08
   * @author: gongxiaobiao
   * @param
   * @return
   */
  public int setConfigFileLoadDir(String configFilePath);

  /**
   * @method
   * @description 打开逻辑设备
   * @date: 2019/9/10 14:39
   * @author: gongxiaobiao
   * @param p_pcLogicDevName 设备名称
   * @return  返回操作句柄
   */
  public int openLogicDevice(String p_pcLogicDevName);
  /**
   * @method
   * @description 打开逻辑设备
   * @date: 2019/9/10 14:39
   * @author: gongxiaobiao
   * @param p_pcLogicDevName 设备名称
   *                  configFilePath 配置文件路径
   *                        logFilePath  日志存放路径
   * @return  返回操作句柄
   */
  public int openLogicDevice(String p_pcLogicDevName,String configFilePath,String logFilePath);
  /**
   * @method
   * @description 关闭逻辑设备
   * @date: 2019/9/10 14:40
   * @author: gongxiaobiao
   * @param  p_pcLogicDevName 设备名称
   * @return
   */
  public void closeLogicDevice(int p_pcLogicDevName);

  /**
   * @method
   * @description 打开通信端口
   * @date: 2019/9/10 14:41
   * @author: gongxiaobiao
   * @param devReturn 端口名称
   * @return
   */
  public int setCommPara(int devHandler, DevReturn devReturn);

/**
 * @method
 * @description 初始化
 * @date: 2019/9/10 15:48
 * @author: gongxiaobiao
 * @param
 * @return
 */

  int  init(int p_hDevHandle,DevReturn  devReturn);
  /**
   * @method
   * @description 左开闸门单次
   * @date: 2019/9/10 15:48
   * @author: gongxiaobiao
   * @param
   * @return
   */
  int  openGateLeftOnce(DevReturn devReturn);
  /**
   * @method
   * @description 左开闸门常开
   * @date: 2019/9/10 15:48
   * @author: gongxiaobiao
   * @param
   * @return
   */
  int  openGateLeftAways(DevReturn devReturn);
  /**
   * @method
   * @description 右开闸门单次
   * @date: 2019/9/10 15:48
   * @author: gongxiaobiao
   * @param
   * @return
   */
  int  openGateRightAways(DevReturn devReturn);
  /**
   * @method
   * @description 右开闸门单次
   * @date: 2019/9/10 15:48
   * @author: gongxiaobiao
   * @param
   * @return
   */
  int  openGateRightOnce(DevReturn devReturn);

//  /**
//   * @method
//   * @description 打开闸门
//   * @date: 2019/9/10 15:48
//   * @author: gongxiaobiao
//   * @param
//   * @return
//   */
//  int  openGate(int p_hDevHandle, int dir, int mode, DevReturn devReturn);


  /**
   * @method
   * @description 关闭闸门
   * @date: 2019/9/10 15:48
   * @author: gongxiaobiao
   * @param
   * @return
   */
  int  closeGate(DevReturn devReturn);
  int  closeGate(int dir,DevReturn devReturn);


  /**
   * @method
   * @description 获取通行数量
   * @date: 2019/9/10 15:49
   * @author: gongxiaobiao
   * @param
   * @return
   */
  int  getPassageNum( TJZNGateDev_Passage_Num p_psPassageNum, DevReturn devReturn);

 }
