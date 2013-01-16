package spas.nhandling.nelements;

/**
 * Superclass for all different NElements. Organization and department types are
 * made straight out of this class. Point for this class is to get an
 * object-type representation of all the information needed from <a href=
 * "https://wiki.aalto.fi/download/attachments/71895449/NoppaAPI_eng.pdf?version=2&modificationDate=1352361073000"
 * target="_blank">the Noppa API</a>.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 0.1
 * @see NElementType
 * 
 */
public class NElement {
	/**
	 * Id for NElement.
	 */
	private String id = "";
	/**
	 * Id for organization NElement belongs to. Only used with departments.
	 */
	private String orgid = "";
	/**
	 * Name for NElement.
	 */
	private String name = "";

	/**
	 * Setter for NElement's id.
	 * 
	 * @param elementid
	 *            The id for NElement.
	 */
	public void setId(String elementid) {
		id = elementid;
	}

	/**
	 * Getter for NElement's id.
	 * 
	 * @return Id for NElement, or an empty String (not <code>null</code>), if
	 *         it doesn't exist.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for NElement's organization's id. Only used with departments.
	 * 
	 * @param id
	 *            The organization id for NElement.
	 */
	public void setOrgId(String id) {
		orgid = id;
	}

	/**
	 * Getter for NElement's organization's id. Only used with departments.
	 * 
	 * @return Organization id for NElement, or and empty String (not
	 *         <code>null</code>), if it doesn't exist.
	 */
	public String getOrgId() {
		return orgid;
	}

	/**
	 * Setter for NElement's name.
	 * 
	 * @param elementname
	 *            The name for NElement.
	 */
	public void setName(String elementname) {
		name = elementname;
	}

	/**
	 * Getter for NElement's name.
	 * 
	 * @return The NElement's name, or an empty String (not <code>null</code>),
	 *         if it doesn't exist.
	 */
	public String getName() {
		return name;
	}
}
