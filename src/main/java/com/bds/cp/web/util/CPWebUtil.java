package com.bds.cp.web.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bds.cp.annotations.ExecutableCommand;
import com.bds.cp.bean.CommandMetaData;
import com.bds.cp.bean.CommandParameter;
import com.bds.cp.bean.CommandParameterType;
import com.bds.cp.core.util.CPStartupUtil;
import com.bds.cp.core.util.CPStore;
import com.bds.cp.core.util.CPUtil;
import com.bds.cp.executors.Executor;
import com.bds.cp.web.AppConstants;
import com.bds.cp.web.OperatingMode;
import com.bds.cp.web.commandexecutor.CommandExecutorFactory;
import com.google.gson.Gson;

public class CPWebUtil {
	
	private static Logger log = Logger.getLogger(CPWebUtil.class);
	
    public static void initializeCPSystem(){
    	log.info("Initializing properties.");
        CPUtil.loadPropertiesFileIntoClass(CPUtil.getPathForString("cp.properties"), "CPConstants",true);
        log.info("Loading web application properties.");
        CPUtil.loadPropertiesFileIntoClass(CPUtil.getPathForString("cpweb.properties"), "com.bds.cp.web.AppConstants", true);
        log.info("Web application properties loaded, continuing initilization.");
        if(AppConstants.getOperatingMode().equals(OperatingMode.NATIVE)){
        	log.info("OperatingMode is : " + AppConstants.getOperatingMode().name() + ", Loading commands natively.");
        	CPStartupUtil.loadCommands();
        }else if(AppConstants.getOperatingMode().equals(OperatingMode.NETWORK)){
        	log.info("OperatingMode is : " + AppConstants.getOperatingMode().name() + ", Initializing the network connectivity.");
        	NetworkUtil.getNetworkClient().connectToServer();
        	log.info("Successfully connected to the network server.");
        }
    }

    public static CommandMetaData getCommandMetadata(String commandName){
        log.info("Preparing Command metadata for command : ." + commandName);
        CommandMetaData commandMetaData = null;
        String commandDescription = "";
        String[] paramNames, paramNameDescription, paramType;
        Executor executor = CPStore.getCommandFromCommandStore(commandName);
        
        if(null != executor && executor.getClass().isAnnotationPresent(ExecutableCommand.class)){
        	
        	commandMetaData = new CommandMetaData();
            
        	ExecutableCommand executableCommand = executor.getClass().getAnnotation(ExecutableCommand.class);
            
            commandDescription = executableCommand.commandDescription();
            
            commandMetaData.setCommandName(commandName);
            commandMetaData.setCommandDescription(commandDescription);
            
            paramNames = executableCommand.commandParams();
            paramNameDescription = executableCommand.commandParamsDescription();
            paramType = executableCommand.commandParameterType();
            
            for(int i = 0;i <paramNames.length;i++){
            	if(paramNames[i].isEmpty() || paramNames[i].length() <= 0) continue;
            	CommandParameter cp = new com.bds.cp.bean.CommandParameter(paramNames[i], paramNameDescription[i], CommandParameterType.getCommandParameterType(paramType[i]));
            	commandMetaData.addParamNameAndDescription(paramNames[i], cp);
            }
        }
        log.info("Done collecting parameter metadata for command name : " + commandName);
        return commandMetaData;
    }

    public static String prepareHTMLForCommandMetadata(String commandName){
    	Gson gson = new Gson();
    	String cmdString = "CmdDetail -cn " + commandName;
    	String jsonCmdDetail = CommandExecutorFactory.getCommandExecutor(AppConstants.getOperatingMode()).executeCommandBare(cmdString);
    	
    	CommandMetaData cmdDetail = gson.fromJson(jsonCmdDetail, CommandMetaData.class);
    	
    	Map<String, String> dataMap = new HashMap<String,String>();
    	if(null == jsonCmdDetail) return "<div><p> "+ commandName + " command not found.</p></div>";
    	StringBuilder sb = new StringBuilder();
    	sb.append("<div>");
    	sb.append("<p>");
    	sb.append("<p>");
    	sb.append("Command Name :: ");
    	sb.append(commandName);
    	sb.append("</p>");
    	sb.append("<p>");
    	sb.append(cmdDetail.getCommandDescription());
    	sb.append("</p>");
    	Set<String> paramNames = new LinkedHashSet<String>();
    	for(String paramName:cmdDetail.getParamNames()){
    		if(cmdDetail.getParamNames().isEmpty()) break;
    		CommandParameter cp = cmdDetail.getDescriptionForParam(paramName);
    		String paramDesc = cp.getParamDescription();
    		sb.append("<p>");
    		sb.append(paramDesc + " : ");
    		if(cp.getParamType().equals(CommandParameterType.STRING))
    			sb.append("<input type = 'text' name='" + paramName + "' id='" + paramName + "'/>");
    		else if(cp.getParamType().equals(CommandParameterType.FILE))
    			sb.append("<input type='file' name='" + paramName + "' id='" + paramName + "'/>");
    		paramNames.add(paramName);
    		sb.append("</p>");
    	}
    	sb.append("</p>");
    	sb.append("</div>");
    	dataMap.put("htmlData", sb.toString());
    	dataMap.put("paramNames",gson.toJson(paramNames));
    	return gson.toJson(dataMap);
    }
    
    public static String getCommandList(){
    	String cmdList = CommandExecutorFactory.getCommandExecutor(AppConstants.getOperatingMode()).executeCommand("com.bds.cp.executors.CmdList");
    	log.info("Command List is : " + cmdList);
    	return cmdList;
    }

}
