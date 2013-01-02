package spas.nelements;

/**
 * Course-type NElement. This class exists because SPAS has use for more
 * information considering courses than other NElements.
 * 
 * @author Lauri Lavanti
 * @version 0.2
 * @since 0.1
 * 
 */
public class NCourse extends NElement {

	/**
	 * Constructor for a new Course. Same basic functionality than NElement's
	 * constructor, except it always selects the {@link NElementType} as Course.
	 * 
	 * @see NElement#NElement(NElementType) Superclass constructor.
	 */
	NCourse() {
		super(NElementType.COURSE);
	}

	/**
	 * Basic setter for course's credits.
	 * 
	 * @param credits
	 *            Credits for course.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setCredits(String credits) {
		info.put("credits", credits);
	}

	/**
	 * Basic getter for course's credits.
	 * 
	 * @return Credits for course, or an empty String (not <code>null</code>),
	 *         if they don't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getCredits() {
		String credits = (String) info.get("credits");
		return credits == null ? "" : credits;
	}

	/**
	 * Setter for course's period(s).
	 * 
	 * @param periods
	 *            The period(s) for course.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setPeriod(String periods) {
		info.put("periods", periods);
	}

	/**
	 * Getter for course's period(s).
	 * 
	 * @return The period(s) for course, or an empty String (not
	 *         <code>null</code>), if they don't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getPeriod() {
		String periods = (String) info.get("periods");
		return periods == null ? "" : periods;
	}

	/**
	 * Setter for course's content.
	 * 
	 * @param content
	 *            Content for course.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setContent(String content) {
		info.put("content", content);
	}

	/**
	 * Getter for course's content.
	 * 
	 * @return The content for course, or an empty String (not <code>null</code>
	 *         ), if it doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getContent() {
		String content = (String) info.get("content");
		return content == null ? "" : content;
	}

	/**
	 * Setter for course's group. Only used when asking for a user's courses (if
	 * user has selected a group).
	 * 
	 * @param content
	 *            Group for course.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setGroup(String group) {
		info.put("group", group);
	}

	/**
	 * Getter for course's group. Only used when creating user's calendar (if
	 * user has selected a group).
	 * 
	 * @return Group for course, or an empty String (not <code>null</code>), if
	 *         it doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getGroup() {
		String group = (String) info.get("group");
		return group == null ? "" : group;
	}

	/**
	 * Setter for course's state. Only used with logged users.
	 * 
	 * @param content
	 *            State for Course.
	 * @see NElement#info Holder for NElements' info.
	 * @see spas.usercontrol.UserCourseHandler#ACTIVE
	 * @see spas.usercontrol.UserCourseHandler#NONACTIVE
	 * @see spas.usercontrol.UserCourseHandler#COMPLETED
	 */
	public void setState(int state) {
		info.put("state", state);
	}

	/**
	 * Getter for course's state. Only used with logged users.
	 * 
	 * @return The state for course, or 0, if it doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 * @see spas.usercontrol.UserCourseHandler#ACTIVE
	 * @see spas.usercontrol.UserCourseHandler#NONACTIVE
	 * @see spas.usercontrol.UserCourseHandler#COMPLETED
	 */
	public int getState() {
		/*
		 * Try blocks, because Map's get(key)-method returns null if it doesn't
		 * exist.
		 */
		try {
			return (int) info.get("state");
		} catch (ClassCastException e) {
			/*
			 * This is to prevent getting an error in case the key "state" is
			 * not set in map.
			 */
			return 0;
		}
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
