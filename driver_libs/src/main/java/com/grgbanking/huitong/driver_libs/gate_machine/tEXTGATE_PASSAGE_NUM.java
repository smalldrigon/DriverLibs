package com.grgbanking.huitong.driver_libs.gate_machine;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/10 16:57
 * Email: 904430803@qq.com
 * Description:
 */
public class tEXTGATE_PASSAGE_NUM extends Structure {
    public static class ByReference extends tEXTGATE_PASSAGE_NUM implements Structure.ByReference {
    }

    public static class ByValue extends tEXTGATE_PASSAGE_NUM implements Structure.ByValue {
    }

    tEXTGATE_NUM [] sEXTNum = (tEXTGATE_NUM [])new tEXTGATE_NUM().toArray(4);
    protected List getFieldOrder()
    {
        return Arrays.asList(new String[]{"sEXTNum"});
    }
}