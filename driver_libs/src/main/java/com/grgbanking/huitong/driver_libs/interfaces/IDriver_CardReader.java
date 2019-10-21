package com.grgbanking.huitong.driver_libs.interfaces;

import com.decard.entitys.IDCard;
import com.decard.entitys.SSCard;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/4 16:03
 * Email: 904430803@qq.com
 * Description: 德卡读卡器接口
 */
public interface IDriver_CardReader extends IDriverBaseInterface {
      IDCard readIDCard();//读取身份证

        SSCard readSSCard();//读取社保卡

        boolean checkSSCardPwd(String pwd);//校验社保密码
        boolean changeSSCardPwd(String pwd);//修改社保卡密码

        String getContactlessCard();//获取非接触卡类型

        boolean configContactlessCard();//配置非接触卡类型

        String findContactlessCard();//寻卡操作、、非接触ic卡

        boolean resetContactlessCard();//非接触卡复位

        boolean endContactlessCard();//终止非接触卡操作，此时必须把卡移出感应区后再次放入感应区才能寻到这张卡

        void pauseReadIDCard();//暂停读卡
        void  restartReadIDCard();//重新开始读卡

  /**
   * @method
   * @description 设置是读卡一次还是循环读卡
   * @date: 2019/9/6 10:19
   * @author: gongxiaobiao
   * @param  interval 时间间隔，毫秒数
   *          cardCallBack 结果回调
   * @return
   */
        void readIdCard(boolean onlyOnce,long interval,IDCardCallBack cardCallBack);//设置是读卡一次还是循环读卡  interval  时间
      interface IDCardCallBack{
    void IDCardResult(IDCard idCard);
  }
}
