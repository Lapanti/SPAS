package spas.nelements;

/**
 * Factory-class for creating different NoppaElements. Mostly so that the only
 * thing required to do is call one method instead of calling each and every
 * Nelement's own construction-method.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class NelementFactory {

	/**
	 * The only point in NelementFactory, which creates the appropriate
	 * NoppaElements according to NelementType.
	 * 
	 * @param net
	 *            Which kind of Nelement is called for.
	 * @return The appropriate Nelement.
	 */
	@SuppressWarnings("static-method")
	public Nelement createNoppaElement(NelementType net) {
		switch (net) {
		case ORGANIZATION:
			return new Nelement(net);
		case DEPARTMENT:
			return new Nelement(net);
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
