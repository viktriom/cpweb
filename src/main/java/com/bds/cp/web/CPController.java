package com.bds.cp.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bds.cp.core.util.CPUtil;
import com.bds.cp.core.util.web.WebUtil;
import com.bds.cp.web.util.CPWebUtil;
import com.google.gson.Gson;

/**
 * Created by Vivek Tripathi on 17/02/17.
 */

@Controller
@RequestMapping("/")
public class CPController{
	
	private static Logger log = Logger.getLogger(CPController.class);
	
    @RequestMapping(value={"/index"}, method = RequestMethod.GET)
    public String indexClass(ModelMap modelMap){
        WebUtil.initializeCPSystem();
        return "main";
    }
    
    
@RequestMapping(value = {"/commandList"}, method = RequestMethod.GET)
    public @ResponseBody String getCommands(ModelMap modelMap){
		log.info("Request for command list received.");
    	Gson gson = new Gson();
    	String jsonString = gson.toJson(WebUtil.getCommandList());
    	log.info("The command list send is : " + jsonString);
    	return jsonString;
    }
    
    @RequestMapping(value={"/cmdDetailsJSON/{commandName}"}, method=RequestMethod.GET)
    public @ResponseBody String getCommandMetaDataJOSN(@PathVariable(value="commandName") String commandName, 
    		ModelMap modelMap){
    	Gson gson = new Gson();
    	return gson.toJson(WebUtil.getCommandMetadata(commandName));
    }
    
    @RequestMapping(value={"/cmdDetailsHTML/{commandName:.+}"}, method=RequestMethod.GET)
    public @ResponseBody String getCommandMetadataHTML(@PathVariable(value="commandName") String commandName){
    	return WebUtil.prepareHTMLForCommandMetadata(commandName);
    }
    
    @RequestMapping(value={"/executeCommand/{commandString:.+}"}, method=RequestMethod.GET)
    public @ResponseBody String executeCommand(@PathVariable(value="commandString") String commandString){
    	log.info("Request to exeucte command " + commandString + "received. Strting the execution process now.");
    	return CPWebUtil.executeWebCommand(commandString);
    	
    }
}
