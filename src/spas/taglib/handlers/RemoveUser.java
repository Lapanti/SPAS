package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserHandler;

/**
 * Tries to remove user. If successful, also logs user out and sends him to
 * front page, otherwise prints accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 1.0
 * @see UserHandler
 * 
 */
public class RemoveUser extends TagSupport {
	private UserHandler handler = new UserHandler();

	@Override
	public int doStartTag() {
		// Get writer and session.
		JspWriter out = pageContext.getOut();
		HttpSession session = pageContext.getSession();

		// Get parameters.
		String username = (String) session.getAttribute("username");
		String pword = ((HttpServletRequest) pageContext.getRequest())
				.getParameter("pword");

		try {
			// Check that password was given.
			if (pword.equals("")) {
				out.println("<p class='error'>Anna salasana!</p>");
			} else {
				// Set path for handler.
				handler.setPath(pageContext.getServletContext().getRealPath(
						"/resources/users/" + username + ".xml"));

				// User gave parameters, check if they were correct.
				if (handler.authenticate(username, pword)) {
					// Try to remove the user.
					if (handler.removeUser(username, pword)) {
						// Remove username from ression.
						session.removeAttribute("username");

						// Send the user back to frontpage.
						((HttpServletResponse) pageContext.getResponse())
								.sendRedirect("index.jsp");
					} else {
						// User removal was unsuccessful.
						out.println("<p class='error'>Käyttäjän poisto ei "
								+ "onnistunut, kokeile myöhemmin "
								+ "uudestaan.</p>");
					}
				} else {
					// User gave wrong password, print message.
					out.println("<p class='error'>Antamasi salasana oli "
							+ "väärin!</p>");
				}
			}
		} catch (IOException e) {
			// This should never happen.
		}

		return SKIP_BODY;
	}
}
