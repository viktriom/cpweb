package com.bds.cp.web.util;

import org.apache.log4j.Logger;

import com.bds.cp.core.util.CPUtil;
import com.bds.cp.core.util.web.WebUtil;

public class CPWebUtil {
	
	private static Logger log = Logger.getLogger(CPWebUtil.class);
	
	public static String executeWebCommand(String cmdString){
		String completeCmdName = cmdString.split(" ")[0];
		String shortCmdName = completeCmdName.substring(CPUtil.getIndexOfCharFromRight(completeCmdName, '.')+1, completeCmdName.length());
		String cmdContext = "SetContext " + cmdString.substring(0, CPUtil.getIndexOfCharFromRight(completeCmdName, '.'));
		String newCmdStr = cmdString.substring(cmdString.indexOf(shortCmdName), cmdString.length());
		log.info("The command context is : " + cmdContext);
    	CPUtil.executeCommand(cmdContext);
    	log.info("Done setting command context, starting command execution now.");
    	String response = shortCmdName + "> " + CPUtil.executeCommand(newCmdStr); 
    	log.info("Successfully completed the command execution.");
		return response;
	}

}
