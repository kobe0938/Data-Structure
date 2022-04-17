import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Represent a set of nonnegative ints from 0 to maxElement for some initially
 * specified maxElement.
 */
public class BooleanSet implements SimpleSet {

    private boolean[] contains;
    private int size;
    private HashSet<Integer> there = new HashSet<>();

    /** Initializes a set of ints from 0 to maxElement. */
    public BooleanSet(int maxElement) {
        contains = new boolean[maxElement + 1];
        size = 0;
    }

    /** Adds k to the set. */
    public void add(int k) {

        if(!there.contains(k)) {
            contains[k] = true;
            size++;
            there.add(k);
        }



        // TODO
    }

    /** Removes k from the set. */
    public void remove(int k) {
        if(there.contains(k)) {
            there.remove(k);
            contains[k] = false;
            size--;
            // TODO
        }
    }

    /** Return true if k is in this set, false otherwise. */
    public boolean contains(int k) {
        return contains[k];
    }

    /** Return true if this set is empty, false otherwise. */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /** Returns the number of items in the set. */
    public int size() {
        //TODO
        return size;
    }

    /** Returns an array containing all of the elements in this collection. */
    public int[] toIntArray() {
        //Integer[] arr = there.toArray(new Integer[there.size()]);
        // TODO
        int[] arr = new int[size];
        Iterator it = there.iterator();
        int index =0;
        while(it.hasNext()){
            arr[index] = Integer.parseInt(it.next().toString());
            index++;
        }

        return arr;
    }
}