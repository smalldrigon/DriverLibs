package com.grgbanking.huitong.driver_libs.interfaces;

import com.grgbanking.huitong.driver_libs.gate_machine.DevReturn;
import com.grgbanking.huitong.driver_libs.gate_machine.TJZNGateDev_Passage_Num;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/4 16:03
 * Email: 904430803@qq.com
 * Description: 闸机接口
 */
public abstract class IDriver_GateMachine implements IDriverBaseInterface {

    /**
     * @param
     * @return
     * @method
     * @description 设置通行模式
     * @date: 2019/9/11 8:46
     * @author: gongxiaobiao
     */
    public abstract int setMode(int p_hDevHandle, int mode, DevReturn devReturn);

    /**
     * @param
     * @return
     * @method
     * @description 设置日志路径
     * @date: 2019/9/12 15:08
     * @author: gongxiaobiao
     */
    public abstract int setDriverLogDir(String logPath);

    /**
     * @param
     * @return
     * @method
     * @description 设置配置文件路径
     * @date: 2019/9/12 15:08
     * @author: gongxiaobiao
     */
    public abstract int setConfigFileLoadDir(String configFilePath);

    /**
     * @param p_pcLogicDevName 设备名称
     * @return 返回操作句柄
     * @method
     * @description 打开逻辑设备
     * @date: 2019/9/10 14:39
     * @author: gongxiaobiao
     */
    public abstract int openLogicDevice(String p_pcLogicDevName);

    /**
     * @param p_pcLogicDevName 设备名称
     *                         configFilePath 配置文件路径
     *                         logFilePath  日志存放路径
     * @return 返回操作句柄
     * @method
     * @description 打开逻辑设备
     * @date: 2019/9/10 14:39
     * @author: gongxiaobiao
     */
    public abstract int openLogicDevice(String p_pcLogicDevName, String configFilePath, String logFilePath);

    /**
     * @param p_pcLogicDevName 设备名称
     * @return
     * @method
     * @description 关闭逻辑设备
     * @date: 2019/9/10 14:40
     * @author: gongxiaobiao
     */
    public abstract void closeLogicDevice(int p_pcLogicDevName);

    /**
     * @param devReturn 端口名称
     * @return
     * @method
     * @description 打开通信端口
     * @date: 2019/9/10 14:41
     * @author: gongxiaobiao
     */
    public abstract int setCommPara(int devHandler, DevReturn devReturn);

    /**
     * @param
     * @return
     * @method
     * @description 初始化
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     */

    public abstract int init(int p_hDevHandle, DevReturn devReturn);

    /**
     * @param
     * @return
     * @method
     * @description 左开闸门单次
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     */
    public abstract int openGateLeftOnce(DevReturn devReturn);

    /**
     * @param
     * @return
     * @method
     * @description 左开闸门常开
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     */
    public abstract int openGateLeftAways(DevReturn devReturn);

    /**
     * @param
     * @return
     * @method
     * @description 右开闸门单次
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     */
    public abstract int openGateRightAways(DevReturn devReturn);

    /**
     * @param
     * @return
     * @method
     * @description 右开闸门单次
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     */
    public abstract int openGateRightOnce(DevReturn devReturn);

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
     * @param
     * @return
     * @method
     * @description 关闭闸门
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     */
    public abstract int closeGate(DevReturn devReturn);

    public abstract int closeGate(int dir, DevReturn devReturn);


    /**
     * @param
     * @return
     * @method
     * @description 获取通行数量
     * @date: 2019/9/10 15:49
     * @author: gongxiaobiao
     */
    public abstract int getPassageNum(TJZNGateDev_Passage_Num p_psPassageNum, DevReturn devReturn);

    public IGateMachineActionCallBack getIGateMachineActionCallBack() {
        return mIGateMachineActionCallBack;
    }

    public void setIGateMachineActionCallBack(IGateMachineActionCallBack mIGateMachineActionCallBack) {
        this.mIGateMachineActionCallBack = mIGateMachineActionCallBack;
    }

    private IGateMachineActionCallBack mIGateMachineActionCallBack = null;


    /**
     * 设置超时时间  默认6S
     *
     * @param seconds 单位秒
     * @return
     */
    public abstract void  setTimeout(int p_hDevHandle,int seconds);



}
