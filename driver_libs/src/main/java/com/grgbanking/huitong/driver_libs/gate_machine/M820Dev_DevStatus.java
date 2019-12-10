package com.grgbanking.huitong.driver_libs.gate_machine;
import com.sun.jna.Structure;

 import java.util.Arrays;
import java.util.List;

public class M820Dev_DevStatus extends Structure {
	public static class ByReference extends MyDevReturn implements Structure.ByReference{
	}

	public static class ByValue extends MyDevReturn implements Structure.ByValue{
	}

	public  byte[]	abyDevStatus = new byte[8];	// 硬件返回信息
	public   byte[]	abyReserved = new byte[32];		// 保留信息
	@Override
	protected List getFieldOrder()
	{
		return Arrays.asList("acDevReturn","acReserve");

	}
}