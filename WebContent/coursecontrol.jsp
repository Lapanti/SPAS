<%@ taglib uri="WEB-INF/tlds/spasTags.tld" prefix="st" %>
<st:validate />
<jsp:useBean id="nReader" scope="page" class="spas.nreading.NReader" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="spas.nelements.*" import="java.util.*"
	import="spas.usercontrol.UserCourseHandler"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.html"%>
<script type="text/javascript" src="javascript/submitconf.js"></script>
<body>
	<br />
	<br /> Kurssit:
	<br />
	<st:courseedit />
</body>
</html>