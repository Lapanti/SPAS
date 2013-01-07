package spas.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserHandler;

/**
 * JSP custom tag for validating user's login. If successful does nothing,
 * otherwise logs user out and sends user to front page.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 1.0
 * @see UserHandler
 */
public class Validation extends TagSupport {
	private UserHandler handler = new UserHandler();

	@Override
	public int doStartTag() {
		// Get session.
		HttpSession session = pageContext.getSession();

		// Get username from session and session id.
		String username = (String) session.getAttribute("username");

		// Set userfile for handler.
		handler.setPath(pageContext.getServletContext().getRealPath(
				"resources/users/" + username + ".xml"));

		// Try to validate.
		if (!handler.validateUser(username, session.getId())) {
			// Validation failed, log out.
			handler.logOut(username);
			session.removeAttribute("username");
			try {
				// Send the user back to frontpage.
				((HttpServletResponse) pageContext.getResponse())
						.sendRedirect("index.jsp");
			} catch (IOException e) {
				// This should never happen.
			}
		}
		return SKIP_BODY;
	}
}
