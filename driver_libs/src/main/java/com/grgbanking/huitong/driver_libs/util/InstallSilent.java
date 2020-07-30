package com.grgbanking.huitong.driver_libs.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class InstallSilent {
	
	public static final String COMMAND_SU = "su";
	public static final String COMMAND_SH = "sh";
	public static final String COMMAND_LINE_END = "\n";
	public static final String COMMAND_EXIT= "exit\n";
	public static final String COMMAND_RCCSU = "rccsu";
	
	//Fixed：可以将返回值更加细化
	//执行批量命令，如果没有root权限，则调用su获取，如果已经存在rccsu，则isRoot为true
	//isWaiting:是否等待命令执行完成，不等待则为false
	public static boolean execRootCommand(List<String> commands,boolean isRoot,boolean isWaiting){
		int result = -1;
		if(commands == null || commands.size() == 0){
			return true;
		}
		
		Process process = null;
		DataOutputStream os = null;


		try {
			process = Runtime.getRuntime().exec(isRoot? COMMAND_RCCSU:COMMAND_SU);
			os = new DataOutputStream(process.getOutputStream());


			for(String command :commands){
				if (command == null) {
					continue;
				}
				
				os.writeBytes(command);
				os.writeBytes(COMMAND_LINE_END);
				os.flush();
			}
			
			os.writeBytes(COMMAND_EXIT);
			os.flush();

			//获得结果的输入流

			InputStream input = process.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(input));

			String strLine;

			while(null != (strLine = br.readLine())){

				System.out.println(strLine);

			}
//			System.out.println( new String(read(process.getInputStream())));

//			RuntimeStream errorStream = new RuntimeStream(
//					process.getErrorStream(), "ERROR");

			//由于exec执行出现错误或警告时，主控程序的waitfor方法会被阻塞一直等待下去，所有需要自己处理err和out信息
			// kick off stderr
//			errorStream.start();

//			RuntimeStream outStream = new RuntimeStream(
//					process.getInputStream(), "STDOUT");
			// kick off stdout
//			outStream.start();
			
			//若无需等待进程返回，则使用exitValue
			if (isWaiting) {
				result = process.waitFor();
			}else{
				result = process.exitValue();
			}

			System.out.println("ExitValue: " + result);

			if (result !=0) {
				
				return false;
			}

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
			System.out.println("ExitValue: " + result);
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
			System.out.println("ExitValue: " + result);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
			try {
				if (os != null) {
					os.close();
				}
				
				if (process != null) {
					process.destroy();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				return false;
			}
						
		}
		
		return true;
	}


	public static byte[] read(InputStream inStream) throws Exception{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len = inStream.read(buffer)) != -1){
			outputStream.write(buffer, 0, len);
		}
		inStream.close();
		return outputStream.toByteArray();
	}











	//执行单个命令，execRootCommand的简单实现
	//可以优化实现，返回值可以带回更多信息
//	private static boolean execRootCmdsByrccsu(String command)
//	{
//		//执行系统命令
//		Process process = null;
//		try
//		{
//			if (command == null) {
//				return true;
//			}
//
//			//rccsu 支持-c 参数实现
//			process = Runtime.getRuntime().exec(COMMAND_RCCSU + " -c "+ command);
//			int result = 0;
//			try {
//
//				RuntimeStream errorStream = new RuntimeStream(
//						process.getErrorStream(), "ERROR");
//
//				//由于exec执行出现错误或警告时，主控程序的waitfor方法会被阻塞一直等待下去，所有需要自己处理err和out信息
//				// kick off stderr
//				errorStream.start();
//
//				RuntimeStream outStream = new RuntimeStream(
//						process.getInputStream(), "STDOUT");
//				// kick off stdout
//				outStream.start();
//
//				result = process.waitFor();
//				System.out.println("ExitValue: " + result);
//
//				if(result !=0 )
//				{
//					return false;
//				}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return false;
//			}
//
//		}
//		catch(IOException ex)
//		{
//			ex.printStackTrace();
//			return false;
//		}finally{
//
//			try {
//				if (process != null) {
//					process.destroy();
//				}
//			} catch (Exception e2) {
//				// TODO: handle exception
//				return false;
//			}
//
//		}
//
//		return true;
//
//	}

}
