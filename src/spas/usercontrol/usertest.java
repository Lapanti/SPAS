package spas.usercontrol;

import java.util.List;

import spas.Test;
import spas.nelements.Course;

public class usertest extends Test {

	public static void main(String[] args) {
		String name = "admin";
		String pword = "admin";
		String email = "mail";
		String newpword = "nimda";
		String newmail = "newmail";
		String cname = "Informaatioverkostot: Studio 1";
		String cid = "T-106.2001";
		String cname2 = "Matematiikan peruskurssi C1";
		String cid2 = "Mat-1.1110";

		UserCreatorValidator ucv = new UserCreatorValidator();
		UserAccountHandler uah = new UserAccountHandler();
		UserCourseHandler uch = new UserCourseHandler(name);

		boolean testing = true;

		if (!test(ucv.createUser(name, pword, email),
				"Testing user creation......")) {
			testing = false;
		}

		if (!test((ucv.authenticate(name, pword)),
				"Testing user authentication")) {
			testing = false;
		}

		if (!test((ucv.changeEmail(name, pword, newmail) && uah.getEmail(name)
				.equals(newmail)), "Testing email change.......")) {
			testing = false;
		}

		if (!test((ucv.changePassword(name, newpword) && ucv.authenticate(name,
				newpword)), "Testing password change....")) {
			testing = false;
		}

		if (!test((uch.addCourse(cname, cid) && uch.addCourse(cname2, cid2)),
				"Testing course adding......")) {
			testing = false;
		}

		List<Course> courses = uch.getCourses();
		if (!test((courses.get(0).getId().equals(cid) && courses.get(1)
				.getName().equals(cname2)), "Testing courses............")) {
			testing = false;
		}

		if (!test(
				(uch.removeCourse(cname2, cid2) && uch.getCourses().size() == 1),
				"Testing course removal.....")) {
			testing = false;
		}

		if (!test(ucv.removeUser(name, newpword), "Testing user deletion......")) {
			testing = false;
		}

		result(testing);
	}
}
