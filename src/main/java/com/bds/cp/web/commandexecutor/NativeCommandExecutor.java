package com.bds.cp.web.commandexecutor;

import org.apache.log4j.Logger;

import com.bds.cp.core.util.CPUtil;

public class NativeCommandExecutor extends AbstractCommandExecutor{

	private static Logger log = Logger.getLogger(NativeCommandExecutor.class);
	
	public String executeCommandForType(String cmdString){
		log.info("Executing the command natively, command is : " + cmdString);
		return CPUtil.executeCommand(cmdString);
	}
	
}
