package com.grgbanking.huitong.driver_libs.gate_machine_v1;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/10 14:55
 * Email: 904430803@qq.com
 * Description:
 */
public class MyDevReturn extends Structure {
    public static class ByReference extends MyDevReturn implements Structure.ByReference{
    }

    public static class ByValue extends MyDevReturn implements Structure.ByValue{
    }

   public int		iLogicCode;			// 逻辑错误码
    public int		iPhyCode;			// 物理错误码
    public  int		iHandle;			// 处理方法：0-不处理 1-初始化 2-重发命令
    public int		iType;				// 错误类型：0-警告 1-严重

    public  byte[]	acDevReturn = new byte[128];	// 硬件返回信息
    public   byte[]	acReserve = new byte[128];		// 保留信息
    @Override
    protected List getFieldOrder()
    {
        return Arrays.asList("iLogicCode","iPhyCode","iHandle","iType","acDevReturn","acReserve");

    }
}
