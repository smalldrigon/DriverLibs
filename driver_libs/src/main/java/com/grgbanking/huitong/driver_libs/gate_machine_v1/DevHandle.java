package com.grgbanking.huitong.driver_libs.gate_machine_v1;
import java.io.Serializable;

public class DevHandle implements Serializable
{
	public int handle;
	public String LogicalName;


	public int getHandle() {
		return handle;
	}

	public void setHandle(int handle) {
		this.handle = handle;
	}

	public String getLogicalName() {
		return LogicalName;
	}

	public void setLogicalName(String logicalName) {
		LogicalName = logicalName;
	}
}