
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
	cmdDetails = document.getElementById("cmdDetails");
	innerView = responseText + '<div><button type="button" name="executeCommand" id="executeCommand" onClick=executeCommand()>Execute Command</button></div>';
	cmdDetails.innerHTML = innerView;
}

function executeCommand(){
	alert("executeCommand() - Mehod Called..");
}

function refreshCmdList(){
	var url="/cpweb/commandList";
	httpGetAsync(url, populateCommandDropDown, "GET");
	
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
	
}

		