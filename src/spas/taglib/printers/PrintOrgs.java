package spas.taglib.printers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.nhandling.NReader;
import spas.nhandling.nelements.NElement;



/**
 * Prints out organizations in <code>option</code>-tags.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 1.0
 * @see NReader
 *
 */
public class PrintOrgs extends TagSupport {

	@Override
	public int doStartTag() {
		// Get writer.
		JspWriter out = pageContext.getOut();

		// Get organization parameter and organization list.
		String selected = ((HttpServletRequest) pageContext.getRequest())
				.getParameter("org");
		List<NElement> orgs = new NReader().getOrganizations();

		// Loop through organizations and print them out.
		for (NElement ne : orgs) {
			// Get current organization's id and compare it to selected.
			String id = ne.getId();
			String select = "";
			if (selected != null) {
				select = selected.equals(id) ? "selected" : "";
			}

			try {
				// Print organization.
				out.println("<option value='" + id + "' " + select + ">"
						+ ne.getName() + "</option>");
			} catch (IOException e) {
				// This should never happen.
			}
		}

		return SKIP_BODY;
	}
}
