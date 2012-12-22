<jsp:useBean id="nReader" scope="session" class="noppa.NoppaReader" />
<%@ page import="java.util.*" import="nElements.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String title = " - haku";
%>
<%@ include file="head.jsp"%>
<body>
	<form name="search" action="search.jsp" method="get">
		Avainsana: <input type="text" name="kword"> <br />
		Organisaatio: <select name="org">
			<option value="" selected>----------------------------------</option>
			<%
				try {
					List<NoppaElement> orgs = nReader.getOrganizations();
					for (NoppaElement ne : orgs) {
			%>
			<option value="<%=ne.getId()%>">
				<%=ne.getName()%></option>
			<%
				}
				} catch (Exception ex) {
				}
			%>
		</select> <br /> Osasto: <select name="dept">
			<option value="" selected>-------------------------------------</option>
			<%
				try {
					List<NoppaElement> depts = nReader.getDepartments(request
							.getParameter("org"));
					for (NoppaElement ne : depts) {
			%>
			<option value="<%=ne.getId()%>">
				<%=ne.getName()%></option>
			<%
				}
				} catch (Exception ex) {
				}
			%>
		</select> <br /> <input type="submit" value="Hae"><br /> <br />
		<%
			String kword = request.getParameter("kword");
			String org = request.getParameter("org");
			String dept = request.getParameter("dept");
			try {
			if (kword != null || org != null || dept != null) {
				List<Course> courses = nReader.getCourses(org, dept, kword);
				for (Course c : courses) {
		%>
		<a href="course.jsp?id=<%=c.getId()%>&name=<%=c.getName()%>"><%=c.getName()%></a>
		<%
			}
			}
			} catch (Exception ex) {
				out.println(ex.getMessage());
			}
		%>
	</form>
</body>
</html>