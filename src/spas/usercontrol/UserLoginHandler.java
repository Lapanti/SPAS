package spas.usercontrol;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import spas.XMLTools;

/**
 * Class for creating and reading user's login information. None of the methods
 * in this class are public for security reasons. If you change the
 * implementation of this classes methods you can easily change the user
 * database-system to another one. This version uses XML-files to not be reliant
 * on server's databases.
 * 
 * @see UserHandler
 * @author Lauri Lavanti
 * @version 1.0
 * @since 0.1
 */
public class UserLoginHandler {
	/**
	 * The file containing user's file.
	 */
	private File userfile;

	/**
	 * Constructor for a new UserLoginHandler. Userpath must be absolutely
	 * correct or all methods will return false or empty.
	 * 
	 * @param userpath
	 *            The absolute path for user's file.
	 */
	UserLoginHandler(String userpath) {
		userfile = new File(userpath);
	}

	/**
	 * Creates and saves the information for a new user.
	 * 
	 * @see UserHandler#createUser(String, String, String, String)
	 *      Security-perspective.
	 * @param name
	 *            User's login name.
	 * @param pword
	 *            User's encrypted password.
	 * @param salt
	 *            Salt used for password's encryption.
	 * @param email
	 *            User's email.
	 * @param modelpath
	 *            Path to the model of a user-file.
	 * @return <code>true</code>, if and only if, nothing went wrong.
	 */
	boolean saveUser(String name, String pword, String salt, String email,
			String modelpath) {
		try {
			// Parse the file to document.
			Document doc = XMLTools.parse(new File(modelpath));

			// Get login-element to be edited.
			Element loginElement = XMLTools.getElement(doc, "login");

			// Setting the username element.
			loginElement.appendChild(XMLTools.setTextValue(doc, "username",
					name));

			// Setting the pword element.
			loginElement
					.appendChild(XMLTools.setTextValue(doc, "pword", pword));

			// Setting the salt element.
			loginElement.appendChild(XMLTools.setTextValue(doc, "salt", salt));

			// Setting the email element.
			loginElement
					.appendChild(XMLTools.setTextValue(doc, "email", email));

			// Setting the session-id element.
			loginElement.appendChild(XMLTools.setTextValue(doc, "session-id",
					""));

			// Attach it all back to document.
			doc.getDocumentElement().appendChild(loginElement);

			// Save it to file and check it exists.
			return XMLTools.saveFile(doc, userfile) && userExists(name);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Deletes user from database. Be very careful when calling this, as it is
	 * final.
	 * 
	 * @see UserHandler#removeUser(String, String) Security-perspective.
	 * @param username
	 *            The user's name to be deleted.
	 * @return <code>true</code>, if and only if, everything worked.
	 */
	boolean deleteUser(String username) {

		// Make sure user actually exists before doing anything.
		if (userExists(username)) {
			return userfile.delete();
		}
		return false;
	}

	/**
	 * Gets user's registered password (encrypted) and the salt used in it's
	 * encryption.
	 * 
	 * @param username
	 *            User's registered login name.
	 * @return Array with password and salt, or <code>null</code> if something
	 *         went wrong.
	 */
	String[] getPassword(String username) {

		// If user doesn't exist, do nothing.
		if (!userExists(username)) {
			return null;
		}
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get login element to examine.
			Element LoginElement = XMLTools.getElement(doc, "login");

			// Checking to see if user element is the one requested.
			if (XMLTools.getTagValue("username", LoginElement)
					.equalsIgnoreCase(username)) {

				// Username matched, getting pword and salt.
				String[] pwordANDsalt = new String[2];

				// Getting the pword.
				pwordANDsalt[0] = XMLTools.getTagValue("pword", LoginElement);

				// Getting the salt.
				pwordANDsalt[1] = XMLTools.getTagValue("salt", LoginElement);

				return pwordANDsalt;
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
		}
		return null;
	}

	/**
	 * Get user's email.
	 * 
	 * @see UserHandler#getEmail(String) Security-perspective.
	 * @param username
	 *            User's registered login name.
	 * @return User's registered email, or <code>null</code> if there was an
	 *         error.
	 */
	String getEmail(String username) {

		// If user doesn't exist, do nothing.
		if (!userExists(username)) {
			return null;
		}
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get the element to be examined.
			Element LoginElement = XMLTools.getElement(doc, "login");

			// Checking to see if user element is the one requested.
			if (XMLTools.getTagValue("username", LoginElement)
					.equalsIgnoreCase(username)) {

				// Getting the pword.
				return XMLTools.getTagValue("email", LoginElement);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
		}
		return null;
	}

	/**
	 * Changes user's registered password.
	 * 
	 * @see UserHandler#changePassword(String, String, String)
	 *      Security-perspective.
	 * @param username
	 *            The user's login name.
	 * @param pword
	 *            The new password to be saved.
	 * @param salt
	 *            The salt used for password's encryption.
	 * @return <code>true</code>, if and only if, nothing went wrong.
	 */
	boolean changePassword(String username, String pword, String salt) {

		// Make sure user exists before doing anything.
		if (!userExists(username)) {
			return false;
		}
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get the element to be edited.
			Element loginElement = XMLTools.getElement(doc, "login");

			// Changing the password.
			loginElement
					.appendChild(XMLTools.setTextValue(doc, "pword", pword));

			// Changing the salt.
			loginElement.appendChild(XMLTools.setTextValue(doc, "salt", salt));

			// Attach it all back to document.
			doc.getDocumentElement().appendChild(loginElement);

			// Save it to file.
			return XMLTools.saveFile(doc, userfile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These shouldn't normally happen.
		}
		return false;
	}

	/**
	 * Changes user's registered email.
	 * 
	 * @see UserHandler#changeEmail(String, String, String) Security-perspective.
	 * @param username
	 *            The user's login name.
	 * @param newmail
	 *            New email to be saved.
	 * @return <code>true</code>, if and only if, nothing goes wrong.
	 */
	boolean changeEmail(String username, String newmail) {

		// If user doesn't exist, do nothing.
		if (!userExists(username)) {
			return false;
		}
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get element to be edited.
			Element loginElement = XMLTools.getElement(doc, "login");

			// Changing the email.
			loginElement.appendChild(XMLTools.setTextValue(doc, "email",
					newmail));

			// Attach it all back to document.
			doc.getDocumentElement().appendChild(loginElement);

			// Save it to file.
			return XMLTools.saveFile(doc, userfile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These shouldn't normally happen.
		}
		return false;
	}

	/**
	 * Changes user's saved session-id.
	 * 
	 * @param username
	 *            User's registered login name.
	 * @param id
	 *            User's session-id.
	 * @return <code>true</code>, if and only if, nothing went wrong.
	 */
	boolean changeSessionId(String username, String id) {

		// If user doesn't exist, do nothing.
		if (!userExists(username)) {
			return false;
		}
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get element to be edited.
			Element loginElement = XMLTools.getElement(doc, "login");

			// Changing the email.
			loginElement.appendChild(XMLTools.setTextValue(doc, "session-id",
					id));

			// Attach it all back to document.
			doc.getDocumentElement().appendChild(loginElement);

			// Save it to file.
			return XMLTools.saveFile(doc, userfile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These shouldn't normally happen.
		}
		return false;
	}

	/**
	 * Checks, if given session-id matches the one saved on file.
	 * 
	 * @param username
	 *            User's registered login name.
	 * @param id
	 *            User's current session id.
	 * @return <code>true</code>, if and only if, id matched the one on file.
	 */
	boolean validateUser(String username, String id) {
		// If user doesn't exist, do nothing.
		if (!userExists(username)) {
			return false;
		}
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get the element to be examined.
			Element LoginElement = XMLTools.getElement(doc, "login");

			// Checking to see if user element is the one requested.
			if (XMLTools.getTagValue("username", LoginElement)
					.equalsIgnoreCase(username)) {

				// Checking if session-ids match.
				return XMLTools.getTagValue("session-id", LoginElement).equals(
						id);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
		}
		return false;

	}

	/**
	 * Check to see if user actually exists.
	 * 
	 * @param username
	 *            User's registered login name.
	 * @return <code>true</code>, if and only if, user exists.
	 */
	boolean userExists(String username) {

		// If userfile doesn't exist or is not a file do nothing.
		if (!userfile.exists() && !userfile.isFile()) {
			return false;
		}
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get element to examine.
			Element LoginElement = XMLTools.getElement(doc, "login");

			// Checking to see if user element is the one suggested.
			if (XMLTools.getTagValue("username", LoginElement)
					.equalsIgnoreCase(username)) {
				return true;
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
		}
		return false;
	}
}
