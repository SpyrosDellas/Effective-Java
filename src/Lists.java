import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

/**
 * An example class containing static a factory method that returns
 * a fully functional {@code List} implementation atop {@code AbstractList}.
 * <p></p>
 * The design emulates the {@code Collections} class.
 *
 * @author Spyros Dellas
 */
public class Lists {

    /**
     * Do not instantiate.
     */
    private Lists() {
    }

    /**
     * Background:
     * The {@code Collections} framework provides a skeletal implementation to go along with each
     * main collection interface: AbstractCollection, AbstractSet, AbstractList,
     * and AbstractMap.
     * <p>
     * When properly designed, skeletal implementations (whether a separate abstract class, or
     * consisting solely of default methods on an interface) can make it very easy for
     * programmers to provide their own implementations of an interface.
     * This static factory method demonstrates this fact.
     * <p>
     * When we consider all that a List implementation does, this example
     * is an impressive demonstration of the power of skeletal implementations.
     * Incidentally, this example is an Adapter that allows an int array to be viewed
     * as a list of Integer instances. Because of all the translation back and forth
     * between int values and Integer instances (boxing and unboxing), its performance
     * is not terribly good.
     *
     * @param a the specified array
     * @return a fixed-size modifiable list containing the elements in the specified array
     * @throws NullPointerException if a is null
     */
    public static List<Integer> intArrayAsList(int[] a) {
        Objects.requireNonNull(a);

        /* The implementation takes the form of an anonymous class that extends AbstractList.
         *
         * There are many limitations on the applicability of anonymous classes:
         *
         * 1. We can’t instantiate them except at the point they’re declared
         * 2. We can’t perform instanceof tests or do anything else that requires
         *    naming the class
         * 3. We can’t declare an anonymous class to implement multiple interfaces or to extend a
         *    class and implement an interface at the same time
         * 4. Clients of an anonymous class can’t invoke any members except those it inherits from its supertype
         */
        return new AbstractList<>() {
            @Override
            public Integer get(int index) {
                return a[index];
            }

            @Override
            public Integer set(int index, Integer value) {
                Integer previousValue = a[index];
                a[index] = value;
                return previousValue;
            }

            @Override
            public int size() {
                return a.length;
            }
        };
    }

}
