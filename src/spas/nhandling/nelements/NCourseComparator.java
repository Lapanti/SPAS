package spas.nhandling.nelements;

import java.util.Comparator;

/**
 * Compares courses to each other according to their id's.
 * 
 * @author Lauri Lavanti
 * @version 1.2
 * @since 1.2
 *
 */
public class NCourseComparator implements Comparator<NCourse> {

	@Override
	public int compare(NCourse arg0, NCourse arg1) {
		return arg0.getId().compareToIgnoreCase(arg1.getId());
	}

}
