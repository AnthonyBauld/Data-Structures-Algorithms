/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class SortedLinkedList < T extends Comparable > {
    /**
     * The Node class stores a list element and a reference to the next node.
     */
    private final class Node < T extends Comparable > {
        T value;
        Node next;

        /**
         * Constructor.
         *
         * @param val The element to store in the node.
         * @param n   The reference to the successor node.
         */
        Node(T val, Node n) {
            value = val;
            next = n;
        }

        /**
         * Constructor.
         *
         * @param val The element to store in the node.
         */
        Node(T val) {
            // Call the other (sister) constructor.
            this(val, null);
        }
    }

    private Node first; // list head

    /**
     * Constructor.
     */
    public SortedLinkedList() {
        first = null;
    }

    /**
     * The isEmpty method checks to see if the list is empty.
     *
     * @return True if list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * The size method returns the length of the list.
     *
     * @return The number of elements in the list.
     */
    public int size() {
        int count = 0;
        Node p = first;
        while (p != null) {
            // There is an element at p
            count++;
            p = p.next;
        }
        return count;
    }

    /**
     * The add method places an element in a specific location.
     *
     * @param element The element that will be added to the list in alphabetical order.
     */
    public void add(T element) {
        Node newNode = new Node(element);
        Node current = first;
        Node previous = null;

        while (current != null && element.compareTo(current.value) >= 0) {
            previous = current;
            current = current.next;
        }
        // insertion at beginning of the list
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        newNode.next = current;

    }

    /**
     * The function toString() method generates the list's string representation.
     *
     * @return The list in string form.
     */
    public String toString() {
        String listOfItems = "";

        Node current = first;
        while (current != null) {
            listOfItems += current.value + ", ";
            current = current.next;
        }

        return listOfItems;
    }

    /**
     * To delete technique is used to get rid of an element.
     *
     * @param element The element to remove.
     * @return If the removal was successful, true; otherwise, false.
     */
    public boolean remove(T element) {
        Node temp = first, prev = null;

        // If the element is held by the head node it's to be deleted.
        if (temp != null && temp.value == element) {
            first = temp.next; // Changed head
            return true;
        }
        // Look find the thing that has to be removed and keep track of it.
        // We need to alter temp.next, therefore we'll go back to the previous node.
        while (temp != null && temp.value != element) {
            prev = temp;
            temp = temp.next;
        }

        if (temp == null)
            return false;

        prev.next = temp.next;
        return true;
    }
}
