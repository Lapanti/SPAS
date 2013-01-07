package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserCourseHandler;

/**
 * Handles removal form for a course. Prints accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 1.0
 * @see UserCourseHandler
 * 
 */
public class RemoveCourse extends TagSupport {

	@Override
	public int doStartTag() {
		// Get writer.
		JspWriter out = pageContext.getOut();

		// Get username.
		String username = (String) pageContext.getSession().getAttribute(
				"username");

		// Create course handler.
		UserCourseHandler handler = new UserCourseHandler(pageContext
				.getServletContext().getRealPath(
						"/resources/users/" + username + ".xml"));

		// Get the id for the course to be removed.
		String id = ((HttpServletRequest) pageContext.getRequest())
				.getParameter("remid");

		// Try to remove course, and print accordingly.
		if (handler.removeCourse(id)) {
			try {
				out.println("<p class='success'>Kurssi " + id
						+ " poistettiin onnistuneesti.</p>");
			} catch (IOException e) {
				// This should never happen.
			}
		} else {
			try {
				out.println("<p class='error'>Kurssin poistamisessa "
						+ "kohdattiin ongelma, yritä myöhemmin "
						+ "uudestaan.</p>");
			} catch (IOException e) {
				// This should never happen.
			}
		}

		return SKIP_BODY;
	}
}
