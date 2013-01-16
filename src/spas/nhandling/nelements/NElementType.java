package spas.nhandling.nelements;

/**
 * Enumeration to differentiate between different NElements.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 0.1
 * 
 */
public enum NElementType {
	/**
	 * Organizations are the top-level structure for searching courses.
	 */
	ORGANIZATION,

	/**
	 * Departments are a part of an organization, differentiating further on
	 * the type of courses offered.
	 */
	DEPARTMENT,

	/**
	 * @see NCourse
	 */
	COURSE,

	/**
	 * @see NEvent
	 */
	LECTURE,

	/**
	 * @see NEvent
	 */
	EXERCISE, 
	
	/**
	 * @see NEvent
	 */
	ASSIGNMENT, 
	
	/**
	 * @see NEvent
	 */
	OTHER;

	@Override
	public String toString() {
		switch (this) {
		case ORGANIZATION:
			return "organization";
		case DEPARTMENT:
			return "department";
		case COURSE:
			return "course";
		case LECTURE:
			return "lecture";
		case EXERCISE:
			return "exercise";
		case ASSIGNMENT:
			return "assignment";
		case OTHER:
			return "event";
		}
		return null;
	}
}
