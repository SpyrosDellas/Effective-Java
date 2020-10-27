import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.*;

/**
 * This class demonstrates usage of streams.
 * <p>
 * REMINDER
 * <p>
 * Streams isn’t just an API, it’s a paradigm based on functional programming. In order to obtain the
 * expressiveness, speed, and in some cases parallelizability that streams have to
 * offer, we have to adopt the paradigm as well as the API.
 * The most important part of the streams paradigm is to structure our computation
 * as a sequence of transformations where the result of each stage is as close as
 * possible to a pure function of the result of the previous stage.
 * A pure function is one whose result depends only on its input: it does not depend on any mutable
 * state, nor does it update any state. In order to achieve this, any function objects
 * that we pass into stream operations, both intermediate and terminal, should be
 * free of side-effects.
 * <p>
 * Proper use of streams in enabled by collectors.
 *
 * @author Spyros Dellas
 */
public class StreamDemo {

    /**
     * Initializes a frequency table.
     *
     * @return a map with the words as keys and their frequencies as values
     */
    public static Map<String, Long> frequencies(String fileName) throws IOException {
        Map<String, Long> freq;
        try (Stream<String> words = new Scanner(Path.of(fileName)).useDelimiter(",").tokens()) {
            freq = words.collect(groupingBy(String::toLowerCase, counting()));
        }
        return freq;
    }

    /**
     * Returns the top-ten most frequent words from the given frequency table.
     *
     * @param frequencies the frequency table
     * @return a list containing the ten most frequent words in the given table
     */
    public static List<String> topTen(Map<String, Long> frequencies) {
        /*
         * Note that we haven’t qualified the toList method with its class, Collectors.
         * It is customary and wise to statically import all members of Collectors because
         * it makes stream pipelines more readable
         */
        return frequencies.keySet().stream()
                .sorted(comparingLong(frequencies::get).reversed())
                .limit(10)
                .collect(toList());
    }

    /**
     * Make a map from the string form of the specified enum to the enum itself.
     *
     * @return the map
     */
    public static <V extends Enum<V>> Map<String, V> stringToEnum(Class<V> op) {
        /*
         * Using a toMap collector to make a map from string to enum. This simple form of toMap is
         * perfect if each element in the stream maps to a unique key
         */
        return Stream.of(op.getEnumConstants())
                .collect(toMap(Enum::toString, e -> e));
    }

    /**
     * Used in topHits(), see below
     */
    private static final class Artist {
        private final String name;

        public Artist(String name) {
            this.name = name;
        }
    }

    /**
     * Used in topHits(), see below
     */
    private static final class Album {
        private final Artist artist;
        private final long sales;

        public Album(Artist artist, long sales) {
            this.artist = artist;
            this.sales = sales;
        }

        public Artist artist() {
            return artist;
        }

        public long sales() {
            return sales;
        }
    }

    /**
     * Creates a map from artist to his/her best-selling album.
     *
     * @param albums a stream of albums by various artists
     * @return the map from artist to his/her best-selling album
     */
    public static Map<Artist, Album> topHits(Stream<Album> albums) {
        return albums.collect(toMap(Album::artist, a -> a, BinaryOperator.maxBy(comparingLong(Album::sales))));
    }

    /**
     * Demonstrates use of joining collector.
     *
     * @param words a stream of words
     */
    public static String join(Stream<String> words) {
        return words.collect(joining(", "));
    }

    /*
     * Unit tests
     */
    public static void main(String[] args) {
        System.out.println(stringToEnum(Operation.class));
    }

}
