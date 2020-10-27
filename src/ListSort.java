import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparingInt;

/**
 * This class provides static methods to sort a list of strings in order of length.
 *
 * @author Spyros Dellas
 */
public class ListSort {

    /**
     * Sorts the given list of strings in order of length using an anonymous class to create the
     * {@code Comparator}.
     *
     * <p> As of Java 8, this is obsolete!
     *
     * @param strings the list of strings
     */
    public static void obsoleteSort(List<String> strings) {
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });
    }

    /**
     * Sorts the given list of strings in order of length using a lambda expression to create the
     * {@code Comparator}.
     * <p>
     * The types of the lambda (Comparator<String>), of its parameters
     * (s1 and s2, both String), and of its return value (int) are not present in the code.
     * The compiler deduces these types from context, using Java's type inference rules/
     *
     * @param strings the list of strings
     */
    public static void sortMethodA(List<String> strings) {
        Collections.sort(strings, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
    }

    /**
     * Sorts the given list of strings in order of length using a comparator construction method
     * in place of a lambda.
     *
     * @param strings the list of strings
     */
    public static void sortMethodB(List<String> strings) {
        // Note: Comparator.comparingInt accepts a function that extracts an int sort key from a type T,
        // and returns a Comparator<T> that compares by that sort key.
        Collections.sort(strings, comparingInt(String::length));
    }

    /**
     * Same as sortMethodB, but takes advantage of the sort method of the List interface.
     *
     * @param strings the list of strings
     */
    public static void sortMethodC(List<String> strings) {
        strings.sort(comparingInt(String::length));
    }

}
