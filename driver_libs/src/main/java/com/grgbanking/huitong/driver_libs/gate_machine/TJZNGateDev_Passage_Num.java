package com.grgbanking.huitong.driver_libs.gate_machine;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/11 8:05
 * Email: 904430803@qq.com
 * Description:
 */
public class TJZNGateDev_Passage_Num extends Structure{
     public    int passeNumL;
    public  int unPassNumL;
    public  int  timeoutNumL;

    @Override
    public String toString() {
        return "TJZNGateDev_Passage_Num{" +
                "passeNumL=" + passeNumL +
                ", unPassNumL=" + unPassNumL +
                ", timeoutNumL=" + timeoutNumL +
                ", passeNumR=" + passeNumR +
                ", unPassNumR=" + unPassNumR +
                ", timeoutNumR=" + timeoutNumR +
                '}';
    }

    public  int  passeNumR;
    public      int  unPassNumR;
    public    int timeoutNumR;
    public static class ByReference extends TJZNGateDev_Passage_Num implements Structure.ByReference {
    }

    public static class ByValue extends TJZNGateDev_Passage_Num implements Structure.ByValue {
    }


    protected List getFieldOrder()
    {
        return Arrays.asList(new String[]{
                "passeNumL",
                "unPassNumL",
                "timeoutNumL",
                "passeNumR",
                "unPassNumR",
                "timeoutNumR",

        });
    }

}
