package com.bds.cp.web;

public enum OperatingMode {
	NATIVE, 
	NETWORK;
	
	public static OperatingMode getOperatingMode(String operatingMode){
		for(OperatingMode opMode : OperatingMode.values()){
			if(opMode.name().equals(operatingMode))
				return opMode;
		}
		throw new EnumConstantNotPresentException(null, "OperatingMode named : " + operatingMode + "not found in the enum.");
	}
}