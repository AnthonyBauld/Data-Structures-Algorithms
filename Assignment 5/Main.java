import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class Main {

    public static void main(String[] args) {

        int xLanes;
        int nLanes;
        // each customer's time spent in the express lane is recorded.
        int[] express;
        // In a regular lane, the time spent by each customer is recorded.
        int[] normal;

        LinkedQueue < Customer > [] expressLine;
        LinkedQueue < Customer > [] normalLine;

        final String filename = "CustomerData_Example.txt";
        final String file = "CustomerData.txt";

        // ============= Part A ==============

        List < Object > results = checkout(filename);
        normal = (int[]) results.get(2);
        express = (int[]) results.get(0);

        expressLine = (LinkedQueue < Customer > []) results.get(1);
        normalLine = (LinkedQueue < Customer > []) results.get(3);

        xLanes = ((int[]) results.get(4))[0];
        nLanes = ((int[]) results.get(4))[1];

        int maxTimeLane = normal[0];
        // Counts the total number of checkout lines for display.
        int totalCheckoutLanes = 1;

        //==== EXPRESS CHECKOUT LINES ====//
        for (int i = 0; i < xLanes; i++) {

            System.out.println("CheckOut(Express) # " + (totalCheckoutLanes) + " (Est Time = " + express[i] + " s) =" + expressLine[i]);
            totalCheckoutLanes++;

            if (express[i] > maxTimeLane) {
                maxTimeLane = express[i];
            }
        }

        //==== NORMAL CHECKOUT LINES ====//
        for (int i = 0; i < nLanes; i++) {
            System.out.println("CheckOut (Normal) # " + (totalCheckoutLanes) + " (Est Time = " + normal[i] + " s) =" + normalLine[i]);
            totalCheckoutLanes++;
            if (normal[i] > maxTimeLane) {
                maxTimeLane = normal[i];
            }
        }

        System.out.printf("Time to serve all customers = %d s\n\n", maxTimeLane);

        try {

            // ============= Part B ==============

            Scanner fin = new Scanner(new File(file));

            results = checkout(file);

            normal = (int[]) results.get(2);
            express = (int[]) results.get(0);

            expressLine = (LinkedQueue < Customer > []) results.get(1);
            normalLine = (LinkedQueue < Customer > []) results.get(3);

            xLanes = ((int[]) results.get(4))[0];
            nLanes = ((int[]) results.get(4))[1];
            maxTimeLane = normal[0];

            for (int i = 0; i < xLanes; i++) {
                if (express[i] > maxTimeLane)
                    maxTimeLane = express[i];
            }
            for (int i = 0; i < nLanes; i++)
                if (normal[i] > maxTimeLane)
                    maxTimeLane = normal[i];

            int SIMULATION_STEP = 30;

            System.out.print("t(s)");
            for (int i = 0; i < xLanes + nLanes; i++) {
                System.out.print("    Line " + (i + 1));
            }
            System.out.println("");

            for (int i = 0; i < expressLine.length; i++)
                express[i] = expressLine[i].peek().calculateTime();

            for (int i = 0; i < normalLine.length; i++)
                normal[i] = normalLine[i].peek().calculateTime();

            for (int i = 0; i <= maxTimeLane; i++) {
                for (int j = 0; j < express.length; j++) {
                    if (express[j] == 0) {
                        if (!expressLine[j].isEmpty())
                            expressLine[j].dequeue();
                        if (!expressLine[j].isEmpty())
                            express[j] = ((expressLine[j]).peek()).calculateTime();
                    }
                }

                for (int j = 0; j < normal.length; j++) {
                    if (normal[j] == 0) {
                        if (!normalLine[j].isEmpty())
                            normalLine[j].dequeue();
                        if (!normalLine[j].isEmpty())
                            normal[j] = ((normalLine[j]).peek()).calculateTime();
                    }
                }

                if ((i % SIMULATION_STEP == 0 && i > 0) || i == maxTimeLane) {
                    System.out.printf("%3d", i);
                    for (int j = 0; j < express.length; j++) {
                        System.out.printf("%10d", expressLine[j].size());
                    }
                    for (int j = 0; j < normal.length; j++)
                        System.out.printf("%10d", normalLine[j].size());
                    System.out.println("");
                    System.out.println("========================================================================================================");
                }

                for (int j = 0; j < express.length; j++) {
                    if (express[j] > 0)
                        express[j]--;
                }
                for (int j = 0; j < normal.length; j++)
                    if (normal[j] > 0)
                        normal[j]--;
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Error loading file. " + ex.getMessage());
        }
    }

    public static List < Object > checkout(String fileName) {

        int x;
        int xLanes = 0;
        int nLanes = 0;
        // Each customer's time spent in the express lane is recorded.
        int[] express = null;
        // In a regular lane, the time spent by each customer is recorded.
        int[] normal = null;

        LinkedQueue < Customer > [] expressLine = null;
        LinkedQueue < Customer > [] normalLine = null;

        try {
            Scanner fin = new Scanner(new File(fileName));

            xLanes = fin.nextInt();
            nLanes = fin.nextInt();

            express = new int[xLanes];
            normal = new int[nLanes];

            x = fin.nextInt();

            expressLine = new LinkedQueue[xLanes];
            normalLine = new LinkedQueue[nLanes];

            // To avoid getting a NullPointerException, initialise the arrays.
            for (int i = 0; i < xLanes; i++) {
                expressLine[i] = new LinkedQueue < > ();
                express[i] = 0;
            }

            for (int i = 0; i < nLanes; i++) {
                normalLine[i] = new LinkedQueue < > ();
                normal[i] = 0;
            }

            while (fin.hasNext()) {

                Customer c = new Customer(fin.nextInt());

                int fastestInExpress = -1;
                // The number of the express lane (e.g., Express Lane #3).
                int positionInExpress = -1;

                if (c.getNi() <= x) {

                    fastestInExpress = express[0];
                    positionInExpress = 0;

                    // The fastest express lane should be updated.
                    for (int i = 1; i < expressLine.length; i++) {
                        if (express[i] < fastestInExpress) {
                            fastestInExpress = express[i];
                            positionInExpress = i;
                        }
                    }
                }

                int fastestInNormal = normal[0];
                // The standard lane number (e.g., Standard Lane #2).
                int positionInNormal = 0;

                // The quickest regular lane should be updated.
                for (int i = 1; i < normalLine.length; i++) {
                    if (normal[i] < fastestInNormal) {
                        fastestInNormal = normal[i];
                        positionInNormal = i;
                    }
                }

                if (positionInExpress > -1 && c.getNi() <= x) {
                    if (fastestInNormal >= fastestInExpress) {
                        // If the time spent in the normal lane is greater than or equal to the time spent in the
                        // express lane, take the express lane.
                        expressLine[positionInExpress].enqueue(c);
                        express[positionInExpress] = express[positionInExpress] + c.calculateTime();
                    } else {
                        normalLine[positionInNormal].enqueue(c);
                        normal[positionInNormal] = normal[positionInNormal] + c.calculateTime();
                    }
                } else {
                    normalLine[positionInNormal].enqueue(c);
                    normal[positionInNormal] = normal[positionInNormal] + c.calculateTime();
                }
            }
        } catch (FileNotFoundException EX) {
            System.out.println("Error Loading File. " + EX.getMessage());
        }

        return Arrays.asList(express, expressLine, normal, normalLine, new int[] {
                xLanes,
                nLanes
        });
    }
}
