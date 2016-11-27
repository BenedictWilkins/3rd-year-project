package utilities;

import java.util.Arrays;
import java.util.Collection;

public class GeneralUtilities {

	/**
	 * Returns a {@link String} representing the array.
	 * 
	 * @param arg
	 * @return
	 */
	public static <T> String ArrayToString(T[] arg) {
		if (arg == null) {
			return null;
		}
		return arg.getClass().getSimpleName() + Arrays.toString(arg);
	}

	public static <T> String CollectionToString(Collection<T> arg) {
		if (arg == null) {
			return null;
		}
		return arg.getClass().getSimpleName() + Arrays.toString(arg.toArray());
	}

}
