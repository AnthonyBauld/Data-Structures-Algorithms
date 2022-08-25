package proj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * I Anthony Bauld, 000754334 certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 * @author Anthony Bauld - 000754334
 */
public class elevation {
    int[][] data_array = {
            {}
    };
    int[] min_val = {
            0,
            0
    };
    int[][] maxima;
    int[][] freq;
    int[][] max_dist;

    // constructor reads data and stores it into data_arr
    public elevation() {
        try {
            File myObj = new File("files/ELEVATIONS.TXT");
            Scanner myReader = new Scanner(myObj);
            int line_count = 1;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (line_count >= 2) {
                    int[] row = {};
                    String[] row_str = data.split(" ");
                    for (String s: row_str) {
                        row = Arrays.copyOf(row, row.length + 1);
                        row[row.length - 1] = Integer.parseInt(s);
                    }
                    this.data_array = Arrays.copyOf(this.data_array, this.data_array.length + 1);
                    this.data_array[this.data_array.length - 1] = row;
                }
                line_count += 1;
            }
            myReader.close();
            if (this.data_array.length - 1 >= 0)
                System.arraycopy(this.data_array, 1, this.data_array, 0, this.data_array.length - 1);
            this.data_array = Arrays.copyOf(this.data_array, this.data_array.length - 1);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // find the lowest value in data
    public void find_lowest() {
        int min_val = 200000;
        int occurred = 0;
        for (int[] ints: this.data_array) {
            for (int anInt: ints) {
                if (anInt < min_val) {
                    min_val = anInt;
                    occurred = 1;
                } else {
                    if (anInt == min_val) {
                        occurred += 1;
                    }
                }
            }
        }
        this.min_val[0] = min_val;
        this.min_val[1] = occurred;
    }

    // find local maxima in a window of 13
    public void local_maxima() {
        int max_val = 0;
        int max_row = -1;
        int max_col = -1;
        int[][] maxima_arr = {};
        int row_start = 0;
        int col_start = 0;
        // so long as we are not out of range on rows
        while (row_start < this.data_array.length - 13) {
            while (col_start < this.data_array[0].length - 13) {
                for (int i = row_start; i < row_start + 13; i++) {
                    // from the starting column to the one 13 ahead
                    for (int j = col_start; j < col_start + 13; j++) {
                        // if max_val falls short of the current value, replace it
                        if (max_val < this.data_array[i][j] && this.data_array[i][j] > 111300) {
                            max_val = this.data_array[i][j];
                            max_row = i;
                            max_col = j;
                        }
                    }
                }
                // if after searching through the window of 13, the max_vla is greater than 0
                // add to the array
                if (max_val != 0) {
                    // System.out.println("if");
                    int[] temp = {
                            max_val,
                            max_row,
                            max_col
                    };

                    // if(maxima_arr.length > 0 && temp == maxima_arr[maxima_arr.length-1]){continue;}
                    maxima_arr = Arrays.copyOf(maxima_arr, maxima_arr.length + 1);
                    maxima_arr[maxima_arr.length - 1] = temp;
                    col_start = max_col + 13;
                    max_val = 0;
                } else {
                    // System.out.println("Else");
                    col_start = col_start + 13;
                }
                // start at col of current max + 13, to keep away from its exclusion are
                // System.out.println(col_start+" "+String.valueOf(col_start+13));
            }
            // start at max_row + 13 to keep away from its exclusion area
            row_start = max_row + 13;
            col_start = 0;
            // System.out.println("one row");
        }
        this.maxima = maxima_arr;
    }

    // calculate and display distances between each local maxima
    public void distance() {
        // System.out.println("Distance between local maxima:\n");
        int min_distance = 338;
        int[] min_dist_a = {};
        int[] min_dist_b = {};
        int distance;
        int x_dist;
        int y_dist;
        for (int i = 0; i < this.maxima.length; i++) {
            for (int j = i + 1; j < this.maxima.length; j++) {
                x_dist = Math.abs(this.maxima[i][1] - this.maxima[j][1]);
                y_dist = Math.abs(this.maxima[i][2] - this.maxima[j][2]);
                distance = x_dist ^ 2 + y_dist ^ 2;
                if (distance < min_distance && distance > 13) {
                    min_distance = distance;
                    min_dist_a = this.maxima[i];
                    min_dist_b = this.maxima[j];
                }
            }
        }
        this.max_dist = new int[][] {
                {
                        min_distance
                }, min_dist_a, min_dist_b
        };
    }

    // get frequency of each data point
    public void most_freq() {
        // create array to store
        int[][] freq = {};
        // loop from row 0 to data_array.length -1
        for (int[] ints: this.data_array) {
            // loop from col 0 to data_array.length -1
            for (int j = 0; j < this.data_array[0].length; j++) {
                // if freq contains anything
                if (freq.length > 0) {
                    // set variable checked to 0
                    int check = 0;
                    // for all entries in freq
                    for (int al = 0; al < freq.length; al++) {
                        // if current data_array entry is the same current freq entry
                        if (ints[j] == freq[al][0]) {
                            // add to the freq count of the existing element in freq
                            freq[al][1] += 1;
                            // set checked to 1
                            check = 1;
                        }
                    }
                    // if check remains 0 i.e. no entry in freq matched current entry in data_array
                    if (check == 0) {
                        // create temp array
                        int[] temp = {
                                ints[j],
                                1
                        };
                        // extend the length of freq
                        freq = Arrays.copyOf(freq, freq.length + 1);
                        // add temp to the end of the freq array
                        freq[freq.length - 1] = temp;
                    }
                }
                // if there is no element in freq array
                else {
                    // create temp with current element of data_array
                    int[] temp = {
                            ints[j],
                            1
                    };
                    // extend freq array by 1
                    freq = Arrays.copyOf(freq, freq.length + 1);
                    // insert temp at last position og freq
                    freq[freq.length - 1] = temp;
                }
            }
        }
        this.freq = freq;
    }

    public void lowestPeak() {
        long startTime = System.nanoTime();
        System.out.println("The lowest peak is " + this.min_val[0] + ". It occurs " + this.min_val[1] + " times in the map.");
        System.out.printf("[Time = %d us]\n", (System.nanoTime() - startTime) / 1000);
    }

    public void numberOfPeaks() {
        long startTime = System.nanoTime();
        System.out.println("Total Number of peaks is " + this.maxima.length + ".");
        System.out.printf("[Time = %d us]\n", (System.nanoTime() - startTime) / 1000);
    }

    public void distancePeaks() {
        long startTime = System.nanoTime();
        System.out.println("Closest distance between two peaks = " + this.max_dist[0][0] + ". Between (" + this.max_dist[1][1] + ", " + this.max_dist[1][2] + ", " + this.max_dist[1][0] + ") and (" + this.max_dist[2][1] + ", " + this.max_dist[2][2] + ", " + this.max_dist[2][0] + ").");
        System.out.printf("[Time = %d us]\n", (System.nanoTime() - startTime) / 1000);
    }

    public void commonElevation() {
        long startTime = System.nanoTime();
        int max_freq = 0;
        int entry = 0;
        for (int[] ints: this.freq) {
            if (ints[1] > max_freq) {
                max_freq = ints[1];
                entry = ints[0];
            }
        }
        System.out.println("The most common elevation occurs " + max_freq + " times and is " + entry + ".");
        System.out.printf("[Time = %d us]\n", (System.nanoTime() - startTime) / 1000);
    }
}
