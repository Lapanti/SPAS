package spas.nelements;

/**
 * Enumeration to differentiate between different NElements.
 * 
 * @author Lauri Lavanti
 * @version 1.0
 * @since 0.1
 * 
 */
public enum NElementType {
	/**
	 * Organizations' are the top-level structure for searching courses.
	 */
	ORGANIZATION,

	/**
	 * Departments' are a part of an organization, differentiating further on
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
	ASSIGNMENT,
	OTHER;
}
