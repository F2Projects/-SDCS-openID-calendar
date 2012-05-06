<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="com.login.data.User, com.login.openid.GoogleLogin"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/Login/theme.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	User currentUser = (User)((GoogleLogin)request.getSession().getAttribute("loginManager")).getLoggedUser();
	if(currentUser==null){
		request.setAttribute("loginFailedMessage", "Are you trying to fuck me? You must login to access to private area...");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		currentUser = new User();
	}
%>
<title><%out.print(currentUser.getName() + " " + currentUser.getSurname());%>'s Profile</title>
<script>
	function logoutGoogle(){	
		xmlhttp=new XMLHttpRequest();
		xmlhttp.open("GET","https://www.google.com/accounts/Logout",false);
		xmlhttp.send();
	}
</script>
</head>
<body>
<div class="titleBar">
	<h2>Profile</h2>
</div>
<div class="main">
	<div class="welcome" align="center">
		Hi, <br> 
		I'm <%out.print(currentUser.getName() + " " + currentUser.getSurname());%> and I'm a <% out.print(currentUser.getRole()); %><br>
		I'm logged in as <% out.print(currentUser.getUsername()); %><br>
		<form action="#" onsubmit="logoutGoogle();" method="post">
		<input type="hidden" id="username" name="username" value="<% out.print(currentUser.getUsername()); %>">
		<input type="submit" value="Logout">
		</form>
	</div>
	<div class="fileBrowser">
		<form action="/Login/remote/upload" enctype="multipart/form-data" method="post">
			<fieldset>
			<legend>Exams:</legend>
			
			</fieldset>
			<input type="hidden" name="username" id="username" value="<%out.print(currentUser.getUsername());%>">
		</form>
	</div>
</div>
<div class="message" id="messageContainer">
<% 
	String message = (String)request.getAttribute("uploadStatus");
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