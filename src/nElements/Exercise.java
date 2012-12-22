package nElements;

import org.joda.time.DateTime;

/**
 * Exercises are a specific type of Event which hold an ending date (they are
 * weekly) and a group.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class Exercise extends Event {

	/**
	 * The basic constructor for Exercise.
	 * 
	 */
	public Exercise() {
		super(NoppaElementType.EXERCISE);
	}

	/**
	 * Method for setting the group for this Exercise.
	 * 
	 * @param group
	 *            This Exercise's group.
	 */
	public void setGroup(String group) {
		info.put("group", group);
	}

	/**
	 * Method for getting the group for this Exercise.
	 * 
	 * @return This Exercises group, or null if it doesn't exist.
	 */
	public String getGroup() {
		try {
			return (String) info.get("group");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting the ending date for this Exercise.
	 * 
	 * @param dt
	 *            The ending date for this Exercise.
	 */
	public void setEndDate(DateTime dt) {
		info.put("end_date", dt);
	}

	/**
	 * Method for getting the ending date for this Exercise.
	 * 
	 * @return The ending date for this Exercise, or null if it doesn't exist.
	 */
	public DateTime getEndDate() {
		try {
			return (DateTime) info.get("end_date");
		} catch (Exception e) {
			return null;
		}
	}
}
