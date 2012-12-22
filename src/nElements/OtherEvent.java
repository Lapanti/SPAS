package nElements;

import org.joda.time.DateTime;

/**
 * Class for other types of Events than Lectures etc.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class OtherEvent extends Event {

	/**
	 * Basic constructor for OtherEvent.
	 */
	public OtherEvent() {
		super(NoppaElementType.OTHER);
	}

	/**
	 * Method for setting the type of this OtherEvent.
	 * 
	 * @param type
	 *            The type for this OtherEvent.
	 */
	public void setType(String type) {
		info.put("type", type);
	}

	/**
	 * Method for getting the type of this OtherEvent.
	 * 
	 * @return This OtherEvent's type, or null if it doesn't exist.
	 */
	public String getType() {
		try {
			return (String) info.get("type");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting the title for this OtherEvent.
	 * 
	 * @param title
	 *            This OtherEvent's title.
	 */
	public void setTitle(String title) {
		info.put("title", title);
	}

	/**
	 * Method for getting this OtherEvent's title.
	 * 
	 * @return The title for this OtherEvent, or null if it doesn't exist.
	 */
	public String getTitle() {
		try {
			return (String) info.get("title");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting the ending date for this OtherEvent.
	 * 
	 * @param dt
	 *            The ending date for this OtherEvent.
	 */
	public void setEndDate(DateTime dt) {
		info.put("end_date", dt);
	}

	/**
	 * Method for getting the ending date of this OtherEvent.
	 * 
	 * @return This OtherEvent's ending date, or null if it doesn't exist.
	 */
	public DateTime getEndDate() {
		try {
			return (DateTime) info.get("end_date");
		} catch (Exception e) {
			return null;
		}
	}
}
