package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.CalendarCreator;

/**
 * Tries to save/update user's iCalendar file. Prints out accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 1.0
 * @see CalendarCreator
 * 
 */
public class Calendar extends TagSupport {

	@Override
	public int doStartTag() {
		// Get ServletContext and writer.
		ServletContext context = pageContext.getServletContext();
		JspWriter out = pageContext.getOut();

		// Get username.
		String username = (String) pageContext.getSession().getAttribute(
				"username");

		// Get path to user's file and calendar file.
		String userpath = context.getRealPath("/resources/users/" + username
				+ ".xml");
		String icspath = context.getRealPath("/resources/ics/" + username
				+ ".ics");

		// Try to create/update the calendar.
		if (new CalendarCreator(userpath, icspath).createCalendar()) {
			try {
				out.println("<p class='success'>Kalenteri "
						+ "luotiin/päivitettiin onnistuneesti</p>");
			} catch (IOException e) {
				// This should never happen.
			}
		} else {
			try {
				out.println("<p class='error'>Kalenterin "
						+ "luomisessa/päivittämisessä oli ongelma, yritä"
						+ " myöhemmin uudelleen.</p>");
			} catch (IOException e) {
				// This should never happen.
			}
		}

		return SKIP_BODY;
	}
}
