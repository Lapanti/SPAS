<%@ page import="spas.usercontrol.LoginTools"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.jsp"%>
<%
	String username = request.getParameter("username");
	String pword = request.getParameter("pword");
	String pword2 = request.getParameter("pword2");
	String email = request.getParameter("email");
	boolean register = false;
	LoginTools auth;
	if (username != null && !username.equals("") && pword != null
			&& !pword.equals("") && email != null
			&& pword.equals(pword2)) {
		String userpath = application.getRealPath("resources/"
				+ username + ".xml");
		String resourcepath = application
				.getRealPath("resources/model/user.xml");
		auth = new LoginTools(userpath);
		register = auth
				.createUser(username, pword, email, resourcepath);
	} else {
		auth = null;
	}
%>
<body>
	<%
		if (!register) {
	%>
	<form action="register.jsp" method="post">
		K�ytt�j�nimi:<br /> <input type="text" name="username"
			value="<%=username%>" /><br /> S�hk�posti:<br /> <input
			type="text" name="email" value="<%=email%>" /><br /> Salasana:<br />
		<input type="password" name="pword" /><br /> Toista salasana:<br />
		<input type="password" name="pword2" /><br /> <input type="submit"
			name="Rekister�idy" />
	</form>
	<%
		if (pword != null && !pword.equals(pword2)) {
				out.println("<p class=\"error\">Antamasi salasanat eiv�t vastanneet toisiaan.</p>");
			}
			if (username != null && auth != null && !register
					&& auth.userExists(username)) {
				out.println("<p class=\"error\">K�ytt�j�tunnus on jo k�yt�ss�</p>");
			}

		} else {
			out.println("Rekister�inti onnistui.");
			if (email.equals("")) {
				out.println("<br/><p class\"warning\">Et antanut s�hk�postia."
						+ " Asia kannattaa korjata heti kirjauduttuasi, sill�"
						+ " mik�li unohdat salasanasi, on s�hk�postisi ainoa"
						+ " tapa palauttaa salasana.</p>");
			}
		}
	%>
	<a href="index.jsp">Takaisin</a>
</body>
</html>