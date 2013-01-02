// Simmple submit confirmation.
// Nannette Thacker http://www.shiningstar.net
function confirmSubmit() {
	var agree = confirm("Oletko varma?");
	if (agree)
		return true;
	else
		return false;
}