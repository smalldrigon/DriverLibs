package com.grgbanking.huitong.driver_libs.gate_machine;
import java.io.Serializable;

public class DevReturn implements Serializable
{
	public int iPhyCode;
	public int iLogicCode;

	@Override
	public String toString() {
		return "DevReturn{" +
				"iPhyCode=" + iPhyCode +
				", iLogicCode=" + iLogicCode +
				'}';
	}

	public int getiPhyCode() {
		return iPhyCode;
	}

	public void setiPhyCode(int iPhyCode) {
		this.iPhyCode = iPhyCode;
	}

	public int getiLogicCode() {
		return iLogicCode;
	}

	public void setiLogicCode(int iLogicCode) {
		this.iLogicCode = iLogicCode;
	}
}