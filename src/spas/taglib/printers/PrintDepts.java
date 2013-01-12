package spas.taglib.printers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.nhandling.NReader;
import spas.nhandling.nelements.NElement;



/**
 * Prints out departments in <code>option</code>-tags.
 * 
 * @author Lauri Lavanti
 * @version 1.2
 * @since 1.0
 * @see NReader
 *
 */
public class PrintDepts extends TagSupport {

	@Override
	public int doStartTag() {
		// Get writer.
		JspWriter out = pageContext.getOut();

		// Get department parameter and department list.
		String selected = ((HttpServletRequest) pageContext.getRequest())
				.getParameter("dept");
		List<NElement> depts = new NReader().getDepartments(null);

		// Loop through departments and print them out.
		for (NElement ne : depts) {
			// Get current department's id and compare it to selected.
			String id = ne.getId();
			
			String select = "";
			if (selected != null) {
				select = selected.equals(id) ? "selected" : "";
			}

			try {
				// Print department.
				out.println("<option data-org='" + ne.getOrgId() + "' value='"
						+ id + "' " + select + ">" + ne.getName() + "</option>");
			} catch (IOException e) {
				// This should never happen.
			}
		}

		return SKIP_BODY;
	}
}
