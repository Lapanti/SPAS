package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserCourseHandler;

/**
 * Handles deactivation form for a course. Prints out accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 1.1
 * @see UserCourseHandler
 * 
 */
public class DeactivateCourse extends TagSupport {

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

		// Get id for course to be deactivated.
		String id = ((HttpServletRequest) pageContext.getRequest())
				.getParameter("deactiveid");

		// Try to deactivate the course, and print accordingly.
		if (handler.changeState(id, UserCourseHandler.NONACTIVE)) {
			try {
				out.println("<p class='success'>Kurssi " + id
						+ " deaktivoitiin.");
			} catch (IOException e) {
				// This should never happen.
			}
		} else {
			try {
				out.println("<p class='error'>Kurssin deaktivoinnissa "
						+ "kohdattiin ongelma, yritä myöhemmin uudelleen.");
			} catch (IOException e) {
				// This should never happen.
			}
		}

		return SKIP_BODY;
	}
}
