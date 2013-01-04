<%@ taglib uri="WEB-INF/tlds/spasTags.tld" prefix="st" %>
<st:validate />
<jsp:useBean id="nReader" scope="session" class="spas.nreading.NReader" />
<%@ page import="java.util.*" import="spas.nelements.*"
	import="spas.usercontrol.UserCourseHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.html"%>
<script type="text/javascript" src="javascript/submitconf.js"></script>
<body>
	<st:printcourse />
	<br />
	<br />
	<a href="search.jsp">Takaisin</a>
</body>
</html>