import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * This class demonstrates usage of streams instead of a nested for-loop.
 *
 * @author Spyros Dellas
 */
public class Card {

    enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
        NINE, TEN, JACK, QUEEN, KING
    }

    enum Suit {
        DIAMONDS, SPADES, HEARTS, CLUBS
    }

    private final Rank rank;
    private final Suit suit;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "(" + suit + ", " + rank + ")";
    }

    /**
     * Generates a deck of 52 cards as a {@code List<Card>}.
     *
     * @return the deck of cards
     */
    public static List<Card> newDeckIterative() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
        return deck;
    }

    /**
     * Generates a deck of 52 cards as a {@code List<Card>} using streams.
     * <p>
     * Note:
     * In this case there is no clear winner; both the iterative and
     * the stream-based versions are equally clear and concise.
     *
     * @return the deck of cards
     */
    public static List<Card> newDeckStreamBased() {
        return Stream.of(Suit.values())
                .flatMap(suit -> Stream.of(Rank.values()).map(rank -> new Card(suit, rank)))
                .collect(toList());
    }

    public static void main(String[] args) {
        System.out.println(newDeckIterative());
        System.out.println(newDeckStreamBased());
    }
}
