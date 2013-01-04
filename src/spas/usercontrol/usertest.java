package spas.usercontrol;

import java.util.List;
import java.util.Scanner;

import spas.Test;
import spas.nelements.NCourse;

/**
 * Class for testing different methods/classes of package. For development
 * purposes only.
 * 
 * @author Lauri Lavanti
 * @version 1.0
 * @since 0.1
 * 
 */
public class usertest extends Test {

	public static void main(String[] args) {
		String name = "admin";
		String pword = "admin";
		String email = "asd";
		String newpword = "nimda";
		String newmail = "dsa";
		String cname = "Informaatioverkostot: Studio 1";
		String cid = "T-106.2001";
		String cname2 = "Matematiikan peruskurssi C1";
		String cid2 = "Mat-1.1110";
		String group = "OLO 5";

		UserHandler ucv = new UserHandler("WebContent/resources/admin.xml");
		UserCourseHandler uch = new UserCourseHandler(
				"WebContent/resources/admin.xml");
		EmailNewPassword enp = new EmailNewPassword();

		boolean testing = true;

		// Testing user creation.
		if (!test(ucv.createUser(name, pword, email,
				"WebContent/resources/model/user.xml"),
				"Testing user creation......")) {
			testing = false;
		}

		// Testing authentication.
		if (!test((ucv.authenticate(name, pword)),
				"Testing user authentication")) {
			testing = false;
		}

		// Testing email changing.
		if (!test((ucv.changeEmail(name, pword, newmail) && ucv.getEmail(name)
				.equals(newmail)), "Testing email change.......")) {
			testing = false;
		}

		// Testing password changing.
		if (!test(
				(ucv.changePassword(name, pword, newpword) && ucv.authenticate(
						name, newpword)), "Testing password change....")) {
			testing = false;
		}

		/*
		 * Testing getting a new password by email. (Must set legit account info
		 * for sender in EmailNewPassword.)
		 */
		Scanner sc = new Scanner(System.in);
		if (!test(
				(enp.createNewPassword("laurilavanti@gmail.com",
						"WebContent/resources") && ucv.authenticate(name,
						sc.nextLine())), "Testing email password change")) {
			testing = false;
		}
		sc.close();

		// Testing course adding.
		if (!test((uch.addCourse(cname, cid) && uch.addCourse(cname2, cid2)),
				"Testing course adding......")) {
			testing = false;
		}

		// Testing course status-change.
		if (!test(
				(uch.changeState(cid, UserCourseHandler.COMPLETED) && uch
						.getState(cid) == UserCourseHandler.COMPLETED),
				"Testing status editing.....")) {
			testing = false;
		}

		// Testing course group-change.
		if (!test(
				(uch.changeGroup(cid, group) && uch.getGroup(cid).equals(group)),
				"Testing group editing......")) {
			testing = false;
		}

		// Testing course getting.
		List<NCourse> courses = uch.getCourses();
		if (!test((courses.get(0).getId().equals(cid2) && courses.get(1)
				.getName().equals(cname)), "Testing courses............")) {
			testing = false;
		}

		// Testing course removing.
		if (!test((uch.removeCourse(cid2) && uch.getCourses().size() == 1),
				"Testing course removal.....")) {
			testing = false;
		}

		// Testing user deleting.
		if (!test(ucv.removeUser(name, newpword), "Testing user deletion......")) {
			testing = false;
		}

		result(testing);
	}
}
