package com.grgbanking.huitong.driver_libs.interfaces;

import android.view.KeyEvent;
import com.grgbanking.huitong.driver_libs.scan_qr_code.OnScanSuccessListener;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/4 16:03
 * Email: 904430803@qq.com
 * Description: 德卡读卡器接口
 */
public interface IDriver_ScanGun extends IDriverBaseInterface {

      void startScan(KeyEvent event, OnScanSuccessListener listener);//开始扫码

      void pauseReadQrCode();//暂停读卡
      void  restartReadQrCode();//重新开始读卡


}
