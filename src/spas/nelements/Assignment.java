package spas.nelements;

/**
 * Assignment is a specific type of Event, that holds a tilte and content. Also
 * remember that Assignments, even though they extend Event, do not have a
 * starting time, only an ending time.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class Assignment extends Event {

	/**
	 * Basic constructor for Assignment.
	 */
	public Assignment() {
		super(NelementType.ASSIGNMENT);
	}

	/**
	 * Method for setting the title for this Assignment.
	 * 
	 * @param title
	 *            This Assignment's title.
	 */
	public void setTitle(String title) {
		info.put("title", title);
	}

	/**
	 * Method for getting the title for this Assignment.
	 * 
	 * @return This Assignment's title, or null if it doesn't exist.
	 */
	public String getTitle() {
		try {
			return (String) info.get("title");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting the content for this Assignment.
	 * 
	 * @param content
	 *            This Assignment's content.
	 */
	public void setContent(String content) {
		info.put("content", content);
	}

	/**
	 * Method for getting the content of this Assignment.
	 * 
	 * @return This Assignment's content, or null if it doesn't exist.
	 */
	public String getContent() {
		try {
			return (String) info.get("content");
		} catch (Exception e) {
			return null;
		}
	}
}
