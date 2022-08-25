/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class Customer {

    private int ni;
    private int time;

    public Customer(int items) {
        this.ni = items;
    }

    public int getNi() {
        return this.ni;
    }

    public int calculateTime() {
        return this.time = 45 + 5 * this.ni;
    }

    @Override
    public String toString() {
        return "[" + this.ni + "(" + this.time + " s)]";
    }

}
