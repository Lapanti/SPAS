package spas.usercontrol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class UserXMLHandler {
	String startPath = "resources/";
	final String basefile = "resources/model/user.xml";

	boolean saveUser(String name, String pword, String salt, String email) {
		if (userExists(name)) {
			return false;
		}

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(basefile));
			doc.getDocumentElement().normalize();

			Element rootElement = doc.getDocumentElement();

			Element loginElement = (Element) doc.getElementsByTagName("login")
					.item(0);

			// Setting the username element.
			Node nameNode = doc.getElementsByTagName("username").item(0);
			nameNode.setTextContent(name);
			loginElement.appendChild(nameNode);

			// Setting the pword element.
			Node pwordNode = doc.getElementsByTagName("pword").item(0);
			pwordNode.setTextContent(pword);
			loginElement.appendChild(pwordNode);

			// Setting the salt element.
			Node saltNode = doc.getElementsByTagName("salt").item(0);
			saltNode.setTextContent(salt);
			loginElement.appendChild(saltNode);

			// Setting the email element.
			Node emailNode = doc.getElementsByTagName("email").item(0);
			emailNode.setTextContent(email);
			loginElement.appendChild(emailNode);

			// Attach it all back to document.
			rootElement.appendChild(loginElement);

			// Save it to file.
			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);

			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(
					new File(startPath + name + ".xml")), format);

			serializer.serialize(doc);
			/*
			 * File newfile = new File(startPath + name + ".xml"); if
			 * (newfile.createNewFile()) {
			 * System.out.println("Tiedosto luotiin");
			 * 
			 * newfile.cr }
			 */
			return userExists(name);
		} catch (Exception ex) {
			return false;
		}
	}

	boolean deleteUser(String username) {
		if (userExists(username)) {
			try {
				return Files.deleteIfExists(FileSystems.getDefault().getPath(
						"resources", username + ".xml"));
			} catch (IOException x) {
				// File permission problems are caught here.
				System.err.println(x);
			}
		}
		return false;
	}

	boolean userExists(String username) {
		File userfile = new File(startPath + username + ".xml");
		if (userfile.exists()) {
			try {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory
						.newDocumentBuilder();
				Document doc = docBuilder.parse(userfile);
				doc.getDocumentElement().normalize();

				NodeList loginlist = doc.getElementsByTagName("login");
				Node LoginNode = loginlist.item(0);
				if (LoginNode.getNodeType() == Node.ELEMENT_NODE) {
					Element LoginElement = (Element) LoginNode;

					// Checking to see if user element is the one suggested.
					if (getTagValue("username", LoginElement).equalsIgnoreCase(
							username)) {
						return true;
					}
				}
			} catch (Exception ex) {
			}
		}
		return false;
	}

	String[] getPassword(String username) {
		if (!userExists(username)) {
			return null;
		}
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(startPath + username + ".xml");
			doc.getDocumentElement().normalize();

			NodeList loginlist = doc.getElementsByTagName("login");
			Node LoginNode = loginlist.item(0);
			if (LoginNode.getNodeType() == Node.ELEMENT_NODE) {
				Element LoginElement = (Element) LoginNode;

				// Checking to see if user element is the one requested.
				if (getTagValue("username", LoginElement).equalsIgnoreCase(
						username)) {
					// Username matched, getting pword and salt.
					String[] pwordANDsalt = new String[2];

					// Getting the pword.
					pwordANDsalt[0] = getTagValue("pword", LoginElement);

					// Getting the salt.
					pwordANDsalt[1] = getTagValue("salt", LoginElement);

					return pwordANDsalt;
				}
			}
		} catch (Exception ex) {
		}
		return null;
	}

	String getEmail(String username) {
		if (!userExists(username)) {
			return null;
		}
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(startPath + username + ".xml");
			doc.getDocumentElement().normalize();

			NodeList loginlist = doc.getElementsByTagName("login");
			Node LoginNode = loginlist.item(0);
			if (LoginNode.getNodeType() == Node.ELEMENT_NODE) {
				Element LoginElement = (Element) LoginNode;

				// Checking to see if user element is the one requested.
				if (getTagValue("username", LoginElement).equalsIgnoreCase(
						username)) {

					// Getting the pword.
					return getTagValue("email", LoginElement);
				}
			}
		} catch (Exception ex) {
		}
		return null;
	}

	boolean changePassword(String username, String pword, String salt) {
		if (userExists(username)) {
			try {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory
						.newDocumentBuilder();
				Document doc = docBuilder.parse(new File(startPath + username
						+ ".xml"));

				Element rootElement = doc.getDocumentElement();

				Element loginElement = (Element) doc.getElementsByTagName(
						"login").item(0);

				// Changing the password.
				Node pwordNode = doc.getElementsByTagName("pword").item(0);
				pwordNode.setTextContent(pword);
				loginElement.appendChild(pwordNode);

				// Changing the salt.
				Node saltNode = doc.getElementsByTagName("salt").item(0);
				saltNode.setTextContent(salt);
				loginElement.appendChild(saltNode);

				// Attach it all back to document.
				rootElement.appendChild(loginElement);

				// Save it to file.
				OutputFormat format = new OutputFormat(doc);
				format.setIndenting(true);

				XMLSerializer serializer = new XMLSerializer(
						new FileOutputStream(new File(startPath + username
								+ ".xml")), format);

				serializer.serialize(doc);

				return true;
			} catch (Exception ex) {
			}
		}
		return false;
	}

	boolean changeEmail(String username, String newmail) {
		if (userExists(username)) {
			try {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory
						.newDocumentBuilder();
				Document doc = docBuilder.parse(new File(startPath + username
						+ ".xml"));

				Element rootElement = doc.getDocumentElement();

				Element loginElement = (Element) doc.getElementsByTagName(
						"login").item(0);

				// Changing the password.
				Node emailNode = doc.getElementsByTagName("email").item(0);
				emailNode.setTextContent(newmail);
				loginElement.appendChild(emailNode);

				// Attach it all back to document.
				rootElement.appendChild(loginElement);

				// Save it to file.
				OutputFormat format = new OutputFormat(doc);
				format.setIndenting(true);

				XMLSerializer serializer = new XMLSerializer(
						new FileOutputStream(new File(startPath + username
								+ ".xml")), format);

				serializer.serialize(doc);

				return true;
			} catch (Exception ex) {
			}
		}
		return false;
	}

	private static String getTagValue(String sTag, Element element) {
		NodeList nlList = element.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = nlList.item(0);

		return nValue.getNodeValue();
	}
}
