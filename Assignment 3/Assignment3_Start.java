/**
 * On my PC, the Linear Search took roughly 5501 milliseconds. The time used by the ArrayList's includes() method is
 * of the order O(n), therefore it is proportional to the size of the ArrayList.
 *
 * On my PC, the Binary Search took roughly 32 milliseconds. The temporal complexity of binary search is on the order
 * of O. (log n). Because we used Collections.sort() to sort the data in the ArrayList, we obtained a much
 * better result.
 *
 * The quickest method was to use the Hashset Search. This is due to the fact that the search takes O(1) time.
 * It remains constant regardless of the size of the dataset.
 *
 * A program that counts the number of words and their occurrences throughout the Lord of the Rings Trilogy.
 * @author  Anthony. Bauld (000754334)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class Assignment3_Start {
    /**
     * Enumerator used for Part B which lists any Names of characters for testing proximity to the ring.
     */
    public enum Name {
        frodo,
        sam,
        bilbo,
        gandalf,
        boromir,
        aragorn,
        legolas,
        gollum,
        pippin,
        merry,
        gimli,
        sauron,
        saruman,
        faramir,
        denethor,
        treebeard,
        elrond,
        galadriel
    }

    public static void main(String[] args) {
        /* Part B Initialization */
        ArrayList < FileCharacter > fileCharacters = new ArrayList < > ();
        Ring ring = new Ring();
        for (Name name: Name.values()) {
            fileCharacters.add(new FileCharacter(name.toString()));
        }

        /* Part A - Initialization */
        // Object designed to keep track of every BookWork that appears in a text file just once (with a count
        // in the BookWord class).
        ArrayList < BookWord > bookWords = new ArrayList < > ();
        // Every word in the dictionary from the US.txt file is stored as a BookWord in this object.
        ArrayList < BookWord > dictionaryWords = new ArrayList < > ();
        // Set up the book's and dictionary's file paths.
        final String bookFileName = "src/TheLordOfTheRings.txt";
        final String dictionaryFileName = "src/US.txt";

        /*  File Reading Initialization */
        long partAStart = System.nanoTime();
        // Read the book and dictionary files, then do some extra steps to prepare for Part A and Part B.
        int bookWordCount = 0;
        int dictionaryWordCount = 0;
        int totalWordCount = 0;
        try {
            Scanner bookScanner = new Scanner(new File(bookFileName));
            // Filter - Do Not Change
            bookScanner.useDelimiter("\\s|\"|\\(|\\)|\\.|\\,|\\?|\\!|\\_|\\-|\\:|\\;|\\n");
            Scanner dictionaryScanner = new Scanner(new File(dictionaryFileName));
            dictionaryScanner.useDelimiter("\n");
            while (bookScanner.hasNext() || dictionaryScanner.hasNext()) {
                if (bookScanner.hasNext()) {
                    BookWord bookWord = new BookWord(bookScanner.next().toLowerCase());
                    if (bookWord.getText().length() > 0) {
                        totalWordCount++;
                        // If a bookWord has already been added, increase the count of that bookWord. Add the bookWord
                        // to bookWords if it isn't already there.
                        if (bookWords.contains(bookWord)) {
                            bookWords.get(bookWords.indexOf(bookWord)).incrementCount();
                        } else {
                            bookWord.incrementCount();
                            bookWords.add(bookWord);
                            bookWordCount++;
                        }
                        // Check if bookWord is equal to an enumerated FileCharacter name by iterating over
                        // fileCharacters.
                        for (FileCharacter fileCharacter: fileCharacters) {
                            // If a bookWord is Enum.Name, add the position to the fileCharacter's positions.
                            if (fileCharacter.getName().equals(bookWord.getText())) {
                                fileCharacter.addPosition(totalWordCount);
                                break;
                            }
                        }
                        // If a bookWord is 'ring,' then the position should be added to the ring positions.
                        if (bookWord.getText().equals("ring"))
                            ring.addPosition(totalWordCount);
                    }
                }
                if (dictionaryScanner.hasNext()) {
                    String dictionaryWord = dictionaryScanner.next().toLowerCase();
                    if (dictionaryWord.length() > 0) {
                        // To the dictionary words ArrayList, add each word from the dictionary file.
                        dictionaryWords.add(new BookWord(dictionaryWord));
                        dictionaryWordCount++;
                    }
                }
            }
            bookScanner.close();
            dictionaryScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        //  Part A - Hashing and Search Performance
        System.out.println("\nFILE ANALYSIS:");
        System.out.println("There are " + bookWordCount + " words in the file: " + bookFileName);
        System.out.println("There are " + dictionaryWordCount + " words in the file: " + dictionaryFileName);

        // Sorting the dictionary by hashable text.
        dictionaryWords.sort(Comparator.comparing(BookWord::getText));

        // Counting the amount of unique words in the ArrayList of BookWords with a count of 1..
        int uniqueWords = 0;
        for (BookWord bookWord: bookWords) {
            if (bookWord.getCount() == 1)
                uniqueWords++;
        }
        System.out.println("Number of unique words = " + uniqueWords);

        // Using a lambda expression that only depends on count, find the top 10 most frequent words and their
        // associated counts. Using a two-keyed sorting approach by developing a lambda function that compares each
        // BookWord based on count first and text second (i.e. two-keyed comparison).
        long start = System.nanoTime();
        bookWords.sort((t1, t2) -> {
            int totalOrder;
            int countOrder = t1.getCount().compareTo(t2.getCount());
            int textOrder = t1.getText().compareTo(t2.getText());
            // The first object must be ordered before the second in a multi-keyed order (has lesser order).
            if (countOrder == 0)
                totalOrder = textOrder;
            else
                totalOrder = countOrder;
            return totalOrder;
        });
        long end = System.nanoTime();
        System.out.println("Time to sort book words = " + (double)(end - start) / 1000000 + " ms");
        System.out.println("\nTOP TEN WORDS:");
        for (int i = 1; i < 11; i++)
            System.out.println(i + ". " + bookWords.get(bookWords.size() - i).toString());

        // Find words having a count of 64. (listed alphabetically).
        System.out.println("\nWORDS OCCURRING 64 TIMES:");
        for (BookWord bookWord: bookWords) {
            if (bookWord.getCount() == 64) {
                System.out.println(bookWord);
            }
        }

        // With ArrayList.contains(), look for words that aren't in the dictionary and time them.
        start = System.nanoTime();
        int misspelledWordCount = 0;
        for (BookWord bookWord: bookWords)
            if (!dictionaryWords.contains(bookWord))
                misspelledWordCount++;
        end = System.nanoTime();
        double linearSearchTime = (double)(end - start) / 1000000;
        System.out.println("\nLINEAR SEARCH:\nArrayList.contains() method took " + linearSearchTime +
                " ms to find " + misspelledWordCount + " misspelled words");

        // Collections can be used to look up terms that aren't in the dictionary. binarySearch() is a function that
        // searches for binary strings and calculates the time it takes to complete.
        start = System.nanoTime();
        misspelledWordCount = 0;
        for (BookWord bookWord: bookWords)
            if (Collections.binarySearch(dictionaryWords, bookWord, Comparator.comparing(BookWord::getText)) < 0)
                misspelledWordCount++;
        end = System.nanoTime();
        double binarySearchTime = (double)(end - start) / 1000000;
        System.out.println("\nBINARY SEARCH:\nCollections.binarySearch() method took " + binarySearchTime +
                " ms to find " + misspelledWordCount + " misspelled words");

        // Making a dictionary book word SimpleHashSet.
        start = System.nanoTime();
        SimpleHashSet < BookWord > dictionaryHashed = new SimpleHashSet < > ();
        for (BookWord word: dictionaryWords)
            dictionaryHashed.insert(word);
        end = System.nanoTime();
        double hashTime = (double)(end - start) / 1000000;

        // With SimpleHashSet.contains(), look for words that aren't in the dictionary and time them.
        start = System.nanoTime();
        misspelledWordCount = 0;
        for (BookWord bookWord: bookWords)
            if (!dictionaryHashed.contains(bookWord))
                misspelledWordCount++;
        end = System.nanoTime();
        double hashedSearchTime = (double)(end - start) / 1000000;

        System.out.println("\nHASHSET SEARCH:\nSimpleHashSet.contains() method took " + hashedSearchTime +
                " ms to find " + misspelledWordCount + " misspelled words");
        System.out.println("Time to hash the dictionary = " + hashTime + " ms");
        System.out.println("Number of buckets = " + dictionaryHashed.getNumberOfBuckets());
        System.out.println("Largest bucket size = " + dictionaryHashed.getLargestBucketSize());
        System.out.println("Number of empty buckets = " + dictionaryHashed.getNumberOfEmptyBuckets());
        System.out.println("% empty buckets = " +
                (double) dictionaryHashed.getNumberOfEmptyBuckets() / dictionaryHashed.getNumberOfBuckets() * 100);

        System.out.println("\nRatio of Linear to Hash = " + linearSearchTime / hashedSearchTime);
        System.out.println("Ratio of Binary to Hash = " + binarySearchTime / hashedSearchTime);
        System.out.println("\nTime for PART A = " + (double)(System.nanoTime() - partAStart) / 1000000 + " ms\n");

        // Part B - Proximity search.
        long startPartB = System.nanoTime();
        // Proximity cutoff.
        final int cutOff = 42;
        ArrayList < Integer > ringPositions = ring.getPositions();
        // Iterate over the positions for each important character or word in the book.
        for (FileCharacter fileCharacter: fileCharacters) {
            ArrayList < Integer > characterPositions = fileCharacter.getPositions();
            // Loop through ringPositions first.
            for (int ringPosition: ringPositions) {
                // Second, iterate over the characterPositions array.
                for (int characterPosition: characterPositions) {
                    // Examine each character's closeness. Place each ring in its proper place. Position.
                    if (ringPosition - characterPosition + cutOff < 0) {
                        // Spend less time iterating through characters that aren't necessary. Job Titles (positions
                        // are always increasing).
                        break;
                    } else if (Math.abs(ringPosition - characterPosition) <= cutOff) {
                        fileCharacter.incrementClosenessCount();
                    }
                }
            }
            System.out.println(fileCharacter);
        }
        System.out.println("\nTime for Part B = " + (double)(System.nanoTime() - startPartB) / 1000000 + " ms");
    }
}
