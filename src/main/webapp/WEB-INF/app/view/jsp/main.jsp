<%@page import="java.util.Set"%>
<%@page import="com.bds.cp.core.util.web.WebUtil"%>
<%@ page contentType="text/html; charset = UTF-8"%>

<html>

<head>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<script type="text/javascript"	src="/script/js/controller/commandController.js"></script>
<script type="text/javascript" src="/script/js/common.js"></script>
<title>Command Processor - WEB</title>
</head>
<body onload="refreshCmdList()">
	<div>
		<div style="height:30px;"></div>
		Select a command below to be executed:
		<div style="height:10px;"></div>
		<div>
			<select name="cmdList" id="cmdList" onChange="loadCommandDetails()">
			</select>
			<button type="button" name="refreshCmdList" id="refreshCmdList" onClick="refreshCmdList()">Refresh</button>
		</div>
		<div id="cmdDetails">
		</div>
	</div>
	<script>
	
		function httpGetAsync(theUrl, callback)
		{
		    var xmlHttp = new XMLHttpRequest();
		    xmlHttp.onreadystatechange = function() { 
		        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
		            callback(xmlHttp.responseText);
		    }
		    xmlHttp.open("GET", theUrl, true); // true for asynchronous 
		    xmlHttp.send(null);
		}
	
		function loadCommandDetails(){
			dropDown = document.getElementById("cmdList");
			value = dropDown.options[dropDown.selectedIndex].value;
			value = dropDown.value;
			url = "/cpweb/cmdDetailsHTML/" + value;
			//alert("The generated URL is : " + url);
			httpGetAsync(url, pupulateDataFromServer);
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
			httpGetAsync(url, populateCommandDropDown);
			
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
		
		angular.module('cpweb', [])
		.controller('cmdController', function($scope, $http) {
		    $http.get('/commandList').
		        then(function(response) {
		            $scope.commands = response.data;
		        });
		});
		
		var cmdMetadata = angular.module('cmdDetail', []);
		
		cmdMetadata.controller('cmdDetailCntrlr', function ($scope, $http){
			$scope.update = function () {
				
			};
		});
		
	</script>
</body>

</html>