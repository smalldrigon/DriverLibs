package com.grgbanking.huitong.driver_libs.card_reader;

import android.content.Context;
import android.util.Log;
import com.decard.NDKMethod.BasicOper;
import com.decard.NDKMethod.EGovernment;
import com.decard.entitys.IDCard;
import com.decard.entitys.SSCard;
import com.grgbanking.huitong.driver_libs.interfaces.IDriver_CardReader;
import com.grgbanking.huitong.driver_libs.util.Driver_Contants;

/**
 * Author: gongxiaobiao
 * Date: on 2019/8/27 13:38
 * Email: 904430803@qq.com
 * Description: 读卡器工具类，适用于德卡 读卡器  识别身份证，非接触ic卡,
 * 使用时先调用open_Driver_CardReader方法初始化，然后在调用读卡方法
 */
public class Driver_DeCardReaderImpl implements IDriver_CardReader {
//    private static IDriver_CardReader mInstance;
//    private IDriver_CardReader() {
//    }
//    public static IDriver_CardReader getInstance() {
//        if (mInstance == null) {
//            synchronized (IDriver_CardReader.class) {
//                if (mInstance == null) { mInstance = new IDriver_CardReader();
//
//                }
//            }
//        }
//        return mInstance;
//    }

    private IdCardThread mIDCardThread = null;//循环读卡线程
    private Context mContext;
    private Boolean readIDCard = true; //暂停读卡的标识

    public Driver_DeCardReaderImpl(Context context) {
        mContext = context;
        int res = BasicOper.dc_AUSB_ReqPermission(context);
        if (res > 0) {
            Log.d(Driver_Contants.TAG, "德卡读卡器请求权限成功= " + res);
        } else {
            Log.e(Driver_Contants.TAG, "德卡读卡器请求权限失败 = " + res);
        }
//        open(context);
    }

    /**
     * @param
     * @return
     * @method
     * @description 打开读卡器的方法，使用前需要调用此方法，建议在application中调用
     * @date: 2019/8/27 15:03
     * @author: gongxiaobiao
     */
    @Deprecated
    public static int open_Driver_CardReader(Context context) {
        //向系统申请使用USB权限,此过程为异步,建议放在程序启动时调用。
        int res = BasicOper.dc_AUSB_ReqPermission(context);
        if (res > 0) {
            Log.d(Driver_Contants.TAG, "德卡读卡器请求权限成功= " + res);
        } else {
            Log.e(Driver_Contants.TAG, "德卡读卡器请求权限失败 = " + res);
        }
//打开端口，usb模式，打开之前必须确保已经获取到USB权限，返回值为设备句柄号。

        int devHandle = BasicOper.dc_open("AUSB", context, "", 0);
        if (devHandle > 0) {
            Log.d(Driver_Contants.TAG, "德卡读卡器打开成功= " + devHandle);
        } else {
            Log.e(Driver_Contants.TAG, "德卡读卡器打开失败 = " + devHandle);
        }
        return devHandle;
    }

    /**
     * @param
     * @return 身份证信息对象
     * @method
     * @description 读取身份证信息，只读一次，当返回不为空时说明读身份证信息成功
     * 如果需要循环，建议循环调用此方法,
     * @date: 2019/8/27 14:22
     * @author: gongxiaobiao
     */
    public static IDCard readIdCard() {
        return BasicOper.dc_SamAReadCardInfo(1);
    }


    /**
     * @param
     * @return
     * @method
     * @description 关闭读卡器的方法，释放资源
     * @date: 2019/8/27 15:05
     * @author: gongxiaobiao
     */
    @Deprecated
    public void close_Driver_Reader() {
        if (mIDCardThread != null) {
            mIDCardThread.interrupt();
            mIDCardThread = null;
        }
        BasicOper.dc_exit();
    }

    public Boolean getReadIDCard() {
        return readIDCard;
    }

    public void setReadIDCard(Boolean readIDCard) {
        this.readIDCard = readIDCard;
    }


    public static void get_CardTypr() {
        //获取感应区中存在的非接触卡类型

        String result = BasicOper.dc_RfGetCardType();
        String[] resultArr = result.split("\\|", -1);
        if (resultArr[0].equals("0000")) {
            Log.d(Driver_Contants.TAG, "success card type = " + resultArr[1]);
            if (resultArr[1].equals("00")) {
                Log.d(Driver_Contants.TAG, "无卡");
            } else if (resultArr[1].equals("11")) {
                Log.d(Driver_Contants.TAG, "Type A CPU Card");
            } else if (resultArr[1].equals("13")) {
                Log.d(Driver_Contants.TAG, "Type A Mifare S50");
            } else if (resultArr[1].equals("14")) {
                Log.d(Driver_Contants.TAG, "Type A Mifare S70");
            } else if (resultArr[1].equals("15")) {
                Log.d(Driver_Contants.TAG, "Type A Mifare Ultralight");
            } else if (resultArr[1].equals("21")) {
                Log.d(Driver_Contants.TAG, "Type B CPU Card");
            } else if (resultArr[1].equals("22")) {
                Log.d(Driver_Contants.TAG, "Type B 存储卡");
            }
        } else {
            Log.d(Driver_Contants.TAG, "error code = " + resultArr[0] + " error msg = " + resultArr[1]);
        }

    }


    @Override
    public IDCard readIDCard() {
        return BasicOper.dc_SamAReadCardInfo(1);
    }

    @Override
    public SSCard readSSCard() {

        /*社保卡上电*/
        boolean bCardPowerOn = false;
        String result = null;
        String[] resultArr = null;
        result = EGovernment.EgAPP_SI_CardPowerOn(1);
        resultArr = result.split("\\|", -1);
        if (resultArr[0].equals("0000")) {
            bCardPowerOn = true;
            Log.d(Driver_Contants.TAG, "社保卡上电 success");
        } else {
            Log.d(Driver_Contants.TAG, "社保卡上电 error code = " + resultArr[0] + " error msg = " + resultArr[1]);
        }
        SSCard ssCard = null;
        /*读取社保卡基本信息*/
        if (bCardPowerOn) {
            ssCard = EGovernment.EgAPP_SI_ReadSSCardInfo();
            if (ssCard != null) {
                Log.d(Driver_Contants.TAG, ssCard.toString());
            } else {
                Log.d(Driver_Contants.TAG, "读取社保卡信息失败");
            }
        }
        /*社保卡下电*/
        if (bCardPowerOn) {
            EGovernment.EgAPP_SI_CardPowerOff(1);
        }


        return ssCard;
    }

    @Override
    public boolean checkSSCardPwd(String pwd) {
        /*社保卡上电*/
        boolean bCardPowerOn = false;
        String result = null;
        String[] resultArr = null;
        result = EGovernment.EgAPP_SI_CardPowerOn(1);
        resultArr = result.split("\\|", -1);
        if (resultArr[0].equals("0000")) {
            bCardPowerOn = true;
            Log.d(Driver_Contants.TAG, "社保卡上电 success");
        } else {
            Log.d(Driver_Contants.TAG, "社保卡上电 error code = " + resultArr[0] + " error msg = " + resultArr[1]);
        }

        /*验证密码*/
        if (bCardPowerOn) {
            result = EGovernment.EgAPP_SI_VerifyPIN("0000");
            resultArr = result.split("\\|", -1);
            if (resultArr[0].equals("0000")) {
                Log.d(Driver_Contants.TAG, "校验社保卡密码：success");
                return true;
            } else {
                Log.d(Driver_Contants.TAG, "校验社保卡密码failed");
                return false;
            }

        }
        return false;
    }

    @Override
    public boolean changeSSCardPwd(String pwd) {

        /*社保卡上电*/
        boolean bCardPowerOn = false;
        String result = null;
        String[] resultArr = null;
        result = EGovernment.EgAPP_SI_CardPowerOn(1);
        resultArr = result.split("\\|", -1);
        if (resultArr[0].equals("0000")) {
            bCardPowerOn = true;
            Log.d(Driver_Contants.TAG, "社保卡上电:success");
        } else {
            Log.d(Driver_Contants.TAG, "社保卡上电 error code = " + resultArr[0] + " error msg = " + resultArr[1]);
        }

        /*修改密码*/
        if (bCardPowerOn) {
            result = EGovernment.EgAPP_SI_ChangePIN("0000", "1234");
            resultArr = result.split("\\|", -1);
            if (resultArr[0].equals("0000")) {
                Log.d(Driver_Contants.TAG, "修改社保卡密码changeSSCardPwd:success");
                return true;
            } else {
                Log.d(Driver_Contants.TAG, "修改社保卡密码changeSSCardPwd:failed");
                return false;
            }

        }


        return false;
    }

    @Override
    public String getContactlessCard() {
//获取感应区中存在的非接触卡类型
        String result = BasicOper.dc_RfGetCardType();
        String[] resultArr = result.split("\\|", -1);
        if (resultArr[0].equals("0000")) {
            Log.d(Driver_Contants.TAG, " 获取非接触卡类型 success card type = " + resultArr[1]);
            if (resultArr[1].equals("00")) {
                Log.d(Driver_Contants.TAG, "获取非接触卡类型 无卡");
            } else if (resultArr[1].equals("11")) {
                Log.d(Driver_Contants.TAG, " 获取非接触卡类型Type A CPU Card");
            } else if (resultArr[1].equals("13")) {
                Log.d(Driver_Contants.TAG, "获取非接触卡类型 Type A Mifare S50");
            } else if (resultArr[1].equals("14")) {
                Log.d(Driver_Contants.TAG, "获取非接触卡类型 Type A Mifare S70");
            } else if (resultArr[1].equals("15")) {
                Log.d(Driver_Contants.TAG, "获取非接触卡类型 Type A Mifare Ultralight");
            } else if (resultArr[1].equals("21")) {
                Log.d(Driver_Contants.TAG, "获取非接触卡类型 Type B CPU Card");
            } else if (resultArr[1].equals("22")) {
                Log.d(Driver_Contants.TAG, "获取非接触卡类型 Type B 存储卡");
            }
            return resultArr[1];
        } else {
            Log.d(Driver_Contants.TAG, "获取非接触卡类型 error code = " + resultArr[0] + " error msg = " + resultArr[1]);
            return null;
        }
    }

    @Override
    public boolean configContactlessCard() {
        String result = BasicOper.dc_config_card(0x00);
        String[] resultArr = result.split("\\|", -1);
        if (resultArr[0].equals("0000")) {
            Log.d("dc_config_card", "success ");
        } else {
            Log.d("dc_config_card", "error code = " + resultArr[0] + " error msg = " + resultArr[1]);
        }

        return false;
    }

    @Override
    public String findContactlessCard() {
        //寻卡请求、防卡冲突、选卡操作  支持ISO 14443 Type A类型卡片的寻卡请求

        String result = BasicOper.dc_card_hex(0x01);
        String[] resultArr = result.split("\\|", -1);
        if (resultArr[0].equals("0000")) {
            Log.d(Driver_Contants.TAG, "寻卡请求 success card sn = " + resultArr[1]);
            return resultArr[1];
        } else {
            Log.d(Driver_Contants.TAG, "寻卡请求  error code = " + resultArr[0] + " error msg = " + resultArr[1]);
            return null;
        }


    }

    @Override
    public boolean resetContactlessCard() {
        //非接触式CPU卡复位

        String result = BasicOper.dc_pro_resethex();
        String[] resultArr = result.split("\\|", -1);
        if (resultArr[0].equals("0000")) {
            Log.d(Driver_Contants.TAG, " 非接触cpu卡复位 success ATR/ATS = " + resultArr[1]);
            return true;
        } else {
            Log.d(Driver_Contants.TAG, "非接触cpu卡复位 error code = " + resultArr[0] + " error msg = " + resultArr[1]);
            return false;
        }


    }

    @Override
    public boolean endContactlessCard() {
        // 终止非接触式CPU卡操作
        String result = BasicOper.dc_pro_halt();
        String[] resultArr = result.split("\\|", -1);
        if (resultArr[0].equals("0000")) {
            Log.d(Driver_Contants.TAG, "终止非接触式CPU卡操作 success");
        } else {
            Log.d(Driver_Contants.TAG, "终止非接触式CPU卡操作 error code = " + resultArr[0] + " error msg = " + resultArr[1]);
        }

        return false;
    }

    @Override
    public void pauseReadIDCard() {
//        Log.d(Driver_Contants.TAG, "pauseReadIDCard:暂停读卡 " + mIDCardThread.getState());
//        Log.d(Driver_Contants.TAG, "pauseReadIDCard:暂停读卡 " + (mIDCardThread.getState() == Thread.State.TIMED_WAITING));
//        Log.d(Driver_Contants.TAG, "pauseReadIDCard:暂停读卡 " + (mIDCardThread != null));
//        synchronized (mIDCardThread) {
//            if (mIDCardThread != null
////                   && mIDCardThread.getState() == Thread.State.RUNNABLE
//            ) {
//                try {
//                    Log.d(Driver_Contants.TAG, "pauseReadIDCard:暂停读卡 ");
//                    mIDCardThread.wait();
//
//                } catch (InterruptedException e) {
//                    Log.d(Driver_Contants.TAG, "pauseReadFinger: " + e.getMessage());
//
//                    e.printStackTrace();
//                }
//            }
//        }
        setReadIDCard(false);
    }


    @Override
    public void restartReadIDCard() {
//        Log.d(Driver_Contants.TAG, "restartReadIDCard:重新开始读卡 " + mIDCardThread.getState());
//        synchronized (mIDCardThread) {
//            if (mIDCardThread != null && mIDCardThread.getState() != Thread.State.RUNNABLE) {
//                mIDCardThread.notify();
//                Log.d(Driver_Contants.TAG, "pauseReadIDCard:重新开始读卡 ");
//            }
//        }
        setReadIDCard(true);
    }

    @Override
    public void readIdCard(boolean isOnce, long interval, IDCardCallBack cardCallBack) {

        if (isOnce) {
            cardCallBack.IDCardResult(readIDCard());
        } else {
            if (mIDCardThread == null) {
                mIDCardThread = new IdCardThread(cardCallBack, interval);
                mIDCardThread.start();
            }
        }

    }

    @Override
    public int open(Context context) {
//打开端口，usb模式，打开之前必须确保已经获取到USB权限，返回值为设备句柄号。
        int devHandle = BasicOper.dc_open("AUSB", context, "", 0);
        if (devHandle >= 0) {
            Log.d(Driver_Contants.TAG, "德卡读卡器打开成功= " + devHandle);
        } else {
            Log.e(Driver_Contants.TAG, "德卡读卡器打开失败 = " + devHandle);
        }
        return devHandle;
    }

    @Override
    public int close() {
        return BasicOper.dc_exit();
    }

    class IdCardThread extends Thread {
        IDCardCallBack cardCallBack;
        long interval = 0L;


        public IdCardThread(IDCardCallBack cardCallBack, long interval) {
            this.cardCallBack = cardCallBack;
            this.interval = interval;
        }

        @Override
        public void run() {
            while (true) {
                if (getReadIDCard()) {

                    cardCallBack.IDCardResult(readIDCard());
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