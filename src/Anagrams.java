import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * This class contains static methods that read the words from a dictionary file
 * and print all the anagram groups whose size meets a user-specified minimum.
 * <p>
 * Demonstrates usage of streams.
 *
 * @author Spyros Dellas
 */
public class Anagrams {

    /* Alphabetizes the given string by sorting it's characters.
     *
     * Since all words that are anagrams of each other share the same alphabetized form, we
     * can use if as a key in a map, where the  map value is a list containing all of
     * the words that share an alphabetized form
     *
     */
    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }

    /**
     * Prints all large anagram groups in a dictionary iteratively.
     *
     * @param fileName     the file containing the dictionary
     * @param minGroupSize the minimum anagram group size to print
     * @throws IOException if an IO error occurs
     */
    public static void anagramsIterative(String fileName, int minGroupSize) throws IOException {
        File dictionary = new File(fileName);
        Map<String, Set<String>> groups = new HashMap<>();
        try (Scanner s = new Scanner(dictionary)) {
            while (s.hasNext()) {
                String word = s.next();
                /*
                 * computeIfAbscent looks up a key in the map: If the key is present, the
                 * method simply returns the value associated with it. If not, the method computes a
                 * value by applying the given function object to the key, associates this value with
                 * the key, and returns the computed value. The computeIfAbsent method simplifies
                 * the implementation of maps that associate multiple values with each key
                 */
                groups.computeIfAbsent(alphabetize(word), (unused) -> new TreeSet<>()).add(word);
            }
        }
        // iterate through the map’s values() view and print each list whose size meets the threshold
        for (Set<String> group : groups.values())
            if (group.size() >= minGroupSize)
                System.out.println(group.size() + ": " + group);
    }

    /**
     * Prints all large anagram groups in a dictionary using a stream.
     * <p>
     * Demonstrates tasteful usage of streams that enhances clarity and conciseness.
     *
     * @param fileName     the file containing the dictionary
     * @param minGroupSize the minimum anagram group size to print
     * @throws IOException if an IO error occurs
     */
    public static void anagramsStream(String fileName, int minGroupSize) throws IOException {
        Map<String, Set<String>> groups = new HashMap<>();
        try (Stream<String> words = Files.lines(Path.of(fileName))) {
            /*
             * The pipeline on this stream has no intermediate operations; its terminal operation
             * collects all the words into a map that groups the words by their alphabetized form.
             * Then a new Stream<List<String>> is opened on the values() view of the map. The
             * elements in this stream are, of course, the anagram groups. The stream is filtered
             * so that all of the groups whose size is less than minGroupSize are ignored, and
             *finally, the remaining groups are printed by the terminal operation forEach.
             */
            words.collect(groupingBy(Anagrams::alphabetize))
                    .values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .forEach(group -> System.out.println(group.size() + ": " + group));
        }
    }

    /**
     * Prints all large anagram groups in a dictionary using streams.
     * <p>
     * Overuses streams and as a result the code becomes complicated and difficult to understand.
     *
     * @param fileName     the file containing the dictionary
     * @param minGroupSize the minimum anagram group size to print
     * @throws IOException if an IO error occurs
     */
    public static void anagramsStreamOveruse(String fileName, int minGroupSize) throws IOException {
        Map<String, Set<String>> groups = new HashMap<>();
        try (Stream<String> words = Files.lines(Path.of(fileName))) {
            words.collect(groupingBy(
                    word -> word.chars().sorted()
                            .collect(StringBuilder::new, (sb, c) -> sb.append((char) c), StringBuilder::append)
                            .toString()))
                    .values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .map(group -> group.size() + ": " + group)
                    .forEach(System.out::println);
        }
    }

}
