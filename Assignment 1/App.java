package proj;

/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class App {
    public static void main(String[] args) {
        elevation e = new elevation();
        e.find_lowest();
        e.local_maxima();
        e.distance();
        e.most_freq();
        e.lowestPeak();
        e.numberOfPeaks();
        e.distancePeaks();
        e.commonElevation();
    }
}
