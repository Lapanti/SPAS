package spas.reading;

import java.util.List;

import spas.Test;
import spas.nelements.Assignment;
import spas.nelements.Course;
import spas.nelements.Exercise;
import spas.nelements.Lecture;
import spas.nelements.Nelement;
import spas.nelements.OtherEvent;

public class readertest extends Test {

	public static void main(String[] args) {
		XMLToObject noppa = new XMLToObject();
		boolean testing = true;

		// Testing organizations.
		List<Nelement> orgs = noppa.getOrganizations();
		if (!test((orgs.get(0).getId().equals("CHEM") && orgs.get(6).getId()
				.equals("TaiK")), "Testing organizations...")) {
			testing = false;
		}

		// Testing departments.
		List<Nelement> depts = noppa.getDepartments(null);
		if (!test(
				(depts.get(0).getId().equals("T1010") && depts
						.get(depts.size() - 1).getId().equals("TaiK-0")),
				"Testing departments.....")) {
			testing = false;
		}

		// Testing specific departments.
		List<Nelement> depts2 = noppa.getDepartments(orgs.get(0).getId());
		if (!test(
				(depts2.get(0).getId().equals("T1010") && depts2
						.get(depts2.size() - 1).getId().equals("CHEM-0")),
				"Testing CHEM-departments")) {
			testing = false;
		}

		// Testing courses.
		List<Course> cs = noppa.getCourses(orgs.get(0).getId(), depts2.get(0)
				.getId(), null);
		if (!test(
				(cs.get(0).getId().equals("KE-0.1200") && cs.get(cs.size() - 1)
						.getId().equals("KE-90.5120")),
				"Testing courses.........")) {
			testing = false;
		}

		// Testing a single course.
		Course c = noppa.getCourseOverview(cs.get(0).getId(), cs.get(0)
				.getName());
		if (!test(
				(c.getId().equals("KE-0.1200") && c.getCredits().equals("3") && c
						.getPeriod().equals("I-II")),
				"Testing course..........")) {
			testing = false;
		}

		c = new Course();
		c.setName("Informaatioverkostot: Studio 1");
		c.setId("T-106.2001");

		// Testing course lectures.
		List<Lecture> lectures = noppa.getCourseLectures(c);
		if (!test(
				(lectures.get(0).getTitle().equals("Aloitusluento") && lectures
						.get(lectures.size() - 1).getTitle().equals("L")),
				"Testing lectures........")) {
			testing = false;
		}

		// Testing course exercises.
		List<Exercise> exercises = noppa.getCourseExercises(c);
		if (!test((exercises.get(0).getGroup().equals("OLO 1") && exercises
				.get(exercises.size() - 1).getGroup().equals("OLO 5")),
				"Testing exercises.......")) {
			testing = false;
		}

		// Testing course assignments.
		List<Assignment> assignments = noppa.getCourseAssignments(c);
		if (!test(
				(assignments.get(0).getTitle()
						.equals("Nollaharjoitus (ei palautettava)") && assignments
						.get(assignments.size() - 1).getTitle()
						.equals("Ohjelmointi 5")), "Testing assignments.....")) {
			testing = false;
		}

		// Testing course other events.
		List<OtherEvent> otherevents = noppa.getCourseEvents(c);
		if (!test(otherevents.get(0).getType().equals("exams"),
				"Testing other events....")) {
			testing = false;
		}

		result(testing);
	}
}
