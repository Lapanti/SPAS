package spas.taglib.handlers;

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
 * @version 1.2.1
 * @since 1.0
 * @see UserHandler
 * 
 */
public class Register extends TagSupport {
	/**
	 * Userhandler used in this tag.
	 */
	private UserHandler handler = new UserHandler();

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

		try {

			if (username.equals("") || pword.equals("") || pword2.equals("")
					|| !pword.equals(pword2)) {
				// Check that user gave name and password.
				if (username.equals("")) {
					out.println("<p class='error'>Anna käyttäjänimi!</p><br/>");
				}
				if (pword.equals("") || pword2.equals("")) {
					out.println("<p class='error'>Anna salasanasi kaksi "
							+ "kertaa!</p>");
				}
				// Check that passwords given by user match.
				if (!pword.equals(pword2)) {
					out.println("<p class='error'>Antamasi salasanat eivät "
							+ "vastanneet toisiaan!</p><br/>");
				}
			} else {
				// Get path to user's file and model file.
				String path = context.getRealPath("/resources/users/"
						+ username + ".xml");
				String modelpath = context
						.getRealPath("resources/model/user.xml");

				// Set path to userfile for handler.
				handler.setPath(path);

				// Try to create user.
				if (handler.createUser(username, pword, email, modelpath)) {
					// Print out information of success.
					out.println("<p class='success'>Rekisteröinti "
							+ "onnistui.</p>");
				} else {
					// Print out form and information of failure.
					out.println("<p class='error'>Antamasi käyttäjänimi"
							+ " on jo käytössä.</p>");
				}
			}
		} catch (IOException e) {
			// This should never happen.
		}

		return SKIP_BODY;
	}
}
