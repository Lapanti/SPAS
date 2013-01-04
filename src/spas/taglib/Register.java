package spas.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.usercontrol.UserHandler;

/**
 * Tries to register user into the database. Either way, prints accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.0
 * @since 1.0
 *
 */
public class Register extends TagSupport {

	@Override
	public int doStartTag() {
		// Get request, servlet context and writer.
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		ServletContext context = pageContext.getServletContext();
		JspWriter out = pageContext.getOut();

		// Get given parameters.
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String pword = request.getParameter("pword");
		String pword2 = request.getParameter("pword2");

		// Build register form.
		String regform = "<form action='" + request.getRequestURL()
				+ "' method='post'> Käyttäjänimi:<br /> <input type='text' "
				+ "name='username' value='" + username != null ? username
				: "" + "' /><br /> Sähköposti:<br /> <input "
				+ "type='text' name='email'	value='" + email != null ? email
				: "" + "' /><br /> Salasana:<br /><input type='password' " 
				+ "name='pword' /><br /> Toista salasana:<br /><input " 
				+ "type='password' name='pword2' /><br /><input type='submit'" 
				+ " name='register' value='Rekisteröidy' /></form>";

		try {
			// Check to see if form's submit was pressed.
			if (request.getParameter("register") != null) {

				// Create message containing possible errors to printed later.
				String message = "";

				// Check that user gave name and password.
				if (username.equals("")) {
					message += "<p class='error'>Anna käyttäjänimi!</p><br/>";
				}
				if (pword.equals("") || pword2.equals("")) {
					message += "<p class='error'>Anna salasanasi kaksi "
							+ "kertaa!</p>";
				}
				// Check that passwords given by user match.
				if (!pword.equals(pword2)) {
					message += "<p class='error'>Antamasi salasanat eivät "
							+ "vastanneet toisiaan!</p><br/>";
				}

				// If any of above errors happened, just print form and that.
				if (!message.equals("")) {
					out.println(regform + "<br/><br/>");
					out.println(message);
				} else {
					// Get path to user's file and model file.
					String path = context.getRealPath("/resources/users/"
							+ username + ".xml");
					String modelpath = context
							.getRealPath("resources/model/user.xml");

					// Create user handler.
					UserHandler handler = new UserHandler(path);

					// Try to create user.
					if (handler.createUser(username, pword, email, modelpath)) {
						// Print out information of success.
						out.println("<p class='success'>Rekisteröinti "
								+ "onnistui.</p>");
					} else {
						// Print out form and information of failure.
						out.println(regform + "<br/><br/>");
						out.println("<p class='error'>Antamasi käyttäjänimi"
								+ " on jo käytössä.</p>");
					}
				}
			} else {
				// Wasn't pressed yet, so just print out the form.
				out.println(regform);
			}
		} catch (IOException e) {
			// This should never happen.
		}

		return SKIP_BODY;
	}
}
