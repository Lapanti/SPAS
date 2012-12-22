package nElements;

/**
 * Class for Courses, contains methods for setting and getting information only
 * courses need, unlike organization, lecture etc.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class Course extends NoppaElement {

	/**
	 * Basic constructor for a new Course.
	 */
	public Course() {
		super(NoppaElementType.COURSE);
	}

	/**
	 * Method for setting the current Course's credits.
	 * 
	 * @param credits
	 *            The credits for this Course.
	 */
	public void setCredits(String credits) {
		info.put("credits", credits);
	}

	/**
	 * Method for getting the current Course's credits.
	 * 
	 * @return The current Course's credits, or null if they don't exist.
	 */
	public String getCredits() {
		try {
			return (String) info.get("credits");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting the current Course's period/periods.
	 * 
	 * @param periods
	 *            The period/periods for the current Course.
	 */
	public void setPeriod(String periods) {
		info.put("periods", periods);
	}

	/**
	 * Method for getting the current Course's period/periods.
	 * 
	 * @return The current Course's period/periods, or null if they don't exist.
	 */
	public String getPeriod() {
		try {
			return (String) info.get("periods");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting the current Course's content.
	 * 
	 * @param content
	 *            Content for Course.
	 */
	public void setContent(String content) {
		info.put("content", content);
	}

	/**
	 * Method for getting the current Course's content.
	 * 
	 * @return The current Course's content, of null if it doesn't exist.
	 */
	public String getContent() {
		try {
			return (String) info.get("content");
		} catch (Exception e) {
			return null;
		}
	}
}
