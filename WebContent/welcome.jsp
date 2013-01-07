<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="WEB-INF/tlds/spasTags.tld" prefix="st"%>
<st:validate />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="parts/head.html" />
<title>SPAS - Tervetuloa</title>
</head>
<body>
<c:import url="parts/menu.jsp" />
	<div id="content">
	<a href="search.jsp">Haku</a>
	<br />
	<a href="calendar.jsp">Kalenteri</a>
	<br />
	<a href="coursecontrol.jsp">Kurssien hallinta</a>
	</div>
	<c:import url="parts/footer.html" />
</body>
</html>