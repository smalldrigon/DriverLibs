package com.grgbanking.huitong.driver_libs.scan_qr_code;

import android.content.Context;
import android.os.Handler;
import android.view.InputDevice;
import android.view.KeyEvent;

import com.grgbanking.huitong.driver_libs.interfaces.CustomOnSerialPortDataListener;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_ScanGun;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: gongxiaobiao
 * Date: on 2019/8/27 15:44
 * Email: 904430803@qq.com
 * Description: 扫码类
 * 之后设置回调接口
 */
public class Driver_ScanQrCodeImpl_serialport implements IDriver_ScanGun {
    private static volatile Driver_ScanQrCodeImpl_serialport mInstance = null;


    private StringBuffer mStringBufferResult;                  //扫码内容
    private boolean mCaps;                                     //大小写区分
    private Handler mHandler;
    private Runnable mScanningFishedRunnable;
    private OnScanSuccessListener mOnScanSuccessListener;
    private static int INPUT_TYPE = -1;//输入类型，用于回调类型判断 0 卓德3120扫码枪  1卓德7100N扫码枪 2定义为IC卡读卡器 3微光qt420系列 -1未定义输类型

    private boolean isPaseScan = false;// 暂停扫码，目前只是暂停回调
    private Map<String, SerialPortManager> serialPortManagerList = null;
    private Map<String, String> portsMap = null;

    public Driver_ScanQrCodeImpl_serialport() {
        mStringBufferResult = new StringBuffer();
        mHandler = new Handler();

    }

    public Map<String, SerialPortManager> getSerialPortManagerList() {
        return serialPortManagerList;
    }

    @Deprecated
    public static Driver_ScanQrCodeImpl_serialport getInstance() {
        if (null == mInstance) {
            synchronized (Driver_ScanQrCodeImpl_serialport.class) {
                if (null == mInstance) {
                    mInstance = new Driver_ScanQrCodeImpl_serialport();
                }
            }
        }
        return mInstance;
    }

    /**
     * 输入设备是否存在
     */
    private boolean isInputDeviceExist(String deviceName) {
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int id : deviceIds) {
            if (InputDevice.getDevice(id).getName().equals(deviceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为扫码枪事件(部分机型KeyEvent获取的名字错误)
     */
    private boolean isScanGunEvent(KeyEvent event) {
        return isInputDeviceExist(event.getDevice().getName());
    }

    @Override
    public void startScan(KeyEvent event, OnScanSuccessListener listener) {
        if (isPaseScan) return;
        if (!isScanGunEvent(event)) {
            return;
        }
    }

    /**
     * 串口连接调用此方法，
     *
     * @param portRateMap  串口名称和波特率键值对
     * @param dateListener 不定参接口，有几个端口需要写几个回调
     */
    public boolean startScan(Map<String, String> portRateMap, CustomOnSerialPortDataListener... dateListener) {
        List<Boolean> openPortResult = new ArrayList<Boolean>();//打开端口结果
        int size = 0;
        if (isPaseScan) return false;
        if (portRateMap == null) {
            throw new NullPointerException("portRateMap 不能为空");

        }
        if (dateListener == null || dateListener.length <= 0) {
            throw new NullPointerException("dateListener 不能为空");

        }
        if (portRateMap.size() != dateListener.length) {
            throw new InputMismatchException("设置的端口数跟回调接口数不匹配，请重新设置");

        }
        portsMap = portRateMap;
        serialPortManagerList = new HashMap<>();
        Iterator<Map.Entry<String,String>> it = portRateMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry =   it.next();
            SerialPortManager serialPortManager = new SerialPortManager();
            int finalSize = size;
            serialPortManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
                @Override
                public void onDataReceived(byte[] bytes) {
                    if (!isPaseScan){
                        dateListener[finalSize].onDataReceived(bytes);
                    }
                }

                @Override
                public void onDataSent(byte[] bytes) {
                    if (!isPaseScan) {
                        dateListener[finalSize].onDataSent(bytes);
                    }
                }
            })
                    .setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
                        @Override
                        public void onSuccess(File device) {
                            openPortResult.add(true);
                        }

                        @Override
                        public void onFail(File device, Status status) {
                            openPortResult.add(false);
                        }
                    })
                    .openSerialPort(new File(entry.getKey()), Integer.parseInt(entry.getValue()));
            serialPortManagerList.put(entry.getKey(),
                    serialPortManager);
            size++;
        }

        return !openPortResult.contains(Boolean.FALSE);
    }


    public boolean sendCMD(String portName, String msg) throws FileNotFoundException {
        boolean res = false;
        if (serialPortManagerList.containsKey(portName)&&msg!=null) {
           res =  serialPortManagerList.get(portName).sendBytes(msg.getBytes());
        } else {
            res = false;
            throw  new FileNotFoundException("port not found,please check potrName");
        }
        return res;
    }

    @Override
    public void pauseReadQrCode() {

        this.isPaseScan = true;
    }

    @Override
    public void restartReadQrCode() {
        this.isPaseScan = false;

    }

    @Override
    public int getStatu() {
        return 0;
    }

    @Override
    public int open(Context context) {
        return 0;
    }

    @Override
    public int close() {
        return 0;
    }
}
