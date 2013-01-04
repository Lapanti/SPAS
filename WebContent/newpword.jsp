<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/spasTags.tld" prefix="st"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.html"%>
<body>
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
</body>
</html>