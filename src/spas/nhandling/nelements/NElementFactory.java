package spas.nhandling.nelements;

/**
 * Factory-class for creating different NElements. Mostly so that the only thing
 * required to do is call this one method instead of calling each and every
 * NElement's own constructor.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 0.1
 * 
 */
public class NElementFactory {

	private NElementFactory() {
		throw new InstantiationError("Creation of this object is not allowed.");
	}

	/**
	 * The only point in NelementFactory, which creates the appropriate
	 * NoppaElements according to NelementType.
	 * 
	 * @param net
	 *            Which kind of NElement is called for.
	 * @return The appropriate NElement, or <code>null</code> in case of faulty
	 *         NElementType.
	 */
	public static NElement createNElement(NElementType net) {
		switch (net) {
		case ORGANIZATION:
			return new NElement();
		case DEPARTMENT:
			return new NElement();
		case COURSE:
			return new NCourse();
		case ASSIGNMENT:
			return new NEvent();
		case EXERCISE:
			return new NEvent();
		case LECTURE:
			return new NEvent();
		case OTHER:
			return new NEvent();
		default:
			return null;
		}
	}
}
