import java.util.ArrayList;

/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class FileCharacter implements Index {
    private final String name;
    private int appearanceCount;
    private int closenessCount;
    private double closenessFactor;
    private ArrayList < Integer > positions = new ArrayList < > ();

    public FileCharacter(String name) {
        this.name = name;
        this.appearanceCount = 0;
        this.closenessCount = 0;
    }

    public String getName() {
        return this.name;
    }

    public void incrementClosenessCount() {
        closenessCount++;
        closenessFactor = (double) closenessCount / appearanceCount;
    }

    @Override
    public String toString() {
        return String.format("[%-10s, %d] is close to the Ring %d times with a closeness factor of %5.4f",
                name, appearanceCount, closenessCount, closenessFactor);
    }

    @Override
    public void addPosition(int position) {
        positions.add(position);
        appearanceCount++;
    }

    @Override
    public ArrayList < Integer > getPositions() {
        return positions;
    }
}
