package spas.reading;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import spas.nelements.Assignment;
import spas.nelements.Course;
import spas.nelements.Exercise;
import spas.nelements.Lecture;
import spas.nelements.Nelement;
import spas.nelements.OtherEvent;


/**
 * Class for spas.reading the proper parts of Noppa API. Contains formatting for
 * GET-parameters to be in the correct form.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class XMLToObject {
	/**
	 * The connection to Noppa to be used.
	 * 
	 */
	private Connection connection = new Connection();
	private XMLReader reader = new XMLReader();

	/**
	 * Method for getting the XML-file for organizations found in Noppa.
	 * 
	 * @return List of Organizations.
	 */
	public List<Nelement> getOrganizations() {
		try {
		return reader
				.parseNoppafile(connection.openConnection("organizations"));
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Method for getting the XML-file for departments found in Noppa.
	 * 
	 * @param organizationID
	 *            The organization, from which the departments are from.
	 * @return List of Departments.
	 */
	public List<Nelement> getDepartments(String organizationID) {
		String parameter1 = (organizationID == null) ? null : "&org_id="
				+ organizationID;
		try {
		return reader.parseNoppafile(connection.openConnection("departments",
				parameter1));
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Method for getting the XML-file for Courses according matching the given
	 * parameters.
	 * 
	 * @param organizationID
	 *            The organization, from which to search.
	 * @param departmentID
	 *            The department, from which to search.
	 * @param search
	 *            The keyword for search.
	 * @return List of Courses.
	 */
	public List<Course> getCourses(String organizationID, String departmentID,
			String search) {
		try {
		String parameter1 = (organizationID == null) ? null : "&org_id="
				+ organizationID;
		String parameter2 = (departmentID == null) ? null : "&dept_id="
				+ departmentID;
		String parameter3 = (search == null) ? null : "&search=" + URLEncoder.encode(search, "UTF-8");
		List<Nelement> tempList = reader.parseNoppafile(connection
				.openConnection("courses", parameter1, parameter2, parameter3));
		List<Course> finalList = new ArrayList<Course>();
		for (Nelement nElement : tempList) {
				finalList.add((Course) nElement);
		}
		return finalList;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Method for getting the overview info of given Course.
	 * 
	 * @param courseID
	 *            The Course, from which to get info.
	 * @param courseName
	 *            The name of the Course, from which to get info.
	 * @return The Course for which the overview was for.
	 */
	public Course getCourseOverview(String courseID, String courseName) {
		try {
		Course finalCourse = (Course) reader.parseNoppafile(
				connection.openConnection("courses/" + courseID + "/overview"))
				.get(0);
		finalCourse.setName(courseName);
		return finalCourse;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Method for getting the lectures of given course.
	 * 
	 * @param course
	 *            The course, from which to get the lectures.
	 * @return List containing the Lectures for given course.
	 */
	public List<Lecture> getCourseLectures(Course course) {
		try {
		String courseID = course.getId();
		List<Nelement> tempList = reader.parseNoppafile(connection
				.openConnection("courses/" + courseID + "/lectures"));
		List<Lecture> finalList = new ArrayList<Lecture>();
		for (Nelement nElement : tempList) {
				Lecture lecture = (Lecture) nElement;
				lecture.setName(course.getName());
				finalList.add(lecture);
		}
		return finalList;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Method for getting the exercises of given course.
	 * 
	 * @param course
	 *            The Course, from which to get the exercises.
	 * @return List containing the Exercises for given Course.
	 */
	public List<Exercise> getCourseExercises(Course course) {
		try {
		String courseID = course.getId();
		List<Nelement> tempList = reader.parseNoppafile(connection
				.openConnection("courses/" + courseID + "/exercises"));
		List<Exercise> finalList = new ArrayList<Exercise>();
		for (Nelement nElement : tempList) {
				Exercise exercise = (Exercise) nElement;
				exercise.setName(course.getName());
				finalList.add(exercise);
		}
		return finalList;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Method for getting the Assignments of given course.
	 * 
	 * @param course
	 *            The Course, from which to get the Assignments.
	 * @return List containing the Assignments for given Course.
	 */
	public List<Assignment> getCourseAssignments(Course course) {
		try {
		String courseID = course.getId();
		List<Nelement> tempList = reader.parseNoppafile(connection
				.openConnection("courses/" + courseID + "/assignments"));
		List<Assignment> finalList = new ArrayList<Assignment>();
		for (Nelement nElement : tempList) {
				Assignment assignment = (Assignment) nElement;
				assignment.setName(course.getName());
				finalList.add(assignment);
		}
		return finalList;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Method for getting the OtherEvents of given Course.
	 * 
	 * @param course
	 *            The Course, from which to get the OtherEvents.
	 * @return List of this Course's OtherEvents.
	 */
	public List<OtherEvent> getCourseEvents(Course course) {
		try {
		String courseID = course.getId();
		List<Nelement> tempList = reader.parseNoppafile(connection
				.openConnection("courses/" + courseID + "/events"));
		List<OtherEvent> finalList = new ArrayList<OtherEvent>();
		for (Nelement nElement : tempList) {
				OtherEvent event = (OtherEvent) nElement;
				event.setName(course.getName());
				finalList.add(event);
		}
		return finalList;
		} catch (Exception ex) {
			return null;
		}
	}

	public static void main(String[] args) {
		XMLToObject nr = new XMLToObject();
		try {
			// List<Nelement> orgs = nr.getOrganizations();
			// List<Nelement> deps = nr.getDepartments("SCI");
			// List<Course> cs = nr.getCourses("SCI", "T3070", "perus");
			Course c = new Course();
			c.setId("T-106.2001");
			c.setName("Informaatioverkostot: Studio 1");
			// c = nr.getCourseOverview(c);
			// System.out.println("Course name: " + c.getName());
			// System.out.println("Course credits: " + c.getCredits());
			// System.out.println("Course periods: " + c.getPeriod());
			// System.out.println("Course content: " + c.getContent());
			// List<Lecture> cs = nr.getCourseLectures(c);
			// List<Exercise> cs = nr.getCourseExercises(c);
			// List<Assignment> cs = nr.getCourseAssignments(c);
			List<OtherEvent> cs = nr.getCourseEvents(c);
			for (OtherEvent ne : cs) {
				System.out.println("OtherEvent title: " + ne.getTitle());
				System.out.println("OtherEvent type: " + ne.getType());
				System.out.println("OtherEvent time: from " + ne.getStart()
						+ " to " + ne.getEnd());
				System.out.println("OtherEvent date: from " + ne.getDate()
						+ " to " + ne.getEndDate());
				System.out.println("-----");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
