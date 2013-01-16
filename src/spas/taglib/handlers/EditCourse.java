package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserCourseHandler;

/**
 * Handles editing of a course, prints out accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 1.2
 * @see UserCourseHandler
 * 
 */
public class EditCourse extends TagSupport {

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

		// Get id for course to be edited.
		String id = request.getParameter("id");

		// Try to activate the course, and print accordingly.
		if (handler.changeExec(id,
				Integer.parseInt(request.getParameter("year")),
				request.getParameter("period"))) {
			try {
				out.println("<p class='success'>Kurssin " + id
						+ " suoritusaikaa muutettiin onnistuneesti.");
			} catch (IOException e) {
				// This should never happen.
			}
		} else {
			try {
				out.println("<p class='error'>Kurssin " + id
						+ " suoritusajankohdan muutoksessa kohdattiin ongelma,"
						+ " yritä myöhemmin uudelleen.");
			} catch (IOException e) {
				// This should never happen.
			}
		}
		// Try to change group, and print accordingly.
		String group = request.getParameter("group");
		if (group != null && !group.equals(handler.getGroup(id))) {
			if (handler.changeGroup(id, group)) {
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
		}

		return SKIP_BODY;
	}
}
