<%@ include file="parts/authcheck.jsp"%>
<jsp:useBean id="nReader" scope="page" class="spas.nreading.NReader" />
<%@ page import="java.util.*" import="spas.nelements.*" import="java.net.URLEncoder"
	import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@ include file="parts/head.jsp"%>
<%
	String org = request.getParameter("org");
	String dept = request.getParameter("dept");
	String kword = request.getParameter("kword");
%>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<body>
	<form name="search" action="search.jsp" method="get">
	Avainsana: <input type="text" name="kword"
	<%
		if (kword != null) {
			out.print("value=\"" + kword + "\"");
		}
	%> />
	<br /> Organisaatio: <select name="org">
	<option value="" <%=org == null ? "" : "selected"%>>--
	valitse organisaatio</option>
	<%
		List<NElement> orgs = nReader.getOrganizations();
		for (NElement ne : orgs) {
			String selected = "";
			if (org != null && org.equals(ne.getId())) {
				selected = "selected";
			}
			out.println("<option value=\"" + ne.getId() + "\""
				+ selected + ">" + ne.getName() + "</option>");
		}
	%>
	</select> <br /> Osasto: <select name="dept">
	<option value="" <%=dept == null ? "" : "selected"%>>--
	valitse osasto</option>
	<%
		List<NElement> depts = nReader.getDepartments(null);
		for (NElement ne : depts) {
			String selected = "";
			if (dept != null && dept.equals(ne.getId())) {
				selected = "selected";
			}
			out.println("<option data-org=\"" + ne.getOrgId() + "\" value=\"" + 
			ne.getId() + "\"" + selected + ">" + ne.getName() + "</option>");
		}
	%>
	</select> <br /> <input type="submit" name="search" value="Hae"><br />
	<br />
	<%
		if (request.getParameter("search") != null) {
			List<NCourse> courses = nReader.getCourses(org, dept, kword);
			if (courses.size() == 0) {
				out.println("<p class=\"warning\">Antamillasi hakuehdoilla ei" + 
					" löytynyt yhtään kurssia.</p>");
			}
			for (NCourse c : courses) {
				out.println("<a href=\"course.jsp?id=" + c.getId()+ "&name=" + 
					c.getName() + "\">" + c.getName() + "</a><br/>");
			}
		}
	%>
	</form>
	<script>
	$(document).ready(function() {
		// JavaScript snippet by Vesa Laakso (a.k.a. VesQ). Thanks a lot :)
		// First of all, get a copy of all the <select name="dept"> options.
		// This is because we must DELETE those <option> elements to truly
		// make them hidden, and we'd like to be able to put those elements
		// back, too.
		var originalDepartments = $('select[name="dept"]').clone();
		
		// Function to hide all illegal options.		
		var hideillegal = function() {
			// Store the value of selected organisation to a variable
			var chosenOrganisation = $(this).val();
	
			// We have a handy dandy debug-div where we can output something
			//$("#debuginfo").html('<p>You chose: ' + chosenOrganisation + '</p>');
	
			// Replace the element of <select name="dept"> with the one
			// stored to originalDepartments-variable, i.e. reset the state.
			$('select[name="dept"]').replaceWith(originalDepartments.clone());
	
			// Loop through every element inside a <select> with name-attribute "dept"
			$('select[name="dept"] option').each(function() {
				// The current DOM-element is tied to 'this', let's turn it 
				// into a jQuery object
				var $this = $(this);
	
				// Get the data-org attribute to a variable here already.
				var dataOrg = $this.attr('data-org');
	
				// If the current data-org was empty, i.e. it wasn't an organisation,
				// don't do anything. Returning here means that this callback function
				// will exit and then it'll be called again by the each-function with
				// a new element as 'this'.
				if (!dataOrg) {
					return;
				}
	
				// Now, if the <option> has a data-attribute "data-org" that is
				// not of the same value as the chosen organisation, remove it.
				if (dataOrg !== chosenOrganisation && chosenOrganisation !== '') {
				//$("#debuginfo").append('<div>Removed ' + $this.val() + '</div>');
					$this.remove();
				}
			});
		};

		// On load check if parameters are set to hide illegal options.
		if (decodeURI((RegExp("org" + '=' + '(.+?)(&|$)').exec(location.search)||
			[,null])[1]) != null) {
			hideillegal;
		}
		
		// Listen to "change" event for the wanted <select>
		$('select[name="org"]').change(hideillegal);
	});
	</script>
</body>
</html>