<%@ taglib uri="WEB-INF/tlds/spasTags.tld" prefix="st"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="parts/head.html" />
<title>SPAS - Unohditko salasanasi?</title>
</head>
<body>
<c:import url="parts/menu.jsp" />
<div id="content">
	<br />Hanki uusi salasana:
	<br />
	<form action="newpword.jsp" method="post">
		Sähköposti: <input type="text" name="email"
			value="${empty param.email ? '' : param.email }" /><br /> <input
			type="submit" name="newpword" value="Hae" />
	</form>
	<br />
	<c:if test="${!empty param.newpword}">
		<st:newpword />
	</c:if>
	</div>
	<c:import url="parts/footer.html" />
</body>
</html>