package spas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Contains static methods for helping with XML reading and writing.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 0.2
 * 
 */
public class XMLTools {
	/**
	 * Factory for creating document builders.
	 */
	private static DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
			.newInstance();

	/**
	 * Get tag value of given tag from element.
	 * 
	 * @param sTag
	 *            Tag's name.
	 * @param element
	 *            Element to get tag from.
	 * @return Given tag's value, or an empty String (not <code>null</code>), if
	 *         it doesn't exist..
	 */
	public static String getTagValue(String sTag, Element element) {
		// Find the node sTag.
		NodeList nList = element.getElementsByTagName(sTag);

		// Checking for nullpointers.
		if (nList.getLength() == 0) {
			return "";
		}

		// In case of a nullpointer.
		NodeList childList = nList.item(0).getChildNodes();

		// Checking for nullpointers.
		if (childList.getLength() == 0) {
			return "";
		}

		// Finally get to the right tag.
		Node nValue = childList.item(0);

		// Checking for nullpointers.
		if (nValue == null) {
			return "";
		}

		// Get tag's value.
		String value = nValue.getNodeValue();
		return value == null ? "" : value.trim();
	}

	/**
	 * Creates an element containing Text.
	 * 
	 * @param doc
	 *            Document to create in.
	 * @param name
	 *            Element's tagname.
	 * @param value
	 *            Element's value.
	 * @return Element with given value.
	 */
	public static Element createElement(Document doc, String name, String value) {
		Element element = doc.createElement(name);
		element.appendChild(doc.createTextNode(value));
		return element;
	}

	/**
	 * Change/set the Text of given node.
	 * 
	 * @param node
	 *            Node to be edited.
	 * @param text
	 *            Value to be inserted.
	 * @return Given node with given value.
	 */
	public static Node setTextValue(Node node, String text) {
		node.setTextContent(text);
		return node;
	}

	/**
	 * Change/set the Text of given node.
	 * 
	 * @param doc
	 *            The document in which this all is found.
	 * @param element
	 *            Element in which node is found.
	 * @param node
	 *            Node to be edited.
	 * @param text
	 *            Value to be inserted.
	 * @return Given node with given value.
	 */
	public static Node setTextValue(Document doc, Element element, String node,
			String text) {
		// Make sure child node is not null before editing.
		Node nnode = element.getElementsByTagName(node).item(0);
		if (nnode == null) {
			nnode = doc.createElement(node);
		}
		return setTextValue(nnode, text);
	}

	/**
	 * Change/set the Text of given node.
	 * 
	 * @param doc
	 *            Document in which node is found.
	 * @param node
	 *            Node's tagname.
	 * @param value
	 *            Value to be inserted.
	 * @return Given node with given value.
	 */
	public static Node setTextValue(Document doc, String node, String value) {
		return setTextValue(getNode(doc, node), value);
	}

	/**
	 * Get node from document.
	 * 
	 * @param doc
	 *            Document in which the node is found.
	 * @param name
	 *            Node's tagname.
	 * @return Node with given tagname (item 0).
	 */
	public static Node getNode(Document doc, String name) {
		Node node = doc.getElementsByTagName(name).item(0);

		// Make sure node actually exists.
		if (node == null) {
			node = doc.createElement(name);
		}
		return node;
	}

	/**
	 * Get element from document.
	 * 
	 * @see #getNode(Document, String)
	 * @param doc
	 *            Document the element is found in.
	 * @param name
	 *            Element's tagname.
	 * @return Element with given tagname (item 0).
	 */
	public static Element getElement(Document doc, String name) {
		return (Element) getNode(doc, name);
	}

	/**
	 * Returns iterable list of elements from document with given tagname.
	 * 
	 * @param doc
	 *            Document which contains elements.
	 * @param name
	 *            Elements tagname.
	 * @return List of elements with given tagname, or an empty list, if not
	 *         found.
	 */
	public static List<Element> getElements(Document doc, String name) {
		// Create list to be returned.
		List<Element> elements = new ArrayList<Element>();

		// Get the NodeList and loop through it to add them to list.
		NodeList nodes = doc.getElementsByTagName(name);
		for (int i = 0; i < nodes.getLength(); i++) {
			elements.add((Element) nodes.item(i));
		}

		// Return list.
		return elements;
	}

	/**
	 * Saves document to file.
	 * 
	 * @param doc
	 *            Document to be saved.
	 * @param file
	 *            File to be saved.
	 * @return <code>true</code>, if and only if, nothing went wrong.
	 */
	public static boolean saveFile(Document doc, File file) {
		try {
			// Save document to file.
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			Result output = new StreamResult(file);
			Source input = new DOMSource(doc);
			transformer.transform(input, output);
			return true;
		} catch (TransformerException e) {
			return false;
		}
	}

	/**
	 * Parse given File to document.
	 * 
	 * @param file
	 *            File to be parsed.
	 * @return Document from file, or <code>null</code>, if something went
	 *         wrong.
	 * @throws SAXException
	 *             In case of an error.
	 * @throws IOException
	 *             In case of an error.
	 * @throws ParserConfigurationException
	 *             In case of an error.
	 */
	public static Document parse(File file) throws SAXException, IOException,
			ParserConfigurationException {
		// Create parser.
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

		// Parse
		Document doc = docBuilder.parse(file);

		// Normalize document.
		doc.getDocumentElement().normalize();
		return doc;
	}

	/**
	 * @see #parse(File)
	 * 
	 * @param is
	 *            InputSource to be parsed.
	 * @return Document from source, or <code>null</code>, if something went
	 *         wrong.
	 * @throws SAXException
	 *             In case of an error.
	 * @throws IOException
	 *             In case of an error.
	 * @throws ParserConfigurationException
	 *             In case of an error.
	 */
	public static Document parse(InputSource is) throws SAXException,
			IOException, ParserConfigurationException {

		// Create parser.
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

		// Parse
		Document doc = docBuilder.parse(is);

		// Normalize document.
		doc.getDocumentElement().normalize();
		return doc;
	}
}