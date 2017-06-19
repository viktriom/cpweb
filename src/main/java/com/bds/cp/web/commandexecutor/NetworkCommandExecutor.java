package com.bds.cp.web.commandexecutor;

import org.apache.log4j.Logger;
import com.bds.cp.web.util.NetworkUtil;

public class NetworkCommandExecutor extends AbstractCommandExecutor{
	
	private static Logger log = Logger.getLogger(NetworkCommandExecutor.class);
	
	public String executeCommandForType(String cmdString){
		log.info("Sending the command over the network to server for execution, the command is : " + cmdString);
		NetworkUtil.getNetworkClient().sendMessageToServer(cmdString);
		return NetworkUtil.getNetworkClient().getResponseFromServer();
	}

}
