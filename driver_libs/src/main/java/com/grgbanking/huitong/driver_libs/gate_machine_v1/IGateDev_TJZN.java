package com.grgbanking.huitong.driver_libs.gate_machine_v1;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/10 15:54
 * Email: 904430803@qq.com
 * Description:
 */
public interface IGateDev_TJZN extends Library {
    public IGateDev_TJZN mInstance = Native.loadLibrary("TJZNGateDev", IGateDev_TJZN.class);

    /**
     * @method
     * @description 设置日主输出路径
     * @date: 2019/9/12 15:10
     * @author: gongxiaobiao
     * @param
     * @return
     */
    int setDriverLogDir(String p_LogDir);

    /**
     * @method
     * @description 设置配置文件路径
     * @date: 2019/9/12 15:09
     * @author: gongxiaobiao
     * @param
     * @return
     */
    int SetConfigFileLoadDir(String p_CfgDir);

    /**
     * @method
     * @description 打开设备
     * @date: 2019/9/12 11:36
     * @author: gongxiaobiao
     * @param
     * @return
     */
    int   hOpenLogicDevice(String p_pcLogicDevName);
    /**
     * @method
     * @description 关闭设备
     * @date: 2019/9/12 11:36
     * @author: gongxiaobiao
     * @param
     * @return
     */

    void vCloseLogicDevice(int p_hDevHandle);


    /**
     * @method
     * @description 初始化
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     * @param
     * @return
     */

    int  iInit(int p_hDevHandle, MyDevReturn[] p_psStatus);
    /**
     * @method
     * @description 打开闸门
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     * @param
     * @return
     */
    int  iOpenGate(int p_hDevHandle, int dir, MyDevReturn[] devReturn);
    /**
     * @method
     * @description 关闭闸门
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     * @param
     * @return
     */
    int  iCloseGate(int p_hDevHandle, MyDevReturn[] p_psStatus);
    /**
     * @method
     * @description 设置闸机模式
     * @date: 2019/9/10 15:48
     * @author: gongxiaobiao
     * @param
     * @return
     */
    int iSetMode(int p_hDevHandle, int mode, MyDevReturn[] p_psStatus);
    /**
     * @method
     * @description 获取通行数量
     * @date: 2019/9/10 15:49
     * @author: gongxiaobiao
     * @param
     * @return
     */
    int  iGetPassageNum(int p_hDevHandle, TJZNGateDev_Passage_Num.ByReference p_psPassageNum, MyDevReturn[] p_psStatus);
    int   iSetCommPara(int p_hDevHandle, MyDevReturn[]p_psStatus);
}
