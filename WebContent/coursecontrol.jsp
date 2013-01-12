<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="WEB-INF/tlds/spasTags.tld" prefix="st"%>
<st:validate />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="parts/head.html" />
<script type="text/javascript" src="javascript/submitconf.js"></script>
<title>SPAS - kurssihallinta</title>
</head>
<body>
<c:import url="parts/menu.jsp" />
<div id="content">
	<br />
	<c:when test="${empty param.earlier}">
		<a href="coursecontrol.jsp?earlier=true">Näytä menneet lukuvuodet.</a>
	</c:when>
	<c:otherwise>
		<a href="coursecontrol.jsp">Älä näytä menneitä lukuvuosia.</a>
	</c:otherwise>
	<br />
	<st:printcourses />
</div>
<c:import url="parts/footer.html" />
</body>
</html>