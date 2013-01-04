<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/spasTags.tld" prefix="st"%>
<st:validate />
<%@ page import="spas.usercontrol.CalendarCreator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.html"%>
<body>
	<br />Alla oleva linkki johtaa kalenteriisi (online-importtaus, esim.
	gmail).
	<br />
	<textarea rows="1" readonly>localhost:8080${pageContext.request.contextPath}/resources/ics/${sessionScope.username}.ics</textarea>
	<br />Lataa
	<a
		href="localhost:8080${pageContext.request.contextPath}/resources/ics/${sessionScope.username}.ics"
		target="_blank">tästä</a>.
	<br />
	<br />
	<form action="calendar.jsp" method="post">
		<input type="submit" name="refresh" value="Luo/päivitä kalenteri" />
	</form>
	<br />
	<c:if test="${!empty param.refresh}">
		<st:calendar />
	</c:if>
</body>
</html>