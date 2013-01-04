package spas.nreading;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import spas.XMLTools;
import spas.nelements.NCourse;
import spas.nelements.NElement;
import spas.nelements.NElementFactory;
import spas.nelements.NElementType;
import spas.nelements.NEvent;

/**
 * Parses XML into NElement objects. Needs InputSource to actually do something.
 * Can only parse XML-files as specified in <a href=
 * "https://wiki.aalto.fi/download/attachments/71895449/NoppaAPI_eng.pdf?version=2&modificationDate=1352361073000"
 * target="_blank">the Noppa API</a>.
 * 
 * @see NReader
 * @author Lauri Lavanti
 * @version 1.0
 * @since 0.1
 */
public class XMLParser {

	private XMLParser() {
		throw new InstantiationError("Creation of this object is not allowed.");
	}

	/**
	 * Parses XML-file into NElement-objects. Gathers all the information
	 * relevant to NElement-type into NElement, NCourse or NEvent forms.
	 * 
	 * @param is
	 *            InputSource for the XML-file to parse.
	 * @return Objectified version of the XML-file, <code>List</code> with class
	 *         that extends NElement.
	 */
	public static List<? extends NElement> parseFile(InputSource is) {

		try {
			// Turn XML-file into a Document.
			Document doc = XMLTools.parse(is);

			// Get root name.
			String rootNode = doc.getDocumentElement().getNodeName();

			// Send the Document to be handled in the correct method.
			if (rootNode.equalsIgnoreCase("ORGANIZATIONS")) {
				return parseOrganizations(doc);
			} else if (rootNode.equalsIgnoreCase("DEPARTMENTS")) {
				return parseDepartments(doc);
			} else if (rootNode.equalsIgnoreCase("COURSES")) {
				return parseCourses(doc);
			} else if (rootNode.equalsIgnoreCase("OVERVIEW")) {
				return parseCourse(doc);
			} else if (rootNode.equalsIgnoreCase("LECTURES")) {
				return parseLectures(doc);
			} else if (rootNode.equalsIgnoreCase("EXERCISES")) {
				return parseExercises(doc);
			} else if (rootNode.equalsIgnoreCase("ASSIGNMENTS")) {
				return parseAssignments(doc);
			} else if (rootNode.equalsIgnoreCase("EVENTS")) {
				return parseEvents(doc);
			}
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// These should never happen.
		}
		// In case of an error, return an empty list.
		return new ArrayList<NElement>();
	}

	/**
	 * Parses organizations correctly from Document.
	 * 
	 * @param doc
	 *            Document to be parsed.
	 * @return List of organizations contained in Document.
	 */
	private static List<NElement> parseOrganizations(Document doc) {
		List<NElement> objs = new ArrayList<NElement>();

		// Get the NodeList of organizations and loop through it.
		NodeList listOfOrganizations = doc.getElementsByTagName("organization");
		for (int i = 0; i < listOfOrganizations.getLength(); i++) {

			// Create the NElement object and get Element-form presentation of
			// organization.
			NElement organization = NElementFactory
					.createNElement(NElementType.ORGANIZATION);
			Element OrganizationElement = (Element) listOfOrganizations.item(i);

			// Getting and setting the id for the organization.
			organization.setId(XMLTools.getTagValue("org_id",
					OrganizationElement));

			// Getting and setting the name for the organization.
			organization.setName(XMLTools.getTagValue("name_fi",
					OrganizationElement));

			// Add it to the list.
			objs.add(organization);
		} // end of for-loop.
		return objs;
	}

	/**
	 * Parses departments correctly from Document.
	 * 
	 * @param doc
	 *            Document to be parsed.
	 * @return List of departments contained in the Document.
	 */
	private static List<NElement> parseDepartments(Document doc) {
		List<NElement> objs = new ArrayList<NElement>();

		// Get the NodeList of departments and loop through it.
		NodeList listOfDepartments = doc.getElementsByTagName("department");
		for (int i = 0; i < listOfDepartments.getLength(); i++) {

			// Create the NElement object and get Element-form presentation of
			// department.
			NElement department = NElementFactory
					.createNElement(NElementType.DEPARTMENT);
			Element DepartmentElement = (Element) listOfDepartments.item(i);

			// Getting and setting the id for the department.
			department
					.setId(XMLTools.getTagValue("dept_id", DepartmentElement));

			// Getting and setting the organization id for the department.
			department.setOrgId(XMLTools.getTagValue("org_id",
					DepartmentElement));

			// Getting and setting the name for the department.
			department.setName(XMLTools.getTagValue("name_fi",
					DepartmentElement));

			// Add it to the list.
			objs.add(department);
		} // end of for-loop.
		return objs;
	}

	/**
	 * Parses courses correctly from Document.
	 * 
	 * @param doc
	 *            Document to be parsed.
	 * @return List of courses contained in the Document.
	 */
	private static List<NCourse> parseCourses(Document doc) {
		List<NCourse> objs = new ArrayList<NCourse>();

		// Get the NodeList of courses and loop through it.
		NodeList listOfCourses = doc.getElementsByTagName("course");
		for (int i = 0; i < listOfCourses.getLength(); i++) {

			// Create the NCourse object and Element-form presentation of
			// course.
			NCourse course = (NCourse) NElementFactory
					.createNElement(NElementType.COURSE);
			Element CourseElement = (Element) listOfCourses.item(i);

			// Getting and setting the id for the course.
			course.setId(XMLTools.getTagValue("course_id", CourseElement));

			// Getting and setting the name for the course.
			course.setName(XMLTools.getTagValue("name", CourseElement));

			// Add it to the list.
			objs.add(course);
		} // end of for-loop.
		return objs;
	}

	/**
	 * Parses a course correctly from the Document. Gets more information than
	 * {@link XMLParser#parseCourses(Document) parseCourses(doc)}. The reason
	 * why this method returns a list as well even though it will always contain
	 * one Object at most, is to enable calling of only one method in this
	 * class.
	 * 
	 * @param doc
	 *            Document to be parsed.
	 * @return A list containing the one course parse correctly from Document.
	 */
	private static List<NCourse> parseCourse(Document doc) {
		List<NCourse> objs = new ArrayList<NCourse>();

		// Create the NCourse object and get the Element-form presentation of
		// the course.
		NCourse course = (NCourse) NElementFactory
				.createNElement(NElementType.COURSE);
		Element OverviewElement = doc.getDocumentElement();

		// Getting and setting the id for the course.
		course.setId(XMLTools.getTagValue("course_id", OverviewElement));

		// Getting and setting the credits for the course.
		course.setCredits(XMLTools.getTagValue("credits", OverviewElement));

		// Getting and setting the periods for the course.
		course.setPeriod(XMLTools.getTagValue("teaching_period",
				OverviewElement));

		// Getting and setting the content for the course.
		course.setContent(XMLTools.getTagValue("content", OverviewElement));

		// Add it to the list.
		objs.add(course);

		return objs;
	}

	/**
	 * Parses lectures correctly from Document.
	 * 
	 * @param doc
	 *            Document to be parsed.
	 * @return List of lectures contained in the Document.
	 */
	private static List<NEvent> parseLectures(Document doc) {
		List<NEvent> objs = new ArrayList<NEvent>();

		// Create the Calendar-basis and add TimeZone to them. Also create
		// appropriate String-Date formatter.
		Calendar startDate = new GregorianCalendar();
		startDate.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
		Calendar endDate = (Calendar) startDate.clone();
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm");

		// Get the NodeList of lectures and loop through it.
		NodeList listOfLectures = doc.getElementsByTagName("lecture");
		for (int i = 0; i < listOfLectures.getLength(); i++) {

			// Create the NEvent object and get the Element-form presentation of
			// the lecture.
			NEvent lecture = (NEvent) NElementFactory
					.createNElement(NElementType.LECTURE);
			Element LectureElement = (Element) listOfLectures.item(i);

			// Getting the date for the Lecture.
			String date = XMLTools.getTagValue("date", LectureElement);

			// Getting the starting time for the Lecture.
			String starttime = XMLTools.getTagValue("start_time",
					LectureElement);

			// Getting the ending time for the Lecture.
			String endtime = XMLTools.getTagValue("end_time", LectureElement);

			// Setting the start and end date for the lecture.
			try {
				startDate.setTime(formatter.parse(date + " " + starttime));
				endDate.setTime(formatter.parse(date + " " + endtime));
				lecture.setStartDate(startDate);
				lecture.setEndDate(endDate);
			} catch (ParseException ex) {
				// This should not normally happen.
			}

			// Getting and setting the location for the lecture.
			lecture.setLocation(XMLTools
					.getTagValue("location", LectureElement));

			// Getting and setting the description for the lecture.
			lecture.setDescription(XMLTools.getTagValue("content",
					LectureElement));

			objs.add(lecture);
		} // end of for-loop.
		return objs;
	}

	/**
	 * Parses exercises from Document.
	 * 
	 * @param doc
	 *            Document to be parsed.
	 * @return List of exercises contained in the Document.
	 */
	private static List<NEvent> parseExercises(Document doc) {
		List<NEvent> objs = new ArrayList<NEvent>();

		// Create the Calendar-basis and add TimeZone to them. Also create
		// appropriate String-Date formatter.
		Calendar startDate = new GregorianCalendar();
		startDate.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
		Calendar endDate = (Calendar) startDate.clone();
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm");

		// Get the NodeList of exercises and loop through it.
		NodeList listOfExercises = doc.getElementsByTagName("exercise");
		for (int i = 0; i < listOfExercises.getLength(); i++) {

			// Create the NEvent object and get the Element-form presentation of
			// the lecture.
			NEvent exercise = (NEvent) NElementFactory
					.createNElement(NElementType.EXERCISE);
			Element ExerciseElement = (Element) listOfExercises.item(i);

			// Getting and setting the group for the exercise.
			exercise.setGroup(XMLTools.getTagValue("group", ExerciseElement));

			// Getting the starting time for the exercise.
			String startTime = XMLTools.getTagValue("start_time",
					ExerciseElement);

			// Getting the ending time for the exercise.
			String endTime = XMLTools.getTagValue("end_time", ExerciseElement);

			// Getting and setting the location for the exercise.
			exercise.setLocation(XMLTools.getTagValue("location",
					ExerciseElement));

			// Getting the starting date for the exercise.
			String startdate = XMLTools.getTagValue("start_date",
					ExerciseElement);

			// Getting the ending date for the exercise.
			String enddate = XMLTools.getTagValue("end_date", ExerciseElement);

			// Setting the start and end date for the exercise.
			try {
				startDate.setTime(formatter.parse(startdate + " " + startTime));
				endDate.setTime(formatter.parse(enddate + " " + endTime));
				exercise.setStartDate(startDate);
				exercise.setEndDate(endDate);
			} catch (ParseException ex) {
				// This should not normally happen.
			}

			// Add it to the list.
			objs.add(exercise);
		} // end of for-loop.
		return objs;
	}

	/**
	 * Parses assignments from Document.
	 * 
	 * @param doc
	 *            Document to be parsed.
	 * @return List of assignments contained in Document.
	 */
	private static List<NEvent> parseAssignments(Document doc) {
		List<NEvent> objs = new ArrayList<NEvent>();

		// Create the Calendar-basis and set TimeZone to it. Also create
		// appropriate String-Date formatter.
		Calendar dueDate = new GregorianCalendar();
		dueDate.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm");

		// Get the NodeList of assignments and loop through it.
		NodeList listOfAssignments = doc.getElementsByTagName("assignment");
		for (int i = 0; i < listOfAssignments.getLength(); i++) {

			// Create the NEvent object and get the Element-form presentation of
			// the assignment.
			NEvent assignment = (NEvent) NElementFactory
					.createNElement(NElementType.ASSIGNMENT);
			Element AssignmentElement = (Element) listOfAssignments.item(i);

			// Getting and setting the deadline for the assignment.
			try {
				dueDate.setTime(formatter.parse(XMLTools.getTagValue(
						"deadline", AssignmentElement)));
				assignment.setEndDate(dueDate);
			} catch (ParseException ex) {
				// This should not normally happen.
			}

			// Getting and setting the description for the assignment.
			assignment.setDescription(XMLTools.getTagValue("title",
					AssignmentElement));

			// Add it to the list.
			objs.add(assignment);
		} // end of for-loop.
		return objs;
	}

	/**
	 * Parses events from Document.
	 * 
	 * @param doc
	 *            Document to be parsed.
	 * @return List of events contained in the Document.
	 */
	private static List<NEvent> parseEvents(Document doc) {
		List<NEvent> objs = new ArrayList<NEvent>();

		// Create the Calendar-basis and add TimeZone to them. Also create the
		// appropriate String-Date formatter.
		Calendar startDate = new GregorianCalendar();
		startDate.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
		Calendar endDate = (Calendar) startDate.clone();
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm");

		// Get the NodeList of events and loop through them.
		NodeList listOfEvents = doc.getElementsByTagName("event");
		for (int i = 0; i < listOfEvents.getLength(); i++) {

			// Create the NEvent object and get the Element-form presentation of
			// the event.
			NEvent event = (NEvent) NElementFactory
					.createNElement(NElementType.OTHER);
			Element EventElement = (Element) listOfEvents.item(i);

			// Getting the type for the event.
			String type = XMLTools.getTagValue("type", EventElement);

			// Getting the title for the event.
			String title = XMLTools.getTagValue("title", EventElement);

			// Setting the description for the event.
			event.setDescription(title + ", " + type);

			// Getting and setting the location for the event.
			event.setLocation(XMLTools.getTagValue("location", EventElement));

			// Getting the starting time for the event.
			String starttime = XMLTools.getTagValue("start_time", EventElement);

			// Getting the ending time for the event.
			String endtime = XMLTools.getTagValue("end_time", EventElement);

			// Getting the starting date for the event.
			String startdate = XMLTools.getTagValue("start_date", EventElement);

			// Getting the ending date for the event.
			String enddate = XMLTools.getTagValue("end_date", EventElement);

			// Setting the start and end for the event.
			try {
				startDate.setTime(formatter.parse(startdate + " " + starttime));
				endDate.setTime(formatter.parse(enddate + " " + endtime));
				event.setStartDate(startDate);
				event.setEndDate(endDate);
			} catch (ParseException ex) {
				// This should not normally happen.
			}

			// Add it to the list.
			objs.add(event);
		} // end of for-loop.
		return objs;
	}
}