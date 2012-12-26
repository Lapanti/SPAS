package spas;

public abstract class Test {

	public static boolean test(boolean condition, String message) {
		System.out.printf(message + "...\t\t\t\t");
		System.out.println(condition ? "OK!" : "Error");
		return condition;
	}

	public static void result(boolean testresult) {
		if (testresult) {
			System.out.println("No errors were found in deep examination.");
		}
	}
}
