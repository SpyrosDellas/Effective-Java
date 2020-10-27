/**
 * This class is an example of a non-hierarchical type framework.
 *
 * <p> The {@code SingerSongwriter} interface extends both Singer and Songwriter
 * and adds a new method that is appropriate to the combination.
 *
 * @author Spyros Dellas
 */
public class Musician implements SingerSongwriter {

    private final String name;

    public Musician(String name) {
        this.name = name;
    }

    @Override
    public void compose(String songType) {
        // Compose a new song
    }

    @Override
    public void singNewSong() {
        // Compose and sing a random song
    }

    @Override
    public void sing(String song) {
        // Sing a song
    }

    public static void main(String[] args) {
        Musician x = new Musician("ANOther");
        x.compose("Pop");
        x.sing("Californication");
        x.singNewSong();
    }
}
