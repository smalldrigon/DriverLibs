package com.grgbanking.huitong.driver_libs.interfaces;

/**
 * Created by Kongqw on 2017/11/14.
 * 串口消息监听
 */

public interface CustomOnSerialPortDataListener {

    /**
     * 数据接收
     *
     * @param bytes 接收到的数据
     */
    void onDataReceived(byte[] bytes);

    /**
     * 数据发送
     *
     * @param bytes 发送的数据
     */
    void onDataSent(byte[] bytes);
}
