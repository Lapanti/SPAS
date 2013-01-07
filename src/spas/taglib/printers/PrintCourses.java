package spas.taglib.printers;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import spas.nhandling.NReader;
import spas.nhandling.nelements.NCourse;
import spas.nhandling.nelements.NEvent;
import spas.usercontrol.UserCourseHandler;

/**
 * Prints out user's courses accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 1.1
 * @see UserCourseHandler
 * @see NReader
 * 
 */
public class PrintCourses extends TagSupport {

	@Override
	public int doStartTag() {
		// Get the link to the file using this tag.
		String path = ((HttpServletRequest) pageContext.getRequest())
				.getRequestURL().toString();

		// Create handler.
		UserCourseHandler handler = new UserCourseHandler(pageContext
				.getServletContext().getRealPath(
						"resources/users/"
								+ pageContext.getSession().getAttribute(
										"username") + ".xml"));

		// Create basis for all three divs.
		String div1 = "<div class='activecourses'>Aktiiviset kurssit:";
		String div2 = "<div class='nonactivecourses'>Talletetut kurssit:";
		String div3 = "<div class='nonactivecourses'>Suoritetut kurssit:";

		// Get user's courses and loop through them.
		for (NCourse c : handler.getCourses()) {

			// Get course's id and start building it's html-representation.
			String id = c.getId();
			String course = "<br/>" + id + ": " + c.getName() + "<br/>";

			// Build form for removing the course.
			String end = "<form onsubmit='return confirmSubmit()' "
					+ "action='" + path + "' method='post'>"
					+ "<input type='hidden' name='remid' value='" + id
					+ "'/><input type='submit' "
					+ "name='remove' value='Poista' /></form>";

			// Get course's state and act accordingly.
			int state = c.getState();
			if (state == UserCourseHandler.ACTIVE) {
				
				// Get course's HTML-representation.
				div1 += getActiveCourse(c, path, handler) + end;
				
			} else if (state == UserCourseHandler.NONACTIVE) {

				// Build form for activating course.
				course += "<form action='" + path + "' method='post'>"
						+ "<input type='hidden' name='activeid' value='" + id
						+ "' /><input type='submit' name='activate' "
						+ "value='Aktivoi' /></form>" + end;

				// Add course to div.
				div2 += course;
			} else if (state == UserCourseHandler.COMPLETED) {
				/*
				 * Completed courses don't need anymore work, so just add to
				 * div.
				 */
				div3 += course + end;
			}
		} // end of for-loop.

		try {
			// Print out divs.
			pageContext.getOut().println(div1 + div2 + div3);
		} catch (IOException e) {
			// This should never happen.
		}

		return SKIP_BODY;
	}

	/**
	 * Builds String representation in HTML for an active course.
	 * 
	 * @param c Course to be transformed.
	 * @param path Path to requested url.
	 * @param handler Coursehandler for user.
	 * @return HTML-representation of course.
	 */
	private String getActiveCourse(NCourse c, String path,
			UserCourseHandler handler) {
		String course = "";

		/*
		 * Get course's groups and insert them into a set to avoid duplicates.
		 */
		List<NEvent> exercises = new NReader().getCourseExercises(c);
		Set<String> groups = new HashSet<String>();
		for (NEvent e : exercises) {

			// Make sure exercises actually have groups, before adding.
			if (!e.getGroup().equals("")) {
				groups.add(e.getGroup());
			}
		}
		if (groups.size() > 0) {
			/*
			 * If course had groups, get chosen and then create a form to pick a
			 * new group.
			 */
			String chosengroup = handler.getGroup(id);
			String groupmenu = "Ryhmä: <form action='" + path
					+ "' method='post'><select name='group'>"
					+ "<option value=' '>--</option>";

			// Loop through groups and add them as options.
			for (String group : groups) {
				String selected = chosengroup.equals(group) ? "selected" : null;
				groupmenu += "<option value='" + group + "' " + selected + ">"
						+ group + "</option>";
			}

			// Build the rest of the group selection form.
			String groupend = "</select><input type='hidden'"
					+ " name='changeid' value='" + id
					+ "'/><input type='submit' name='changegroup' "
					+ "value='Vaihda' /></form>";

			// Compile the group selection form into course.
			course += groupmenu + groupend;
		} // end of if-group-size-clause.

		// Add forms for completing and deactivating course.
		course += "<form action='" + path + "' method='post'>"
				+ "<input type='hidden' name='completedid' value='" + id
				+ "' /><input type='submit' name='completed' "
				+ "value='Siirrä suoritettuihin' /></form>" + "<form action='"
				+ path + "' method='post'>"
				+ "<input type='hidden' name='deactiveid' value='" + id
				+ "' /><input type='submit' name='deactivate' "
				+ "value='Poista aktiivisista' /></form>";

		return course;
	}
}
