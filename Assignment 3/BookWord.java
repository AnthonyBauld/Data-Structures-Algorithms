/**
 * I Anthony Bauld, 000754334 certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 * @author Anthony Bauld - 000754334
 */
public class BookWord {

    private String text;
    private Integer count;

    public BookWord(String wordText) {
        this.count = 0;
        this.text = wordText;
    }

    public String getText() {
        return text;
    }

    public Integer getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }

    @Override
    public boolean equals(Object wordToCompare) {
        if (wordToCompare != null && this.getClass() == wordToCompare.getClass()) {
            BookWord bookWord = (BookWord) wordToCompare;
            return bookWord.getText().equals(text);
        }
        return false;
    }

    /**
     * Taken from https://www.geeksforgeeks.org/string-hashing-using-polynomial-rolling-hash-function/
     * @return
     */
    @Override
    public int hashCode() {
        int p = 23;
        int m = (int) Math.pow(10, 9) + 9;
        long power_of_p = 1;
        long hCode = 0;

        for (int i = 0; i < text.length(); i++) {
            // When using integer operators, characters are automatically cast as numbers.
            hCode = (hCode + (text.charAt(i) - 'a' + 1) * power_of_p) % m;
            power_of_p = (power_of_p * p) % m;
        }
        // Because the integer, m, is utilised with the modulus operator, we can safely cast from long to int.
        return (int) hCode;
    }

    @Override
    public String toString() {
        return String.format("[word: %s, count: %d]", text, count);
    }
}
