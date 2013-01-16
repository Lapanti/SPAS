package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserHandler;

/**
 * Tries to change user's password, whether it works or not, prints out
 * accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 1.0
 * @see UserHandler
 * 
 */
public class ChangePword extends TagSupport {
	/**
	 * Userhandler used with this tag.
	 */
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
		String pword = request.getParameter("oldpword");
		String newpword = request.getParameter("newpword");
		String newpword2 = request.getParameter("newpword2");

		try {
			// Check that all required parameters were given.
			if (pword.equals("") || newpword.equals("") || newpword2.equals("")
					|| !newpword.equals(newpword2)) {
				// Check if user gave new password.
				if (newpword.equals("") || newpword2.equals("")) {
					out.println("<p class='error'>Anna uusi salasana!</p>");
				}
				// Check if user gave password.
				if (pword.equals("")) {
					out.println("<p class='error'>Anna vanha salasana!</p>");
				}
				// Check if user's given passwords match.
				if (!newpword.equals(newpword2)) {
					out.println("<p class='error'>Antamasi uudet salasanat eivät"
							+ " vastanneet toisiaan!</p>");
				}
			} else {
				// Set path to userfile for handler.
				handler.setPath(pageContext.getServletContext().getRealPath(
						"/resources/users/" + username + ".xml"));

				// User gave all parameters, check if they were correct.
				if (handler.authenticate(username, pword)) {
					// Try to change the password.
					if (handler.changePassword(username, pword, newpword)) {
						out.println("<p class='success'>Salasana vaihdettiin"
								+ " onnistuneesti.</p>");
					} else {
						// Password change was unsuccessful.
						out.println("<p class='error'>Salasanan vaihto ei "
								+ "onnistunut, kokeile myöhemmin "
								+ "uudestaan.</p>");
					}
				} else {
					// User gave wrong password, print message.
					out.println("<p class='error'>Antamasi vanha salasana oli "
							+ "väärin!</p>");
				}
			}
		} catch (IOException e) {
			// This should never happen.
		}

		return SKIP_BODY;
	}
}
