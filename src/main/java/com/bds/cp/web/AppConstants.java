package com.bds.cp.web;

public class AppConstants {
	
	private static OperatingMode operatingMode;
	private static String hostname;
	private static Integer port;
	
	public static OperatingMode getOperatingMode() {
		return operatingMode;
	}

	public static void setOperatingMode(String operatingMode) {
		AppConstants.operatingMode = OperatingMode.getOperatingMode(operatingMode);
	}

	public static String getHostname() {
		return hostname;
	}

	public static void setHostname(String hostname) {
		AppConstants.hostname = hostname;
	}

	public static Integer getPort() {
		return port;
	}

	public static void setPort(String port) {
		AppConstants.port = Integer.parseInt(port);
	}
}
