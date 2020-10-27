import java.util.EnumSet;
import java.util.Set;

/**
 * This class demonstrates usage of {@code EnumSet}, which is a modern replacement for bit fields.
 *
 * @author Spyros Dellas
 */
public class Text {

    public enum Style {
        BOLD, ITALIC, UNDERLINE, STRIKETHROUGH
    }

    public static void applyStyles(Set<Style> styles) {
        for (Style style : styles) {
            System.out.println("Applying style: " + style);
        }
    }

    public static void main(String[] args) {
        /* Any Set could be passed in, but EnumSet is clearly best.
         * This is because internally, each EnumSet is represented as a bit vector
         *
         * In summary, just because an enumerated type will be used in sets, there is no reason
         * to represent it with bit fields.
         * The EnumSet class combines the conciseness and performance of bit fields with
         * all the many advantages of enum types.
         */
        applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC, Style.UNDERLINE, Style.STRIKETHROUGH));
    }
}
