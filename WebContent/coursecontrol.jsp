<%@ include file="parts/authcheck.jsp"%>
<jsp:useBean id="nReader" scope="page" class="spas.nreading.NReader" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="spas.nelements.*" import="java.util.*"
	import="spas.usercontrol.UserCourseHandler"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="parts/head.jsp"%>
<script type="text/javascript" src="javascript/submitconf.js"></script>
<%
	UserCourseHandler uch = new UserCourseHandler(userpath);
	String remid = request.getParameter("remid");
	String changeid = request.getParameter("changeid");
	String activeid = request.getParameter("activeid");
	String deactiveid = request.getParameter("deactiveid");
	String completedid = request.getParameter("completedid");
	if (request.getParameter("changegroup") != null && changeid != null) {
		uch.changeGroup(changeid, request.getParameter("group"));
	}
	if (request.getParameter("completed") != null
			&& completedid != null) {
		uch.changeState(completedid, UserCourseHandler.COMPLETED);
	}
	if (request.getParameter("activate") != null && activeid != null) {
		uch.changeState(activeid, UserCourseHandler.ACTIVE);
	}
	if (request.getParameter("deactivate") != null
			&& deactiveid != null) {
		uch.changeState(deactiveid, UserCourseHandler.NONACTIVE);
	}
	if (request.getParameter("remove") != null && remid != null) {
		uch.removeCourse(remid);
	}
%>
<body>
	<%=username%>,
	<a href="logout.jsp">kirjaudu ulos.</a>
	<br />
	<br /> Kurssit:
	<br />
	<%
		String div1 = "<div class=\"activecourses\">Aktiiviset kurssit:";
		String div2 = "<div class=\"nonactivecourses\">Talletetut kurssit:";
		String div3 = "<div class=\"nonactivecourses\">Suoritetut kurssit:";
		for (NCourse c : uch.getCourses()) {
			String id = c.getId();
			String course = "<br/>" + id + ": " + c.getName() + "<br/>";
			String end = "<form onsubmit=\"return confirmSubmit()\" "
					+ "action=\"coursecontrol.jsp\" method=\"post\">"
					+ "<input type=\"hidden\" name=\"remid\" value=\"" + id
					+ "\"/><input type=\"submit\" "
					+ "name=\"remove\" value=\"Poista\" /></form>";
			int state = c.getState();
			if (state == UserCourseHandler.ACTIVE) {
				List<NEvent> exercises = nReader.getCourseExercises(c);
				Set<String> groups = new HashSet<String>();
				for (NEvent e : exercises) {
					if (!e.getGroup().equals("")) {
						groups.add(e.getGroup());
					}
				}
				if (groups.size() > 0) {
					String chosengroup = uch.getGroup(id);
					String nogroup = chosengroup.equals(" ") ? " selected"
							: null;
					String groupmenu = "Ryhmä: <form action=\"coursecontrol.jsp"
							+ "\" method=\"post\"><select name=\"group\">"
							+ "<option value=\" \""
							+ nogroup
							+ ">--</option>";
					for (String group : groups) {
						String selected = chosengroup.equals(group) ? "selected"
								: null;
						groupmenu += "<option value=\"" + group + "\" "
								+ selected + ">" + group + "</option>";
					}
					String groupend = "</select><input type=\"hidden\""
							+ " name=\"changeid\" value=\""
							+ id
							+ "\"/><input type=\"submit\" name=\"changegroup\" "
							+ "value=\"Vaihda\" /></form>";
					course += groupmenu + groupend;
				} // end of if-group-size-clause.
				course += "<form action=\"coursecontrol.jsp\" method=\"post\">"
						+ "<input type=\"hidden\" name=\"completedid\" value=\""
						+ id
						+ "\" /><input type=\"submit\" name=\"completed\" "
						+ "value=\"Siirrä suoritettuihin\" /></form>"
						+ "<form action=\"coursecontrol.jsp\" method=\"post\">"
						+ "<input type=\"hidden\" name=\"nonactiveid\" value=\""
						+ id
						+ "\" /><input type=\"submit\" name=\"deactivate\" "
						+ "value=\"Poista aktiivisista\" /></form>" + end;
				div1 += course;
			} else if (state == UserCourseHandler.NONACTIVE) {
				course += "<form action=\"coursecontrol.jsp\" method=\"post\">"
						+ "<input type=\"hidden\" name=\"activeid\" value=\""
						+ id
						+ "\" /><input type=\"submit\" name=\"activate\" "
						+ "value=\"Aktivoi\" /></form>" + end;
				div2 += course;
			} else if (state == UserCourseHandler.COMPLETED) {
				div3 += course + end;
			}
		} // end of for-loop.
		out.println(div1 + div2 + div3);
	%>
</body>
</html>