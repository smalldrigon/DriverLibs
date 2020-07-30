package com.kongqw.serialportlibrary.thread;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.CharsetEncoder;

/**
 * Created by Kongqw on 2017/11/14.
 * 串口消息读取线程
 */

public abstract class SerialPortReadThread extends Thread {

    public abstract void onDataReceived(byte[] bytes);

    private static final String TAG = SerialPortReadThread.class.getSimpleName();
    private InputStream mInputStream;
    private byte[] mReadBuffer;

    public SerialPortReadThread(InputStream inputStream) {
        mInputStream = inputStream;
        mReadBuffer = new byte[1024];
    }

    @Override
    public void run() {
        super.run();

        while (!isInterrupted()) {



            if (mInputStream == null) {
                return;
            }

            byte[] readData = new byte[1024];
            try {
                String receiveCmd = "";
//                String HEAD = "A055";
                StringBuilder sb = new StringBuilder();
                // 为了一次性读完，做了延迟读取
                if (mInputStream.available() > 0) {
                    SystemClock.sleep(200);
                    int size = mInputStream.read(readData);
                    if (size > 0) {
                        byte[] target = new byte[size];
                        System.arraycopy(readData,0,target,0,size);
                        String readString =  new String(target);
                        sb.append(readString);
                        Log.d("gonggggggggggg","000000000000"+sb.toString());
//                        if (!TextUtils.isEmpty(readString)) {
//                            String[] split = readString.split(HEAD);
//                            for (int i = 1; i < split.length; i++) {
//                                receiveCmd = HEAD + split[i];
//                                sb.append(receiveCmd + "\n");
//
//                            }
//                        }

                        onDataReceived(sb.toString().getBytes());
                        sb.setLength(0);
                    }


                }

            }catch (IOException e){
                e.printStackTrace();
            }



//            try {
//                if (null == mInputStream) {
//                    return;
//                }
//
//
//                Log.i(TAG, "run: ");
//                int size = mInputStream.read(mReadBuffer);
//
//                if (-1 == size || 0 >= size) {
//                    return;
//                }
//
//
//
//                byte[] readBytes = new byte[size];
//
//                System.arraycopy(mReadBuffer, 0, readBytes, 0, size);
//
//                Log.i(TAG, "run: readBytes = " + new String(readBytes));
//
//                onDataReceived(readBytes);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
//        }


//        if (!interrupted()){
//            int i=0;
//            while (i!=-1)
//            {
//
//                try{
//                sleep(200L);
//                try {
//                    i = mInputStream.read();
//                }catch (IOException e){
//                    e.printStackTrace();
//
//                }
//                    Log.i(TAG, "run: readBytes = i" + i);
//                    Log.i(TAG, "run: readBytes = mReadBuffer" + mReadBuffer.length);
//                    Log.i(TAG, "run: readBytes = " + (char)i);
////                    onDataReceived(readBytes);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                    continue;
//                }
//                if (i<=0){
//                    break;
//                }
//
//            }
//
//
//        }else{
//            return;
//        }
//        while (!isInterrupted()) {
//            int size;
//            try {
//                byte[] buffer = new byte[64];
//                if (mInputStream == null) return;
//                size = mInputStream.read(buffer);
//                if (size > 0) {
////                    onDataReceived(buffer, size);
//                    Log.i(TAG, "run: readBytes = size" + size);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
        }
    }
    public String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase());
        }

        return sb.toString();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    /**
     * 关闭线程 释放资源
     */
    public void release() {
        interrupt();

        if (null != mInputStream) {
            try {
                mInputStream.close();
                mInputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
