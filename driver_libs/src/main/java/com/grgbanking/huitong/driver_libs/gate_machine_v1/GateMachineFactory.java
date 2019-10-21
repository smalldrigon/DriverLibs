package com.grgbanking.huitong.driver_libs.gate_machine_v1;

import com.grgbanking.huitong.driver_libs.DriverManagers;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_GateMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/11 9:18
 * Email: 904430803@qq.com
 * Description: 闸机工厂类
 */
public class GateMachineFactory {
    private static IDriver_GateMachine machine = null;
    public static IDriver_GateMachine creatInstance(String gateMachineType) {
        String path = "cd data/ca10";
        List commandList =  new ArrayList<String>();
//        commandList.add(InstallSilent.COMMAND_SU);

        String commd1  = "export LD_LIBRARY_PATH=./";
        String commd2  = "./GrgCommManager";
        commandList.add(path);
        commandList.add(commd1);
        commandList.add(commd2);
//        InstallSilent.execRootCommand(commandList,false,true);
        switch (gateMachineType) {
            case DriverManagers
                    .GATEMACHINE_TYPE_M810:
                machine = new Driver_GateM810Impl();
                break;
            case DriverManagers
                    .GATEMACHINE_TYPE_TJZN:

                machine = new Driver_GateTJZNImpl();
            case DriverManagers
                    .GATEMACHINE_TYPE_M820:

                machine = new Driver_GateM820Impl();
            break;
            default:
                break;
        }
        return machine;
    }

}
