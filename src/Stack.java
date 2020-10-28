import java.util.*;
import java.util.stream.StreamSupport;

import static java.lang.Double.*;

/**
 * Implementation of a generic Stack based on a resizing array.
 * <p>
 * Demonstrates use of generics, including bounded wildcards.
 *
 * @param <E> the type of elements in this stack
 * @author Spyros Dellas
 */
public class Stack<E> {

    private static final int DEFAULT_ARRAY_SIZE = 4;
    private E[] stack;  // the resizing array
    private int size;   // the number of elements in the stack

    /**
     * Creates a new empty stack.
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        // this is a safe cast; only elements of type E will ever be stored in the array
        stack = (E[]) new Object[DEFAULT_ARRAY_SIZE];
        size = 0;
    }

    /**
     * Inserts the specified element into this stack.
     *
     * @param e the element to be pushed
     * @return this stack
     */
    public E push(E e) {
        if (size == stack.length)
            increaseSize();
        stack[size++] = e;
        return e;
    }

    /**
     * Doubles the size of stack[].
     */
    private void increaseSize() {
        stack = Arrays.copyOf(stack, 2 * stack.length);
    }

    /**
     * Inserts all the elements in the specified iterable into this stack.
     * <p>
     * Note:
     * A bounded wildcard type is used to allow elements of any subtype of E
     * to be inserted, i.e. the given iterable is a PRODUCER.
     * The mnemonic for bounded wildcards is PECS: Producer-Extends-Consumer-Super
     *
     * @param elements the elements to be pushed
     * @return this stack
     * @throws NullPointerException if the specified collection is null
     */
    public Stack<E> pushAll(Iterable<? extends E> elements) {
        Objects.requireNonNull(elements);
        for (E element : elements)
            push(element);
        return this;
    }

    /**
     * Inserts the maximum element of the specified iterable into this stack, using the specified comparator.
     * <p>
     * Note:
     * The mnemonic for bounded wildcards is PECS: Producer-Extends-Consumer-Super
     *
     * @param elements   the Iterable of elements
     * @param comparator the Comparator used to compare the elements in the Iterable
     * @return this stack
     * @throws NullPointerException   if the specified collection is null
     * @throws NoSuchElementException if the collection is empty
     */
    public <T extends E> Stack<E> pushMax(Iterable<T> elements, Comparator<? super T> comparator) {
        Objects.requireNonNull(elements);
        E max = StreamSupport.stream(elements.spliterator(), false).max(comparator).orElseThrow();
        push(max);
        return this;
    }

    /**
     * Deletes and returns a single element from this stack in LIFO order.
     *
     * @return the popped element
     * @throws EmptyStackException if this stack is empty
     */
    public E pop() {
        if (isEmpty())
            throw new EmptyStackException();
        E last = stack[--size];
        stack[size] = null;
        if (stack.length >= 8 && size <= stack.length / 4)
            reduceSize();
        return last;
    }

    /**
     * Pops each element off the stack in LIFO order and adds the elements to the given
     * collection.
     * <p>
     * Note:
     * A bounded wildcard type is used to allow elements of the stack to be added
     * to a collection that contains elements of any super type of E, i.e. the given
     * collection is a CONSUMER.
     * The mnemonic for bounded wildcards is PECS: Producer-Extends-Consumer-Super
     *
     * @throws NullPointerException if the specified collection is null
     */
    public void popAll(Collection<? super E> collection) {
        Objects.requireNonNull(collection);
        while (!isEmpty())
            collection.add(pop());
    }

    /**
     * Returns the last inserted element without deleting it.
     *
     * @return the last inserted element
     * @throws EmptyStackException if this stack is empty
     */
    public E peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return stack[size - 1];
    }

    /**
     * Halves the size of stack[].
     */
    private void reduceSize() {
        stack = Arrays.copyOf(stack, stack.length / 2);
    }

    /**
     * Checks if this stack is empty.
     *
     * @return true if this stack is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
     * Test client
     */
    public static void main(String[] args) {
        Stack<Number> stack = new Stack<>();
        Iterable<Integer> integerElements = Set.of(1, 2, 3, 4, 5);
        Iterable<Double> doubleElements = Set.of(NaN, NEGATIVE_INFINITY, MIN_VALUE, -0.0, MAX_VALUE);

        // tests push(), peek(), pop(), isEmpty() and pushAll()
        System.out.println("\nFIRST TEST");
        stack.pushAll(integerElements)
                .pushAll(doubleElements)
                .push(1L);
        System.out.println("Last inserted element: " + stack.peek());
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

        // tests popAll()
        System.out.println("\nSECOND TEST");
        Collection<Object> collection = new ArrayList<>();
        stack.pushAll(integerElements)
                .pushAll(doubleElements)
                .popAll(collection);
        System.out.println(collection);

        // tests both versions of pushMax()
        System.out.println("\nTHIRD TEST");
        stack.pushMax(doubleElements, Comparator.reverseOrder())
                .pushMax(integerElements, Integer::compareTo);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

}
