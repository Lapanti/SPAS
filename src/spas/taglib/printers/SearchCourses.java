package spas.taglib.printers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.nhandling.NReader;
import spas.nhandling.nelements.NCourse;



/**
 * Searches for courses according to request's parameters. If successful prints
 * them out with a link to "course.jsp" with course's information, otherwise
 * prints accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.2
 * @since 1.0
 * @see NReader
 * 
 */
public class SearchCourses extends TagSupport {

	@Override
	public int doStartTag() {
		// Get request and out.
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		JspWriter out = pageContext.getOut();

		// Get parameters.
		String kword = request.getParameter("kword");
		String org = request.getParameter("org");
		String dept = request.getParameter("dept");

		// If all parameters are empty, tell user to add them.
		if (kword.equals("") && org.equals("") && dept.equals("")) {
			try {
				out.println("<p class='warning'>Anna ainakin yksi"
						+ " hakutermi!</p>");
			} catch (IOException e) {
				// This should never happen.
			}
		} else {
			// Get all courses matching given parameters.
			List<NCourse> courses = new NReader().getCourses(org, dept, kword);
			if (courses.size() == 0) {
				// In case no courses were found.
				try {
					out.println("<p class='warning'>Antamillasi hakutuloksilla"
							+ " ei löytynyt yhtäkään kurssia.</p>");
				} catch (IOException e) {
					// This should never happen.
				}
			}
			// Loop through found courses.
			for (NCourse c : courses) {
				try {
					// Print out info for every course.
					out.println("<a href='course.jsp?id=" + c.getId()
							+ "&name=" + c.getName() + "'>" + c.getName()
							+ "</a><br/>");
				} catch (IOException e) {
					// This should never happen.
				}
			}
		}

		return SKIP_BODY;
	}
}
