package com.grgbanking.huitong.driver_libs.scan_qr_code;

/**
 * Author: gongxiaobiao
 * Date: on 2019/8/27 15:49
 * Email: 904430803@qq.com
 * Description: 扫码成功回调接口
 */
public interface OnScanSuccessListener {
    void onScanSuccess(int usbInputType,String barcode);
}
