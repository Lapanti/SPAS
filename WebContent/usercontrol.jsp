<%@ include file="parts/authcheck.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="spas.nelements.NCourse" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.jsp"%>
<script type="text/javascript" src="javascript/submitconf.js"></script>
<%
	String oldmail = auth.getEmail(username);
	String newmail = request.getParameter("newmail");
	String newmail2 = request.getParameter("newmail2");
	String epword = request.getParameter("epword");
	String oldpword = request.getParameter("oldpword");
	String newpword = request.getParameter("newpword");
	String newpword2 = request.getParameter("newpword2");
	String pword = request.getParameter("pword");
%>
<body>
	<%=username%>,
	<a href="logout.jsp">kirjaudu ulos.</a>
	<br />
	<br />Vaihda sähköposti:
	<br />
	<form action="usercontrol.jsp" method="post">
		Uusi sähköposti: <input type="text" name="newmail"
			value="<%=newmail == null ? oldmail : newmail%>" /><br /> Toista
		sähköposti: <input type="text" name="newmail2" value="<%=newmail2%>" />
		<br />Salasana: <input type="password" name="epword" /> <br /> <input
			type="submit" name="changemail" value="Vaihda sähköposti" />
	</form>
	<%
		if (request.getParameter("changemail") != null
				&& newmail.equals(newmail2)
				&& auth.changeEmail(username, epword, newmail)) {
			out.println("<p class=\"success\">Sähkpostin vaihto onnistui.</p>");
		} else if (!newmail.equals(newmail2)) {
			out.println("<p class\"warning\">Antamasi sähköpostit eivät"
					+ " vastanneet toisiaan</p>");
		} else {
			out.println("<p class=\"error\">Antamasi salasana oli väärin</p>");
		}
	%>
	<br />
	<br /> Vaihda salasana:
	<form action="usercontrol.jsp" method="post">
		Vanha salasana: <input type="password" name="oldpword" /><br /> Uusi
		salasana: <input type="password" name="newpword" /><br /> Toista
		uusi salasana: <input type="password" name="newpword2" /><br /> <input
			type="submit" name="changepword" value="Vaihda salasana" />
	</form>
	<%
		if (request.getParameter("changepword") != null
				&& newpword.equals(newpword2)
				&& auth.changePassword(username, oldpword, newpword)) {
			out.println("<p class=\"success\">Salasanan vaihto onnistui.</p>");
		} else if (!newpword.equals(newpword2)) {
			out.println("<p class=\"warning\">Antamasi salasanat eivät "
					+ "vastanneet toisiaan.</p>");
		} else {
			out.println("<p class=\"error\">Antamasi salasana oli väärin</p>");
		}
	%>
	<br />
	<br /> Poista käyttäjä:
	<form action="usercontrol.jsp" method="post"
		onsubmit="return confirmSubmit()">
		Salasana: <input type="password" name="pword" /><br /> <input
			type="submit" name="removeuser" value="Poista käyttäjä" />
	</form>
</body>
</html>