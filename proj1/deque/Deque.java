package deque;

/* The Deque interface defines the expected behavior for any
 * Deque, whether it is an ArrayDeque or LinkedListDeque. A
 * Deque is a doubly-ended queue, that allows you to quickly add
 * and remove items from the front and back. */
public interface Deque<T> {
    //add item to the front of the deque
    void addFirst(T item);

    //add item to the back of the deque
    void addLast(T item);

    //return true if deque is empty, false otherwise
    default boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        }
        return false;
    }  //{
//        if(this.size() == 0){
//            return true;
//        }
//        return false;
//    }

    // return the number of items in deque
    int size();

    //print all items from teh first to last, print new line after printing all items
    void printDeque();

    //remove and return the first item in deque
    T removeFirst();

    //remove and return the last item in deque
    T removeLast();

    //return the item at index, return null if item doesn't exist
    T get(int index);

    //return true if o is equal to deque
    boolean equals(Object o);

//    T getRecursive(int index);

}
