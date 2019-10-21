package com.grgbanking.huitong.driver_libs.fingerprints;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_FingerPrints;
import com.grgbanking.huitong.driver_libs.util.Driver_Contants;
import com.grgbanking.huitong.driver_libs.util.InstallSilent;
import com.xdisklib.FpDev;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/4 14:18
 * Email: 904430803@qq.com
 * Description:  乙木指纹仪工具类,先调用 openF()  打开
 * 之后
 */
public class Driver_FingerRecongnitionImpl implements IDriver_FingerPrints {

    private FpDev mFpDev = null;

    private FingerCallBack mFingerCallBack = null;//采集指纹回调
    private FingerThread mFingerThread = null;//采集指纹线程
    private Boolean readFinger = true;

    public void setReadFinger(Boolean readFinger) {
        this.readFinger = readFinger;
    }

    public Boolean getReadFinger() {
        return readFinger;
    }

    /**
     * @param
     * @return
     * @method
     * @description
     * @date: 2019/9/4 14:32
     * @author: gongxiaobiao
     */
    @Deprecated
    public void openFingerPrints() {
        if (mFpDev == null) {
            mFpDev = new FpDev();
        }

        String var1 = "chmod -R 666 /dev/sg*";
        List commandList = new ArrayList<String>();
//        commandList.add(InstallSilent.COMMAND_SU);
        commandList.add(var1);
        InstallSilent.execRootCommand(commandList, false, true);
        boolean result = mFpDev.openDevice();
        if (result) {
            Log.d(Driver_Contants.TAG, "打开指纹仪成功");
        } else {
            Log.d(Driver_Contants.TAG, "打开指纹仪失败");
        }
    }

    /**
     * @param
     * @return 返回特征值字节数组, 识别特征值失败，返回空
     * @method
     * @description 获取指纹特征值
     * @date: 2019/9/4 14:41
     * @author: gongxiaobiao
     */
    public byte[] getFeature() {

        if (mFpDev == null) throw new NullPointerException("空指针,请先调用 open()  方法");
        byte[] feature = new byte[FpDev.FEATURE_SIZE];
        int r = mFpDev.readFeature(feature, 0);
        if (r != FpDev.FEATURE_SIZE) {
            return null;
        } else {
            return feature;
        }

    }


    /**
     * @param
     * @return 获取指纹特征值照片成功返回图片bitmap 反之返回null
     * @method
     * @description 获取特征值图片
     * @date: 2019/9/4 14:53
     * @author: gongxiaobiao
     */

    public Bitmap getFeatureImg() {
        if (mFpDev == null) throw new NullPointerException("空指针,请先调用 open()  方法");
        if (mFpDev.getImage(false) != null) {
            return mFpDev.getImage(false);
        } else {
            return null;
        }
    }

    @Override
    public void getFeatureImg(boolean isOnce, long interval, FingerCallBack cardCallBack) {
        if (isOnce) {
            cardCallBack.fingerResultBitmap(getFeatureImg());
        } else {
            if (mFingerThread == null) {
                mFingerThread = new FingerThread(cardCallBack, interval);
                mFingerThread.start();
            }
        }

    }

    @Deprecated
    @Override
    public boolean matchFeature() {
        return false;
    }

    @Override
    public String readSerial() {
        if (mFpDev == null) throw new NullPointerException("空指针,请先调用 open()  方法");
        return mFpDev.readSerial();
    }

    @Override
    public void getFeature(boolean isOnce, long interval, FingerCallBack fingerCallBack) {
        if (isOnce) {
            fingerCallBack.fingerResultFeature(getFeature());
        } else {
            if (mFingerThread == null) {
                mFingerThread = new FingerThread(fingerCallBack, interval);
                mFingerThread.start();
            }
        }
    }

    @Override
    public void pauseReadFinger() {
//        synchronized (mFingerThread) {
//            if (mFingerThread != null
////                    && mFingerThread.getState() == Thread.State.RUNNABLE
//            ) {
//                try {
//                    mFingerThread.wait();
//                    Log.d(Driver_Contants.TAG, "pauseReadFinger:暂停录入指纹 ");
//                } catch (InterruptedException e) {
//                    Log.d(Driver_Contants.TAG, "pauseReadFinger: "+e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        }

        setReadFinger(false);

    }

    @Override
    public void restartReadFinger() {
//        synchronized (mFingerThread){
////            if (mFingerThread!=null&&mFingerThread.getState()!=Thread.State.RUNNABLE){
////                mFingerThread.notify();
////                Log.d(Driver_Contants.TAG, "pauseReadFinger:重新开始录入指纹 ");
////            }}
        setReadFinger(true);
    }


    @Override
    public int open(Context context) {
        if (mFpDev == null) {
            mFpDev = new FpDev();
        }
        String var1 = "chmod -R 666 /dev/sg*";
        List commandList = new ArrayList<String>();
        commandList.add(var1);
        InstallSilent.execRootCommand(commandList, false, true);
        boolean result = mFpDev.openDevice();
        if (result) {
            Log.d(Driver_Contants.TAG, "打开指纹仪成功");

        } else {
            Log.d(Driver_Contants.TAG, "打开指纹仪失败");

        }
        return result ? 0 : -1;
    }

    @Override
    public int close() {
        if (mFpDev != null) mFpDev.closeDevice();
        if (mFingerThread != null) {
            mFingerThread.interrupt();
            mFingerThread = null;
        }
        return 0;
    }

    class FingerThread extends Thread {
        private IDriver_FingerPrints.FingerCallBack callBack;
        private long interval = 0L;

        public FingerThread(IDriver_FingerPrints.FingerCallBack callBack, long interval) {
            this.callBack = callBack;
            this.interval = interval;

        }

        @Override
        public void run() {
            while (true) {
                if (getReadFinger()) {
                    callBack.fingerResultBitmap(getFeatureImg());
                    callBack.fingerResultFeature(getFeature());
                    try {
                        sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}

