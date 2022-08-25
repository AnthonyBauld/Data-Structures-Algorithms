import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
// Needed for Q4 - Given - No mods required.
class Video implements Comparable < Video > {

    private String title;
    private String category;
    private int popularity;

    public Video(String title, String category, int popularity) {
        this.title = title;
        this.category = category;
        this.popularity = popularity;
    }

    public Video(String title, String category) {
        this(title, category, 0);
    }
    public String getTitle() {
        return title;
    }
    public String getCategory() {
        return category;
    }
    public int getPopularity() {
        return popularity;
    }

    @Override
    public String toString() {
        return title + " (" + category + ") ( " + popularity + " %) ";
    }

    @Override
    public int compareTo(Video comparevid) {
        int comparepop = ((Video) comparevid).getPopularity();

        return comparepop - this.popularity;
    }

}

public class Main_Start {
    public static void main(String[] args) {
        final int Q1SIZE = 1000;
        Random rand = new Random(0xC0FFEE);
        int[][] data1000 = new int[Q1SIZE][Q1SIZE];
        for (int x = 0; x < Q1SIZE; x++)
            for (int y = 0; y < Q1SIZE; y++)
                data1000[x][y] = rand.nextInt(20) + 1;

        // Q2 - PART C - Add code here to test your method with the data1000 data set.
        Date startingTime = Calendar.getInstance().getTime();
        System.out.println(minimumCenters(data1000)[0]);
        Date now = Calendar.getInstance().getTime();
        long timeElapsed = now.getTime() - startingTime.getTime();
        System.out.println(timeElapsed);
        startingTime = Calendar.getInstance().getTime();
        System.out.println(minimumCenters(data1000)[1]);
        now = Calendar.getInstance().getTime();
        timeElapsed = now.getTime() - startingTime.getTime();
        System.out.println(timeElapsed);
    }

    private static int[] minimumCenters(int[][] array) {
        int count = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (
                        (i > 0 && j > 0 && i < array.length - 1 && j < array[0].length - 1 &&
                                array[i][j] < array[i - 1][j] && array[i][j] < array[i + 1][j + 1] &&
                                array[i][j] < array[i][j - 1] && array[i][j] < array[i - 1][j - 1] &&
                                array[i][j] < array[i + 1][j] && array[i][j] < array[i - 1][j + 1] &&
                                array[i][j] < array[i][j + 1] && array[i][j] < array[i + 1][j - 1]
                        )
                ) {
                    sum += array[i][j];
                    count++;
                }
            }
        }
        int[] result = {
                count,
                sum
        };
        return result;
    }

    // Given for Q1 - Provide answers in the Submission document
    public static int countMatch(int[] array, int value) {
        return countMatch(array, 0, array.length - 1, value);
    }

    private static int countMatch(int[] array, int low,
                                  int high, int value) {
        int middle;
        if (low > high)
            return 0; // RETURN1

        middle = (low + high) / 2;

        if (array[middle] == value) {
            int count = 1;
            for (int i = middle - 1; i >= 0 && array[i] == value; i--)
                count++;
            for (int i = middle + 1; i < array.length && array[i] == value; i++)
                count++;
            return count; //RETURN2
        } else if (value > array[middle])
            return countMatch(array, middle + 1, high, value);
        else
            return countMatch(array, low, middle - 1, value);
    }
}

class WeatherStation implements Comparable < WeatherStation > {
    private int id;
    private String date;
    private float minTemperature;
    private float maxTemperature;
    private float avgTemperature;

    public WeatherStation(int id, String date, float minTemperature, float maxTemperature) {
        this.id = id;
        this.date = date;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        avgTemperature = (minTemperature + maxTemperature) / 2;
    }

    public float getMinTemperature() {
        return minTemperature;
    }
    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }
    public float getMaxTemperature() {
        return maxTemperature;
    }
    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
    public float getAvgTemperature() {
        return avgTemperature;
    }
    public void setAvgTemperature(float avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    @Override
    public String toString() {
        return "WeatherStation{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", minTemperature=" + String.format("%.1f", minTemperature) +
                ", maxTemperature=" + String.format("%.1f", maxTemperature) +
                ", avgTemperature=" + String.format("%.1f", avgTemperature) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherStation that = (WeatherStation) o;
        return id == that.id && Float.compare(that.minTemperature, minTemperature) == 0 && Float.compare(that.maxTemperature, maxTemperature) == 0 && Float.compare(that.avgTemperature, avgTemperature) == 0 && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, minTemperature, maxTemperature, avgTemperature);
    }

    @Override
    public int compareTo(WeatherStation o) {
        return toString().compareTo(o.toString());
    }
}
