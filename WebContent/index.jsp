<%@ page import="spas.usercontrol.LoginTools"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.jsp"%>
<%
	String logusername = (String) session.getAttribute("username");
	if (logusername != null) {
		String loguserpath = application.getRealPath("resources/"
				+ logusername + ".xml");
		LoginTools logauth = new LoginTools(loguserpath);
		if (logauth.userExists(logusername)) {
			response.sendRedirect("welcome.jsp");
		}
	}
	String username = request.getParameter("username");
	String pword = request.getParameter("pword");
	String keeplogged = request.getParameter("keeplogged");
	boolean login = false;
	if (username != null && !username.equals("") && pword != null
			&& !pword.equals("")) {
		if (keeplogged != null && !keeplogged.equals("")) {
			session.setMaxInactiveInterval(0);
		}
		String resourcepath = application
				.getRealPath("resources/model/user.xml");
		String userpath = application.getRealPath("resources/"
				+ username + ".xml");
		LoginTools auth = new LoginTools(userpath);
		login = auth.authenticate(username, pword);
	}
	if (login) {
		session.setAttribute("username", username);
		response.sendRedirect("welcome.jsp");
	}
%>
<body>
	SPAS - Study Plan And Schedule.
	<br />
	<br />
	<form action="index.jsp" method="post">
		Käyttäjänimi:<br /> <input type="text" name="username"
			<%if (username != null) {
				out.println("value=\"" + username + "\"");
			}%> /><br />
		Salasana:<br /> <input type="password" name="pword" /><br /> <input
			type="submit" value="Kirjaudu" /> <br /> <input type="checkbox"
			name="keeplogged" value="true">Pidä minut
		sisäänkirjautuneena.
	</form>
	<%
		if ((username == null && pword != null && !pword.equals(""))
				|| (username != null && !username.equals("") && pword == null)) {
			out.println("<p class=\"error\">Et antanut käyttäjänimeä tai salasanaa.</p>");
		} else if (username != null && pword != null) {
			out.println("<p class=\"error\">Käyttäjänimi tai salasana oli väärin!</p>");
		}
	%>
	<br />
	<a href="newpword.jsp">Unohditko salasanasi?</a>
	<a href="register.jsp">Rekisteröidy.</a>
	<br />
	<br />
	<br /> Tulossa:
	<br /> Viikkonäkymä.
	<br /> Kielen vaihtaminen (englanti ja suomi).
	<br /> Periodi-/vuosikohtainen kurssien suoritus.
	<br />
</body>
</html>