<jsp:useBean id="nReader" scope="session" class="spas.reading.XMLToObject" />
<%@ page import="java.util.*" import="spas.nelements.Course"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String name = request.getParameter("name");
	String id = request.getParameter("id");
	String title = " - kurssi " + name;
%>
<%@ include file="head.jsp"%>
<body>
	<%
		Course c;
		try {
			c = nReader.getCourseOverview(id, name);
		} catch (Exception ex) {
			c = new Course();
			c.setName(request.getParameter("name"));
			c.setId(request.getParameter("id"));
		}
		String credits = c.getCredits();
		String periods = c.getPeriod();
		String content = c.getContent();
	%>
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
</body>
</html>