package deque;

public class ArrayDeque<T> implements Deque<T> {

    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int originalLength;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }


    //add item to the front of the deque
    /*When nextFirst is approaching the edge of left, we create a new ArrayDeque, which double
    the size of the orginal ArrayDeque and its index is rearranged accordingly. Then copy the
    newly-created ArrayDeque. Also, change the Get method.*/
    @Override
    public void addFirst(T item) {
        if (nextFirst == -1) {
            originalLength = items.length;
            T[] newArray = (T[]) new Object[2 * originalLength]; //8
            for (int i = 0; i < items.length; i++) {
                newArray[i + originalLength] = items[i];
            }
            nextFirst = originalLength - 1;
            nextLast = nextLast + originalLength;
            items = newArray;
            items[nextFirst] = item;
            nextFirst--;
        } else {
            items[nextFirst] = item;
            //numFirst++;
            nextFirst--;
        }
        size++;
    }

    //add item to the back of the deque
    @Override
    public void addLast(T item) {
        if (nextLast == items.length) {
            originalLength = items.length;
            T[] newArray = (T[]) new Object[2 * originalLength];
            for (int i = 0; i < items.length; i++) {
                newArray[i] = items[i];
            }
            nextLast = items.length;
            items = newArray;
            items[nextLast] = item;
            nextLast++;
        } else {
            items[nextLast] = item;
            //numFirst++;
            nextLast++;
        }
        size++;
    }

    // return the number of items in deque
    @Override
    public int size() {
        return size;
    }

    //print all items from teh first to last, print new line after printing all items
    @Override
    public void printDeque() {
        for (int i = nextFirst + 1; i < nextLast; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    //remove and return the first item in deque
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T beforeMoved = items[nextFirst + 1];
        if (nextFirst == originalLength && originalLength > 8) {
            T[] newArray = (T[]) new Object[originalLength];
            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = items[i + originalLength];
            }
            nextFirst = 1;
            nextLast = nextLast - originalLength;
            originalLength = originalLength / 2;
            items = newArray;
            items[nextFirst] = null;
            size--;
            return beforeMoved;
        } else {
            items[nextFirst + 1] = null;
            nextFirst++;
            size--;
            return beforeMoved;
        }

    }

    //remove and return the last item in deque
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T beforeMoved = items[nextLast - 1];
        if (nextLast == originalLength && originalLength > 8) {
            T[] newArray = (T[]) new Object[originalLength];
            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = items[i];
            }
            nextLast = newArray.length - 1;
            originalLength = originalLength / 2;
            items = newArray;
            items[nextLast] = null;
            size--;
            return beforeMoved;
        } else {
            items[nextLast - 1] = null;
            nextLast--;
            size--;
            return beforeMoved;
        }
    }

    //return the item at index, return null if item doesn't exist
    @Override
    public T get(int index) {
        return items[nextFirst + index + 1];
    }


    //return true if o is equal to deque
    public boolean equals(Object o) {
        if (o == null) {
            if (this.size() != 0) {
                return false;
            } else {
                return true;
            }
        }
        if (o instanceof Deque) {
            if (((Deque) o).size() != this.size()) {
                return false;
            } else {
                for (int i = 0; i < this.size(); i++) {
                    if (!((Deque) o).get(i).equals(this.get(i))) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
