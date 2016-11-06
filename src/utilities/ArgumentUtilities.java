package utilities;

public class ArgumentUtilities {

	private static String[] ERRMESSAGES = { "Argument cannot be null: ",
			"Array cannot be empty: " };

	public static void checkNullArgs(Object[] args)
			throws IllegalArgumentException {
		if(args == null) return;
		for (Object o : args) {
			if (o == null) {
				throw new IllegalArgumentException(ERRMESSAGES[0] + o);
			}
		}
	}

	public static void checkEmptyArray(Object[] arg) throws IllegalArgumentException {
		if(arg == null) return;
		if(arg.length <= 0) {
			throw new IllegalArgumentException(ERRMESSAGES[1] + arg);
		}
	}
}
