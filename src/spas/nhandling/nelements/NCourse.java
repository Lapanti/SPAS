package spas.nhandling.nelements;

/**
 * Course-type NElement. This class exists because SPAS has use for more
 * information considering courses than other NElements.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 0.1
 * 
 */
public class NCourse extends NElement {
	private String credits = "";
	private String period = "";
	private String content = "";
	private String group = "";
	private int state = 0;

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
	 * @param periods
	 *            The period(s) for course.
	 */
	public void setPeriod(String periods) {
		period = periods;
	}

	/**
	 * Getter for course's period(s).
	 * 
	 * @return The period(s) for course, or an empty String (not
	 *         <code>null</code>), if they don't exist.
	 */
	public String getPeriod() {
		return period;
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
	 * Setter for course's state. Only used with logged users.
	 * 
	 * @param status
	 *            State for Course.
	 * @see spas.usercontrol.UserCourseHandler#ACTIVE
	 * @see spas.usercontrol.UserCourseHandler#NONACTIVE
	 * @see spas.usercontrol.UserCourseHandler#COMPLETED
	 */
	public void setState(int status) {
		state = status;
	}

	/**
	 * Getter for course's state. Only used with logged users.
	 * 
	 * @return The state for course, or 0, if it doesn't exist.
	 * @see spas.usercontrol.UserCourseHandler#ACTIVE
	 * @see spas.usercontrol.UserCourseHandler#NONACTIVE
	 * @see spas.usercontrol.UserCourseHandler#COMPLETED
	 */
	public int getState() {
		return state;
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		// First checking to see if other is course.
		if (other instanceof NCourse) {
			String thisid = getId();
			String otherid = ((NCourse) other).getId();
			// Don't want to compare courses which don't have an id.
			if (!thisid.equals("") && !otherid.equals("")) {
				result = thisid.equals(otherid);
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		// Making sure it doesn't equal with String's hashCode().
		return (47 * (47 + getId().hashCode()));
	}
}
