<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="WEB-INF/tlds/spasTags.tld" prefix="st"%>
<st:validate />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.html"%>
<body>
	<a href="usercontrol.jsp"><c:out value="${sessionScope.username}" /></a>,
	<a href="logout.jsp">kirjaudu ulos</a>.
	<br />
	<br />
	<br />
	<a href="search.jsp">Haku</a>
	<br />
	<a href="calendar.jsp">Kalenteri</a>
	<br />
	<a href="coursecontrol.jsp">Kurssien hallinta</a>
</body>
</html>