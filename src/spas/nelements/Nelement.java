package spas.nelements;

import java.util.HashMap;
import java.util.Map;

/**
 * Superclass for all different NElements. Organization and department types are
 * made straight out of this class. Point for this class is to get an objet-type
 * representation of all the information needed from <a href=
 * "https://wiki.aalto.fi/download/attachments/71895449/NoppaAPI_eng.pdf?version=2&modificationDate=1352361073000"
 * target="_blank">the Noppa API</a>.
 * 
 * @author Lauri Lavanti
 * @version 0.2
 * @since 0.1
 * @see NElementType
 * 
 */
public class NElement {
	/**
	 * Map to hold the different types of information on the NElement. Reason
	 * for using a Map, instead of different Strings etc. was so that NElement
	 * and classes that extend it can use one storage for data.
	 */
	public Map<String, Object> info = new HashMap<String, Object>();
	/**
	 * The type for NElement.
	 * 
	 * @see NElementType
	 */
	public final NElementType type;

	/**
	 * Constructor for creating a new NElement. The only point for this method
	 * is to let other classes know which methods they can use out of this
	 * class.
	 * 
	 * @param net
	 *            The appropriate NElementType for the new NElement.
	 */
	NElement(NElementType net) {
		type = net;
	}

	/**
	 * Setter for NElement's id. It can't be edited afterwards.
	 * 
	 * @param id
	 *            The id for NElement.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setId(String id) {
		if (getId().equals("")) {
			info.put("id", id);
		}
	}

	/**
	 * Getter for NElement's id.
	 * 
	 * @return Id for NElement, or an empty String (not <code>null</code>), if
	 *         it doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getId() {
		String id = (String) info.get("id");
		return id == null ? "" : id;
	}

	/**
	 * Setter for NElemen'ts organization's id. Only used with departments.
	 * 
	 * @param id
	 *            The organization id for NElement.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setOrgId(String id) {
		info.put("org_id", id);
	}

	/**
	 * Getter for NElement's organization's id. Only used with departments.
	 * 
	 * @return Organization id for NElement, or and empty String (not
	 *         <code>null</code>), if it doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getOrgId() {
		String orgid = (String) info.get("org_id");
		return orgid == null ? "" : orgid;
	}

	/**
	 * Setter for NElement's name.
	 * 
	 * @param name
	 *            The name for NElement.
	 * @see NElement#info Holder for NElements' info.
	 */
	public void setName(String name) {
		info.put("name", name);
	}

	/**
	 * Getter for NElement's name.
	 * 
	 * @return The NElement's name, or an empty String (not <code>null</code>),
	 *         if it doesn't exist.
	 * @see NElement#info Holder for NElements' info.
	 */
	public String getName() {
		String name = (String) info.get("name");
		return name == null ? "" : name;
	}
}
