package com.grgbanking.huitong.driver_libs.interfaces;

import android.graphics.Bitmap;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/4 16:03
 * Email: 904430803@qq.com
 * Description: 德卡读卡器接口
 */
public interface IDriver_FingerPrints extends IDriverBaseInterface {

  byte[] getFeature();//返回特征值字节数组,识别特征值失败，返回空

  /**
   * @method  getFeatureImg
   * @description 获取指纹仪识别的图片
   * @date: 2019/9/6 11:28
   * @author: gongxiaobiao
   * @param
   * @return
   */
  Bitmap getFeatureImg(); //获取指纹特征值照片成功返回图片bitmap 反之返回null
  void  getFeatureImg(boolean onlyOnce, long interval, FingerCallBack cardCallBack); //获取指纹特征值照片成功返回图片bitmap 反之返回null

  boolean matchFeature();//指纹对比
  String readSerial();//读取序列号

  /**
   * @method
   * @description 设置是读卡一次还是循环读卡
   * @date: 2019/9/6 10:19
   * @author: gongxiaobiao
   * @param  interval 时间间隔，毫秒数
   *          cardCallBack 结果回调
   * @return
   */
  void getFeature(boolean onlyOnce, long interval, FingerCallBack fingerCallBack);//设置是读卡一次还是循环读卡  interval  时间
  interface FingerCallBack{
    void fingerResultFeature(byte[] feature);
    void fingerResultBitmap(Bitmap bitmap);
  }

  void pauseReadFinger();//暂停读卡
  void  restartReadFinger();//重新开始读卡

}
