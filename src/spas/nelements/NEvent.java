package spas.nelements;

import java.util.Calendar;

/**
 * Event type NElements. Most importantly containing Calendar-type information
 * about their duration etc.
 * 
 * @author Lauri Lavanti
 * @version 0.2
 * @since 0.1
 * 
 */
public class NEvent extends NElement {

	/**
	 * Constructor for an event.
	 * 
	 * @param net
	 *            The type for this event
	 * @see NElement#NElement(NElementType) Superclass constructor.
	 */
	NEvent(NElementType net) {
		super(net);
	}

	/**
	 * Setter for event's location.
	 * 
	 * @param location
	 *            The location for event.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setLocation(String location) {
		info.put("location", location);
	}

	/**
	 * Getter for event's location.
	 * 
	 * @return Location for event, or an empty String (not <code>null</code>),
	 *         if it doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getLocation() {
		String location = (String) info.get("location");
		return location == null ? "" : location;
	}

	/**
	 * Setter for event's description.
	 * 
	 * @param summary
	 *            The description for event.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setDescription(String description) {
		info.put("description", description);
	}

	/**
	 * Getter for event's description.
	 * 
	 * @return Description for event, or an empty String (not <code>null</code>
	 *         ), if it doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getDescription() {
		String description = (String) info.get("description");
		return description == null ? "" : description;
	}

	/**
	 * Getter for event's starting date. Contains both time and date.
	 * 
	 * @param dt
	 *            Starting time and date for event.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setStartDate(Calendar dt) {
		info.put("startdate", dt);
	}

	/**
	 * Getter for event's starting date. Contains both time and date.
	 * 
	 * @return Starting date and time for event, or a new Calendar, if it
	 *         doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public Calendar getStartDate() {
		Calendar start = (Calendar) info.get("startdate");
		return start == null ? Calendar.getInstance() : start;
	}

	/**
	 * Setter for event's ending date. Contains both time and date. Except when
	 * handling exercises, remember that they go weekly from start date's time
	 * to end date's time.
	 * 
	 * @param dt
	 *            Ending date and time for event.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setEndDate(Calendar dt) {
		info.put("enddate", dt);
	}

	/**
	 * Getter for event's ending date. Contains both time and date. Except when
	 * handling exercises, remember that they go weekly from start date's time
	 * to end date's time.
	 * 
	 * @return Ending date and time for event, or a new Calendar, if it doesn't
	 *         exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public Calendar getEndDate() {
		Calendar end = (Calendar) info.get("enddate");
		return end == null ? Calendar.getInstance() : end;
	}

	/**
	 * Setter for event's group.
	 * 
	 * @param group
	 *            The group for event.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setGroup(String group) {
		info.put("group", group);
	}

	/**
	 * Getter for event's group.
	 * 
	 * @return Group for event, or an empty String (not <code>null</code>), if
	 *         it doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getGroup() {
		String group = (String) info.get("group");
		return group == null ? "" : group;
	}
}
