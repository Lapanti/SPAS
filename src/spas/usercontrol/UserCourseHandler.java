package spas.usercontrol;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import spas.XMLTools;
import spas.nelements.Course;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class UserCourseHandler {
	File filepath;

	public UserCourseHandler(String username) {
		filepath = new File("resources/" + username + ".xml");
	}

	boolean addCourse(String name, String id) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			doc.getDocumentElement().normalize();

			NodeList courseslist = doc.getElementsByTagName("courses");
			Node coursesNode = courseslist.item(0);
			if (coursesNode.getNodeType() == Node.ELEMENT_NODE) {
				Element coursesElement = (Element) coursesNode;

				Element firstcourse = (Element) doc.getElementsByTagName(
						"course").item(0);
				String firstcourseidvalue = XMLTools.getTagValue("id",
						firstcourse);
				if (firstcourseidvalue != null) {
					// This isn't the first course to be added, so creating it
					// from scratch.

					// Create the course element.
					Element courseElement = doc.createElement("course");

					// Create the name element.
					Text cname = doc.createTextNode(name);
					Element nameE = doc.createElement("name");
					nameE.appendChild(cname);
					courseElement.appendChild(nameE);

					// Create the id element.
					Text cid = doc.createTextNode(id);
					Element idE = doc.createElement("id");
					idE.appendChild(cid);
					courseElement.appendChild(idE);

					// Attach it all back to courses-element.
					coursesElement.appendChild(courseElement);

				} else {
					// This is the first course to be added, so just going to
					// edit existing course-model.
					Element courseElement = (Element) coursesElement
							.getElementsByTagName("course").item(0);

					// Setting the name of course.
					Node nameNode = courseElement.getElementsByTagName("name")
							.item(0);
					nameNode.setTextContent(name);
					courseElement.appendChild(nameNode);

					// Setting the id of course.
					Node idNode = courseElement.getElementsByTagName("id")
							.item(0);
					idNode.setTextContent(id);
					courseElement.appendChild(idNode);

					// Attach it all back to courses-element.
					coursesElement.appendChild(courseElement);
				}

				// Attach it all back to document.
				doc.getDocumentElement().appendChild(coursesElement);

				// Save it to file.
				OutputFormat format = new OutputFormat(doc);
				format.setIndenting(true);

				XMLSerializer serializer = new XMLSerializer(
						new FileOutputStream(filepath), format);

				serializer.serialize(doc);
				return true;
			}

		} catch (Exception ex) {
		}
		return false;
	}

	boolean removeCourse(String name, String id) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			doc.getDocumentElement().normalize();

			NodeList courseslist = doc.getElementsByTagName("courses");
			Node coursesNode = courseslist.item(0);
			if (coursesNode.getNodeType() == Node.ELEMENT_NODE) {
				Element coursesElement = (Element) coursesNode;

				NodeList courseList = coursesElement
						.getElementsByTagName("course");
				for (int i = 0; i < courseList.getLength(); i++) {
					Element courseElement = (Element) courseList.item(i);
					if (XMLTools.getTagValue("name", courseElement)
							.equals(name)
							&& XMLTools.getTagValue("id", courseElement)
									.equals(id)) {
						coursesElement.removeChild(courseElement);
						break;
					}
				}

				// Attach it all back to document.
				doc.getDocumentElement().appendChild(coursesElement);

				// Save it to file.
				OutputFormat format = new OutputFormat(doc);
				format.setIndenting(true);

				XMLSerializer serializer = new XMLSerializer(
						new FileOutputStream(filepath), format);

				serializer.serialize(doc);
				return true;
			}

		} catch (Exception ex) {
		}
		return false;
	}

	List<Course> getCourses() {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			doc.getDocumentElement().normalize();

			NodeList courseslist = doc.getElementsByTagName("courses");
			Node coursesNode = courseslist.item(0);
			if (coursesNode.getNodeType() == Node.ELEMENT_NODE) {
				Element coursesElement = (Element) coursesNode;

				NodeList courseList = coursesElement
						.getElementsByTagName("course");
				List<Course> courses = new ArrayList<Course>();
				for (int i = 0; i < courseList.getLength(); i++) {
					Element courseElement = (Element) courseList.item(i);
					Course c = new Course();
					
					c.setName(XMLTools.getTagValue("name", courseElement));
					c.setId(XMLTools.getTagValue("id", courseElement));
					
					courses.add(c);
				}
				
				return courses;
			}

		} catch (Exception ex) {
		}
		return null;
	}
}
