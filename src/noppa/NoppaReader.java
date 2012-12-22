package noppa;

import java.util.ArrayList;
import java.util.List;

import nElements.Assignment;
import nElements.Course;
import nElements.Exercise;
import nElements.Lecture;
import nElements.NoppaElement;
import nElements.OtherEvent;

/**
 * Class for reading the proper parts of Noppa API. Contains formatting for
 * GET-parameters to be in the correct form.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class NoppaReader {
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
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public List<NoppaElement> getOrganizations() throws ConnectionException {
		return reader
				.parseNoppafile(connection.openConnection("organizations"));
	}

	/**
	 * Method for getting the XML-file for departments found in Noppa.
	 * 
	 * @param organizationID
	 *            The organization, from which the departments are from.
	 * @return List of Departments.
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public List<NoppaElement> getDepartments(String organizationID)
			throws ConnectionException {
		String parameter1 = (organizationID == null) ? null : "&org_id="
				+ organizationID;
		return reader.parseNoppafile(connection.openConnection("departments",
				parameter1));
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
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public List<Course> getCourses(String organizationID, String departmentID,
			String search) throws ConnectionException {
		String parameter1 = (organizationID == null) ? null : "&org_id="
				+ organizationID;
		String parameter2 = (departmentID == null) ? null : "&dept_id="
				+ departmentID;
		String parameter3 = (search == null) ? null : "&search=" + search;
		List<NoppaElement> tempList = reader.parseNoppafile(connection
				.openConnection("courses", parameter1, parameter2, parameter3));
		List<Course> finalList = new ArrayList<Course>();
		for (NoppaElement nElement : tempList) {
			try {
				finalList.add((Course) nElement);
			} catch (Exception ex) {
			}
		}
		return finalList;
	}

	/**
	 * Method for getting the overview info of given Course.
	 * 
	 * @param courseID
	 *            The Course, from which to get info.
	 * @param courseName
	 *            The name of the Course, from which to get info.
	 * @return The Course for which the overview was for.
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public Course getCourseOverview(String courseID, String courseName)
			throws ConnectionException {
		Course finalCourse = (Course) reader.parseNoppafile(
				connection.openConnection("courses/" + courseID + "/overview"))
				.get(0);
		finalCourse.setName(courseName);
		return finalCourse;
	}

	/**
	 * Method for getting the lectures of given course.
	 * 
	 * @param course
	 *            The course, from which to get the lectures.
	 * @return List containing the Lectures for given course.
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public List<Lecture> getCourseLectures(Course course)
			throws ConnectionException {
		String courseID = course.getId();
		List<NoppaElement> tempList = reader.parseNoppafile(connection
				.openConnection("courses/" + courseID + "/lectures"));
		List<Lecture> finalList = new ArrayList<Lecture>();
		for (NoppaElement nElement : tempList) {
			try {
				Lecture lecture = (Lecture) nElement;
				lecture.setName(course.getName());
				finalList.add(lecture);
			} catch (Exception ex) {
			}
		}
		return finalList;
	}

	/**
	 * Method for getting the exercises of given course.
	 * 
	 * @param course
	 *            The Course, from which to get the exercises.
	 * @return List containing the Exercises for given Course.
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public List<Exercise> getCourseExercises(Course course)
			throws ConnectionException {
		String courseID = course.getId();
		List<NoppaElement> tempList = reader.parseNoppafile(connection
				.openConnection("courses/" + courseID + "/exercises"));
		List<Exercise> finalList = new ArrayList<Exercise>();
		for (NoppaElement nElement : tempList) {
			try {
				Exercise exercise = (Exercise) nElement;
				exercise.setName(course.getName());
				finalList.add(exercise);
			} catch (Exception ex) {
			}
		}
		return finalList;
	}

	/**
	 * Method for getting the Assignments of given course.
	 * 
	 * @param course
	 *            The Course, from which to get the Assignments.
	 * @return List containing the Assignments for given Course.
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public List<Assignment> getCourseAssignments(Course course)
			throws ConnectionException {
		String courseID = course.getId();
		List<NoppaElement> tempList = reader.parseNoppafile(connection
				.openConnection("courses/" + courseID + "/assignments"));
		List<Assignment> finalList = new ArrayList<Assignment>();
		for (NoppaElement nElement : tempList) {
			try {
				Assignment assignment = (Assignment) nElement;
				assignment.setName(course.getName());
				finalList.add(assignment);
			} catch (Exception ex) {
			}
		}
		return finalList;
	}

	/**
	 * Method for getting the OtherEvents of given Course.
	 * 
	 * @param course
	 *            The Course, from which to get the OtherEvents.
	 * @return List of this Course's OtherEvents.
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public List<OtherEvent> getCourseEvents(Course course)
			throws ConnectionException {
		String courseID = course.getId();
		List<NoppaElement> tempList = reader.parseNoppafile(connection
				.openConnection("courses/" + courseID + "/events"));
		List<OtherEvent> finalList = new ArrayList<OtherEvent>();
		for (NoppaElement nElement : tempList) {
			try {
				OtherEvent event = (OtherEvent) nElement;
				event.setName(course.getName());
				finalList.add(event);
			} catch (Exception ex) {
			}
		}
		return finalList;
	}

	public static void main(String[] args) {
		NoppaReader nr = new NoppaReader();
		try {
			// List<NoppaElement> orgs = nr.getOrganizations();
			// List<NoppaElement> deps = nr.getDepartments("SCI");
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
