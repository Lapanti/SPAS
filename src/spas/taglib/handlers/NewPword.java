package spas.taglib.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.EmailNewPassword;
import spas.usercontrol.UserLoginHandler;

/**
 * Tries to email user his new password and save it to file. Prints information
 * accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 1.0
 * @see EmailNewPassword
 * 
 */
public class NewPword extends TagSupport {

	@Override
	public int doStartTag() {
		// Get writer.
		JspWriter out = pageContext.getOut();

		// Get given email.
		String email = ((HttpServletRequest) pageContext.getRequest())
				.getParameter("email");

		// Check that email isn't empty.
		if (!email.equals("")) {
			// Try to email new password.
			if (new EmailNewPassword(new UserLoginHandler()).createNewPassword(
					email,
					pageContext.getServletContext().getRealPath(
							"/resources/users"))) {
				try {
					// Print message of success.
					out.println("<p class='success'>Uusi salasana lähetettiin"
							+ " sähköpostiisi.");
				} catch (IOException e) {
					// This should never happen.
				}
			} else {
				try {
					// Print error message.
					out.println("<p class='error'>Antamallesi sähköpostille "
							+ "ei ole rekisteröity käyttäjää.</p>");
				} catch (IOException e) {
					// This should never happen.
				}
			}
		} else {
			try {
				// No email was given so print a warning of that.
				out.println("<p class='warning'>Anna sähköpostiosoite!</p>");
			} catch (IOException e) {
				// This shouldn't happen.
			}
		}

		return SKIP_BODY;
	}
}
