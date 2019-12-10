package com.grgbanking.huitong.driver_libs.gate_machine;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/10 15:47
 * Email: 904430803@qq.com
 * Description:
 */
public class Ext_Passage_Num extends Structure {
    public static class ByReference extends Ext_Passage_Num implements Structure.ByReference {
    }

    public static class ByValue extends Ext_Passage_Num implements Structure.ByValue {
    }

    Ext_Passage_Num [] sEXTNum = (Ext_Passage_Num [])new Ext_Passage_Num().toArray(4);
    protected List getFieldOrder()
    {
        return Arrays.asList(new String[]{"sEXTNum"});
    }
}
