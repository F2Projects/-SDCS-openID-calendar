<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="com.login.data.Subject, com.login.data.Event, com.login.data.UsersDB, com.login.data.User, com.login.openid.GoogleLogin"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/Login/theme.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String[] mounths = new String[]{
		"Gennary",
		"Febbrary",
		"March",
		"April",
		"June",
		"July",
		"Agoust",
		"September",
		"October",
		"November",
		"December",
	};
	
	User currentUser = null;
	GoogleLogin loginManager = (GoogleLogin)request.getSession().getAttribute("loginManager");
	if(loginManager==null){
		request.setAttribute("loginFailedMessage", "Are you trying to fuck me? You must login to access to private area...");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		currentUser = new User();
	} else {
		currentUser = (User)loginManager.getLoggedUser();
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
			<input type="submit" value="Logout">
		</form>
	</div>
	<div class="fileBrowser">
		<form action="/Login/remote/save" method="post">
			<%
				if(currentUser.getRole().equals("teacher")){
					out.print("<fieldset>");
					out.print("<legend>Add new exam:</legend>");
					out.print("<select name=\"subject\" id=\"subject\">");
					for(Subject s : UsersDB.getDb().getSubjectList())
						out.print("<option value=\""+s.getAcronim()+"\">"+s.getAcronim() + " - " +s.getName()+"</option>");
					out.print("</select><br>");
					out.print("<select name=\"day\" id=\"day\">");
					for(int i=1; i<=31; i++)
						out.print("<option value=\""+i+"\">"+i+"</option>");
					out.print("</select> ");
					out.print("<select name=\"mounth\" id=\"mount\">");
					for(int i=0; i<11; i++)
						out.print("<option value=\""+i+"\">"+mounths[i]+"</option>");
					out.print("</select> ");
					out.print("<input type=\"text\" name=\"year\" id=\"year\"><br>");
					out.print("<textarea name=\"comments\" id=\"comments\" rows=\"2\" cols=\"20\"></textarea><br>");
					out.print("<input type=\"submit\" value=\"Save\">");
					out.print("</fieldset>");
				}
			%>
			<table>
			<%
				int index=0;
				for(Event e : UsersDB.getDb().getEventList()){
					if((index++%2)==0)
						out.print("<tr bgcolor=\"#FF0000\">");
					else
						out.print("<tr>");
					out.print("<td>" + e.getSubject() + "<br>");
					out.print(e.getDay() + " " + mounths[e.getMounth()] + " " + e.getYear() + "<br>");
					out.print(e.getComment() + "</td>");
					out.print("</tr>");
				}
			%>
			</table>
		</form>
	</div>
</div>
<div class="message" id="messageContainer">
<% 
	String message = (String)request.getAttribute("saveStatus");
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