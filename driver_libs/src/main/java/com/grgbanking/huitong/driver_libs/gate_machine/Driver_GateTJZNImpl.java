package com.grgbanking.huitong.driver_libs.gate_machine;

import android.content.Context;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_GateMachine_TJZN;
import com.grgbanking.huitong.driver_libs.interfaces.IGateMachineActionCallBack;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/10 15:52
 * Email: 904430803@qq.com
 * Description:  铁军机芯控制接口实现类
 */
public class Driver_GateTJZNImpl extends IDriver_GateMachine_TJZN {
    private int mHandle = -1;
    @Override
    public int setMode(int p_hDevHandle, int mode, DevReturn devReturn) {

        return 0;
    }

    @Override
    public int setDriverLogDir(String logPath) {

          return IGateDev_TJZN.mInstance.setDriverLogDir(logPath);
    }

    @Override
    public int setConfigFileLoadDir(String configFilePath) {
          return IGateDev_TJZN.mInstance.SetConfigFileLoadDir(configFilePath);
    }

    @Override
    public int openLogicDevice(String p_pcLogicDevName) {
return IGateDev_TJZN.mInstance.hOpenLogicDevice(p_pcLogicDevName);
    }

    @Override
    public int openLogicDevice(String p_pcLogicDevName, String configFilePath, String logFilePath) {
        System.out.println("TJZN初始化 openLogicDevice()");

        DevReturn devReturnsetPara = new DevReturn();
        DevReturn devReturnInit = new DevReturn();
        setConfigFileLoadDir(configFilePath
        );//设置配置文件路径
        setDriverLogDir(logFilePath
        );//设置闸机日志文件路径
        mHandle = IGateDev_TJZN.mInstance.hOpenLogicDevice(p_pcLogicDevName);
        setCommPara(mHandle, devReturnsetPara);
        init(mHandle, devReturnInit);
        if (devReturnsetPara.getiPhyCode() != 0 || devReturnsetPara.getiLogicCode() != 0) {
            System.err.println("TJZN初始化 setCommPara 失败" + "mHandle:" + mHandle + "devReturn" + devReturnsetPara.toString());
        }else{
            System.out.println("TJZN初始化 setCommPara()"+devReturnsetPara.toString());

        }
        if (devReturnInit.getiPhyCode() != 0 || devReturnInit.getiLogicCode() != 0) {
            System.err.println("TJZN初始化 init 失败" + "mHandle:" + mHandle + "devReturn" + devReturnInit.toString());
        }else{
            System.out.println("TJZN初始化 init()"+devReturnInit.toString());

        }
        return mHandle;

     }

    @Override
    public void closeLogicDevice(int p_pcLogicDevName) {
        IGateDev_TJZN.mInstance.vCloseLogicDevice(p_pcLogicDevName);
    }

    @Override
    public int setCommPara(int devHandler, DevReturn  devReturn) {
        MyDevReturn[] mydevReturn1 =creatMyDevReturnArray();
        return IGateDev_TJZN.mInstance.iSetCommPara(devHandler,mydevReturn1);
    }

    @Override
    public int init(int p_hDevHandle, DevReturn devReturn) {
        MyDevReturn[] mydevReturn =creatMyDevReturnArray();
        int ret =IGateDev_TJZN.mInstance.iInit(p_hDevHandle,mydevReturn);

        devReturn.iLogicCode=mydevReturn[0].iLogicCode;

        devReturn.iPhyCode=mydevReturn[0].iPhyCode;
        return 0;
    }

    @Override
    public int openGateLeftOnce( DevReturn devReturn) {
        MyDevReturn[] mydevReturn1 = creatMyDevReturnArray();
//        int ret = IGateDev_M810.mInstance.iOpenGate(mHandle, 1, 0, mydevReturn1);
        int ret = openGate(mHandle,1,devReturn);
        devReturn.iLogicCode = mydevReturn1[0].iLogicCode;
        devReturn.iPhyCode = mydevReturn1[1].iPhyCode;

        return ret;
    }



    @Override
    public int openGateLeftAways(DevReturn devReturn) {
        MyDevReturn[] mydevReturn1 = creatMyDevReturnArray();
//        int ret = IGateDev_M810.mInstance.iOpenGate(mHandle, 1, 0, mydevReturn1);
        int ret = openGate(mHandle,1,devReturn);
        devReturn.iLogicCode = mydevReturn1[0].iLogicCode;
        devReturn.iPhyCode = mydevReturn1[1].iPhyCode;

        return ret;
    }

    @Override
    public int openGateRightAways(DevReturn devReturn) {
        MyDevReturn[] mydevReturn1 = creatMyDevReturnArray();
        int ret = openGate(mHandle,2,devReturn);
        devReturn.iLogicCode = mydevReturn1[0].iLogicCode;
        devReturn.iPhyCode = mydevReturn1[1].iPhyCode;
        return ret;
    }

    @Override
    public int openGateRightOnce(  DevReturn devReturn) {

        MyDevReturn[] mydevReturn1 = creatMyDevReturnArray();
        int ret = openGate(mHandle,2,devReturn);
        devReturn.iLogicCode = mydevReturn1[0].iLogicCode;
        devReturn.iPhyCode = mydevReturn1[1].iPhyCode;
        return ret;
    }

    private int openGate(int p_hDevHandle, int dir, DevReturn devReturn) {
        MyDevReturn[] mydevReturn1 = creatMyDevReturnArray();
        int ret = IGateDev_TJZN.mInstance.iOpenGate(p_hDevHandle, dir, mydevReturn1);
        devReturn.iLogicCode = mydevReturn1[0].iLogicCode;
        devReturn.iPhyCode = mydevReturn1[1].iPhyCode;
        return ret;
    }



    @Override
    public int closeGate(DevReturn devReturn) {
        MyDevReturn[] mydevReturn1 =creatMyDevReturnArray();
        int ret = IGateDev_TJZN.mInstance.iCloseGate(mHandle,mydevReturn1);
        devReturn.iLogicCode = mydevReturn1[0].iLogicCode;
        devReturn.iPhyCode = mydevReturn1[1].iPhyCode;
        return ret;
    }

    @Override
    public int closeGate(int dir, DevReturn devReturn) {
        
        return 0;
    }


    @Override
    public int getPassageNum(TJZNGateDev_Passage_Num num, DevReturn devReturn) {

        int ret;
        MyDevReturn[] mydevReturn =creatMyDevReturnArray();
        TJZNGateDev_Passage_Num.ByReference passageNum = new TJZNGateDev_Passage_Num.ByReference();
        ret = IGateDev_TJZN.mInstance.iGetPassageNum(mHandle,passageNum,mydevReturn);

        //        num.passeNumL = passageNum.passeNumL[0].uiAuthPassNum;
//        num.unPassNumL = passageNum.sEXTNum[0].uiPassNum;
//        num.timeoutNumL = passageNum.sEXTNum[0].uiTimeoutNum;
//        num.passeNumR = passageNum.sEXTNum[1].uiAuthPassNum;
//        num.unPassNumR = passageNum.sEXTNum[1].uiPassNum;
//        num.timeoutNumR = passageNum.sEXTNum[1].uiTimeoutNum;


        num.passeNumL = passageNum.passeNumL;
        num.unPassNumL = passageNum.unPassNumL;
        num.timeoutNumL = passageNum.timeoutNumL;
        num.passeNumR = passageNum.passeNumR;
        num.unPassNumR = passageNum.unPassNumR;
        num.timeoutNumR = passageNum.timeoutNumR;

        devReturn.iLogicCode=mydevReturn[0].iLogicCode;

        devReturn.iPhyCode=mydevReturn[0].iPhyCode;


        return  ret;


    }

    @Override
    public void setIGateMachineActionCallBack(IGateMachineActionCallBack callBack) {
        setIGateMachineActionCallBack(  callBack);
    }


    @Override
    public int open(Context context) {
        return 0;
    }

    @Override
    public int close() {
        return 0;

    }
    private MyDevReturn[] creatMyDevReturnArray(){
        return (MyDevReturn[]) new MyDevReturn().toArray(8);
    }
}
