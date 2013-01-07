package spas;

/**
 * Contains methods for creating a simple testing class.
 * 
 * @see nreading.readertest
 * @see usercontrol.usertest
 * @author Lauri Lavanti
 * @version 1.1
 * @since 0.1
 * 
 */
public abstract class Test {

	/**
	 * Prints out testing subject and result.
	 * 
	 * @param condition What to test.
	 * @param message Explanation on subject.
	 * @return Whether test was successful or not.
	 */
	public static boolean test(boolean condition, String message) {
		System.out.printf(message + "...\t\t\t\t");
		System.out.println(condition ? "OK!" : "Error");
		return condition;
	}

	/**
	 * Prints out result of test.
	 * 
	 * @param testresult Result of test.
	 */
	public static void result(boolean testresult) {
		if (testresult) {
			System.out.println("No errors were found in deep examination.");
		} else {
			System.out.println("Test was unsuccessful.");
		}
	}
}
