package nElements;

/**
 * Factory-class for creating different NoppaElements. Mostly so that the only
 * thing required to do is call one method instead of calling each and every
 * NoppaElement's own construction-method.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class NoppaElementFactory {

	/**
	 * The only point in NoppaElementFactory, which creates the appropriate
	 * NoppaElements according to NoppaElementType.
	 * 
	 * @param net
	 *            Which kind of NoppaElement is called for.
	 * @return The appropriate NoppaElement.
	 */
	@SuppressWarnings("static-method")
	public NoppaElement createNoppaElement(NoppaElementType net) {
		switch (net) {
		case ORGANIZATION:
			return new NoppaElement(net);
		case DEPARTMENT:
			return new NoppaElement(net);
		case COURSE:
			return new Course();
		case ASSIGNMENT:
			return new Assignment();
		case EXERCISE:
			return new Exercise();
		case LECTURE:
			return new Lecture();
		case OTHER:
			return new OtherEvent();
		default:
			return null;
		}
	}
}
