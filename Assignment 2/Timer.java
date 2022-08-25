/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class Timer {
    int count;

    public Timer() {
        count = 0;
    }

    public void reset() {
        count = 0;
    }

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
