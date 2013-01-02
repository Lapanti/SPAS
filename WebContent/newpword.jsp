<jsp:useBean id="mailer" scope="page"
	class="spas.usercontrol.EmailNewPassword" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.jsp"%>
<%
	String email = request.getParameter("email");
	String userdb = application.getRealPath("resources");
%>
<body>
	<br />Hanki uusi salasana:
	<br />
	<form action="newpword.jsp" method="post">
		Sähköposti: <input type="text" name="email" value="<%=email%>" /><br />
		<input type="submit" name="newpword" value="Hae" />
	</form>
	<br />
	<%
		if (request.getParameter("newpword") != null) {
			if (mailer.createNewPassword(email, userdb)) {
				out.println("<p class=\"success\">Uusi salasanasi lähetettiin"
						+ " sähköpostiin.</p>");
			} else {
				out.println("<p class=\"error\">Antamallesi sähköpostille"
						+ " ei ole rekisteröity yhtäkään käyttäjää.");
			}
		}
	%>
</body>
</html>