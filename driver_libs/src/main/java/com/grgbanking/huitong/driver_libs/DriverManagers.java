package com.grgbanking.huitong.driver_libs;

import android.content.Context;
import com.grgbanking.huitong.driver_libs.card_reader.Driver_DeCardReaderImpl;
import com.grgbanking.huitong.driver_libs.fingerprints.Driver_FingerRecongnitionImpl;
import com.grgbanking.huitong.driver_libs.gate_machine.GateMachineFactory;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_CardReader;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_FingerPrints;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_GateMachine;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_ScanGun;
import com.grgbanking.huitong.driver_libs.scan_qr_code.Driver_ScanQrCodeImpl;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/5 8:40
 * Email: 904430803@qq.com
 * Description:驱动管理类，在此处初始化一些硬件，读卡器，指纹仪，扫码枪等，并返回对应实例
 */
public class DriverManagers {
    public static final String SACNGUN_TYPE_ZD7100 = "ZD7100";//扫码枪类型，型号，厂家  卓德ZD7100
    public static final String CARD_READER_TYPE_T10 = "德卡T10";//读卡器类型，型号，厂家  德卡T10
    public static final String FINGERPRINTS_TYPE_FPC1011 = "FPC1011+";//指纹仪类型，型号，厂家  FPC1011+  乙木
    public static final String GATEMACHINE_TYPE_M810 = "M810";//闸机机芯类型，型号，厂家  M810
    public static final String GATEMACHINE_TYPE_M820 = "M820";//闸机机芯类型，型号，厂家  奥义M820
    public static final String GATEMACHINE_TYPE_TJZN = "TJZN";//闸机机芯类型，型号，厂家  铁军
    public static final String GATEMACHINE_TYPE_TJZN01 = "TJZN01";//闸机机芯类型，型号，厂家  铁军电动双向三辊闸

    private IDriver_CardReader Driver_CardReader = null;
    private IDriver_FingerPrints Driver_FingerPrints = null;
    private IDriver_GateMachine Driver_GateMachine = null;
    public static DriverManagers instance = null;

    public String getmMachineType() {
        return mMachineType;
    }

    public void setmMachineType(String mMachineType) {
        this.mMachineType = mMachineType;
    }

    public   String mMachineType = null;

    public IDriver_GateMachine getDriver_GateMachine() {
        return Driver_GateMachine;
    }

    public IDriver_CardReader getDriver_CardReader() {
        return Driver_CardReader;
    }

    public IDriver_FingerPrints getDriver_FingerPrints() {
        return Driver_FingerPrints;
    }

    public IDriver_ScanGun getDriver_ScanGun() {
        return Driver_ScanGun;
    }

    private IDriver_ScanGun Driver_ScanGun = null;


    private Context mContext = null;

    private DriverManagers() {

    }

    public static class Builder {
        private IDriver_CardReader mIDriver_CardReader = null;
        private IDriver_FingerPrints mIDriver_FingerPrints = null;
        private IDriver_ScanGun mIDriver_ScanGun = null;
        private Context mContext = null;
        private IDriver_GateMachine mDriver_GateMachine = null;
        private    String mMachineType = null;
        public Builder() {

        }

        public Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }


        /**
         * @param
         * @return
         * @method
         * @description 设置读卡器类型
         * @date: 2019/9/5 11:55
         * @author: gongxiaobiao
         */
        public Builder setCardReader(String cardReaderName) {
            switch (cardReaderName) {
                case CARD_READER_TYPE_T10:
                    mIDriver_CardReader = new Driver_DeCardReaderImpl(mContext);
//                    mIDriver_CardReader.open(mContext);
                    break;
            }
            return this;
        }

        /**
         * @param
         * @return
         * @method
         * @description 设置扫码枪类型
         * @date: 2019/9/5 11:54
         * @author: gongxiaobiao
         */
        public Builder setScanGun(String scanGunType) {
            switch (scanGunType) {
                case SACNGUN_TYPE_ZD7100:
                    mIDriver_ScanGun = new Driver_ScanQrCodeImpl();
                    break;
            }
            return this;
        }

        /**
         * @param
         * @return
         * @method
         * @description 设置指纹仪类型
         * @date: 2019/9/5 11:55
         * @author: gongxiaobiao
         */
        public Builder setFingerPrints(String fingerPrintsType) {
            switch (fingerPrintsType) {
                case FINGERPRINTS_TYPE_FPC1011:
                    mIDriver_FingerPrints = new Driver_FingerRecongnitionImpl();
                    mIDriver_FingerPrints.open(mContext);
                    break;
            }
            return this;
        }

        public Builder setGateMachine(String machineType) {
            switch (machineType) {
                case GATEMACHINE_TYPE_M810:
                    mDriver_GateMachine = GateMachineFactory.creatInstance(GATEMACHINE_TYPE_M810);
                    break;
                case GATEMACHINE_TYPE_TJZN:
                    mDriver_GateMachine = GateMachineFactory.creatInstance(GATEMACHINE_TYPE_TJZN);
                    break;
                case GATEMACHINE_TYPE_M820:
                    mDriver_GateMachine = GateMachineFactory.creatInstance(GATEMACHINE_TYPE_M820);
                    break;
                case GATEMACHINE_TYPE_TJZN01:
                    mDriver_GateMachine = GateMachineFactory.creatInstance(GATEMACHINE_TYPE_TJZN01);
                    break;
            }

            mMachineType = machineType;
            return this;
        }


        public DriverManagers build() {
            DriverManagers managers = new DriverManagers();
            managers.Driver_CardReader = mIDriver_CardReader;
            managers.Driver_FingerPrints = mIDriver_FingerPrints;
            managers.Driver_ScanGun = mIDriver_ScanGun;
            managers.Driver_GateMachine = mDriver_GateMachine;
            managers.setmMachineType(mMachineType);
            instance = managers;
            return managers;
        }

    }


}
