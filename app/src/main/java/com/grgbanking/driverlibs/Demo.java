package com.grgbanking.driverlibs;

import com.grgbanking.baselibrary.util.LogUtil;
import com.grgbanking.driverlibs.dll.Vapi;


import java.io.UnsupportedEncodingException;




public class Demo {
	Vapi V = new Vapi();
	
	public static Demo vguangSample = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}
	
	
	public Demo() throws UnsupportedEncodingException {
		//��ʼ���ؼ�


		
	
		
		
	}

	
	


	class Devices implements Runnable{ 
	    public void run(){ 
	    	while(true)
			{
	    		String decode = null;
	    		try {
					decode = V.vbarScan();
				} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		if(decode != null)
		    	{
	    			V.vbarBeep((byte)1);   //   ���ý�����һ��
		    		LogUtil.INSTANCE.i("gong",decode);
		    	}	
	    	}	
		}
}


	Devices devices = new Devices();
	Thread device;
	public void startdecodeThread(){
		device = new Thread(devices);
		
		device.start();
		
	}
	@SuppressWarnings("deprecation")
	public void stopdecodeThread(){
		if(device.isAlive())
		{
			device.stop();
		}
		
	}
	
	
}








