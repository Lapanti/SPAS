package spas.nelements;

import java.util.HashMap;
import java.util.Map;

/**
 * Superclass for all different NoppaElements. Organization and department types
 * are made straight out of this class. Main point for this class is to get an
 * objet-type representation of all the information needed from the Noppa API.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class Nelement {
	/**
	 * Map to hold the different types of information on the Nelement.
	 */
	public Map<String, Object> info = new HashMap<String, Object>();
	/**
	 * The type for particular Nelement.
	 */
	public final NelementType type;

	/**
	 * Constructor-method for creating a new Nelement.
	 * 
	 * @param net
	 *            The appropriate NelementType for the new Nelement.
	 */
	public Nelement(NelementType net) {
		type = net;
	}

	/**
	 * Method for setting Nelement's id.
	 * 
	 * @param id
	 *            The id for this Nelement.
	 */
	public void setId(String id) {
		info.put("id", id);
	}

	/**
	 * Method for getting the id of current Nelement.
	 * 
	 * @return The Nelement's id or null, if it doesn't exist.
	 */
	public String getId() {
		try {
			return (String) info.get("id");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for setting Nelement's name.
	 * 
	 * @param name
	 *            The name for this Nelement.
	 */
	public void setName(String name) {
		info.put("name", name);
	}

	/**Method for getting the name of current Nelement.
	 * 
	 * @return The Nelement's name or null, if it doesn't exist.
	 */
	public String getName() {
		try {
			return (String) info.get("name");
		} catch (Exception e) {
			return null;
		}
	}
}
