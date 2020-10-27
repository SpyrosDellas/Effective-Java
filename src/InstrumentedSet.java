import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Decorator (wrapper) class demonstrating usage of composition in place of inheritance.
 *
 * <p> The design of the InstrumentedSet class is enabled by the existence of the
 * Set interface, which captures the functionality of the HashSet class.
 * Besides being robust, this design is extremely flexible. The InstrumentedSet class implements
 * the Set interface and has a single constructor whose argument is also of
 * type Set. In essence, the class transforms one Set into another, adding the instrumentation
 * functionality.
 * Unlike the inheritance-based approach, which works only for a single concrete class and requires
 * a separate constructor for each supported constructor in the superclass, the wrapper class
 * can be used to instrument any Set implementation and will work in conjunction with any
 * preexisting constructor.
 * NOTE:
 * <p>The implementation is broken into two pieces, the class itself and a reusable forwarding
 * class, which contains all of the forwarding methods and nothing else.
 *
 * @author Spyros Dellas
 */
public class InstrumentedSet<E> extends ForwardingSet<E> {

    private int addCount = 0;  // counts the elements added since the set was created

    public InstrumentedSet(Set<E> s) {
        super(s);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedSet<Plant> plants = new InstrumentedSet<>(new HashSet<>());
        plants.add(new Plant("Carrot", Plant.LifeCycle.BIENNIAL));
        System.out.println("plants: " + plants);
        System.out.println("addCount = " + plants.getAddCount());

        // The InstrumentedSet class can even be used to temporarily instrument a set
        //instance that has already been used without instrumentation
        Set<Phase> phases = new HashSet<>();
        phases.add(Phase.LIQUID);
        InstrumentedSet<Phase> iPhases = new InstrumentedSet<>(phases);
        iPhases.add(Phase.GAS);
        System.out.println("iPhases: " + iPhases);
        System.out.println("addCount = " + iPhases.getAddCount());
    }

}
