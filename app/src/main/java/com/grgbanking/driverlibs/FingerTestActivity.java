package com.grgbanking.driverlibs;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;;
import android.widget.ImageView;
import com.grgbanking.huitong.driver_libs.util.Driver_Contants;
import com.grgbanking.huitong.driver_libs.util.InstallSilent;
import com.xdisklib.FpDev;

import java.util.ArrayList;
import java.util.List;


public class FingerTestActivity extends Activity {
    private ImageView mIvID;
    private EditText mEdtOut;
    private EditText mEdtFeature;
    private FpDev mFpDev;
    private ProgressDialog mProgressDialog;
    private Bitmap mFpBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fingertest);


        mEdtOut = (EditText)findViewById(R.id.edtOut);
        mEdtFeature = (EditText)findViewById(R.id.edtFeature);
        mIvID = (ImageView)findViewById(R.id.ivID);

        ButtonOnClickListener btnListener = new ButtonOnClickListener();

        findViewById(R.id.btnOpen).setOnClickListener(btnListener);
        findViewById(R.id.btnClose).setOnClickListener(btnListener);
        findViewById(R.id.btnSerial).setOnClickListener(btnListener);
        findViewById(R.id.btnGetImage).setOnClickListener(btnListener);
        findViewById(R.id.btnGetFeature).setOnClickListener(btnListener);
        findViewById(R.id.btnMatch).setOnClickListener(btnListener);

        if(mFpDev == null){
            //mFpDev = new FpDev(this, m_IConnectionHandler);
            mFpDev = new FpDev();

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

    }

    private void openDevice()
    {
        if(mFpDev == null) return;
        mFpDev.openDevice();
//    	if(!mFpDev.isInit())
//    		mFpDev.open();
    }

    private void closeDevice(){
        if(mFpDev == null) return;
        //mFpDev.close();
        mFpDev.closeDevice();
    }

    private void readSerial(){
        if(mFpDev == null) return;
        String s = mFpDev.readSerial();
        if(s != null){
            mEdtOut.setText(s);
        }else{
            mEdtOut.setText("读序列号失败!");
        }
    }

    private void getImage(){
        if(mFpDev == null) return;
        if(mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("指纹图像采集");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("正在采集...");
        mProgressDialog.setCancelable(false);
//		 mProgressDialog.setButton("取消", new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface arg0, int arg1) {
//				// TODO Auto-generated method stub
//				mProgressDialog.dismiss();
//				mProgressDialog = null;
//			}
//		 });
        mProgressDialog.show();

        new Thread(){
            @Override
            public void run() {
                try {

                    while(true)
                    {
                    sleep(1000L);

                        byte[] feature = new byte[FpDev.FEATURE_SIZE];
                        int r = mFpDev.readFeature(feature, 0);
                        if(r != FpDev.FEATURE_SIZE){
                            mEdtOut.setText(String.format("读指纹特征失败!, ret code = %d", r));
                            mEdtFeature.setText("");
                        }else {
                            mEdtOut.setText("读指纹特征成功");
                            System.err.println("读指纹特征成功");
                            mEdtFeature.setText(mFpDev.bytesToHexString(feature, 0, r));
                        }











                    mFpBmp = mFpDev.getImage(false);
                    if (mFpBmp != null) {
                        mEdtOut.post(new Runnable() {
                            @Override
                            public void run() {
                                System.err.println("读图像成功");

                                mIvID.setImageBitmap(mFpBmp);
                                mEdtOut.setText(String.valueOf(mFpBmp.getWidth()) + ", " + String.valueOf(mFpBmp.getHeight()));
                            }
                        });
                    } else {
                        mEdtOut.post(new Runnable() {
                            @Override
                            public void run() {
                                mIvID.setImageBitmap(null);
                                mEdtOut.setText("读图像失败!");
                                System.err.println("读图像失败");
                            }
                        });
                    }
                }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    mProgressDialog.dismiss();
                }
            }
        }.start();

//    	Bitmap bmp = mFpDev.getImage(false);
//    	mIvID.setImageBitmap(bmp);
//    	if(bmp == null)
//    		mEdtOut.setText("读图像失败!");
//    	else
//    		mEdtOut.setText(String.valueOf(bmp.getWidth())+ ", "+String.valueOf(bmp.getHeight()));
    }

    private void getFeature(){
        if(mFpDev == null) return;
        byte[] feature = new byte[FpDev.FEATURE_SIZE];
        int r = mFpDev.readFeature(feature, 0);
        if(r != FpDev.FEATURE_SIZE){
            mEdtOut.setText(String.format("读指纹特征失败!, ret code = %d", r));
            mEdtFeature.setText("");
        }else {
            mEdtOut.setText("读指纹特征成功");
            System.err.println("读指纹特征成功");
            mEdtFeature.setText(mFpDev.bytesToHexString(feature, 0, r));
        }
    }

    private void matchFeature(){
        if(mFpDev == null) return;
        String s = mEdtFeature.getText().toString();
        if(s.length() != (FpDev.FEATURE_SIZE *2)){
            mEdtOut.setText("指纹特征数据错误");
            return;
        }
        int r = mFpDev.getMatchLevel();
        if(r < 0){
            mEdtOut.setText(String.format("获取匹配等级失败, ret = %d", r));
            return;
        }
        mEdtOut.setText(String.format("对比等级：%d  ",r));
        r = mFpDev.setMatchLevel((byte)5);
        if(r < 0){
            mEdtOut.setText(String.format("设置匹配等级失败, ret = %d", r));
            return;
        }
        byte[] feature = FpDev.hexStringToBytes(s);
        r = mFpDev.matchFeature(feature, feature.length);
        if(r == 0){
            mEdtOut.append("指纹匹配");
        }else{
            mEdtOut.append(String.format("指纹不匹配, ret code = %d", r));
        }
    }

    private class ButtonOnClickListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            switch(arg0.getId()){
                case R.id.btnOpen:
                    openDevice();
                    break;

                case R.id.btnClose:
                    closeDevice();
                    break;

                case R.id.btnSerial:
                    readSerial();
                    break;

                case R.id.btnGetImage:
                    getImage();
                    break;

                case R.id.btnGetFeature:
                    getFeature();
                    break;

                case R.id.btnMatch:
                    matchFeature();
                    break;
            }
        }
    }

//    private final IUsbConnState m_IConnectionHandler = new IUsbConnState() {
//        @Override
//        public void onUsbConnected() {
////        	String[] w_strInfo = new String[1];
//
////        	if (m_usbComm.Run_TestConnection() == DevComm.ERR_SUCCESS)
////        	{
////        		if (m_usbComm.Run_GetDeviceInfo(w_strInfo) == DevComm.ERR_SUCCESS)
////	        	{
////        			EnableCtrl(true);
////		            m_btnOpenDevice.setEnabled(false);
////		            m_btnCloseDevice.setEnabled(true);
////		            m_txtStatus.setText("Open Success!\r\nDevice Info : " + w_strInfo[0]);
////	        	}
////        	}
////        	else
////        		m_txtStatus.setText("Can not connect to device!");
//        	Toast.makeText(getApplicationContext(), "Device connected!", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onUsbPermissionDenied() {
//        	//m_txtStatus.setText("Permission denied!");
//        	Toast.makeText(getApplicationContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onDeviceNotFound() {
//        	//m_txtStatus.setText("Can not find usb device!");
//        	Toast.makeText(getApplicationContext(), "Can not find usb device!", Toast.LENGTH_SHORT).show();
//        }
//    };
}