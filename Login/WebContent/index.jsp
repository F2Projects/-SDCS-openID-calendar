<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/Login/theme.css">
<title>Security and Dependability of Computer Systems</title>
</head>
<body>
<div class="titleBar">
	<h2>Login</h2>
</div>
<div class="main">
	<div class="welcome" align="justify">
		Welcome to SDCS Project.<br>
	</div>
	<div class="loginForm">
		<form action="/Login/remote/login" method="post">
			<input type="submit" value="Login with Google">
		</form>
	</div>
</div>
<div class="message" id="messageContainer">
<%
	String message = (String)request.getAttribute("loginFailedMessage");
	if(message!=null)
		out.print(message);
%>
</div>
<div class="footer">
	This is a project for the exam of security and dependability of computing system 2011/2012; Authors are:<br>
	Pasquale Boemio m63000200<br>
	Antonio Bevilacqua m63eccecc<br>
	Danilo Cicalese m63eccecc
</div>
</body>
</html>