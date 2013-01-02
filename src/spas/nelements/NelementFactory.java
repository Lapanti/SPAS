package spas.nelements;

/**
 * Factory-class for creating different NElements. Mostly so that the only thing
 * required to do is call this one method instead of calling each and every
 * NElement's own constructor.
 * 
 * @author Lauri Lavanti
 * @version 0.2
 * @since 0.1
 * 
 */
public class NElementFactory {

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
			return new NElement(net);
		case DEPARTMENT:
			return new NElement(net);
		case COURSE:
			return new NCourse();
		case ASSIGNMENT:
			return new NEvent(net);
		case EXERCISE:
			return new NEvent(net);
		case LECTURE:
			return new NEvent(net);
		case OTHER:
			return new NEvent(net);
		default:
			return null;
		}
	}
}
