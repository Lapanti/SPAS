package spas.taglib.printers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import spas.nhandling.NReader;
import spas.nhandling.nelements.NCourse;
import spas.usercontrol.UserCourseHandler;

/**
 * Prints out user's courses accordingly.
 * 
 * @author Lauri Lavanti
 * @version 1.2
 * @since 1.1
 * @see UserCourseHandler
 * @see NReader
 * 
 */
public class PrintCourses extends TagSupport {

	@Override
	public int doStartTag() {
		// Get writer and request.
		JspWriter out = pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		// Create handler.
		UserCourseHandler handler = new UserCourseHandler(pageContext
				.getServletContext().getRealPath(
						"resources/users/"
								+ pageContext.getSession().getAttribute(
										"username") + ".xml"));

		// Get user's courses and loop through them.
		SortedMap<Integer, List<NCourse>> courses = new TreeMap<Integer, List<NCourse>>();
		for (NCourse c : handler.getCourses()) {

			// Get execution year and period divide them to lists.
			Integer year = c.getExecyear();
			String period = c.getExecperiod();

			// In case it's part of academic year 'X'-'year', where X = year-1,
			// make 'year' into 'X'.
			if (period.contains("III") || period.contains("IV")) {
				year--;
			}

			// Add it to the map, and make sure map has a list there.
			if (courses.get(year) == null) {
				courses.put(year, new ArrayList<NCourse>());
			}
			courses.get(year).add(c);

		} // end of for-loop.

		// Build the tables for every academic year.
		for (Integer year : courses.keySet()) {
			// Make sure past years are only shown in case user wanted them to.
			if (request.getParameter("earlier") == null) {
				if (year < Calendar.getInstance().get(Calendar.YEAR) -1) {
					continue;
				}
			}

			// Prepare the building of every table.
			String I = "";
			String II = "";
			String III = "";
			String IV = "";

			// Prepare credits for each period and year.
			int crI = 0;
			int crII = 0;
			int crIII = 0;
			int crIV = 0;

			// Loop through courses for this year.
			for (NCourse c : courses.get(year)) {
				String id = c.getId();
				String name = c.getName();
				String course = "<a href='course.jsp?id=" + id + "&name="
						+ name + "'>" + id + ": " + name + "</a><br/>";

				// Get credits for course.
				int credits = 0;
				for (String cr : c.getCredits().split("-")) {
					try {
						int newcr = Integer.parseInt(cr);
						if (credits < newcr) {
							credits = newcr;
						}
					} catch (NumberFormatException e) {
						// This shouldn't happen.
					}
				}

				// Get periods.
				String[] periods = c.getExecperiod().split(" - ");
				// Because teachers don't have a standard.
				if (periods.length == 1) {
					periods = periods[0].split("-");
				}
				
				// Add course to according divs.
				for (String period : periods) {
					if (period.contains("IV")) {
						IV += course;
						crIV += credits;
					} else if (period.contains("III")) {
						III += course;
						crIII += credits;
					} else if (period.contains("II")) {
						II += course;
						crII += credits;
					} else if (period.contains("I")) {
						I += course;
						crI += credits;
					}
				}
			}

			// Build the rest of the year div and print it.
			try {
				out.println("<div class='year'><h1>Lukuvuosi " + year + "-"
						+ (year + 1) + "</h1>");
				out.println("<div class='period'><h2>I (" + crI + ")</h2>");
				out.println(I + "</div>");
				out.println("<div class='period'><h2>II (" + crII + ")</h2>");
				out.println(II + "</div>");
				out.println("<div class='period'><h2>III (" + crIII + ")</h2>");
				out.println(III + "</div>");
				out.println("<div class='period'><h2>IV (" + crIV + ")</h2>");
				out.println(IV + "</div>");
				out.println("</div>");
			} catch (IOException e) {
				// This should never happen.
			}
		} // end of for-loop.

		return SKIP_BODY;
	}
}
