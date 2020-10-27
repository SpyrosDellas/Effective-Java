import java.util.HashMap;
import java.util.Map;

/**
 * A typesafe heterogeneous container.
 * <p>
 * Demonstrates use of generics to parameterize the keys instead of the container.
 * A Favorites instance is typesafe: it will never return an Integer when you ask it for a String.
 * It is also heterogeneous: unlike an ordinary map, all the keys are of different types.
 * Therefore, we call Favorites a typesafe heterogeneous container.
 * <p>
 * There are two limitations to the Favorites class that are worth noting:
 * <p>
 * First, a malicious client could easily corrupt the type safety of a Favorites instance, by
 * using a Class object in its raw form. But the resulting client code would generate
 * an unchecked warning when it was compiled. This is no different from a normal
 * collection implementation such as HashSet and HashMap. You can easily put a
 * String into a HashSet<Integer> by using the raw type HashSet
 * <p>
 * The second limitation of the Favorites class is that it cannot be used on a
 * non-reifiable type. In other words, we can store a {@code String} or {@code String[]},
 * but not a {@code List<String>}.
 * The reason is that we can’t get a Class object for {@code List<String>}. The class literal
 * {@code List<String>.class} is a syntax error, and it’s a good thing, too.
 * {@code List<String>} and {@code List<Integer>} share a single Class object, which is {@code List.class}.
 * It would wreak havoc with the internals of a Favorites object if the “type literals”
 * {@code List<String>.class} and {@code List<Integer>.class} were legal and returned the same object reference.
 * There is no entirely satisfactory workaround for this limitation.
 *
 * @author Spyros Dellas
 */
public class Favorites {

    private Map<Class<?>, Object> favorites = new HashMap<>();

    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(type, instance);
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }

    /**
     * Test client.
     */
    public static void main(String[] args) {
        Favorites f = new Favorites();

        f.putFavorite(String.class, "Java");
        f.putFavorite(Integer.class, 0xcafebabe);
        f.putFavorite(Class.class, Favorites.class);

        String favoriteString = f.getFavorite(String.class);
        int favoriteInteger = f.getFavorite(Integer.class);
        Class<?> favoriteClass = f.getFavorite(Class.class);

        System.out.printf("%s %n%x %n%s %n", favoriteString, favoriteInteger, favoriteClass.getName());
    }
}
