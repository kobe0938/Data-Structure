package deque;

public class LinkedListDeque<T> implements Deque<T> {

    private TListNode sentinel;
    private int size;
    public LinkedListDeque() {
        sentinel = new TListNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    //add item to the front of the deque
    @Override
    public void addFirst(T item) {
        sentinel.next = new TListNode(item, sentinel.next, sentinel);
        if (sentinel.prev == sentinel) {
            sentinel.prev = sentinel.next;
        }
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    //add item to the back of the deque
    @Override
    public void addLast(T item) {
        sentinel.prev = new TListNode(item, sentinel, sentinel.prev);
        if (sentinel.next == sentinel) {
            sentinel.next = sentinel.prev;
        }
        sentinel.prev.prev.next = sentinel.prev;
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
        TListNode a = this.sentinel.next;
        while (a.next != this.sentinel) {
            System.out.print(a.item + " ");
            a = a.next;
        }
        System.out.println(a.item);
    }

    //remove and return the first item in deque
    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T temp = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return temp;
    }

    //remove and return the last item in deque
    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        T temp = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return temp;
    }

    //return the item at index, return null if item doesn't exist
    @Override
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        TListNode a = this.sentinel.next;
        for (int i = 0; i < index; i++) {
            a = a.next;
        }
        return a.item;
    }

    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        TListNode a = this.sentinel.next;
        return getHelper(a, index);
    }

    private T getHelper(TListNode helper, int index) {
        if (index == 0) {
            return helper.item;
        } else {
            T temp = getHelper(helper.next, index - 1);
            return temp;
        }
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

    private class TListNode {
        public T item;
        public TListNode next;
        public TListNode prev;

        public TListNode(T item, TListNode next, TListNode prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
