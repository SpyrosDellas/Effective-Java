/**
 * This class demonstrates the Builder pattern.
 *
 * <p> Instead of making the desired object directly, the client calls a constructor
 * (or static factory) with all of the required parameters and
 * gets a builder object.
 * Then the client calls setter-like methods on the builder object
 * to set each optional parameter of interest.
 * Finally, the client calls a parameterless build method to generate the object, which is typically
 * immutable.
 * The builder is typically a static member class of the class it builds.
 * <p>
 * In summary, the Builder pattern is a good choice when designing classes
 * whose constructors or static factories would have more than a handful of
 * parameters, especially if many of the parameters are optional or of identical type.
 * Client code is much easier to read and write with builders than with telescoping
 * constructors, and builders are much safer than JavaBeans.
 *
 * @author Spyros Dellas
 */
public class NutritionFacts {

    private final String name;
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    /**
     * The Builder’s setter methods return the Builder itself so that invocations
     * can be chained, resulting in a fluent API.
     */
    public static class Builder {

        // Required parameters
        private final String name;
        private final int servingSize;
        private final int servings;

        // Optional parameters - initialized to default values
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(String name, int servingSize, int servings) {
            this.name = name;
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(Builder builder) {
        // Validity checks omitted for brevity
        name = builder.name;
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public static void main(String[] args) {
        NutritionFacts beans = new Builder("Beans", 250, 1)
                .calories(250)
                .fat(7)
                .sodium(5)
                .carbohydrate(30)
                .build();
    }
}
