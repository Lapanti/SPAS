package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserHandler;

/**
 * Tries to change user's email, whether it works or not, prints out
 * accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.2
 * @since 1.0
 * @see UserHandler
 * 
 */
public class ChangeEmail extends TagSupport {
	private UserHandler handler = new UserHandler();

	@Override
	public int doStartTag() {
		// Get request and writer.
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		JspWriter out = pageContext.getOut();

		// Get parameters.
		String username = (String) pageContext.getSession().getAttribute(
				"username");
		String email = request.getParameter("newmail");
		String email2 = request.getParameter("newmail2");
		String pword = request.getParameter("epword");

		try {
			// Check that all required parameters were given.
			if (email.equals("") || email2.equals("") || pword.equals("")
					|| !email.equals(email2)) {
				// Check if user gave email.
				if (email.equals("") || email2.equals("")) {
					out.println("<p class='error'>Anna sähköposti!</p>");
				}
				// Check if user gave password.
				if (pword.equals("")) {
					out.println("<p class='error'>Anna salasana!</p>");
				}
				// Check if user's given emails match.
				if (!email.equals(email2)) {
					out.println("<p class='error'>Antamasi sähköpostit eivät "
							+ "vastanneet toisiaan!</p>");
				}
			} else {
				// Set path for handler.
				handler.setPath(pageContext.getServletContext().getRealPath(
						"/resources/users/" + username + ".xml"));

				// User gave all parameters, check if they were correct.
				if (handler.authenticate(username, pword)) {
					// Try to change the email.
					if (handler.changeEmail(username, pword, email)) {
						out.println("<p class='success'>Sähköposti vaihdettiin"
								+ " onnistuneesti.</p>");
					} else {
						// Email change was unsuccessful.
						out.println("<p class='error'>Sähköpostin vaihto ei "
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
