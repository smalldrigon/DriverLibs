package com.grgbanking.huitong.driver_libs.scan_qr_code;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
 * override fun dispatchKeyEvent(event: KeyEvent): Boolean {
 * if (event.device.name.contains(Driver_ScanQrCode.BARCODE_DECODER_DEVICE_NAME)) {
 * startScan(event, this)
 * return true
 * }
 * return super.dispatchKeyEvent(event)
 * }
 * 之后设置回调接口
 */
public class Driver_ScanQrCodeImpl implements IDriver_ScanGun {
    private static volatile Driver_ScanQrCodeImpl mInstance = null;

    private int productId_ZD3120 = 41985;//卓德3120扫码枪产品id
    private int vendorId_ZD3120 = 1317;//卓德3120扫码枪供应商id

    private int productId_ZD7100N = 9921;//卓德7100N扫码枪产品id
    private int vendorId_ZD7100N = 11734;//卓德7100N扫码枪供应商id

    private int productId_IDCARD = 9;//usbid读卡器产品id
    private int vendorId_IDCARD = 2303;//usbid读卡器供应商id

    public static int INPUT_RQCODE = 1;// 扫码枪回调类型为1
    public static int INPUT_ICCARD = 2;// usb 读卡器回调类型为2
    public static int UNDEFINE_INPUT_TYPE = -1;// 未定义usb 读卡器回调类型为-1
    private final static long MESSAGE_DELAY = 50;             //延迟500ms，判断扫码是否完成。
    public final static String BARCODE_DECODER_DEVICE_NAME_ZD_7100N = "SuperLead";             //延迟500ms，判断扫码是否完成。
    public final static String BARCODE_DECODER_DEVICE_NAME_ZD_3120 = "SuperLead";             //延迟500ms，判断扫码是否完成。


    public final static String ICCARD_DECODER_DEVICE_NAME = "Sycreader RFID Technology Co., Ltd SYC ID&IC USB Reader";             //延迟500ms，判断扫码是否完成。
    private StringBuffer mStringBufferResult;                  //扫码内容
    private boolean mCaps;                                     //大小写区分
    private Handler mHandler;
    private Runnable mScanningFishedRunnable;
    private OnScanSuccessListener mOnScanSuccessListener;
    private static int INPUT_TYPE = -1;//输入类型，用于回调类型判断 0 卓德3120扫码枪  1卓德7100N扫码枪 2定义为IC卡读卡器  -1未定义输类型

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
                if (INPUT_TYPE == 1) mOnScanSuccessListener.onScanSuccess(INPUT_RQCODE, barcode);
                if (INPUT_TYPE == 0) mOnScanSuccessListener.onScanSuccess(INPUT_RQCODE, barcode);
                if (INPUT_TYPE == 2) mOnScanSuccessListener.onScanSuccess(INPUT_ICCARD, barcode);
                if (INPUT_TYPE == -1) mOnScanSuccessListener.onScanSuccess(UNDEFINE_INPUT_TYPE, barcode);
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
     * 已弃用  请使用 startScan 方法
     */
    @Deprecated
    public void analysisKeyEvent(KeyEvent event, OnScanSuccessListener listener) {
        Log.i("gong", event.getDevice().getName());
        if (!isScanGunEvent(event)) {
//            Log.i("gong","!isScanGunEvent");
            return;
        }
        //Virtual是我所使用机器的内置软键盘的名字
        //在这判断是因为项目中避免和软键盘冲突（扫码枪和软键盘都属于按键事件）
        if (event.getDevice().getName().contains(BARCODE_DECODER_DEVICE_NAME_ZD_7100N)) {
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


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void startScan(KeyEvent event, OnScanSuccessListener listener) {
        if (isPaseScan) return;
        if (!isScanGunEvent(event)) {
            return;
        }
        //Virtual是我所使用机器的内置软键盘的名字
        //在这判断是因为项目中避免和软键盘冲突（扫码枪和软键盘都属于按键事件）


        if (event.getDevice().getVendorId() == vendorId_ZD7100N && event.getDevice().getProductId() == productId_ZD7100N) {
            INPUT_TYPE = 1;//usb 扫码枪输入类型

        } else if (event.getDevice().getVendorId() == vendorId_ZD3120 && event.getDevice().getProductId() == productId_ZD3120) {
            INPUT_TYPE = 0;//usb 扫码枪输入类型

        } else if (event.getDevice().getVendorId() == vendorId_IDCARD && event.getDevice().getProductId() == productId_IDCARD) {
            INPUT_TYPE = 2;//usb ic卡读卡器输入类型
        } else {
            INPUT_TYPE = -1;//未定义输入类型
        }
        setCallBak(event, listener);

    }


    private void setCallBak(KeyEvent event, OnScanSuccessListener listener) {
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
