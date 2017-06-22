package com.bds.cp.web.commandexecutor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bds.cp.core.util.CPUtil;
import com.bds.cp.web.AppConstants;
import com.bds.cp.web.util.CPWebUtil;
import com.google.gson.Gson;

public abstract class AbstractCommandExecutor {
	
	private static Logger log = Logger.getLogger(AbstractCommandExecutor.class);

	public synchronized String executeCommand(String cmdString){
		
		log.info("The executeCommand() method called, the method is " + AppConstants.getOperatingMode().name());
		String completeCmdName = cmdString.split(" ")[0]; 
		Gson gson = new Gson();
		
		String shortCmdName = completeCmdName.substring(CPUtil.getIndexOfCharFromRight(completeCmdName, '.')+1, completeCmdName.length());
		String newCmdStr = cmdString.substring(cmdString.indexOf(shortCmdName), cmdString.length());
		
		sendSetContextMessage(cmdString, completeCmdName);
		log.info("Done setting command context, starting command execution now.");
    	
    	String resp = prepareJSONString(shortCmdName, newCmdStr, false);
    	log.info("Successfully completed the command execution.");
    	return resp;
	}
	
	public synchronized String executeCommandBare(String cmdString){
		log.info("The executeCommand() method called, the method is ");
		String completeCmdName = cmdString.split(" ")[0]; 
		Gson gson = new Gson();
		
		String shortCmdName = completeCmdName.substring(CPUtil.getIndexOfCharFromRight(completeCmdName, '.')+1, completeCmdName.length());
		String newCmdStr = cmdString.substring(cmdString.indexOf(shortCmdName), cmdString.length());
		
		//sendSetContextMessage(cmdString, completeCmdName);
		log.info("Done setting command context, starting command execution now.");
    	
		log.info("Successfully completed the command execution.");
    	return prepareJSONString(shortCmdName, newCmdStr, true);
	}
	
	private String prepareJSONString(String cmdName, String newCmdStr, boolean getBareResponse){
		Map<String, String> response = new LinkedHashMap<String, String>();
		String resp;
		Gson gson = new Gson();
		if(getBareResponse){
			resp = executeCommandForType(newCmdStr);
			log.info("Returing the bare response : " + resp);
		}else{
			response.put("cmdName", cmdName);
	    	response.put("text", executeCommandForType(newCmdStr));
	    	resp = gson.toJson(response);
	    	log.info("Returning command reponse which can be displayed in web console : " + resp);
		}
		return resp;
		
	}
	
	private void sendSetContextMessage(String cmdString, String completeCmdName){
		String cmdContext = "SetContext " + cmdString.substring(0, CPUtil.getIndexOfCharFromRight(completeCmdName, '.'));
		log.info("Executing the command to set command context : " + cmdContext);
		executeCommandForType(cmdContext);
    	log.info("Sending message to the network server : (msg = " + cmdContext  );
	}
	
	protected abstract String executeCommandForType(String cmdName);

	
}