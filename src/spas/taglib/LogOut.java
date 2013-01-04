package spas.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserHandler;

/**
 * Tries to log user out of the system. If successful, sends user to frontpage,
 * otherwise does nothing.
 * 
 * @author Lauri Lavanti
 * @version 1.0
 * @since 1.0
 * 
 */
public class LogOut extends TagSupport {

	/**
	 * Tries to log user out, if successful, sends him to frontpage.
	 */
	@Override
	public int doStartTag() {

		// Get username.
		String username = (String) pageContext.getSession().getAttribute(
				"username");

		// Create handler.
		UserHandler handler = new UserHandler(pageContext.getServletContext()
				.getRealPath("/resources/users/" + username + ".xml"));

		// Get response.
		HttpServletResponse response = (HttpServletResponse) pageContext
				.getResponse();

		// Log out.
		if (handler.logOut(username)) {
			pageContext.getSession().removeAttribute("username");
			try {
				// Send the user back to frontpage.
				response.sendRedirect("index.jsp");
			} catch (IOException e) {
				// This should never happen.
			}
		} else {
			try {
				// If logging out failed, send user back to welcome page.
				response.sendRedirect("welcome.jsp");
			} catch (IOException e) {
				// This should never happen.
			}
		}

		return SKIP_BODY;
	}
}
