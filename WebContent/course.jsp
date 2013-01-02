<%@ include file="parts/authcheck.jsp"%>
<jsp:useBean id="nReader" scope="session" class="spas.nreading.NReader" />
<%@ page import="java.util.*" import="spas.nelements.*"
	import="spas.usercontrol.UserCourseHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.jsp"%>
<script type="text/javascript" src="javascript/submitconf.js"></script>
<%
	String name = request.getParameter("name");
	String id = request.getParameter("id");
	if (name == null && id == null) {
		response.sendRedirect("search.jsp");
	}
	NCourse c = nReader.getCourseOverview(id, name);
	if (c.getName().equals("")) {
		c.setName(request.getParameter("name"));
	}
	if (c.getId().equals("")) {
		c.setId(request.getParameter("id"));
	}
	String credits = c.getCredits();
	String periods = c.getPeriod();
	String content = c.getContent();
%>
<body>
	<%=name%><br />
	<table>
		<tr>
			<td>Tunniste:</td>
			<td><%=id%></td>
		</tr>
		<tr>
			<td>Opintopisteet:</td>
			<td><%=credits%></td>
		</tr>
		<tr>
			<td>Opetusperiodi(t):</td>
			<td><%=periods%></td>
		</tr>
		<tr>
			<td>Sisältö:</td>
			<td><%=content%></td>
		</tr>
	</table>
	<br />
	<br />
	<%
		UserCourseHandler handler = new UserCourseHandler(
				application.getRealPath("resources/" + username + ".xml"));
		if (request.getParameter("remove") != null
				&& handler.removeCourse(id)) {
			out.println("<p class=\"success\">Kurssi poistettiin"
					+ " onnistuneesti.</p>");
		}
		if (handler.containsCourse(id)) {
			out.println("Kurssi on lisättynä käyttäjällesi.<br/>");
			out.println("<form onsubmit=\"return confirmSubmit()\" "
					+ "action=\"course.jsp?id=" + id + "&name=" + name
					+ "\" method=\"post\">");
			out.println("<input type=\"submit\" name=\"remove\""
					+ " value=\"Poista\" />");
			out.println("</form>");
		} else {
			if (request.getParameter("add") != null
					&& handler.addCourse(name, id)) {
				out.println("<p class=\"success\">Kurssi lisättiin"
						+ " onnistuneesti.</p>");
			} else {
				out.println("<form action=\"course.jsp?id=" + id + "&name="
						+ name + "\" method=\"post\">");
				out.println("<input type=\"submit\" name=\"add\" value=\"Lisää\" />");
				out.println("</form>");
			}
		}
	%>
	<br />
	<br />
	<a href="search.jsp">Takaisin</a>
</body>
</html>