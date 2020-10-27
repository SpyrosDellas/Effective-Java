import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * Demo class that demonstrates usage of {@code ENumMap} combined with streams.
 *
 * @author Spyros Dellas
 */
public enum Phase {
    SOLID,
    LIQUID,
    GAS;

    public enum Transition {
        MELT(SOLID, LIQUID),
        FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS),
        CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS),
        DEPOSIT(GAS, SOLID);

        private final Phase from;
        private final Phase to;

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        /*
         * Initializes a phase transition map.
         *
         * The code to initialize the phase transition map is a bit complicated. The type
         * of the map is Map<Phase, Map<Phase, Transition>>, which means “map from (source) phase to
         * map from (destination) phase to transition.”
         * This map-of-maps is initialized using a cascaded sequence of two collectors. The first collector groups
         * the transitions by source phase, and the second creates an EnumMap with mappings from destination phase to
         * transition. The merge function in the second collector ((x, y) -> y)) is unused; it is required only
         * because we need to specify a map factory in order to get an EnumMap, and Collectors provides telescoping
         * factories.
         *
         * PERFORMANCE:
         * Internally, the map of maps is implemented with an array of arrays, so we pay little in space or
         * time cost for the added clarity, safety, and ease of maintenance.
         */
        private static final Map<Phase, Map<Phase, Transition>> m =
                Stream.of(Transition.values()).collect(groupingBy(t -> t.from, () -> new EnumMap<>(Phase.class),
                        toMap(t -> t.to, t -> t, (x, y) -> y, () -> new EnumMap<>(Phase.class))));

        public static Transition from(Phase from, Phase to) {
            return m.get(from).get(to);
        }
    }

}
