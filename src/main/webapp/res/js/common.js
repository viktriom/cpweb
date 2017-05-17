
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

function loadCommandDetails(){
	dropDown = document.getElementById("cmdList");
	value = dropDown.options[dropDown.selectedIndex].value;
	value = dropDown.value;
	url = "/cpweb/cmdDetailsHTML/" + value;
	//alert("The generated URL is : " + url);
	httpGetAsync(url, pupulateDataFromServer, "GET");
}

/**
To be called when json command data is received from the server.
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

function executeCommand(){
	var cmdString = prepareCommandString();
	url = "/cpweb/executeCommand/" + cmdString;
	httpGetAsync(url, addLogToConsole, "GET")
}

function refreshCmdList(){
	var url="/cpweb/commandList";
	httpGetAsync(url, populateCommandDropDown, "GET")	
}

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

function addLogToConsole(responseText){
	var innerView = document.getElementById("console").innerHTML;
	innerView = innerView + "<p>$:" + responseText + "</p>";
	document.getElementById("console").innerHTML = innerView;
}

		