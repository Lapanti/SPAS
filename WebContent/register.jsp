<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="WEB-INF/tlds/spasTags.tld" prefix="st"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="parts/head.html" />
<title>SPAS - Rekisteröidy</title>
</head>
<body>
	<c:import url="parts/menu.jsp" />
	<div id="content">
		Rekisteröidy<br/><br/>
		<form action="register.jsp" method="post">
			Käyttäjänimi: <input type="text" name="username"
				value="${!empty param.username ? param.username : ''}" /><br /><br />
			Sähköposti: <input type="text" name="email"
				value="${!empty param.email ? param.email : ''}" /><br /><br />
			Salasana: <input type="password" name="pword" /><br /><br />
			Toista salasana: <input type="password" name="pword2" /><br /><br />
			<input type="submit" name="register" value="Rekisteröidy" />
		</form>
		<c:if test="${!empty param.register}">
			<st:register />
		</c:if>
	</div>
	<c:import url="parts/footer.html" />
</body>
</html>