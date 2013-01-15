<%@ taglib uri="WEB-INF/tlds/spasTags.tld" prefix="st"%>
<st:validate />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	SPAS - Study Plan And Schedule.
	<br />
	<br />
	<br /> SPAS on palvelu, jolla Aalto-yliopiston opiskelijat voivat
	<br /> suunnitella opintojaan (näyttää kuinka monta opintopistettä 
	<br /> on per periodi) ja generoida lukujärjestyksen nykyiselle periodille.
	<br />
	<br />
	<br /> Tulossa:
	<br /> Viikkonäkymä.
	<br /> Kielivaihtoehdot (suomi, englanti).
	</div>
	<c:import url="parts/footer.html" />
</body>
</html>