/**
 * Method place a asunchronus HTTP request to the server for theUrl, and calls back the clallback once success.
 * @param theUrl - The url to which the reques is to be placed.
 * @param callback - The method to be called back once the request is completed successfully.
 * @param method - The method [GET/POST] used to send data to the server.
 */
function httpGetAsync(theUrl, callback, method)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open(method, theUrl, true); // true for asynchronous 
    xmlHttp.send(null);
}

/**
 * Gets the command details from the server and populates the command drop down.
 */
function loadCommandDetails(){
	dropDown = document.getElementById("cmdList");
	value = dropDown.options[dropDown.selectedIndex].value;
	value = dropDown.value;
	url = "/cpweb/cmdDetailsHTML/" + value;
	httpGetAsync(url, pupulateDataFromServer, "GET");
	url = "/cpweb/cmdDetailsJSON/" + value;
	httpGetAsync(url, prepareCommandDetailFromJson, "GET");
}

/**
To be called when HTML command data is received from the server.
This can even handle HTML Data. And will try to substitute the html in place.
*/
function pupulateDataFromServer(responseText){
	var cmdData = JSON.parse(responseText);
	var cmdParamsData = cmdData.htmlData;
	var cmdParamNames = cmdData.paramNames;
	var txtParamNames = document.getElementById("paramNames");
	txtParamNames.value = cmdParamNames;
	cmdDetails = document.getElementById("cmdDetails");
	innerView = cmdParamsData + '<div><button type="button" name="executeCommand" id="executeCommand" onClick=executeCommand()>Execute Command</button></div>';
	cmdDetails.innerHTML = innerView;
}

/**
 * The method does the following:
 *  1. Receive the command description and command detail from server for selected command.
 *  2. Prepare HTML elements to represent the command details and parameters required by command.
 * @param responseText - The command detail in JSON format received from server.
 */
function prepareCommandDetailFromJson(responseText){
	var cmdData = JSON.parse(responseText);
	document.getElementById("cmdDetailJSON").innerHTML = "";
	var cmdDetailDiv = document.getElementById("cmdDetailJSON");
	createAndAddElement(cmdDetailDiv, "Command Name is : ", cmdData.commandName);
	createAndAddElement(cmdDetailDiv, "", cmdData.commandDescription);
	
}

function createAndAddElement(parent, label, val){
	var para = document.createElement("p");
	var spanLabel = document.createElement("span");
	var spanVal = document.createElement("span");
	var label = document.createTextNode(label);
	var val = document.createTextNode(val);
	spanLabel.appendChild(label);
	spanVal.appendChild(val);
	spanLabel.setAttribute("class", "cmdLabel");
	spanVal.setAttribute("class","cmdValue");
	para.appendChild(spanLabel);
	para.appendChild(spanVal);
	parent.appendChild(para);
}

function executeCommand(){
	var cmdString = prepareCommandString();
	url = "/cpweb/executeCommand/" + cmdString;
	httpGetAsync(url, addLogToConsole, "GET")
}

/*
 * Refreshes the command list.
 */
function refreshCmdList(){
	var url="/cpweb/initCPSystem";
	httpGetAsync(url, populateCommandDropDown, "GET");
	var url="/cpweb/commandList";
	httpGetAsync(url, populateCommandDropDown, "GET");
	
}

/**
 * Method is actually responsible for 
 * @param responseText
 */
function populateCommandDropDown(responseText){
	document.getElementById("cmdList").options.length = 0;
	var commands = JSON.parse(responseText);
	var cmdDropDown = document.getElementById("cmdList");
	for(var i = 0; i < commands.length; i++){
		var newOption = document.createElement('option');
        newOption.text = newOption.value = commands[i];
		cmdDropDown.add(newOption);
	}
}


/**
 * Prepare the command string which is to be sent to the server.
 * @returns The Command to be executed in the string format.
 */
function prepareCommandString(){
	dropDown = document.getElementById("cmdList");
	cmdFullName = dropDown.options[dropDown.selectedIndex].value;
	cmdFullName = dropDown.value;
	var txtParamNames = document.getElementById("paramNames");
	var paramNames = JSON.parse(txtParamNames.value);
	var cmdString = cmdFullName;
	for(var i = 0; i < paramNames.length; i++){
		pName = paramNames[i];
		var txtParamValue = document.getElementById(pName);
		cmdString = cmdString + " -" + pName + " " + txtParamValue.value;
	}
	return cmdString;
}

/**
 * Adds the given line to the 
 * @param responseText
 */
function addLogToConsole(responseText){
	var consoleLine = document.createElement("p");
	var cmdPromptSpan = document.createElement("span");
	var cmdText = document.createElement("span");
	var response = JSON.parse(responseText);
	var cmdName = document.createTextNode("$:"+response.cmdName+"> ");
	var cmdMsg = document.createTextNode(response.text);
	
	cmdPromptSpan.setAttribute("class", "consolePrompt");
	cmdText.setAttribute("class","consoleText");
	
	cmdPromptSpan.appendChild(cmdName);
	cmdText.appendChild(cmdMsg);
	
	consoleLine.appendChild(cmdPromptSpan);
	consoleLine.appendChild(cmdText);
	
	document.getElementById("console").appendChild(consoleLine);
	document.getElementById("console").style.display = "block";
}

		