package spas.nhandling.nelements;

import java.util.Calendar;

/**
 * Event type NElements. Most importantly containing Calendar-type information
 * about their duration etc.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 0.1
 * 
 */
public class NEvent extends NElement {
	/**
	 * The location of NEvent.
	 */
	private String location = "";
	/**
	 * A more accurate description of NEvent.
	 */
	private String description = "";
	/**
	 * Starting date and time for NEvent.
	 */
	private Calendar startdate = null;
	/**
	 * Ending date and time for NEvent.
	 */
	private Calendar enddate = null;
	/**
	 * NEvent's group. Only used with exercises.
	 */
	private String group = "";

	/**
	 * Setter for event's location.
	 * 
	 * @param elocation
	 *            The location for event.
	 */
	public void setLocation(String elocation) {
		location = elocation;
	}

	/**
	 * Getter for event's location.
	 * 
	 * @return Location for event, or an empty String (not <code>null</code>),
	 *         if it doesn't exist.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Setter for event's description.
	 * 
	 * @param edescription
	 *            The description for event.
	 */
	public void setDescription(String edescription) {
		description = edescription;
	}

	/**
	 * Getter for event's description.
	 * 
	 * @return Description for event, or an empty String (not <code>null</code>
	 *         ), if it doesn't exist.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Getter for event's starting date. Contains both time and date.
	 * 
	 * @param dt
	 *            Starting time and date for event.
	 */
	public void setStartDate(Calendar dt) {
		startdate = dt;
	}

	/**
	 * Getter for event's starting date. Contains both time and date.
	 * 
	 * @return Starting date and time for event, or a new Calendar, if it
	 *         doesn't exist.
	 */
	public Calendar getStartDate() {
		return startdate == null ? Calendar.getInstance() : startdate;
	}

	/**
	 * Setter for event's ending date. Contains both time and date. Except when
	 * handling exercises, remember that they go weekly from start date's time
	 * to end date's time.
	 * 
	 * @param dt
	 *            Ending date and time for event.
	 */
	public void setEndDate(Calendar dt) {
		enddate = dt;
	}

	/**
	 * Getter for event's ending date. Contains both time and date. Except when
	 * handling exercises, remember that they go weekly from start date's time
	 * to end date's time.
	 * 
	 * @return Ending date and time for event, or a new Calendar, if it doesn't
	 *         exist.
	 */
	public Calendar getEndDate() {
		return enddate == null ? Calendar.getInstance() : enddate;
	}

	/**
	 * Setter for event's group. Only used with exercises.
	 * 
	 * @param egroup
	 *            The group for event.
	 */
	public void setGroup(String egroup) {
		group = egroup;
	}

	/**
	 * Getter for event's group. Only used with exercises.
	 * 
	 * @return Group for event, or an empty String (not <code>null</code>), if
	 *         it doesn't exist.
	 */
	public String getGroup() {
		return group;
	}
}
