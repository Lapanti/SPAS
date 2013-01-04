package spas.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserHandler;

/**
 * Tries to change user's email, whether it works or not, prints out
 * accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.0
 * @since 1.0
 * 
 */
public class ChangeEmail extends TagSupport {

	@Override
	public int doStartTag() {
		// Get servlet context, request and writer.
		ServletContext context = pageContext.getServletContext();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		JspWriter out = pageContext.getOut();

		// Get parameters.
		String username = (String) pageContext.getSession().getAttribute(
				"username");
		String email = request.getParameter("email");
		String email2 = request.getParameter("email2");
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
				// Create handler.
				UserHandler handler = new UserHandler(
						context.getRealPath("/resources/users/" + username
								+ ".xml"));

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
