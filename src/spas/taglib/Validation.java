package spas.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserHandler;

/**
 * JSP custom tag for validating user's login. If successful does nothing,
 * otherwise logs user out and sends user to front page.
 * 
 * @author Lauri Lavanti
 * @version 1.0
 * @since 1.0
 */
public class Validation extends TagSupport {

	@Override
	public int doStartTag() {
		// Get session.
		HttpSession session = pageContext.getSession();

		// Get servlet context.
		ServletContext cont = pageContext.getServletContext();

		// Get username from session and session id.
		String username = (String) session.getAttribute("username");
		String id = session.getId();

		// Create handler.
		UserHandler handler = new UserHandler(
				cont.getRealPath("resources/users/" + username + ".xml"));

		// Try to validate.
		if (!handler.validateUser(username, id)) {
			// Validation failed, log out.
			if (handler.logOut(username)) {
				session.removeAttribute("username");
				try {
					// Send the user back to frontpage.
					((HttpServletResponse) pageContext.getResponse())
							.sendRedirect("index.jsp");
				} catch (IOException e) {
					// This should never happen.
				}
			}
		}
		return SKIP_BODY;
	}
}
