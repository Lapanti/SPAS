package spas.taglib;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.nelements.NCourse;
import spas.nelements.NEvent;
import spas.nreading.NReader;
import spas.usercontrol.UserCourseHandler;

/**
 * Prints out user's courses and forms to edit their status, also handles those
 * forms.
 * 
 * @author Lauri Lavanti
 * @version 1.0
 * @since 1.0
 * 
 */
public class CourseEdit extends TagSupport {

	@Override
	public int doStartTag() {
		// Get ServletContext and writer.
		ServletContext context = pageContext.getServletContext();
		JspWriter out = pageContext.getOut();

		// Get username.
		String username = (String) pageContext.getSession().getAttribute(
				"username");

		// Get request and path to userfile.
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String userpath = context.getRealPath("/resources/users/" + username
				+ ".xml");

		// Get the link to the file using this tag.
		StringBuffer path = request.getRequestURL();

		// Create course handler and reader.
		UserCourseHandler handler = new UserCourseHandler(userpath);
		NReader reader = new NReader();

		// Handle all different forms, first removal form.
		if (request.getParameter("remove") != null) {
			// Get the id for the course to be removed.
			String remid = request.getParameter("remid");

			// Try to remove course, and print accordingly.
			if (handler.removeCourse(remid)) {
				try {
					out.println("<p class='success'>Kurssi " + remid
							+ " poistettiin onnistuneesti.</p>");
				} catch (IOException e) {
					// This should never happen.
				}
			} else {
				try {
					out.println("<p class='error'>Kurssin poistamisessa "
							+ "kohdattiin ongelma, yritä myöhemmin "
							+ "uudestaan.</p>");
				} catch (IOException e) {
					// This should never happen.
				}
			}
		} // end of removal handling.

		// Handle activation form.
		if (request.getParameter("activate") != null) {
			// Get id for course to be activated.
			String activeid = request.getParameter("activeid");

			// Try to activate the course, and print accordingly.
			if (handler.changeState(activeid, UserCourseHandler.ACTIVE)) {
				try {
					out.println("<p class='success'>Kurssi " + activeid
							+ " aktivoitiin.");
				} catch (IOException e) {
					// This should never happen.
				}
			} else {
				try {
					out.println("<p class='error'>Kurssin aktivoimisessa "
							+ "kohdattiin ongelma, yritä myöhemmin uudelleen.");
				} catch (IOException e) {
					// This should never happen.
				}
			}
		}

		// Handle deactivation form.
		if (request.getParameter("deactivate") != null) {
			// Get id for course to be deactivated.
			String deactiveid = request.getParameter("deactiveid");

			// Try to deactivate the course, and print accordingly.
			if (handler.changeState(deactiveid, UserCourseHandler.NONACTIVE)) {
				try {
					out.println("<p class='success'>Kurssi " + deactiveid
							+ " deaktivoitiin.");
				} catch (IOException e) {
					// This should never happen.
				}
			} else {
				try {
					out.println("<p class='error'>Kurssin deaktivoinnissa "
							+ "kohdattiin ongelma, yritä myöhemmin uudelleen.");
				} catch (IOException e) {
					// This should never happen.
				}
			}
		}

		// Handle completed form.
		if (request.getParameter("completed") != null) {
			// Get id for course to be completed.
			String completedid = request.getParameter("completedid");

			// Try to complete course, and print accordingly.
			if (handler.changeState(completedid, UserCourseHandler.COMPLETED)) {
				try {
					out.println("<p class='success'> Kurssi " + completedid
							+ " siirrettiin suoritettuihin.");
				} catch (IOException e) {
					// This should never happen.
				}
			} else {
				try {
					out.println("<p class='error'>Kurssin siirtämisessä "
							+ "suoritettuihin kohdattiin ongelma, yritä "
							+ "myöhemmin uudelleen.");
				} catch (IOException e) {
					// This should never happen.
				}
			}
		}

		// Handle group changing form.
		if (request.getParameter("changegroup") != null) {
			// Get given group and id.
			String changeid = request.getParameter("changeid");
			String changegroup = request.getParameter("group");

			// Try to change group, and print accordingly.
			if (handler.changeGroup(changeid, changegroup)) {
				try {
					out.println("<p class='success'>Kurssin " + changeid
							+ " ryhmä vaihdettiin onnistuneesti.</p>");
				} catch (IOException e) {
					// This should never happen.
				}
			} else {
				try {
					out.println("<p class='error'>Ryhmän vaihtamisessa "
							+ "kohdattiin ongelma, yritä myöhemmin uudelleen.");
				} catch (IOException e) {
					// This should never happen.
				}
			}
		}

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

				/*
				 * Get course's groups and insert them into a set to avoid
				 * duplicates.
				 */
				List<NEvent> exercises = reader.getCourseExercises(c);
				Set<String> groups = new HashSet<String>();
				for (NEvent e : exercises) {

					// Make sure exercises actually have groups, before adding.
					if (!e.getGroup().equals("")) {
						groups.add(e.getGroup());
					}
				}
				if (groups.size() > 0) {
					/*
					 * If course had groups, get chosen and then create a form
					 * to pick a new group.
					 */
					String chosengroup = handler.getGroup(id);
					String groupmenu = "Ryhmä: <form action='" + path
							+ "' method='post'><select name='group'>"
							+ "<option value=' '>--</option>";

					// Loop through groups and add them as options.
					for (String group : groups) {
						String selected = chosengroup.equals(group) ? "selected"
								: null;
						groupmenu += "<option value='" + group + "' "
								+ selected + ">" + group + "</option>";
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
						+ "<input type='hidden' name='completedid' value='"
						+ id + "' /><input type='submit' name='completed' "
						+ "value='Siirrä suoritettuihin' /></form>"
						+ "<form action='" + path + "' method='post'>"
						+ "<input type='hidden' name='deactiveid' value='" + id
						+ "' /><input type='submit' name='deactivate' "
						+ "value='Poista aktiivisista' /></form>" + end;

				// Add course to div.
				div1 += course;
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
			out.println(div1 + div2 + div3);
		} catch (IOException e) {
			// This should never happen.
		}

		return SKIP_BODY;
	}
}
