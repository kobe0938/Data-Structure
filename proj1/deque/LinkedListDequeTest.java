package deque;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list deque tests.
 */
public class LinkedListDequeTest {

    /**
     * You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test.
     */

    public static Deque<Integer> lld = new LinkedListDeque<Integer>();

    @Test
    /** adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
        lld.addFirst(0);

        assertFalse("lld should now contain 1 item", lld.isEmpty());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void testaddFirst() {
        lld.addFirst(5);
        assertEquals(5, (int) lld.get(0));
        lld.addFirst(10);
        assertEquals(10, (int) lld.get(0));
        lld.addFirst(15);
        lld.addFirst(20);
        lld.addFirst(25);
        assertEquals(25, (int) lld.get(0));
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void testaddLast() {
        lld.addLast(10);
        assertEquals(10, (int) lld.get(0));
        lld.addLast(14);
        assertEquals(14, (int) lld.get(1));
        lld.addLast(15);
        lld.addLast(16);
        lld.addLast(17);
        assertEquals(17, (int) lld.get(4));
        lld = new LinkedListDeque<Integer>();
    }


    @Test
    public void testsize() {
        assertEquals(0, lld.size());
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        lld.removeLast();
        assertEquals(2, lld.size());
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void testprintDeque() {
        lld.addLast(5);
        lld.addLast(4);
        lld.addLast(3);
        lld.addLast(2);
        lld.printDeque();
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void testremoveFirst() {
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        assertEquals(3, (int) lld.removeFirst());
        assertEquals(2, (int) lld.removeFirst());
        assertEquals(1, (int) lld.removeFirst());
        assertEquals(null, lld.removeFirst());
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void testremoveLast() {
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        assertEquals(1, (int) lld.removeLast());
        assertEquals(2, (int) lld.removeLast());
        assertEquals(3, (int) lld.removeLast());
        assertEquals(null, lld.removeLast());
        lld = new LinkedListDeque<Integer>();

    }

    @Test
    public void testget() {
        assertEquals(null, lld.get(0));
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        assertEquals(3, (int) lld.get(0));
        assertEquals(2, (int) lld.get(1));
        assertEquals(1, (int) lld.get(2));
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void testEquals() {
        assertTrue(lld.equals(null));
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        Deque<Integer> da = new LinkedListDeque<Integer>();
        da.addFirst(1);
        da.addFirst(2);
        da.addFirst(3);
        assertTrue(lld.equals(da));

        Deque<Integer> arr = new ArrayDeque<Integer>();
        arr.addFirst(1);
        arr.addFirst(2);
        arr.addFirst(3);
        assertTrue(lld.equals(arr));

        da.removeLast();
        assertFalse(lld.equals(da));
        arr.removeLast();
        assertFalse(lld.equals(arr));
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void testgetRecursive() {
        assertEquals(null, ((LinkedListDeque) lld).getRecursive(0));
        lld.addFirst(4);
        lld.addFirst(5);
        lld.addFirst(6);
        assertEquals(6, (int) ((LinkedListDeque) lld).getRecursive(0));
        assertEquals(5, (int) ((LinkedListDeque) lld).getRecursive(1));
        assertEquals(4, (int) ((LinkedListDeque) lld).getRecursive(2));
        lld = new LinkedListDeque<Integer>();
    }
}
