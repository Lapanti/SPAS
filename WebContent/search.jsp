<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="WEB-INF/tlds/spasTags.tld" prefix="st"%>
<st:validate />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="parts/head.html" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<title>SPAS - Hae kursseja</title>
</head>
<body>
<c:import url="parts/menu.jsp" />
<div id="content">
	<form name="search" action="search.jsp" method="get">
		Avainsana: <input type="text" name="kword"
			value="${!empty param.kword ? param.kword : ''}" /> <br />
		Organisaatio: <select name="org">
			<option value="">-- valitse organisaatio</option>
			<st:printorgs />
		</select> <br /> Osasto: <select name="dept">
			<option value="">-- valitse osasto</option>
			<st:printdepts />
		</select> <br /> <input type="submit" name="search" value="Hae"><br />
		<br />
		<c:if test="${!empty param.search}">
			<st:search />
		</c:if>
	</form>
	</div>
	<c:import url="parts/footer.html" />
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
				//$("#debuginfo").html('<p>You chose: ' + 
				//chosenOrganisation + '</p>');
	
				// Replace the element of <select name="dept"> with the one
				// stored to originalDepartments-variable, i.e. reset the state.
				$('select[name="dept"]').replaceWith(
						originalDepartments.clone());
	
				// Loop through every element inside a <select> 
				//with name-attribute "dept"
				$('select[name="dept"] option').each(function() {
					// The current DOM-element is tied to 'this', 
					//let's turn it 
					// into a jQuery object
					var $this = $(this);

					// Get the data-org attribute to a variable 
					// here already.
					var dataOrg = $this.attr('data-org');

					// If the current data-org was empty, i.e. 
					// it wasn't an organisation,
					// don't do anything. Returning here 
					// means that this callback function
					// will exit and then it'll be called 
					// again by the each-function with
					// a new element as 'this'.
					if (!dataOrg) {
						return;
					}

					// Now, if the <option> has a data-attribute 
					// "data-org" that is
					// not of the same value as the chosen 
					// organisation, remove it.
					if (dataOrg !== chosenOrganisation
							&& chosenOrganisation !== '') {
						//$("#debuginfo").append('<div>Removed ' + 
						//$this.val() + '</div>');
						$this.remove();
					}
				});
			};
	
			// Listen to "change" event for the wanted <select>
			$('select[name="org"]').change(hideillegal);
		});
	</script>
</body>
</html>