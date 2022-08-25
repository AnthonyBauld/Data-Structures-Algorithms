import java.util.ArrayList;

/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class SimpleHashSet<T> {

    public ArrayList<T>[] buckets;
    public int numberOfBuckets = 10;
    public int size;
    public static final double AVERAGE_BUCKET_SIZE = 3;

    public SimpleHashSet() {
        // Create buckets.
        buckets = new ArrayList[numberOfBuckets];
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new ArrayList<T>();
        }
        size = 0;
    }

    private int getHash(T x, int hashSize) {
        // Use modulus as hash function.
        return Math.abs(x.hashCode() % hashSize);
    }

    private void resize() {
        // Double number of buckets.
        int newBucketsSize = numberOfBuckets * 2;
        ArrayList<T>[] newBuckets = new ArrayList[newBucketsSize];

        // Create new buckets.
        for (int i = 0; i < newBucketsSize; i++) {
            newBuckets[i] = new ArrayList<T>();
        }

        // Copy elements over and use new hashes.
        for (int i = 0; i < numberOfBuckets; i++) {
            for (T y : buckets[i]) {
                int hash = getHash(y, newBucketsSize);
                newBuckets[hash].add(y);
            }
        }

        // Set new buckets.
        buckets = newBuckets;
        numberOfBuckets = newBucketsSize;
    }

    public boolean insert(T x) {
        // Get hash of x.
        int hash = Math.abs(getHash(x, numberOfBuckets));

        // Get current bucket from hash.
        ArrayList<T> curBucket = buckets[hash];

        // Stop, if current bucket already has x.
        if (curBucket.contains(x)) {
            return false;
        }

        // Otherwise, add x to the bucket.
        curBucket.add(x);

        // Resize if the collision chance is higher than threshold.
        if ((float) size / numberOfBuckets > AVERAGE_BUCKET_SIZE) {
            resize();
        }
        size++;
        return true;
    }

    public boolean contains(T x) {
        // Get hash of x.
        int hash = getHash(x, numberOfBuckets);

        // Get current bucket from hash.
        ArrayList<T> curBucket = buckets[hash];

        // Return if bucket contains x.
        return curBucket.contains(x);
    }

    public boolean remove(T x) {
        // Get hash of x.
        int hash = getHash(x, numberOfBuckets);

        // Get bucket from hash.
        ArrayList<T> curBucket = buckets[hash];

        // Remove x from bucket and return if operation successful.
        return curBucket.remove(x);
    }

    /**
     * Utility method that will return the current number of buckets
     *
     * @return
     */

    public int getNumberOfBuckets() {
        return numberOfBuckets;
    }

    public int getNumberOfEmptyBuckets() {
        int empty = 0;
        for (ArrayList bucket : buckets)
            if (bucket.size() == 0)
                empty++;
        return empty;
    }

    public int size() {
        return size;
    }

    public int getLargestBucketSize() {
        int maximumSize = 0;
        for (ArrayList bucket : buckets)
            if (bucket.size() > maximumSize)
                maximumSize = bucket.size();
        return maximumSize;
    }
}
