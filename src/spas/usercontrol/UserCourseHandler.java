package spas.usercontrol;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import spas.XMLTools;
import spas.nhandling.nelements.NCourse;
import spas.nhandling.nelements.NElementFactory;
import spas.nhandling.nelements.NElementType;

/**
 * Handles the course-saving and editing aspect of user database.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 0.2
 * 
 */
public class UserCourseHandler {
	/**
	 * The file containing user's file.
	 */
	File userfile;
	/**
	 * Status for active courses.
	 */
	public static final int ACTIVE = 1;
	/**
	 * Status for nonactive courses.
	 */
	public static final int NONACTIVE = 2;
	/**
	 * Status for completed courses.
	 */
	public static final int COMPLETED = 3;

	/**
	 * Constructor for a new UserCourseHandler. Path must be working.
	 * 
	 * @param path
	 *            Absolute path to user's file.
	 */
	public UserCourseHandler(String path) {
		userfile = new File(path);
	}

	/**
	 * Adds a course to user's active courses.
	 * 
	 * @param name
	 *            Course's name.
	 * @param id
	 *            Course's id.
	 * @return <code>true</code>, if and only if, adding was successful.
	 */
	public boolean addCourse(String name, String id) {
		// Make sure it isn't already added.
		if (containsCourse(id)) {
			return false;
		}
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get element to be examined and edited.
			Element coursesElement = XMLTools.getElement(doc, "courses");

			// Get the first course-element and examine it.
			Element firstcourse = XMLTools.getElement(doc, "course");
			if (!XMLTools.getTagValue("id", firstcourse).equals("")) {
				// This isn't the first course to be added, so creating it
				// from scratch.

				// Create the course element.
				Element courseElement = doc.createElement("course");

				// Create the name element.
				courseElement.appendChild(XMLTools.createElement(doc, "name",
						name));

				// Create the id element.
				courseElement
						.appendChild(XMLTools.createElement(doc, "id", id));

				// Create the state element.
				courseElement.appendChild(XMLTools.createElement(doc, "state",
						ACTIVE + ""));

				// Create the group element.
				courseElement.appendChild(XMLTools.createElement(doc, "group",
						" "));

				// Attach it all back to courses-element.
				coursesElement.appendChild(courseElement);

				// Attach it all back to document.
				doc.getDocumentElement().appendChild(coursesElement);

				// Save it to file.
				return XMLTools.saveFile(doc, userfile);

			}
			// The first course to add, so just going to edit existing one.

			// Setting the name of course.
			firstcourse.appendChild(XMLTools.setTextValue(doc, "name", name));

			// Setting the id of course.
			firstcourse.appendChild(XMLTools.setTextValue(doc, "id", id));

			// Attach it all back to courses-element.
			coursesElement.appendChild(firstcourse);

			// Attach it all back to document.
			doc.getDocumentElement().appendChild(coursesElement);

			// Save it to file.
			if (XMLTools.saveFile(doc, userfile)) {
				if (changeState(id, ACTIVE)) {
					return changeGroup(id, "");
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
		}
		return false;
	}

	/**
	 * Get the status of a course. If there was no error it will return one of
	 * these: {@link #ACTIVE}, {@link #NONACTIVE} or {@link #COMPLETED}.
	 * 
	 * @param id
	 *            The course's id.
	 * @return The course's status, or <code>0</code>, if somethin went wrong.
	 */
	public int getState(String id) {
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get list of courses and loop through them.
			for (Element e : XMLTools.getElements(doc, "course")) {

				// Check to see if current element is the correct one.
				if (XMLTools.getTagValue("id", e).equals(id)) {
					// Return current course's status.
					try {
						return Integer.parseInt(XMLTools
								.getTagValue("state", e));
					} catch (NumberFormatException ex) {
						// In case there is a problem with getting status.
						return 0;
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should not normally happen.
		}
		return 0;
	}

	/**
	 * Change the status of a course. Use {@link #ACTIVE}, {@link #NONACTIVE} or
	 * {@link #COMPLETED} to change the status.
	 * 
	 * @param id
	 *            The course's id.
	 * @param state
	 *            The new status for course.
	 * @return <code>true</code>, if and only if, nothing went wrong.
	 */
	public boolean changeState(String id, int state) {
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get a list of courses and loop through them.
			for (Element e : XMLTools.getElements(doc, "course")) {

				// Get the current element and check if it's the right one.
				if (XMLTools.getTagValue("id", e).equals(id)) {

					// Change the status.
					e.appendChild(XMLTools.setTextValue(e, "state", state + ""));

					// Attach it back to document.
					Element courses = XMLTools.getElement(doc, "courses");
					courses.appendChild(e);
					doc.getDocumentElement().appendChild(courses);

					// Save it to file.
					return XMLTools.saveFile(doc, userfile);
				}
			} // end of for-loop.
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
		}
		return false;
	}

	/**
	 * Get the group saved for user on a course.
	 * 
	 * @param id
	 *            The course's id.
	 * @return Course's group, or an empty String (not <code>null</code>), if it
	 *         doesn't exist.
	 */
	public String getGroup(String id) {
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get the element to be examined.
			Element coursesElement = XMLTools.getElement(doc, "courses");

			// Get a list of courses and loop through them.
			NodeList courseList = coursesElement.getElementsByTagName("course");
			for (int i = 0; i < courseList.getLength(); i++) {

				// Check to see if current course is the correct one.
				Element courseElement = (Element) courseList.item(i);
				if (XMLTools.getTagValue("id", courseElement).equals(id)) {

					// Return it's value.
					return XMLTools.getTagValue("group", courseElement);
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
		}
		return "";
	}

	/**
	 * Changes user's saved group for a given course.
	 * 
	 * @param id
	 *            The course's id.
	 * @param group
	 *            The new group.
	 * @return <code>true</code>, if and only if, nothing went wrong.
	 */
	public boolean changeGroup(String id, String group) {
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get a list of courses and loop through it.
			for (Element e : XMLTools.getElements(doc, "course")) {

				// Check if current element is the correct one.
				if (XMLTools.getTagValue("id", e).equals(id)) {

					// Change the group
					e.appendChild(XMLTools.setTextValue(e, "group", group));

					// Attach it all back to document.
					Element courses = XMLTools.getElement(doc, "courses");
					courses.appendChild(e);
					doc.getDocumentElement().appendChild(courses);

					// Save it to file.
					return XMLTools.saveFile(doc, userfile);
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
		}
		return false;
	}

	/**
	 * Removes a course from user's file.
	 * 
	 * @param id
	 *            The course's id.
	 * @return <code>true</code>, if and only if, nothing went wrong.
	 */
	public boolean removeCourse(String id) {
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get the courses-element to be examined.
			Element coursesElement = XMLTools.getElement(doc, "courses");

			// Get the list of courses.
			List<Element> courseList = XMLTools.getElements(doc, "course");
			if (courseList.size() == 1) {
				/*
				 * This is the last course to be deleted, so just going to empty
				 * it's model.
				 */

				// Get the course-element to be edited.
				Element courseElement = courseList.get(0);

				// Set name to empty.
				courseElement.appendChild(XMLTools
						.setTextValue(doc, "name", ""));

				// Set id to empty.
				courseElement.appendChild(XMLTools.setTextValue(doc, "id", ""));

				// Set group to empty.
				courseElement.appendChild(XMLTools.setTextValue(doc, "group",
						""));

				// Set state to empty.
				courseElement.appendChild(XMLTools.setTextValue(doc, "state",
						""));

				// Attach course to courses-element.
				coursesElement.appendChild(courseElement);

			} else {
				/*
				 * This wasn't the last course to be removed, so going to loop
				 * through them to find the correct one.
				 */
				loop: for (Element e : courseList) {

					// Check if current element is the correct one.
					if (XMLTools.getTagValue("id", e).equals(id)) {

						// Remove the course and end loop.
						coursesElement.removeChild(e);
						break loop;
					}
				}
			}

			// Attach it all back to document.
			doc.getDocumentElement().appendChild(coursesElement);

			// Save it to file.
			return XMLTools.saveFile(doc, userfile);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These should never happen.
		}
		return false;
	}

	/**
	 * Get the courses user has saved.
	 * 
	 * @return A list of chosen courses, or an empty list, if something went
	 *         wrong.
	 */
	public List<NCourse> getCourses() {
		List<NCourse> courses = new ArrayList<NCourse>();
		try {
			// Parse file to document.
			Document doc = XMLTools.parse(userfile);

			// Get a list of courses and loop through it.
			for (Element e : XMLTools.getElements(doc, "course")) {

				// Create the course-object and get the current element.
				NCourse c = (NCourse) NElementFactory
						.createNElement(NElementType.COURSE);

				// Set the course's information.
				c.setName(XMLTools.getTagValue("name", e));
				c.setId(XMLTools.getTagValue("id", e));
				String state = XMLTools.getTagValue("state", e);
				c.setState(Integer.parseInt(state.equals("") ? "0" : state));
				c.setGroup(XMLTools.getTagValue("group", e));

				// Add it to the list to be returned.
				courses.add(c);
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// These shouldn't normally happen.
		}
		return courses;
	}

	/**
	 * Checks to see, if given course if saved to file.
	 * 
	 * @param id
	 *            Course's id.
	 * @return <code>true</code>, if it is saved.
	 */
	public boolean containsCourse(String id) {

		// Get the courses on file and loop through them.
		List<NCourse> courses = getCourses();
		for (NCourse c : courses) {

			// Check if current course is the right one.
			if (c.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
}