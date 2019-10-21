package com.grgbanking.huitong.driver_libs.scan_qr_code;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_ScanGun;

/**
 * Author: gongxiaobiao
 * Date: on 2019/8/27 15:44
 * Email: 904430803@qq.com
 * Description: 扫码类
 * 使用方法如下：
 *  override fun dispatchKeyEvent(event: KeyEvent): Boolean {
 *         if (event.device.name.contains(Driver_ScanQrCode.BARCODE_DECODER_DEVICE_NAME)) {
 *             startScan(event, this)
 *             return true
 *         }
 *         return super.dispatchKeyEvent(event)
 *     }
 *  之后设置回调接口
 *
 */
public class Driver_ScanQrCodeImpl implements IDriver_ScanGun {
    private static volatile Driver_ScanQrCodeImpl mInstance = null;
    public static int INPUT_RQCODE  =1;// 扫码枪回调类型为1
    public static int INPUT_ICCARD =2;// usb 读卡器回调类型为2
    private final static long MESSAGE_DELAY = 50;             //延迟500ms，判断扫码是否完成。
    public final static String BARCODE_DECODER_DEVICE_NAME = "SuperLead";             //延迟500ms，判断扫码是否完成。
    public final static String ICCARD_DECODER_DEVICE_NAME = "Sycreader RFID Technology Co., Ltd SYC ID&IC USB Reader";             //延迟500ms，判断扫码是否完成。
    private StringBuffer mStringBufferResult;                  //扫码内容
    private boolean mCaps;                                     //大小写区分
    private Handler mHandler;
    private Runnable mScanningFishedRunnable;
    private  OnScanSuccessListener mOnScanSuccessListener;
    private static int INPUT_TYPE = 1;//输入类型，用于回调类型判断  1定义为扫码枪 2定义为IC卡读卡器

    private boolean isPaseScan = false;// 暂停扫码，目前只是暂停回调



    public Driver_ScanQrCodeImpl() {
        mStringBufferResult = new StringBuffer();
        mHandler = new Handler();
        mScanningFishedRunnable = new Runnable() {
            @Override
            public void run() {
                performScanSuccess();
            }
        };
    }


    @Deprecated
    public static Driver_ScanQrCodeImpl getInstance() {
        if (null == mInstance) {
            synchronized (Driver_ScanQrCodeImpl.class) {
                if (null == mInstance) {
                    mInstance = new Driver_ScanQrCodeImpl();
                }
            }
        }
        return mInstance;
    }


    /**
     * 返回扫码成功后的结果
     */
    private void performScanSuccess() {
        try {
            String barcode = mStringBufferResult.toString();
            if (mOnScanSuccessListener != null && !TextUtils.isEmpty(barcode)) {
                if (INPUT_TYPE==1)mOnScanSuccessListener.onScanSuccess(INPUT_RQCODE,barcode);
                if (INPUT_TYPE==2)mOnScanSuccessListener.onScanSuccess(INPUT_ICCARD,barcode);
            }
            mStringBufferResult.setLength(0);
            if (mHandler != null) {
                mHandler.removeCallbacks(mScanningFishedRunnable);
            }
            mOnScanSuccessListener = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 扫码枪事件解析
     */
    @Deprecated
    public void analysisKeyEvent(KeyEvent event,  OnScanSuccessListener listener) {
        Log.i("gong",event.getDevice().getName());
        if (!isScanGunEvent(event)) {
//            Log.i("gong","!isScanGunEvent");
            return;
        }
        //Virtual是我所使用机器的内置软键盘的名字
        //在这判断是因为项目中避免和软键盘冲突（扫码枪和软键盘都属于按键事件）
        if (event.getDevice().getName().contains(BARCODE_DECODER_DEVICE_NAME)) {
            int keyCode = event.getKeyCode();
            //字母大小写判断
            checkLetterStatus(event);
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                mOnScanSuccessListener = listener;
                char aChar = getInputCode(event);
                if (aChar != 0) {
                    mStringBufferResult.append(aChar);
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    mHandler.removeCallbacks(mScanningFishedRunnable);
                    mHandler.post(mScanningFishedRunnable);
                } else {
                    mHandler.removeCallbacks(mScanningFishedRunnable);
                    mHandler.postDelayed(mScanningFishedRunnable, MESSAGE_DELAY);
                }
            }
        }
    }

    //检查shift键
    private void checkLetterStatus(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
            //按着shift键，表示大写
            //松开shift键，表示小写
            mCaps = event.getAction() == KeyEvent.ACTION_DOWN;
        }
    }

    //获取扫描内容
    private char getInputCode(KeyEvent event) {
        int keyCode = event.getKeyCode();
        char aChar;
        if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            //字母
            aChar = (char) ((mCaps ? 'A' : 'a') + keyCode - KeyEvent.KEYCODE_A);
        } else if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            //数字
            aChar = (char) ('0' + keyCode - KeyEvent.KEYCODE_0);
        } else {
            //其他符号
            switch (keyCode) {
                case KeyEvent.KEYCODE_PERIOD:
                    aChar = '.';
                    break;
                case KeyEvent.KEYCODE_MINUS:
                    aChar = mCaps ? '_' : '-';
                    break;
                case KeyEvent.KEYCODE_SLASH:
                    aChar = '/';
                    break;
                case KeyEvent.KEYCODE_BACKSLASH:
                    aChar = mCaps ? '|' : '\\';
                    break;
                default:
                    aChar = 0;
                    break;
            }
        }
        return aChar;
    }


    /**
     * 扫码成功回调接口
     */


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
            if (isPaseScan)return;
         if (!isScanGunEvent(event)) {
             return;
        }
        //Virtual是我所使用机器的内置软键盘的名字
        //在这判断是因为项目中避免和软键盘冲突（扫码枪和软键盘都属于按键事件）
        if (event.getDevice().getName().contains(BARCODE_DECODER_DEVICE_NAME)
            ||event.getDevice().getName().contains(ICCARD_DECODER_DEVICE_NAME)
        ) {
            if (event.getDevice().getName().contains(BARCODE_DECODER_DEVICE_NAME)){
                INPUT_TYPE =1;
            }else{
                INPUT_TYPE =2;
            }
            int keyCode = event.getKeyCode();
            //字母大小写判断
            checkLetterStatus(event);
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                mOnScanSuccessListener = listener;
                char aChar = getInputCode(event);
                if (aChar != 0) {
                    mStringBufferResult.append(aChar);
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    mHandler.removeCallbacks(mScanningFishedRunnable);
                    mHandler.post(mScanningFishedRunnable);
                } else {
                    mHandler.removeCallbacks(mScanningFishedRunnable);
                    mHandler.postDelayed(mScanningFishedRunnable, MESSAGE_DELAY);
                }
            }
        }
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
    public int open(Context context) {
        return 0;
    }

    @Override
    public int close() {
        return 0;
    }
}
