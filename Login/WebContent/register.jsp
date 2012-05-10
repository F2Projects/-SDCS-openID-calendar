<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="org.openid4java.message.ParameterList, com.login.data.User, com.login.openid.GoogleLogin"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/Login/theme.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
<%
	GoogleLogin loginManager = (GoogleLogin)request.getSession().getAttribute("loginManager");
	User user = loginManager.getLoggedUser();
		
%>
</head>
<body>
<div class="titleBar">
	<h2>Registration</h2>
</div>
<div class="main" align="center">
	<div class="registrationForm"  align="center">
		<form action="/Login/remote/register" method="post">
			<table>
			<tr>
			<td>Real name: </td><td><% out.print(user.getName()); %></td>
			</tr>
			<tr>
			<td>Surname: </td><td><% out.print(user.getSurname()); %></td>
			</tr>
			<tr>
			<td>Role: </td><td><select id="role" name="role">
				<option value="2">Student</option>
				<option value="1">Teacher</option>
			</select></td>
			</tr>
			<tr>
			<td>Username: </td><td><% out.print(user.getUsername()); %><input type="hidden" name="username" id="username" value="<% out.print(user.getUsername()); %>"></td>
			</tr>
			<tr align="right">
			<td><input type="button" value="Back to index" onclick="location.href='/Login/index.jsp'"></td><td><input type="submit" value="Register!"></td>
			</tr>
			</table>
		</form>
	</div>
</div>
<div class="footer">
	This is a project for the exam of security and dependability of computing system 2011/2012; Authors are:<br>
	Pasquale Boemio m63000200<br>
	Antonio Bevilacqua m63000162<br>
	Danilo Cicalese m63000196
</div>
</body>
</html>