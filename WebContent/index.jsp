<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/spasTags.tld" prefix="st"%>
<c:if test="${!empty sessionScope.username}">
	<c:redirect url="welcome.jsp"/>
</c:if>
<%@ page import="spas.usercontrol.UserHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.html"%>
<body>
	SPAS - Study Plan And Schedule.
	<br />
	<br />
	<form action="index.jsp" method="post">
		Käyttäjänimi:<br /> <input type="text" name="username"
			value="${!empty param.username ? param.username : ''}" /><br />
		Salasana:<br /> <input type="password" name="pword" /><br /> <input
			type="submit" name="login" value="Kirjaudu" /> <br /> <input
			type="checkbox" name="keeplogged" value="true">Pidä minut
		sisäänkirjautuneena.
	</form>
	<c:if test="${!empty param.login}">
		<st:login />
		<p class="error">Antamasi käyttäjänimi tai salasana oli väärin.</p>
	</c:if>
	<br />
	<a href="newpword.jsp">Unohditko salasanasi?</a>
	<a href="register.jsp">Rekisteröidy.</a>
	<br />
	<br />
	<br /> Tulossa:
	<br /> Viikkonäkymä.
	<br /> Kielen vaihtaminen (englanti ja suomi).
	<br /> Periodi-/vuosikohtainen kurssien suoritus.
	<br />
</body>
</html>