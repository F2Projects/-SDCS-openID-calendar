<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="com.login.data.User, java.io.File"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/Login/theme.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	User currentUser = (User)request.getAttribute("current_user");
	File[] repoFiles = (File[])request.getAttribute("repoFiles");
	if(currentUser==null){
		request.setAttribute("loginFailedMessage", "Are you trying to fuck me? You must login to access to private area...");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		currentUser = new User();
	}
	if(repoFiles==null){
		repoFiles = new File[0];
	}
%>
<title><%out.print(currentUser.getName() + " " + currentUser.getSurname());%>'s Profile</title>
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
		<form action="/Login/remote/logout" method="post">
		<input type="hidden" id="username" name="username" value="<% out.print(currentUser.getUsername()); %>">
		<input type="submit" value="Logout">
		</form>
	</div>
	<div class="fileBrowser">
		<form action="/Login/remote/upload" enctype="multipart/form-data" method="post">
			<fieldset>
			<legend>File Browser</legend>
		<%
			if(currentUser.getRole().equals("Teacher"))
				out.print("<input type=\"file\" name=\"selectedFile\" id=\"selectedFile\"> <input type=\"submit\" value=\"Upload\"> <hr>");
			
			out.print("<table>");
			for(File f : repoFiles){
				String fileName;
				if(currentUser.getRole().equals("Student") || currentUser.getRole().equals("Teacher"))
					fileName="<a href=\"\\Login\\Repo\\" +f.getName() +"\">" + f.getName() + "</a>";
				else
					fileName=f.getName();
				out.print("<tr><td>" + fileName + "</tr></td>");
			}
			out.print("</table");
			
		%>
			
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