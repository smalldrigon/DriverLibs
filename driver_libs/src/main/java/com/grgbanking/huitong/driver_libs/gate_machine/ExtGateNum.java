package com.grgbanking.huitong.driver_libs.gate_machine;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/10 15:05
 * Email: 904430803@qq.com
 * Description:
 */
public class ExtGateNum extends Structure {
    public static class ByReference extends ExtGateNum implements Structure.ByReference {
    }

    public static class ByValue extends ExtGateNum implements Structure.ByValue {
    }

    int uiAuthPassNum;	// 给了放行但未通过的累计次数
    int uiPassNum;		// 通过的累计次数
    int uiTimeoutNum;	// 超时的累计次数
    int []auiRsv = new int[4];
    protected List getFieldOrder()
    {
        return Arrays.asList(new String[]{"uiAuthPassNum","uiPassNum","uiTimeoutNum","auiRsv"});
    }
}
