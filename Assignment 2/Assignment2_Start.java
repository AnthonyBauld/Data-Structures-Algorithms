import java.util.Arrays;

/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class Assignment2_Start {
    /**
     * The swap method swaps the contents of two elements in an int array.
     *
     * @param array containing the two elements.
     * @param a the subscript of the first element.
     * @param b the subscript of the second element.
     */
    private static void swap(int[] array, int a, int b) {
        int temp;

        temp = array[a];
        array[a] = array[b];
        array[b] = temp;

    }

    /**
     * A recursive implementation of the Quick Sort Algorithm.
     *
     * @param array an unsorted array that will be sorted upon method completion
     * @return
     */
    public static int aSort(int array[], Timer timer) {
        //Return the result of the recursive count - finally ending with total count returned.
        return doASort(array, 0, array.length - 1, timer);
    }

    /**
     * The doASort method sorts each half of an array and its sub-arrays using a recursive algorithm. Although the
     * pivot can be chosen in a variety of ways, it is most typically used to partition each recursively partitioned
     * array at its respective midpoints (beginning with the array to be sorted).
     *
     * @param array the array to sort.
     * @param start the starting subscript of the list to sort
     * @param end the ending subscript of the list to sort
     */
    private static int doASort(int array[], int start, int end, Timer timer) {
        int pivotPoint;

        if (start < end) {
            // Get the pivot point.
            pivotPoint = part(array, start, end, timer);
            // Note - only one +/=
            // Sort the first sub list.
            doASort(array, start, pivotPoint - 1, timer);

            // Sort the second sub list.
            doASort(array, pivotPoint + 1, end, timer);
        }
        return timer.getCount();
    }

    /**
     * The partition method divides an array into two sub lists by selecting a pivot value. The left sub list will
     * contain all values less than the pivot, while the right sub list will contain all values greater than or equal
     * to the pivot.
     *
     * @param array the array to partition.
     * @param start the starting subscript of the area to partition.
     * @param end the ending subscript of the area to partition.
     * @return The subscript of the pivot value.
     */
    private static int part(int array[], int start, int end, Timer timer) {
        int pivotValue; // To hold the pivot value
        int endOfLeftList; // Last element in the left sub list.
        int mid; // To hold the mid-point subscript

        // see http://www.cs.cmu.edu/~fp/courses/15122-s11/lectures/08-qsort.pdf
        // for discussion of middle point
        // Find the subscript of the middle element.
        // This will be our pivot value.
        mid = (start + end) / 2;

        // Swap the middle element with the first element.
        // This moves the pivot value to the start of
        // the list.
        swap(array, start, mid);

        // Save the pivot value for comparisons.
        pivotValue = array[start];

        // For now, the end of the left sub list is
        // the first element.
        endOfLeftList = start;

        // Scan the entire list and move any values that
        // are less than the pivot value to the left
        // sub list.
        for (int scan = start + 1; scan <= end; scan++) {
            //Incrementing the static Timer object that has been passed around by reference.
            timer.increment();
            if (array[scan] < pivotValue) {
                endOfLeftList++;
                swap(array, endOfLeftList, scan);
            }
        }

        // Move the pivot value to end of the
        // left sub list.
        swap(array, start, endOfLeftList);

        // Return the subscript of the pivot value.
        return endOfLeftList;
    }

    /**
     * An implementation of the Selection Sort Algorithm.
     *
     * @param array an unsorted array that will be sorted upon method completion.
     * @return
     */

    public static int bSort(int[] array, Timer timer) {
        int startScan; // Starting position of the scan
        int index; // To hold a subscript value
        int minIndex; // Element with smallest value in the scan
        int minValue; // The smallest value found in the scan

        // The outer loop iterates once for each element in the
        // array. The startScan variable marks the position where
        // the scan should begin.
        for (startScan = 0; startScan < (array.length - 1); startScan++) {
            // Assume the first element in the scannable area
            // is the smallest value.
            minIndex = startScan;
            minValue = array[startScan];

            // Scan the array, starting at the 2nd element in
            // the scannable area. We are looking for the smallest
            // value in the scannable area.
            for (index = startScan + 1; index < array.length; index++) {
                timer.increment();
                if (array[index] < minValue) {
                    minValue = array[index];
                    minIndex = index;
                }
            }

            // Swap the element with the smallest value
            // with the first element in the scannable area.
            array[minIndex] = array[startScan];
            array[startScan] = minValue;
        }
        return timer.getCount();
    }

    /**
     * An implementation of the Insertion Sort Algorithm.
     *
     * @param array an unsorted array that will be sorted upon method completion.
     */
    public static int cSort(int[] array, Timer timer) {
        int unsortedValue; // The first unsorted value
        int scan; // Used to scan the array

        // The outer loop steps the index variable through
        // each subscript in the array, starting at 1. The portion of
        // the array containing element 0  by itself is already sorted.
        for (int index = 1; index < array.length; index++) {
            // The first element outside the sorted portion is
            // array[index]. Store the value of this element
            // in unsortedValue.
            unsortedValue = array[index];

            // Start scan at the subscript of the first element
            // outside the sorted part.
            scan = index;

            timer.increment();

            // Move the first element in the still unsorted part
            // into its proper position within the sorted part.
            while (scan > 0 && array[scan - 1] > unsortedValue) {
                array[scan] = array[scan - 1];
                scan--;
            }

            // Insert the unsorted value in its proper position
            // within the sorted subset.
            array[scan] = unsortedValue;
        }
        return timer.getCount();
    }


    /**
     * An implementation of the Merge Sort algorithm.
     *
     * @param array the unsorted elements will be sorted on completion.
     */
    public static int dSort(int array[], Timer timer) {
        int length = array.length;
        return doDSort(array, 0, length - 1, timer);
    }

    /**
     * Private recursive technique for splitting arrays till we reach 1 array size.
     *
     * @param array starting array.
     * @param lowerIndex lower bound of array to sort.
     * @param higherIndex upper bound of array to sort.
     */

    private static int doDSort(int[] array, int lowerIndex, int higherIndex, Timer timer) {
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doDSort(array, lowerIndex, middle, timer);
            // Below step sorts the right side of the array
            doDSort(array, middle + 1, higherIndex, timer);
            // Now put both parts together
            part1(array, lowerIndex, middle, higherIndex, timer);
        }
        return timer.getCount();
    }

    /**
     * Combines two smaller sorted arrays into a single larger sorted array.
     *
     * @param array provided in two sorted parts (1,4,9,2,3,11).
     * @param lowerIndex the location of the first index.
     * @param middle the middle element.
     * @param higherIndex the upper bound of the sorted elements.
     */
    private static void part1(int[] array, int lowerIndex, int middle, int higherIndex, Timer timer) {

        int[] mArray = new int[higherIndex - lowerIndex + 1];
        for (int i = lowerIndex; i <= higherIndex; i++) {
            mArray[i - lowerIndex] = array[i];
        }
        // Part A - from lowerIndex to middle
        // Part B - from middle + 1 to higherIndex
        int partAIndex = lowerIndex;
        int partBIndex = middle + 1;
        int m = lowerIndex;
        while (partAIndex <= middle && partBIndex <= higherIndex) {
            timer.increment();
            // Place items back into Array in order
            // Select the lowest of the two elements
            if (mArray[partAIndex - lowerIndex] <= mArray[partBIndex - lowerIndex]) {
                array[m] = mArray[partAIndex - lowerIndex];
                partAIndex++;
            } else {
                array[m] = mArray[partBIndex - lowerIndex];
                partBIndex++;
            }
            m++;
        }
        // Copy the remainder of PartA array (if required)
        while (partAIndex <= middle) {
            array[m] = mArray[partAIndex - lowerIndex];
            m++;
            partAIndex++;
        }
    }

    /**
     * The Bubble Sort method is used to sort the data.
     *
     * @param array is an unsorted array that will be sorted after the procedure is finished.
     */
    public static int eSort(int[] array, Timer timer) {
        int lastPos; // Position of last element to compare
        int index; // Index of an element to compare

        // The outer loop positions lastPos at the last element
        // to compare during each pass through the array. Initially
        // lastPos is the index of the last element in the array.
        // During each iteration, it is decreased by one.
        for (lastPos = array.length - 1; lastPos >= 0; lastPos--) {
            // The inner loop steps through the array, comparing
            // each element with its neighbor. All the elements
            // from index 0 through lastPos are involved in the
            // comparison. If two elements are out of order, they
            // are swapped.
            for (index = 0; index <= lastPos - 1; index++) {
                timer.increment();
                // Compare an element with its neighbor.
                if (array[index] > array[index + 1]) {
                    // Swap the two elements.

                    swap(array, index, index + 1);
                }
            }
        }
        return timer.getCount();
    }

    /**
     * Print an array to the Console.
     *
     * @param A array to be printed.
     */
    public static void printArray(int[] A) {
        for (int i = 0; i < A.length; i++) {
            System.out.printf("%5d ", A[i]);
        }
        System.out.println();
    }

    /**
     * This method is used to perform a time complexity, runtime, and basic step analysis. For each of the sorting
     * algorithms defined in this file, these results are calculated and reported to the terminal. The analysis is
     * carried out on an array of random integers, with the size of the array being specified as a parameter.
     * The Arrays.sort() method's runtime is also displayed to the console.
     *
     * @param size of the random integers array to be checked.
     */
    public static void conductAnalysis(int size) {
        Timer timer = new Timer();
        int[] A = new int[size];
        int[] B;

        // Create random array with elements in the range of 0 to SIZE - 1;
        for (int i = 0; i < size; i++) {
            A[i] = (int)(Math.random() * size);
        }

        System.out.printf("\nCOMPARISONS FOR ARRAY SIZE OF %d:\n" +
                "-------------------------------------------------\n", size);
        // Make sure you do this before each call to a sort method
        B = Arrays.copyOf(A, A.length);
        long startTime = System.nanoTime();
        int sortaCompares = aSort(B, timer);
        long elapsedTime = System.nanoTime() - startTime;

        // Printing results for aSort() analysis.
        System.out.printf("Number of compares for aSort     = %10d\n", sortaCompares);
        System.out.printf("Time required for aSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step for aSort             =     %6.1f ns\n" +
                "-------------------------------------------------\n", (double) elapsedTime / sortaCompares);
        // Reset the comparison timer for the next algorithm.
        timer.reset();
        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        int sortbCompares = bSort(B, timer);
        elapsedTime = System.nanoTime() - startTime;

        // Printing results for bSort() analysis.
        System.out.printf("Number of compares for bSort     = %10d\n", sortbCompares);
        System.out.printf("Time required for bSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step for bSort             =     %6.1f ns\n" +
                "-------------------------------------------------\n", (double) elapsedTime / sortbCompares);
        // Reset the comparison timer for the next algorithm.
        timer.reset();
        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        int sortcCompares = cSort(B, timer);
        elapsedTime = System.nanoTime() - startTime;

        // Printing results for cSort() analysis.
        System.out.printf("Number of compares for cSort     = %10d\n", sortcCompares);
        System.out.printf("Time required for cSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step for cSort             =     %6.1f ns\n" +
                "-------------------------------------------------\n", (double) elapsedTime / sortcCompares);
        // Reset the comparison timer for the next algorithm.
        timer.reset();
        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        int sortdCompares = dSort(B, timer);
        elapsedTime = System.nanoTime() - startTime;

        // Printing results for dSort() analysis.
        System.out.printf("Number of compares for dSort     = %10d\n", sortdCompares);
        System.out.printf("Time required for dSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step for dSort             =     %6.1f ns\n" +
                "-------------------------------------------------\n", (double) elapsedTime / sortdCompares);
        // Reset the comparison timer for the next algorithm.
        timer.reset();
        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        int sorteCompares = eSort(B, timer);
        elapsedTime = System.nanoTime() - startTime;

        // Printing results for eSort() analysis.
        System.out.printf("Number of compares for eSort     = %10d\n", sorteCompares);
        System.out.printf("Time required for eSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step for eSort             =     %6.1f ns\n" +
                "-------------------------------------------------\n", (double) elapsedTime / sorteCompares);

        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        Arrays.sort(B);
        elapsedTime = System.nanoTime() - startTime;

        // Printing results for Java's Arrays.sort() analysis.
        System.out.printf("Time required for Arrays.sort()  = %10d ns\n" +
                "=================================================\n", elapsedTime);
    }

    /**
     * This method is used to conduct an average analysis for time complexity, runtime, and basic step. These results are
     * calculated and printed to the console for each of the sorting algorithms defined in this file. The analysis is
     * performed on an array of random integers with array size passed in as a parameter. The random array is sorted
     * iteratively and an average for each of the time complexities, runtimes, and basic steps is computed per sorting
     * algorithm defined in this file. Additionally, average runtime of the Arrays.sort() method is printed to console.
     *
     * @param size       size of the array of random integers to be tested
     * @param iterations number of iterations for computing average results
     */
    public static void conductAverageTimeAnalysis(int size, int iterations) {
        // Cumulative total time elapsed over iterations of each sorting algorithm.
        long aTotalTime = 0, bTotalTime = 0, cTotalTime = 0, dTotalTime = 0, eTotalTime = 0, sTotalTime = 0;
        // Cumulative basic step time over iterations of each sorting algorithm.
        double aTotalBasic = 0.0, bTotalBasic = 0.0, cTotalBasic = 0.0, dTotalBasic = 0.0, eTotalBasic = 0.0;
        // Cumulative comparison count over iterations of each sorting algorithm.
        int aTotalComp = 0, bTotalComp = 0, cTotalComp = 0, dTotalComp = 0, eTotalComp = 0;
        // Timer is needed since methods have been re-written to pass a timer object.
        Timer timer = new Timer();
        // Array to be initialized with random data.
        int[] A = new int[size];
        // A temporary array to store copies of A such that A does not change.
        int[] B;

        // Create random array with elements in the range of 0 to SIZE - 1;
        for (int i = 0; i < size; i++) {
            A[i] = (int)(Math.random() * size);
        }

        System.out.printf("\nAVERAGE TIME ANALYSIS FOR AN ARRAY SIZE OF %d AND %d ITERATIONS:\n" +
                "--------------------------------------------------------\n", size, iterations);
        for (int i = 0; i < iterations; i++) {
            // Each 'B' is a copy of random array to be sorted so every iteration and sorting algorithm has same initial data.
            B = Arrays.copyOf(A, A.length);
            // Start time gets re-used for each method being timed
            long startTime = System.nanoTime();
            // Executes the aSort algorithm with a timer for basic step.
            int sortaCompares = aSort(B, timer);
            // Elapsed time gets re-used for each method being timed.
            long elapsedTime = System.nanoTime() - startTime;
            // Increment the total time elapsed for iterations of aSort.
            aTotalTime += elapsedTime;
            // Increment the total number of comparisons for iterations of aSort.
            aTotalComp += sortaCompares;
            // Increment total basic comparison time for aSort.
            aTotalBasic += (double) elapsedTime / sortaCompares;
            // Reset the comparison timer for the next algorithm.
            timer.reset();
            B = Arrays.copyOf(A, A.length);
            startTime = System.nanoTime();
            // Executes the bSort algorithm with a timer for basic step.
            int sortbCompares = bSort(B, timer);
            elapsedTime = System.nanoTime() - startTime;
            // Increment the total time elapsed for iterations of bSort.
            bTotalTime += elapsedTime;
            // Increment the total number of comparisons for iterations of bSort.
            bTotalComp += sortbCompares;
            // Increment total basic comparison time for bSort.
            bTotalBasic += (double) elapsedTime / sortbCompares;
            // Reset the comparison timer for the next algorithm.
            timer.reset();
            B = Arrays.copyOf(A, A.length);
            startTime = System.nanoTime();
            // Executes the cSort algorithm with a timer for basic step.
            int sortcCompares = cSort(B, timer);
            elapsedTime = System.nanoTime() - startTime;
            // Increment the total time elapsed for iterations of cSort.
            cTotalTime += elapsedTime;
            //  Increment the total number of comparisons for iterations of cSort.
            cTotalComp += sortcCompares;
            // Increment total basic comparison time for cSort.
            cTotalBasic += (double) elapsedTime / sortcCompares;
            // Reset the comparison timer for the next algorithm.
            // Reset the comparison timer for the next algorithm.
            timer.reset();
            B = Arrays.copyOf(A, A.length);
            startTime = System.nanoTime();
            // Executes the dSort algorithm with a timer for basic step.
            int sortdCompares = dSort(B, timer);
            elapsedTime = System.nanoTime() - startTime;
            // Increment the total time elapsed for iterations of dSort.
            dTotalTime += elapsedTime;
            // Increment the total number of comparisons for iterations of dSort.
            dTotalComp += sortdCompares;
            // Increment total basic comparison time for dSort.
            dTotalBasic += (double) elapsedTime / sortdCompares;
            // Reset the comparison timer for the next algorithm.
            timer.reset();
            B = Arrays.copyOf(A, A.length);
            startTime = System.nanoTime();
            // Executes the eSort algorithm with a timer for basic step.
            int sorteCompares = eSort(B, timer);
            elapsedTime = System.nanoTime() - startTime;
            // Increment the total time elapsed for iterations of eSort.
            eTotalTime += elapsedTime;
            // Increment the total number of comparisons for iterations of eSort.
            eTotalComp += sorteCompares;
            // Increment total basic comparison time for eSort.
            eTotalBasic += (double) elapsedTime / sorteCompares;

            B = Arrays.copyOf(A, A.length);
            startTime = System.nanoTime();
            // Executes Java's Arrays.sort() algorithm without a timer for basic step.
            Arrays.sort(B);
            elapsedTime = System.nanoTime() - startTime;
            // Increment the total time elapsed for iterations of Java's Arrays.sort().
            sTotalTime += elapsedTime;
        }

        //Printing results for aSort() average time analysis.
        System.out.printf("Average runtime for aSort [Quick]        = %10.0f ns\n" +
                        "Average number of compares for aSort     = %10.0f\n" +
                        "Average time for aSort basic step        =     %6.1f ns\n" +
                        "--------------------------------------------------------\n",
                (double) aTotalTime / iterations, (double) aTotalComp / iterations, aTotalBasic / iterations);

        //Printing results for bSort() average time analysis.
        System.out.printf("Average runtime for bSort [Selection]    = %10.0f ns\n" +
                        "Average number of compares for bSort     = %10.0f\n" +
                        "Average time for bSort basic step        =     %6.1f ns\n" +
                        "--------------------------------------------------------\n",
                (double) bTotalTime / iterations, (double) bTotalComp / iterations, bTotalBasic / iterations);

        //Printing results for cSort() average time analysis.
        System.out.printf("Average runtime for cSort [Insertion]    = %10.0f ns\n" +
                        "Average number of compares for cSort     = %10.0f\n" +
                        "Average time for cSort basic step        =     %6.1f ns\n" +
                        "--------------------------------------------------------\n",
                (double) cTotalTime / iterations, (double) cTotalComp / iterations, cTotalBasic / iterations);

        //Printing results for dSort() average time analysis.
        System.out.printf("Average runtime for dSort [Merge]        = %10.0f ns\n" +
                        "Average number of compares for dSort     = %10.0f\n" +
                        "Average time for dSort basic step        =     %6.1f ns\n" +
                        "--------------------------------------------------------\n",
                (double) dTotalTime / iterations, (double) dTotalComp / iterations, dTotalBasic / iterations);

        //Printing results for eSort() average time analysis.
        System.out.printf("Average runtime for eSort [Bubble]       = %10.0f ns\n" +
                        "Average number of compares for eSort     = %10.0f\n" +
                        "Average time for eSort basic step        =     %6.1f ns\n" +
                        "--------------------------------------------------------\n",
                (double) eTotalTime / iterations, (double) eTotalComp / iterations, eTotalBasic / iterations);

        //Printing results for Arrays.sort() average time analysis.
        System.out.printf("Average runtime for Arrays.sort()        = %10.0f ns\n" +
                "========================================================\n", (double) sTotalTime / iterations);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.printf("Assignment#2 Sorting and Performance Analysis\n");
        System.out.printf("Completed by Anthony. Bauld (000754334)\n");

        //Conducting an analysis on random arrays of integers with size being 30, 300, and 30000 that prints to console.
        conductAnalysis(30);
        conductAnalysis(300);
        conductAnalysis(30000);

        //Conducting an average analysis with 40 iterations on random arrays of integers with size being 30 and 30000.
        conductAverageTimeAnalysis(30, 40);
        conductAverageTimeAnalysis(30000, 40);
    }
}
