package spas.taglib.printers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.nhandling.NReader;
import spas.nhandling.nelements.NCourse;
import spas.usercontrol.UserCourseHandler;

/**
 * Prints out course given as GET-parameters (name and id). If information was
 * not given, it redirects user to search.jsp, otherwise prints it out in a
 * table containing information about it. Also adds a remove/add button
 * accordingly and handles it.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 1.0
 * 
 */
public class PrintCourse extends TagSupport {

	@Override
	public int doStartTag() {
		// Get request and writer.
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		JspWriter out = pageContext.getOut();

		// Get given id and name.
		String id = request.getParameter("id");
		String name = request.getParameter("name");

		// If given parameters were null or empty redirect back to search.
		if (id == null || id.equals("") || name == null || name.equals("")) {
			try {
				((HttpServletResponse) pageContext.getResponse())
						.sendRedirect("search.jsp");
			} catch (IOException e) {
				// This should never happen.
			}
		} else {
			// Get the course.
			NCourse c = new NReader().getCourseOverview(id, name);

			// In case of a new course, add id.
			if (c.getId().equals("")) {
				c.setId(id);
			}

			// In case of a new course, add name.
			if (c.getName().equals("")) {
				c.setName(name);
			}

			// Create course handler.
			UserCourseHandler handler = new UserCourseHandler(pageContext
					.getServletContext().getRealPath(
							"/resources/users/"
									+ pageContext.getSession().getAttribute(
											"username") + ".xml"));

			try {
				// Print out the information for the course.
				out.println(c.getId() + ": " + c.getName() + "<br/>");
				out.println("<table>");
				out.println("<tr><td>Opintopisteet</td><td>" + c.getCredits()
						+ "</td></tr>");
				out.println("<tr><td>Opetusperiodi(t)</td><td>" + c.getPeriod()
						+ "</td></tr>");
				out.println("<tr><td>Sisältö</td><td>" + c.getContent()
						+ "</td></tr>");
				out.println("<table><br/><br/><br/>");

				/*
				 * If user pressed add, try to add course and print
				 * appropriately.
				 */
				if (request.getParameter("add") != null) {
					if (handler.addCourse(request.getParameter("aname"),
							request.getParameter("aid"))) {
						out.println("<p class='success'>Kurssi lisättiin"
								+ " onnistuneesti.</p>");
					} else {
						// Adding failed.
						out.println("<p class='error'>Kurssin lisäyksessä "
								+ "oli ongelma, kokeile myöhemmin "
								+ "uudelleen.</p>");
					}
				}

				// Get the link to the file using this tag..
				String path = request.getRequestURL() + "?"
						+ request.getQueryString();

				if (handler.containsCourse(id)) {
					// Print removing form.
					out.println("Kurssi on lisättynä.");
				} else {
					// Print adding form.
					out.println("<form action='" + path + "' method='post'>");
					out.println("<input type='hidden' name='aid' value='" + id
							+ "' />");
					out.println("<input type='hidden' name='aname' value='"
							+ name + "' />");
					out.println("<input type='submit' name='add' "
							+ "value='Lisää' /></form>");
				}
			} catch (IOException e) {
				// This should never happen.
			}
		}

		return SKIP_BODY;
	}
}
