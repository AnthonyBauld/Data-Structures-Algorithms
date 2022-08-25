import java.util.ArrayList;

/**
 * I Anthony Bauld, 000754334 certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 * @author Anthony Bauld - 000754334
 */
public class Ring implements Index {
    private final ArrayList < Integer > positions = new ArrayList < > ();

    public Ring() {}

    @Override
    public void addPosition(int position) {
        positions.add(position);
    }

    @Override
    public ArrayList < Integer > getPositions() {
        return positions;
    }
}
