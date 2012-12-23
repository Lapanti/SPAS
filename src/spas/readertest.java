package spas;

import java.util.List;

import spas.nelements.Course;
import spas.nelements.Nelement;
import spas.reading.XMLToObject;

public class readertest {

	private static void test(boolean condition, String message) {
		System.out.printf(message + "...\t\t\t\t");
		System.out.println(condition ? "OK!" : "Error");
	}

	public static void main(String[] args) {
		XMLToObject noppa = new XMLToObject();
			List<Nelement> orgs = noppa.getOrganizations();
			test((orgs.get(0).getId().equals("CHEM") && orgs.get(6).getId()
					.equals("TaiK")), "Testing organizations");
			List<Nelement> depts = noppa.getDepartments(null);
			test((depts.get(0).getId().equals("T1010") && depts
					.get(depts.size() - 1).getId().equals("TaiK-0")),
					"Testing departments..");
			List<Nelement> depts2 = noppa.getDepartments(orgs.get(0).getId());
			test((depts2.get(0).getId().equals("T1010") && depts2
					.get(depts2.size() - 1).getId().equals("CHEM-0")),
					"Testing CHEM-departments");
			List<Course> cs = noppa.getCourses(orgs.get(0).getId(),
					depts2.get(0).getId(), null);
			test((cs.get(0).getId().equals("KE-0.1200") && cs
					.get(cs.size() - 1).getId().equals("KE-90.5120")),
					"Testing courses......");
			Course c = noppa.getCourseOverview(cs.get(0).getId(), cs.get(0)
					.getName());
			test((c.getId().equals("KE-0.1200") && c.getCredits().equals("3") && c
					.getPeriod().equals("I-II")), "Testing course.......");
	}
}
