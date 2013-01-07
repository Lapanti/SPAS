package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserCourseHandler;

/**
 * Handles form for changing group for a course. Prints accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 1.1
 * @see UserCourseHandler
 * 
 */
public class ChangeCourseGroup extends TagSupport {

	@Override
	public int doStartTag() {
		// Get writer and request.
		JspWriter out = pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		// Get username.
		String username = (String) pageContext.getSession().getAttribute(
				"username");

		// Create course handler.
		UserCourseHandler handler = new UserCourseHandler(pageContext
				.getServletContext().getRealPath(
						"/resources/users/" + username + ".xml"));

		// Get given id.
		String id = request.getParameter("changeid");

		// Try to change group, and print accordingly.
		if (handler.changeGroup(id, request.getParameter("group"))) {
			try {
				out.println("<p class='success'>Kurssin " + id
						+ " ryhmä vaihdettiin onnistuneesti.</p>");
			} catch (IOException e) {
				// This should never happen.
			}
		} else {
			try {
				out.println("<p class='error'>Ryhmän vaihtamisessa "
						+ "kohdattiin ongelma, yritä myöhemmin uudelleen.");
			} catch (IOException e) {
				// This should never happen.
			}
		}

		return SKIP_BODY;
	}
}
