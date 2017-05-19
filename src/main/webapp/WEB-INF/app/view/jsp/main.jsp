<%@page import="java.util.Set"%>
<%@ page contentType="text/html; charset = UTF-8"%>

<html>

<head>
<script type="text/javascript" src="./res/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="./res/css/common.css">
<title>Command Processor - WEB</title>
</head>
<body onload="refreshCmdList()">
	<div class="pageContainer">
		<div id="cmdHeader" class="pageHeader">
			<span>Command Processor </span><span class="pageHeaderText">- The web based command Processing System.</span>
		</div>
		<div class="cmdContent">
			<span>Select a command below to be executed:</span>
			<div style="height:1px;"></div>
			<div>
				<select name="cmdList" id="cmdList" onChange="loadCommandDetails()">
				</select>
				<button type="button" name="refreshCmdList" id="refreshCmdList" onClick="refreshCmdList()">Refresh</button>
			</div>
			<div id="cmdDetails">
			</div>
			<div id = "cmdDetailJSON">
			</div>
			<div id='hiddenData'>
				<input type='hidden' id = 'paramNames' name='paramNames' value=''/>	
			</div>
		</div>
		<div id="console" class="console">
			<span id = "colsoneHeading" class="consoleHeading">Console</span>
		</div>
	</div>
</body>

</html>