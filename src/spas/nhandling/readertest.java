package spas.nhandling;

import java.util.List;


import spas.Test;
import spas.nhandling.nelements.NCourse;
import spas.nhandling.nelements.NElement;
import spas.nhandling.nelements.NElementFactory;
import spas.nhandling.nelements.NElementType;
import spas.nhandling.nelements.NEvent;

/**
 * Class for testing different methods/classes of package. For development
 * purposes only.
 * 
 * @author Lauri Lavanti
 * @version 1.1
 * @since 0.1
 * 
 */
public class readertest extends Test {

	public static void main(String[] args) {
		NReader nreader = new NReader();
		boolean testing = true;

		// Testing organizations.
		List<NElement> orgs = nreader.getOrganizations();
		if (!test((orgs.get(0).getId().equals("CHEM") && orgs.get(6).getId()
				.equals("TaiK")), "Testing organizations...")) {
			testing = false;
		}

		// Testing departments.
		List<NElement> depts = nreader.getDepartments(null);
		if (!test(
				(depts.get(0).getId().equals("T1010") && depts
						.get(depts.size() - 1).getId().equals("TaiK-0")),
				"Testing departments.....")) {
			testing = false;
		}

		// Testing specific departments.
		List<NElement> depts2 = nreader.getDepartments(orgs.get(0).getId());
		if (!test(
				(depts2.get(0).getId().equals("T1010") && depts2
						.get(depts2.size() - 1).getId().equals("CHEM-0")),
				"Testing CHEM-departments")) {
			testing = false;
		}

		// Testing courses.
		List<NCourse> cs = nreader.getCourses(orgs.get(0).getId(), depts2
				.get(0).getId(), null);
		if (!test(
				(cs.get(0).getId().equals("KE-0.1200") && cs.get(cs.size() - 1)
						.getId().equals("KE-90.5120")),
				"Testing courses.........")) {
			testing = false;
		}

		// Testing a single course.
		NCourse c = nreader.getCourseOverview(cs.get(0).getId(), cs.get(0)
				.getName());
		if (!test(
				(c.getId().equals("KE-0.1200") && c.getCredits().equals("3") && c
						.getPeriod().equals("I-II")),
				"Testing course..........")) {
			testing = false;
		}

		c = (NCourse) NElementFactory.createNElement(NElementType.COURSE);
		c.setName("Informaatioverkostot: Studio 1");
		c.setId("T-106.2001");

		// Testing course exercises.
		List<NEvent> exercises = nreader.getCourseExercises(c);
		if (!test((exercises.get(0).getGroup().equals("OLO 1") && exercises
				.get(exercises.size() - 1).getGroup().equals("OLO 5")),
				"Testing exercises.......")) {
			testing = false;
		}

		// Testing course assignments.
		List<NEvent> assignments = nreader.getCourseAssignments(c);
		if (!test(
				(assignments.get(0).getDescription()
						.equals("Nollaharjoitus (ei palautettava)") && assignments
						.get(assignments.size() - 1).getDescription()
						.equals("Ohjelmointi 5")), "Testing assignments.....")) {
			testing = false;
		}

		// Testing course other events.
		List<NEvent> otherevents = nreader.getCourseEvents(c);
		if (!test(otherevents.get(0).getDescription().equals(
				"Informaatioverkostot: Studio 1 (T02), exams"),
				"Testing other events....")) {
			testing = false;
		}

		result(testing);
	}
}
