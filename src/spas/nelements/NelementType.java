package spas.nelements;

/**
 * Enumeration to differentiate between different NoppaElements.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public enum NelementType {
	ORGANIZATION, DEPARTMENT, COURSE, LECTURE, EXERCISE, ASSIGNMENT, OTHER;

	@Override
	public String toString() {

		// String representation of events for the calendar view.

		switch (this) {
		case LECTURE:
			return "L";
		case EXERCISE:
			return "E";
		case ASSIGNMENT:
			return "A";
		case OTHER:
			return "O";
		default:
			return null;
		}
	}
}
