import java.util.function.DoubleBinaryOperator;

/**
 * An enum that represents operations on given {@code double} values.
 * <p>
 * Note:
 * <p>Unlike methods and classes, lambdas lack names and documentation; if a
 * computation isn’t self-explanatory, or exceeds a few lines, we shouldn't put it in a
 * lambda. One line is ideal for a lambda, and three lines is a reasonable maximum.
 *
 * @author Spyros Dellas
 */
public enum OperationImproved {
    PLUS("+", Double::sum),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y);

    private final String symbol;
    private final DoubleBinaryOperator operator;

    /**
     * We’re using the DoubleBinaryOperator interface for the lambdas
     * that represent the enum constant’s behavior.
     * This is one of the many predefined functional interfaces in {@code java.util.function}
     *
     * @param symbol   a symbol representing the operation
     * @param operator the operator to apply to specified arguments
     */
    OperationImproved(String symbol, DoubleBinaryOperator operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double x, double y) {
        return operator.applyAsDouble(x, y);
    }
}
