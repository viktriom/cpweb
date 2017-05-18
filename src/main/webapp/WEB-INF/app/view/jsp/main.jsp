<%@page import="java.util.Set"%>
<%@ page contentType="text/html; charset = UTF-8"%>

<html>

<head>
<script type="text/javascript" src="./res/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="./res/css/common.css">
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
		<div id = "cmdDetailJSON">
		</div>
		<div id='hiddenData'>
			<input type='hidden' id = 'paramNames' name='paramNames' value=''/>	
		</div>
		<div id="console">
		</div>
	</div>
</body>

</html>