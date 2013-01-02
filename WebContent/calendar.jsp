<%@ include file="parts/authcheck.jsp"%>
<%@ page import="spas.usercontrol.CalendarCreator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.jsp"%>
<%
	String userfile = application.getRealPath("resources/" + username
			+ ".xml");
	String icsfile = application.getRealPath("resources/ics/"
			+ username + ".ics");
	boolean success = true;
	if (request.getParameter("refresh") != null) {
		CalendarCreator calendar = new CalendarCreator(userfile,
				icsfile);
		success = calendar.createCalendar();
	}
%>
<body>
	<br />Alla oleva linkki johtaa kalenteriisi (online-importtaus, esim.
	gmail).
	<br />
	<textarea rows="1" readonly><%=userfile%></textarea>
	<br />Lataa
	<a href="<%=userfile%>" target="_blank">tästä</a>.
	<br />
	<br />
	<form action="calendar.jsp" method="post">
		<input type="submit" name="refresh" value="Luo/päivitä kalenteri" />
	</form>
	<br />
	<%
		if (!success) {
			out.println("<p class=\"error\">Luomisessa/päivityksessä "
					+ "oli ongelma, yritä myöhemmin uudelleen.</p>");
		}
	%>
</body>
</html>