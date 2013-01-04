package spas.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
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
 * @version 1.0
 * @since 1.0
 * 
 */
public class RemoveUser extends TagSupport {

	@Override
	public int doStartTag() {
		// Get servlet context, request, writer and session.
		ServletContext context = pageContext.getServletContext();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		JspWriter out = pageContext.getOut();
		HttpSession session = pageContext.getSession();

		// Get parameters.
		String username = (String) session.getAttribute("username");
		String pword = request.getParameter("pword");

		try {
			// Check that password was given.
			if (pword.equals("")) {
				out.println("<p class='error'>Anna salasana!</p>");
			} else {
				// Create handler.
				UserHandler handler = new UserHandler(
						context.getRealPath("/resources/users/" + username
								+ ".xml"));

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
