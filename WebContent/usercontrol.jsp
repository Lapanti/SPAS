<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/spasTags.tld" prefix="st"%>
<st:validate />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="spas.nelements.NCourse"
	import="spas.usercontrol.LoginTools" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.html"%>
<script type="text/javascript" src="javascript/submitconf.js"></script>
<body>
	<a href="logout.jsp">kirjaudu ulos.</a>
	<br />
	<br />Vaihda s�hk�posti:
	<br />
	<form action="usercontrol.jsp" method="post">
		Uusi s�hk�posti: <input type="text" name="newmail"
			value="${empty param.newmail ? '' : param.newmail}" /><br /> Toista
		s�hk�posti: <input type="text" name="newmail2" /> <br />Salasana: <input
			type="password" name="epword" /> <br /> <input type="submit"
			name="changemail" value="Vaihda s�hk�posti" />
	</form>
	<c:if test="${!empty param.changemail}">
		<st:changemail />
	</c:if>
	<br />
	<br /> Vaihda salasana:
	<form action="usercontrol.jsp" method="post">
		Vanha salasana: <input type="password" name="oldpword" /><br /> Uusi
		salasana: <input type="password" name="newpword" /><br /> Toista
		uusi salasana: <input type="password" name="newpword2" /><br /> <input
			type="submit" name="changepword" value="Vaihda salasana" />
	</form>
	<c:if test="${!empty param.changepword}">
		<st:changepword />
	</c:if>
	<br />
	<br /> Poista k�ytt�j�:
	<form action="usercontrol.jsp" method="post"
		onsubmit="return confirmSubmit()">
		Salasana: <input type="password" name="pword" /><br /> <input
			type="submit" name="removeuser" value="Poista k�ytt�j�" />
	</form>
	<c:if test="${!empty param.removeuser}">
		<st:removeuser />
	</c:if>
</body>
</html>