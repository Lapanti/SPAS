package nElements;

/**
 * Lecture is a specific type of Event, that holds a title.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class Lecture extends Event {

	/**
	 * The basic constructor for Lecture.
	 */
	public Lecture() {
		super(NoppaElementType.LECTURE);
	}

	/**
	 * Method for setting the title of this Lecture.
	 * 
	 * @param title
	 *            This Lecture's title.
	 */
	public void setTitle(String title) {
		info.put("title", title);
	}

	/**
	 * Method for getting this Lecture's title.
	 * 
	 * @return This Lecture's title, or null if it doesn't exist.
	 */
	public String getTitle() {
		try {
			return (String) info.get("title");
		} catch (Exception e) {
			return null;
		}
	}
}
