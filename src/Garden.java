import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

/**
 * Demo class that demonstrates usage of {@code ENumMap} combined with streams.
 *
 * @author Spyros Dellas
 */
public class Garden {

    private final Plant[] garden;

    public Garden() {
        garden = new Plant[5];
        garden[0] = new Plant("Daffodil", Plant.LifeCycle.ANNUAL);
        garden[1] = new Plant("Petunia", Plant.LifeCycle.ANNUAL);
        garden[2] = new Plant("Grass", Plant.LifeCycle.PERENNIAL);
        garden[3] = new Plant("Poppies", Plant.LifeCycle.ANNUAL);
        garden[4] = new Plant("Brussels Sprouts", Plant.LifeCycle.BIENNIAL);
    }

    /**
     * Demonstrates usage of EnumMap using for loops.
     *
     * @return the plants in the garden, categorized by their lifecycle
     */
    public Map<Plant.LifeCycle, Set<Plant>> listByLifeCycle() {
        Map<Plant.LifeCycle, Set<Plant>> byLifeCycle = new EnumMap<>(Plant.LifeCycle.class);
        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            byLifeCycle.put(lc, new HashSet<>());
        }
        for (Plant plant : garden) {
            byLifeCycle.get(plant.lifeCycle).add(plant);
        }
        return byLifeCycle;
    }

    /**
     * Demonstrates usage of EnumMap using a stream and a predefined Collector.
     *
     * @return the plants in the garden, categorized by their lifecycle
     */
    public Map<Plant.LifeCycle, Set<Plant>> listByLifeCycleStreamCollector() {
        return Arrays.stream(garden)
                .collect(groupingBy(p -> p.lifeCycle, () -> new EnumMap<>(Plant.LifeCycle.class), toCollection(HashSet::new)));
    }

    public static void main(String[] args) {
        Garden garden = new Garden();
        System.out.println("The plants in the garden categorized by LifeCycle (using for loops):\n" +
                garden.listByLifeCycle());
        System.out.println();
        System.out.println("The plants in the garden categorized by LifeCycle (using a stream):\n" +
                garden.listByLifeCycleStreamCollector());
    }

}
