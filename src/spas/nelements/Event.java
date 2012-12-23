package spas.nelements;

import org.joda.time.DateTime;

/**
 * Event contains methods for getting and setting information Exercises and
 * Lectures have.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public abstract class Event extends Nelement {

	/**
	 * Basic constructor for an Event.
	 * 
	 * @param net
	 *            The type of this Event.
	 */
	public Event(NelementType net) {
		super(net);
	}

	/**
	 * Method for setting the location for an Event.
	 * 
	 * @param location
	 *            The location for this Event.
	 */
	public void setLocation(String location) {
		info.put("location", location);
	}

	/**
	 * Method for getting the location of this Event.
	 * 
	 * @return This Event's location, or null if it doesn't exist.
	 */
	public String getLocation() {
		try {
			return (String) info.get("location");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting the date for this Event.
	 * 
	 * @param dt
	 *            Date for this Event.
	 */
	public void setDate(DateTime dt) {
		info.put("date", dt);
	}

	/**
	 * Method for getting this Event's date.
	 * 
	 * @return Date for this Event, or null if it doesn't exist.
	 */
	public DateTime getDate() {
		try {
			return (DateTime) info.get("date");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting the starting time of this Event.
	 * 
	 * @param dt
	 *            Starting time for this Event.
	 */
	public void setStart(DateTime dt) {
		info.put("start_time", dt);
	}

	/**
	 * Method for getting the starting time of this Event.
	 * 
	 * @return This Event's starting time, or null if it doesn't exist.
	 */
	public DateTime getStart() {
		try {
			return (DateTime) info.get("start_time");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting the ending time for this Event.
	 * 
	 * @param dt
	 *            This Event's ending time.
	 */
	public void setEnd(DateTime dt) {
		info.put("end_time", dt);
	}

	/**
	 * Method for getting this Event's ending time.
	 * 
	 * @return This Event's ending time, or null if it doesn't exist.
	 */
	public DateTime getEnd() {
		try {
			return (DateTime) info.get("end_time");
		} catch (Exception e) {
			return null;
		}
	}
}
