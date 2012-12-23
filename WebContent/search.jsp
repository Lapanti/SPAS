<jsp:useBean id="nReader" scope="session"
	class="spas.reading.XMLToObject" />
<%@ page import="java.util.*" import="spas.nelements.*"
	import="java.net.URLEncoder" import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String org = request.getParameter("org");
	String dept = request.getParameter("dept");
	String kword = request.getParameter("kword");
	if (org != null && org.equals("")) {
		org = null;
	}
	if (dept != null && dept.equals("")) {
		dept = null;
	}
	if (kword != null && kword.equals("")) {
		kword = null;
	}
	String title = " - haku";
%>
<%@ include file="head.jsp"%>
<script>
	function selectedOrganization() {
		var selectElem = document.forms[0].elements["org"];
		var value = selectElem[selectElem.selectedIndex].value;
		var href = location.href;
		if (href.indexOf("org=") !== -1) {
			var substr = href.substring(href.indexOf("org=") + 4);
			href.replace(substr, value);
		} else {
			href = href + "?org=" + value;
		}
		window.location.replace(href);
	}
</script>
<body>
	<form name="search" action="search.jsp" method="get">
		Avainsana: <input type="text" name="kword"
			<%if (kword != null) {
				out.print("value=\"" + kword + "\"");
			}%> />
		<br /> Organisaatio: <select name="org"
			onchange="selectedOrganization()">
			<option value="" <%=org == null ? "" : "selected"%>>--
				valitse organisaatio</option>
			<%
				List<Nelement> orgs = nReader.getOrganizations();
				if (orgs == null) {
					System.out.println("orgs on null");
				}
				if (orgs != null) {
					String selected = "";
					for (Nelement ne : orgs) {
						if (org != null && org.equals(ne.getId())) {
							selected = "selected";
						} else {
							selected = "";
						}
						out.println("<option value=\"" + ne.getId() + "\""
								+ selected + ">" + ne.getName() + "</option>");
					}
				}
			%>
		</select> <br /> Osasto: <select name="dept">
			<option value="" <%=dept == null ? "" : "selected"%>>--
				valitse osasto</option>
			<%
				List<Nelement> depts = nReader.getDepartments(org);
				if (depts == null) {
					System.out.println("depts on null");
				}
				if (depts != null) {
					for (Nelement ne : depts) {
						String selected = "";
						if (dept != null && dept.equals(ne.getId())) {
							selected = "selected";
						}
						out.println("<option value=\"" + ne.getId() + "\""
								+ selected + ">" + ne.getName() + "</option>");
					}
				}
			%>
		</select> <br /> <input type="submit" name="search" value="Hae"><br />
		<br />
		<%
			if (request.getParameter("search") != null) {
				List<Course> courses = nReader.getCourses(org, dept, kword);
				if (courses != null) {
					for (Course c : courses) {
						out.println("<a href=\"course.jsp?id=" + c.getId()
								+ "&name=" + c.getName() + "\">" + c.getName()
								+ "</a><br/>");

					}
				}
			}
		%>
	</form>
</body>
</html>