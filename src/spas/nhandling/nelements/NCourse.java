package spas.nhandling.nelements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Course-type NElement. This class exists because SPAS has use for more
 * information considering courses than other NElements.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 0.1
 * 
 */
public class NCourse extends NElement {
	/**
	 * The amount of credits course is worth.
	 */
	private String credits = "";
	/**
	 * Periods in which course is offered.
	 */
	private List<String> periods = new ArrayList<String>();
	/**
	 * Course's content.
	 */
	private String content = "";
	/**
	 * User's selected group for course.
	 */
	private String group = "";
	/**
	 * User's selected period for course.
	 */
	private String execperiod = "";
	/**
	 * User's selected year for course.
	 */
	private int execyear = Calendar.getInstance().get(Calendar.YEAR);

	/**
	 * Basic setter for course's credits.
	 * 
	 * @param elementcredits
	 *            Credits for course.
	 */
	public void setCredits(String elementcredits) {
		credits = elementcredits;
	}

	/**
	 * Basic getter for course's credits.
	 * 
	 * @return Credits for course, or an empty String (not <code>null</code>),
	 *         if they don't exist.
	 */
	public String getCredits() {
		return credits;
	}

	/**
	 * Setter for course's period(s).
	 * 
	 * @param period
	 *            The period(s) for course.
	 */
	public void setPeriods(String period) {
		String[] pds = period.split(", ");
		for (String p : pds) {
			periods.add(p);
		}
	}

	/**
	 * Getter for course's period(s).
	 * 
	 * @return The period(s) for course, or an empty list, if they don't exist.
	 */
	public List<String> getPeriods() {
		return periods;
	}

	/**
	 * Setter for course's content.
	 * 
	 * @param coursecontent
	 *            Content for course.
	 */
	public void setContent(String coursecontent) {
		content = coursecontent;
	}

	/**
	 * Getter for course's content.
	 * 
	 * @return The content for course, or an empty String (not <code>null</code>
	 *         ), if it doesn't exist.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Setter for course's group. Only used when asking for a user's courses (if
	 * user has selected a group).
	 * 
	 * @param cgroup
	 *            Group for course.
	 */
	public void setGroup(String cgroup) {
		group = cgroup;
	}

	/**
	 * Getter for course's group. Only used when creating user's calendar (if
	 * user has selected a group).
	 * 
	 * @return Group for course, or an empty String (not <code>null</code>), if
	 *         it doesn't exist.
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Basic setter for user's period for completing course.
	 * 
	 * @param period
	 *            The period in which user will complete course.
	 */
	public void setExecperiod(String period) {
		execperiod = period;
	}

	/**
	 * Basic getter for user's period for completing course.
	 * 
	 * @return The period in which user will complete course, or if not chosen,
	 *         the first period in which course is offered.
	 */
	public String getExecperiod() {
		if (execperiod != null) {
			return execperiod;
		}
		if (periods.size() > 0) {
			return periods.get(0);
		}
		return "";
	}

	/**
	 * Basic setter for the year in which user will complete course.
	 * 
	 * @param year
	 *            The year in which user will complete course.
	 */
	public void setExecyear(int year) {
		execyear = year;
	}

	/**
	 * Basic getter for the year in which user will complete course.
	 * 
	 * @return The year in which user will complete course, or if not chosen,
	 *         current year.
	 */
	public int getExecyear() {
		return execyear;
	}
}
