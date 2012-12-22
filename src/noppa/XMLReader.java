package noppa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nElements.Assignment;
import nElements.Course;
import nElements.Exercise;
import nElements.Lecture;
import nElements.NoppaElement;
import nElements.NoppaElementFactory;
import nElements.NoppaElementType;
import nElements.OtherEvent;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The class for reading XML-files gotten from Noppa API.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 */
public class XMLReader {
	/**
	 * List for containing the NoppaElements during reading of XML-file.
	 * 
	 */
	private List<NoppaElement> objs = new ArrayList<NoppaElement>();
	private NoppaElementFactory nef = new NoppaElementFactory();

	public List<NoppaElement> parseNoppafile(InputSource is) {

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);

			// Normalize text representation.
			doc.getDocumentElement().normalize();
			String rootNode = doc.getDocumentElement().getNodeName();
			if (rootNode.equalsIgnoreCase("ORGANIZATIONS")) {
				parseOrganizations(doc);
			} else if (rootNode.equalsIgnoreCase("DEPARTMENTS")) {
				parseDepartments(doc);
			} else if (rootNode.equalsIgnoreCase("COURSES")) {
				parseCourses(doc);
			} else if (rootNode.equalsIgnoreCase("OVERVIEW")) {
				parseCourse(doc);
			} else if (rootNode.equalsIgnoreCase("LECTURES")) {
				parseLectures(doc);
			} else if (rootNode.equalsIgnoreCase("EXERCISES")) {
				parseExercises(doc);
			} else if (rootNode.equalsIgnoreCase("ASSIGNMENTS")) {
				parseAssignments(doc);
			} else if (rootNode.equalsIgnoreCase("EVENTS")) {
				parseEvents(doc);
			}
		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());

		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		List<NoppaElement> objsCopy = objs;
		objs = null;
		return objsCopy;
	}

	private void parseOrganizations(Document doc) {
		NodeList listOfOrganizations = doc.getElementsByTagName("organization");

		for (int i = 0; i < listOfOrganizations.getLength(); i++) {

			Node OrganizationNode = listOfOrganizations.item(i);
			if (OrganizationNode.getNodeType() == Node.ELEMENT_NODE) {

				NoppaElement nElement = nef
						.createNoppaElement(NoppaElementType.ORGANIZATION);
				Element OrganizationElement = (Element) OrganizationNode;

				// Getting the id for the Organization.
				NodeList idList = OrganizationElement
						.getElementsByTagName("org_id");
				Element IdElement = (Element) idList.item(0);

				NodeList textidList = IdElement.getChildNodes();
				nElement.setId(textidList.item(0).getNodeValue().trim());

				// Getting the name for the Organization.
				NodeList nameList = OrganizationElement
						.getElementsByTagName("name_fi");
				Element NameElement = (Element) nameList.item(0);

				NodeList textNameList = NameElement.getChildNodes();
				nElement.setName(textNameList.item(0).getNodeValue().trim());

				// TODO for some reason it keeps getting a null pointer here
				// every now and then.
				objs.add(nElement);
			}
		}
	}

	private void parseDepartments(Document doc) {
		NodeList listOfDepartments = doc.getElementsByTagName("department");

		for (int i = 0; i < listOfDepartments.getLength(); i++) {

			Node DepartmentNode = listOfDepartments.item(i);
			if (DepartmentNode.getNodeType() == Node.ELEMENT_NODE) {

				NoppaElement nElement = nef
						.createNoppaElement(NoppaElementType.DEPARTMENT);
				Element DepartmentElement = (Element) DepartmentNode;

				// Getting the id for the Department.
				NodeList idList = DepartmentElement
						.getElementsByTagName("dept_id");
				Element IdElement = (Element) idList.item(0);

				NodeList textidList = IdElement.getChildNodes();
				nElement.setId(textidList.item(0).getNodeValue().trim());

				// Getting the name for the Department.
				NodeList nameList = DepartmentElement
						.getElementsByTagName("name_fi");
				Element NameElement = (Element) nameList.item(0);

				NodeList textNameList = NameElement.getChildNodes();
				nElement.setName(textNameList.item(0).getNodeValue().trim());

				// TODO for some reason it keeps getting a null pointer here
				// every now and then.
				objs.add(nElement);
			}
		}
	}

	private void parseCourses(Document doc) {
		NodeList listOfCourses = doc.getElementsByTagName("course");

		for (int i = 0; i < listOfCourses.getLength(); i++) {

			Node CourseNode = listOfCourses.item(i);
			if (CourseNode.getNodeType() == Node.ELEMENT_NODE) {

				Course course = (Course) nef
						.createNoppaElement(NoppaElementType.COURSE);
				Element CourseElement = (Element) CourseNode;

				// Getting the id for the Course.
				NodeList idList = CourseElement
						.getElementsByTagName("course_id");
				Element IdElement = (Element) idList.item(0);

				NodeList textidList = IdElement.getChildNodes();
				course.setId(textidList.item(0).getNodeValue().trim());

				// Getting the name for the Course.
				NodeList nameList = CourseElement.getElementsByTagName("name");
				Element NameElement = (Element) nameList.item(0);

				NodeList textNameList = NameElement.getChildNodes();
				course.setName(textNameList.item(0).getNodeValue().trim());

				// TODO for some reason it keeps getting a null pointer here
				// every now and then.
				objs.add(course);
			}
		}
	}

	private void parseCourse(Document doc) {

		Node OverviewNode = doc.getDocumentElement();
		if (OverviewNode.getNodeType() == Node.ELEMENT_NODE) {

			Course course = (Course) nef
					.createNoppaElement(NoppaElementType.COURSE);
			Element OverviewElement = (Element) OverviewNode;

			// Getting the id for the Course.
			NodeList idList = OverviewElement.getElementsByTagName("course_id");
			Element IdElement = (Element) idList.item(0);

			NodeList textidList = IdElement.getChildNodes();
			course.setId(textidList.item(0).getNodeValue().trim());

			// Getting the credits for the Course.
			NodeList creditList = OverviewElement
					.getElementsByTagName("credits");
			Element CreditElement = (Element) creditList.item(0);

			NodeList textCreditList = CreditElement.getChildNodes();
			course.setCredits(textCreditList.item(0).getNodeValue().trim());

			// Getting the periods for the Course.
			NodeList periodList = OverviewElement
					.getElementsByTagName("teaching_period");
			Element PeriodElement = (Element) periodList.item(0);

			NodeList textPeriodList = PeriodElement.getChildNodes();
			course.setPeriod(textPeriodList.item(0).getNodeValue().trim());

			// Getting the content for the Course.
			NodeList contentList = OverviewElement
					.getElementsByTagName("content");
			Element ContentElement = (Element) contentList.item(0);

			NodeList textContentList = ContentElement.getChildNodes();
			course.setContent(textContentList.item(0).getNodeValue().trim());

			// TODO for some reason it keeps getting a null pointer here
			// every now and then.
			objs.add(course);
		}
	}

	private void parseLectures(Document doc) {
		NodeList listOfLectures = doc.getElementsByTagName("lecture");
		DateTimeFormatter dateformatter = DateTimeFormat
				.forPattern("yyyy-MM-dd");
		DateTimeFormatter timeformatter = DateTimeFormat.forPattern("HH:mm");

		for (int i = 0; i < listOfLectures.getLength(); i++) {

			Node LectureNode = listOfLectures.item(i);
			if (LectureNode.getNodeType() == Node.ELEMENT_NODE) {

				Lecture lecture = (Lecture) nef
						.createNoppaElement(NoppaElementType.LECTURE);
				Element LectureElement = (Element) LectureNode;

				// Getting the id for the Lecture.
				NodeList idList = LectureElement
						.getElementsByTagName("lecture_id");
				Element IdElement = (Element) idList.item(0);

				NodeList textidList = IdElement.getChildNodes();
				lecture.setId(textidList.item(0).getNodeValue().trim());

				// Getting the date for the Lecture.
				NodeList dateList = LectureElement.getElementsByTagName("date");
				Element DateElement = (Element) dateList.item(0);

				NodeList textDateList = DateElement.getChildNodes();
				lecture.setDate(dateformatter.parseDateTime(textDateList
						.item(0).getNodeValue().trim()));

				// Getting the starting time for the Lecture.
				NodeList startTimeList = LectureElement
						.getElementsByTagName("start_time");
				Element startTimeElement = (Element) startTimeList.item(0);

				NodeList textStartTimeList = startTimeElement.getChildNodes();
				lecture.setStart(timeformatter.parseDateTime(textStartTimeList
						.item(0).getNodeValue().trim()));

				// Getting the ending time for the Lecture.
				NodeList endTimeList = LectureElement
						.getElementsByTagName("end_time");
				Element endTimeElement = (Element) endTimeList.item(0);

				NodeList textEndTimeList = endTimeElement.getChildNodes();
				lecture.setEnd(timeformatter.parseDateTime(textEndTimeList
						.item(0).getNodeValue().trim()));

				// Getting the location for the Lecture.
				NodeList locationList = LectureElement
						.getElementsByTagName("location");
				Element locationElement = (Element) locationList.item(0);

				NodeList textLocationList = locationElement.getChildNodes();
				lecture.setLocation(textLocationList.item(0).getNodeValue()
						.trim());

				// Getting the title for the Lecture.
				NodeList titleList = LectureElement
						.getElementsByTagName("title");
				Element titleElement = (Element) titleList.item(0);

				NodeList textTitleList = titleElement.getChildNodes();
				lecture.setTitle(textTitleList.item(0).getNodeValue().trim());

				// TODO for some reason it keeps getting a null pointer here
				// every now and then.
				objs.add(lecture);
			}
		}
	}

	private void parseExercises(Document doc) {
		NodeList listOfExercises = doc.getElementsByTagName("exercise");
		DateTimeFormatter dateformatter = DateTimeFormat
				.forPattern("yyyy-MM-dd");
		DateTimeFormatter timeformatter = DateTimeFormat.forPattern("HH:mm");

		for (int i = 0; i < listOfExercises.getLength(); i++) {

			Node ExerciseNode = listOfExercises.item(i);
			if (ExerciseNode.getNodeType() == Node.ELEMENT_NODE) {

				Exercise exercise = (Exercise) nef
						.createNoppaElement(NoppaElementType.EXERCISE);
				Element ExerciseElement = (Element) ExerciseNode;

				// Getting the id for the Exercise.
				NodeList idList = ExerciseElement
						.getElementsByTagName("course_id");
				Element IdElement = (Element) idList.item(0);

				NodeList textidList = IdElement.getChildNodes();
				exercise.setId(textidList.item(0).getNodeValue().trim());

				// Getting the group for the Exercise.
				NodeList groupList = ExerciseElement
						.getElementsByTagName("group");
				Element groupElement = (Element) groupList.item(0);

				NodeList textGroupList = groupElement.getChildNodes();
				exercise.setGroup(textGroupList.item(0).getNodeValue().trim());

				// Getting the starting time for the Exercise.
				NodeList startTimeList = ExerciseElement
						.getElementsByTagName("start_time");
				Element startTimeElement = (Element) startTimeList.item(0);

				NodeList textStartTimeList = startTimeElement.getChildNodes();
				exercise.setStart(timeformatter.parseDateTime(textStartTimeList
						.item(0).getNodeValue().trim()));

				// Getting the ending time for the Exercise.
				NodeList endTimeList = ExerciseElement
						.getElementsByTagName("end_time");
				Element endTimeElement = (Element) endTimeList.item(0);

				NodeList textEndTimeList = endTimeElement.getChildNodes();
				exercise.setEnd(timeformatter.parseDateTime(textEndTimeList
						.item(0).getNodeValue().trim()));

				// Getting the location for the Exercise.
				NodeList locationList = ExerciseElement
						.getElementsByTagName("location");
				Element locationElement = (Element) locationList.item(0);

				NodeList textLocationList = locationElement.getChildNodes();
				exercise.setLocation(textLocationList.item(0).getNodeValue()
						.trim());

				// Getting the starting date for the Exercise.
				NodeList dateList = ExerciseElement
						.getElementsByTagName("start_date");
				Element DateElement = (Element) dateList.item(0);

				NodeList textDateList = DateElement.getChildNodes();
				exercise.setDate(dateformatter.parseDateTime(textDateList
						.item(0).getNodeValue().trim()));

				// Getting the ending date for the Exercise.
				NodeList dateEndList = ExerciseElement
						.getElementsByTagName("end_date");
				Element DateEndElement = (Element) dateEndList.item(0);

				NodeList textDateEndList = DateEndElement.getChildNodes();
				exercise.setEndDate(dateformatter.parseDateTime(textDateEndList
						.item(0).getNodeValue().trim()));

				// TODO for some reason it keeps getting a null pointer here
				// every now and then.
				objs.add(exercise);
			}
		}
	}

	private void parseAssignments(Document doc) {
		NodeList listOfAssignments = doc.getElementsByTagName("assignment");
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("yyyy-MM-dd'T'HH:mm");

		for (int i = 0; i < listOfAssignments.getLength(); i++) {

			Node AssignmentNode = listOfAssignments.item(i);
			if (AssignmentNode.getNodeType() == Node.ELEMENT_NODE) {

				Assignment assignment = (Assignment) nef
						.createNoppaElement(NoppaElementType.ASSIGNMENT);
				Element AssignmentElement = (Element) AssignmentNode;

				// Getting the deadline for the Assignment.
				NodeList deadlineList = AssignmentElement
						.getElementsByTagName("deadline");
				Element deadlineElement = (Element) deadlineList.item(0);

				NodeList textDeadlineList = deadlineElement.getChildNodes();
				assignment.setDate(formatter.parseDateTime(textDeadlineList
						.item(0).getNodeValue().trim()));

				// Getting the title for the Assignment.
				NodeList titleList = AssignmentElement
						.getElementsByTagName("title");
				Element TitleElement = (Element) titleList.item(0);

				NodeList textTitleList = TitleElement.getChildNodes();
				assignment
						.setTitle(textTitleList.item(0).getNodeValue().trim());

				// Getting the content for the Assignment.
				NodeList contentList = AssignmentElement
						.getElementsByTagName("content");
				Element contentElement = (Element) contentList.item(0);

				NodeList textContentList = contentElement.getChildNodes();
				assignment.setContent(textContentList.item(0).getNodeValue()
						.trim());

				// TODO for some reason it keeps getting a null pointer here
				// every now and then.
				objs.add(assignment);
			}
		}
	}

	private void parseEvents(Document doc) {
		NodeList listOfEvents = doc.getElementsByTagName("event");
		DateTimeFormatter dateformatter = DateTimeFormat
				.forPattern("yyyy-MM-dd");
		DateTimeFormatter timeformatter = DateTimeFormat.forPattern("HH:mm");

		for (int i = 0; i < listOfEvents.getLength(); i++) {

			Node EventNode = listOfEvents.item(i);
			if (EventNode.getNodeType() == Node.ELEMENT_NODE) {

				OtherEvent event = (OtherEvent) nef
						.createNoppaElement(NoppaElementType.OTHER);
				Element EventElement = (Element) EventNode;

				// Getting the id for this Event.
				NodeList idList = EventElement
						.getElementsByTagName("course_id");
				Element IdElement = (Element) idList.item(0);

				NodeList textIdList = IdElement.getChildNodes();
				event.setId(textIdList.item(0).getNodeValue().trim());

				// Getting the type for the Event.
				NodeList typeList = EventElement.getElementsByTagName("type");
				Element TypeElement = (Element) typeList.item(0);

				NodeList textTypeList = TypeElement.getChildNodes();
				event.setType(textTypeList.item(0).getNodeValue().trim());

				// Getting the title for the Event.
				NodeList titleList = EventElement.getElementsByTagName("title");
				Element TitleElement = (Element) titleList.item(0);

				NodeList textTitleList = TitleElement.getChildNodes();
				event.setTitle(textTitleList.item(0).getNodeValue().trim());

				// Getting the location for the Event.
				NodeList locationList = EventElement
						.getElementsByTagName("location");
				Element LocationElement = (Element) locationList.item(0);

				NodeList textLocationList = LocationElement.getChildNodes();
				event.setLocation(textLocationList.item(0).getNodeValue()
						.trim());

				// Getting the starting time for the Event.
				NodeList startTimeList = EventElement
						.getElementsByTagName("start_time");
				Element StartTimeElement = (Element) startTimeList.item(0);

				NodeList textStartTimeList = StartTimeElement.getChildNodes();
				event.setStart(timeformatter.parseDateTime(textStartTimeList
						.item(0).getNodeValue().trim()));

				// Getting the ending time for the Event.
				NodeList endTimeList = EventElement
						.getElementsByTagName("end_time");
				Element EndTimeElement = (Element) endTimeList.item(0);

				NodeList textEndTimeList = EndTimeElement.getChildNodes();
				event.setEnd(timeformatter.parseDateTime(textEndTimeList
						.item(0).getNodeValue().trim()));

				// Getting the starting date for the Event.
				NodeList startDateList = EventElement
						.getElementsByTagName("start_date");
				Element StartDateElement = (Element) startDateList.item(0);

				NodeList textStartDateList = StartDateElement.getChildNodes();
				event.setDate(dateformatter.parseDateTime(textStartDateList
						.item(0).getNodeValue().trim()));

				// Getting the ending date for the Event.
				NodeList endDateList = EventElement
						.getElementsByTagName("end_date");
				Element EndDateElement = (Element) endDateList.item(0);

				NodeList textEndDateList = EndDateElement.getChildNodes();
				event.setEndDate(dateformatter.parseDateTime(textEndDateList
						.item(0).getNodeValue().trim()));

				// TODO for some reason it keeps getting a null pointer here
				// every now and then.
				objs.add(event);
			}
		}
	}
}