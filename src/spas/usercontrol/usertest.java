package spas.usercontrol;

import spas.Test;

public class usertest extends Test {

	public static void main(String[] args) {
		String name = "admin";
		String pword = "admin";
		String email = "mail";
		String newpword = "nimda";
		String newmail = "newmail";

		UserCreatorValidator ucv = new UserCreatorValidator();
		UserXMLHandler uxh = new UserXMLHandler();

		boolean testing = true;

		if (!test(ucv.createUser(name, pword, email),
				"Testing user creation......")) {
			testing = false;
		}

		if (!test((ucv.authenticate(name, pword)),
				"Testing user authentication")) {
			testing = false;
		}

		if (!test((ucv.changeEmail(name, pword, newmail) && uxh.getEmail(name)
				.equals(newmail)), "Testing email change.......")) {
			testing = false;
		}

		if (!test((ucv.changePassword(name, newpword) && ucv.authenticate(name,
				newpword)), "Testing password change....")) {
			testing = false;
		}

		if (!test(ucv.removeUser(name, newpword), "Testing user deletion......")) {
			testing = false;
		}

		result(testing);
	}
}
