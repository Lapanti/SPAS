package spas.nhandling;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;

import spas.nhandling.nelements.NCourse;
import spas.nhandling.nelements.NElement;
import spas.nhandling.nelements.NElementFactory;
import spas.nhandling.nelements.NElementType;
import spas.nhandling.nelements.NEvent;

/**
 * Class for getting Object-representation of requested information from <a
 * href=
 * "https://wiki.aalto.fi/download/attachments/71895449/NoppaAPI_eng.pdf?version=2&modificationDate=1352361073000"
 * target="_blank">the Noppa API</a>. Works in tandem with
 * {@link XMLParser#parseFile(InputSource) XMLParser}.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 0.1
 * 
 */
public class NReader {
	/**
	 * The first part of the URL required to connect to Noppa API.
	 * 
	 */
	private final String URLBase = "http://noppa-api-dev.aalto.fi/api/v1/";
	/**
	 * The development key required to access content from the Noppa API.
	 * 
	 */
	private final String key = "?key=cdda4ae4833c0114005de5b5c4371bb8";

	/**
	 * Get the organizations of Aalto University.
	 * 
	 * @return List of organizations, or an empty list if there was a problem
	 *         with the connection.
	 */
	@SuppressWarnings("unchecked")
	public List<NElement> getOrganizations() {
		try {
			return (List<NElement>) XMLParser
					.parseFile(openConnection("organizations"));
		} catch (IOException e) {
			// This should never happen.
			return new ArrayList<NElement>();
		}
	}

	/**
	 * Get the departments of Aalto University, or from a specific organization
	 * within it.
	 * 
	 * @param organizationID
	 *            The organization, from which the departments are from (can be
	 *            <code>null</code>, in which case it will return all
	 *            departments).
	 * @return List of departments, or an empty list if there was a problem
	 *         with the connection.
	 */
	@SuppressWarnings("unchecked")
	public List<NElement> getDepartments(String organizationID) {
		// Convert the parameter into the proper form of GET parameters.
		String parameter1 = (organizationID == null) ? null : "&org_id="
				+ organizationID;
		try {
			return (List<NElement>) XMLParser.parseFile(openConnection(
					"departments", parameter1));
		} catch (IOException e) {
			// This should never happen.
			return new ArrayList<NElement>();
		}
	}

	/**
	 * Get courses from within Aalto University, according to given search
	 * parameters. Must give at least one parameter.
	 * 
	 * @param organizationID
	 *            The organization, from which to search.
	 * @param departmentID
	 *            The department, from which to search.
	 * @param search
	 *            The keyword for search.
	 * @return List of courses, or an empty list if there was a problem with
	 *         the connection.
	 */
	@SuppressWarnings("unchecked")
	public List<NCourse> getCourses(String organizationID, String departmentID,
			String search) {
		try {
			/*
			 * Convert the parameters into the correct GET-parameter forms.
			 * Ignore null and empty Strings.
			 */
			String parameter1 = (organizationID == null || organizationID
					.equals("")) ? null : "&org_id=" + organizationID;
			String parameter2 = (departmentID == null || departmentID
					.equals("")) ? null : "&dept_id=" + departmentID;
			String parameter3 = (search == null || search.equals("")) ? null
					: "&search=" + URLEncoder.encode(search, "UTF-8");

			return (List<NCourse>) XMLParser.parseFile(openConnection(
					"courses", parameter1, parameter2, parameter3));
		} catch (IOException e) {
			// This should never happen.
			return new ArrayList<NCourse>();
		}
	}

	/**
	 * Getting a more detailed version of given course.
	 * 
	 * @param courseID
	 *            The ID of the course, from which to get info.
	 * @param courseName
	 *            The name of the course, from which to get info.
	 * @return The course for which the overview was for, or a new Course if
	 *         there was something wrong with the connection.
	 */
	public NCourse getCourseOverview(String courseID, String courseName) {
		try {
			@SuppressWarnings("unchecked")
			List<NCourse> tempList = (List<NCourse>) XMLParser
					.parseFile(openConnection("courses/" + courseID
							+ "/overview"));

			// Making sure nothing went wrong with the parsing.
			if (tempList.size() > 0) {
				NCourse finalCourse = tempList.get(0);
				finalCourse.setName(courseName);
				return finalCourse;
			}
		} catch (IOException e) {
			// This should never happen.
		}
		return (NCourse) NElementFactory.createNElement(NElementType.COURSE);
	}

	/**
	 * Method for getting the lectures of given course.
	 * 
	 * @param course
	 *            The course, from which to get the lectures.
	 * @return List containing the lectures for given course, or an empty list
	 *         if something went wrong with the connection.
	 */
	@SuppressWarnings("unchecked")
	public List<NEvent> getCourseLectures(NCourse course) {
		List<NEvent> finalList = new ArrayList<NEvent>();
		try {

			// Open the connection with the correct ID.
			String courseID = course.getId();
			finalList = (List<NEvent>) XMLParser
					.parseFile(openConnection("courses/" + courseID
							+ "/lectures"));

			// Add course's name to every NEvent.
			for (NEvent lecture : finalList) {
				lecture.setName(course.getName());
			}
		} catch (IOException e) {
			// This should never happen.
		}
		return finalList;
	}

	/**
	 * Method for getting the exercises of given course.
	 * 
	 * @param course
	 *            The course, from which to get the exercises.
	 * @return List containing the exercises for given course, or an empty list
	 *         if there was a problem with the connection.
	 */
	@SuppressWarnings("unchecked")
	public List<NEvent> getCourseExercises(NCourse course) {
		List<NEvent> finalList = new ArrayList<NEvent>();
		try {

			// Open the connection with the correct ID.
			String courseID = course.getId();
			finalList = (List<NEvent>) XMLParser
					.parseFile(openConnection("courses/" + courseID
							+ "/exercises"));

			// Add the course's name to exercises.
			for (NEvent exercise : finalList) {
				exercise.setName(course.getName());
			}
		} catch (IOException e) {
			// This should never happen.
		}
		return finalList;
	}

	/**
	 * Method for getting the assignments of given course.
	 * 
	 * @param course
	 *            The course, from which to get the assignments.
	 * @return List containing the assignments for given course, or an empty
	 *         list if there was a problem with the connection.
	 */
	@SuppressWarnings("unchecked")
	public List<NEvent> getCourseAssignments(NCourse course) {
		List<NEvent> finalList = new ArrayList<NEvent>();
		try {

			// Open the connection with the correct ID.
			String courseID = course.getId();
			finalList = (List<NEvent>) XMLParser
					.parseFile(openConnection("courses/" + courseID
							+ "/assignments"));

			// Add the course's name to assignments.
			for (NEvent assignment : finalList) {
				assignment.setName(course.getName());
			}
		} catch (IOException e) {
			// This should never happen.
		}
		return finalList;
	}

	/**
	 * Method for getting other events of given course. Other events are events
	 * that are neither lectures, exercises nor assignments.
	 * 
	 * @param course
	 *            The course, from which to get the other events.
	 * @return List of this course's other events, or an empty list if there
	 *         was a problem with the connection.
	 */
	@SuppressWarnings("unchecked")
	public List<NEvent> getCourseEvents(NCourse course) {
		List<NEvent> finalList = new ArrayList<NEvent>();
		try {

			// Open the connection with the correct ID.
			String courseID = course.getId();
			finalList = (List<NEvent>) XMLParser
					.parseFile(openConnection("courses/" + courseID + "/events"));

			// Add the course's name to events.
			for (NEvent event : finalList) {
				event.setName(course.getName());
			}
		} catch (IOException e) {
			// This should never happen.
		}
		return finalList;
	}

	/**
	 * The method for getting InputSource of the appropriate XML-file from the
	 * Noppa API.
	 * 
	 * @see #openConnection(String, String, String, String)
	 * @param URL
	 *            The appropriate part of Noppa API you want to connect to. For
	 *            example "course".
	 * @return The InputSource for the asked XML-file.
	 * @throws IOException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public InputSource openConnection(String URL) throws IOException {
		return openConnection(URL, null, null, null);
	}

	/**
	 * The method for getting InputSource of the appropriate XML-file from the
	 * Noppa API. Only used with departments.
	 * 
	 * @see #openConnection(String, String, String, String)
	 * @param URL
	 *            The appropriate part of Noppa API you want to connect to. For
	 *            example "course".
	 * @param parameter1
	 *            The parameter for when searching departments from inside an
	 *            organization.
	 * @return The InputSource for the asked XML-file.
	 * @throws IOException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public InputSource openConnection(String URL, String parameter1)
			throws IOException {
		return openConnection(URL, parameter1, null, null);
	}

	/**
	 * The method for getting InputSource of the appropriate XML-file from the
	 * Noppa API. Only used directly when searching for courses.
	 * 
	 * @param URL
	 *            The appropriate part of Noppa API you want to connect to. For
	 *            example "course".
	 * @param parameter1
	 *            Search parameters for when you are searching for courses. At
	 *            least one must be given for the search to succeed.
	 * @param parameter2
	 *            Search parameters for when you are searching for courses. At
	 *            least one must be given for the search to succeed.
	 * @param parameter3
	 *            Search parameters for when you are searching for courses. At
	 *            least one must be given for the search to succeed.
	 * @return The InputSource for the asked XML-file.
	 * @throws IOException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public InputSource openConnection(String URL, String parameter1,
			String parameter2, String parameter3) throws IOException {
		try {
			// Build the URL to connect to.
			URL connection;
			if (parameter1 == null && parameter2 == null && parameter3 == null) {
				connection = new URL(URLBase + URL + ".xml" + key);
			} else {
				String urltoconnect = URLBase + URL + ".xml" + key;
				if (parameter1 != null) {
					urltoconnect += parameter1;
				}
				if (parameter2 != null) {
					urltoconnect += parameter2;
				}
				if (parameter3 != null) {
					urltoconnect += parameter3;
				}
				connection = new URL(urltoconnect);
			}
			return new InputSource(connection.openStream());
		} catch (IOException e) {
			// This shouldn't happen.
			throw e;
		}
	}
}