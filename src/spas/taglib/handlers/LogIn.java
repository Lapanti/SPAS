package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserHandler;

/**
 * Tries to log user in. If successful, send user to welcome page, otherwise
 * prints accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.2
 * @since 1.0
 * @see UserHandler
 * 
 */
public class LogIn extends TagSupport {
	private UserHandler handler = new UserHandler();
	
	@Override
	public int doStartTag() {
		// Get request.
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		// Get username.
		String username = request.getParameter("username");

		// Set path to userfile for handler.
		handler.setPath(pageContext.getServletContext().getRealPath(
				"resources/users/" + username + ".xml"));

		// Try to authenticate.
		if (handler.authenticate(username, request.getParameter("pword"))) {

			// Get session.
			HttpSession session = pageContext.getSession();

			// Log user in.
			if (handler.logIn(username, session.getId())) {
				session.setAttribute("username", username);
				if (request.getParameter("keeplogged") != null) {
					session.setMaxInactiveInterval(0);
				}
				try {
					// Send user to welcome page.
					((HttpServletResponse) pageContext.getResponse())
							.sendRedirect("welcome.jsp");
				} catch (IOException e) {
					// This shouldn't normally happen.
				}
			}
		}

		return SKIP_BODY;
	}
}
