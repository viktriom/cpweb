package com.bds.cp.web.commandexecutor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.bds.cp.web.OperatingMode;

public class CommandExecutorFactory {
	
	private static Map<OperatingMode, AbstractCommandExecutor> cmdExecutorMap = new LinkedHashMap<OperatingMode, AbstractCommandExecutor>();
	
	public static AbstractCommandExecutor getCommandExecutor(OperatingMode opMode){
		if(cmdExecutorMap.get(opMode) == null){
			if(OperatingMode.NATIVE.equals(opMode))
				cmdExecutorMap.put(opMode, new NativeCommandExecutor());
			else if(OperatingMode.NETWORK.equals(opMode)){
				cmdExecutorMap.put(opMode, new NetworkCommandExecutor());
			}
		}
		return cmdExecutorMap.get(opMode);
	}

}
