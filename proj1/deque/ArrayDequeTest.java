package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Performs some basic array deque tests.
 */

public class ArrayDequeTest {

    /**
     * You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test.
     **/

    public static Deque<Integer> ad = new ArrayDeque<Integer>();

    @Test
    public void testaddFirst() {
        ad.addFirst(5); //3
        assertEquals(5, (int) ad.get(0));
        ad.addFirst(10); //2
        assertEquals(10, (int) ad.get(0));
        ad.addFirst(3); //1
        ad.addFirst(4); //0
        ad.addFirst(5); //7
        ad.addFirst(6); //6
        ad.addFirst(7); //5
        ad.addFirst(8); //4
        ad.addFirst(9); //3
        ad.addFirst(10); //2
        ad.addFirst(11); //1
        ad.addFirst(12); //0
        ad.addFirst(13); //15
        ad.addFirst(14); //14

        assertEquals(14, (int) ad.get(0));
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testaddLast() {
        ad.addFirst(5); //3 1

        ad.addFirst(10); //2  2

        ad.addFirst(3); //1
        ad.addFirst(4); //0
        ad.addFirst(5); //7
        ad.addFirst(6); //6
        ad.addFirst(7); //5
        ad.addFirst(8); //4
        ad.addFirst(9); //3
        ad.addFirst(10); //2
        ad.addFirst(11); //1
        ad.addFirst(12); //0
        ad.addFirst(13); //15
        ad.addFirst(14); //14
        ad.addLast(15); //28 4+8+16=28
        ad.addLast(16); //29
        ad.addLast(17); //30
        ad.addLast(18); //31
        ad.addLast(19); //32
        assertEquals(19, (int) ad.get(18));
        ad.addLast(10); //33
        assertEquals(10, (int) ad.get(19));

        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testisEmpty() {
        assertTrue(ad.isEmpty());
        ad.addFirst(5);
        assertFalse(ad.isEmpty());
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testsize() {
        assertEquals(0, ad.size());
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        ad.removeLast();
        assertEquals(2, ad.size());
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testprintDeque() {
        ad.addLast(5);
        ad.addLast(4);
        ad.addLast(3);
        ad.addLast(2);
        ad.printDeque();
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testremoveFirst() {
        ad.addFirst(1); //3
        ad.addFirst(2); //2
        ad.addFirst(3); //1
        ad.addFirst(4); //0
        ad.addFirst(5); //
        ad.addFirst(6); //
        ad.addFirst(1); //3
        ad.addFirst(2); //2
        ad.addFirst(3); //1
        ad.addFirst(4); //0
        ad.addFirst(5); //
        ad.addFirst(6); //
        ad.addFirst(1); //3
        ad.addFirst(2); //2
        ad.addFirst(3); //1
        ad.addFirst(4); //0
        ad.addFirst(5); //
        ad.addFirst(6); //
        assertEquals(6, (int) ad.removeFirst());
        assertEquals(5, (int) ad.removeFirst());
        assertEquals(4, (int) ad.removeFirst());
        assertEquals(3, (int) ad.removeFirst());
        assertEquals(2, (int) ad.removeFirst());
        assertEquals(1, (int) ad.removeFirst());
        assertEquals(6, (int) ad.removeFirst());
        assertEquals(5, (int) ad.removeFirst());
        assertEquals(4, (int) ad.removeFirst());
        assertEquals(3, (int) ad.removeFirst());
        assertEquals(2, (int) ad.removeFirst());
        assertEquals(1, (int) ad.removeFirst());
    }

    @Test
    public void testremoveLast() {
        ad.addFirst(1); //3
        ad.addFirst(2); //2
        ad.addFirst(3); //1
        ad.addFirst(4); //0
        ad.addFirst(5); //
        ad.addFirst(6); //
        ad.addFirst(1); //3
        ad.addFirst(2); //2
        ad.addFirst(3); //1
        ad.addFirst(4); //0
        ad.addFirst(5); //
        ad.addFirst(6); //
        ad.addFirst(1); //3
        ad.addFirst(2); //2
        ad.addFirst(3); //1
        ad.addFirst(4); //0
        ad.addFirst(5); //
        ad.addFirst(6); //
        ad.addLast(1); //4
        ad.addLast(2); //5
        ad.addLast(3); //6
        ad.addLast(4); //7
        ad.addLast(5); //8
        ad.addLast(6); //9
        ad.addLast(1); //4
        ad.addLast(2); //5
        ad.addLast(3); //6
        ad.addLast(4); //7
        ad.addLast(5); //8
        ad.addLast(6); //9
        ad.addLast(1); //4
        ad.addLast(2); //5
        ad.addLast(3); //6
        ad.addLast(4); //7
        ad.addLast(5); //8
        ad.addLast(6); //9
        assertEquals(6, (int) ad.removeLast());
        assertEquals(5, (int) ad.removeLast());
        assertEquals(4, (int) ad.removeLast());
        assertEquals(3, (int) ad.removeLast());
        assertEquals(2, (int) ad.removeLast());
        assertEquals(1, (int) ad.removeLast());
        assertEquals(6, (int) ad.removeLast());
        assertEquals(5, (int) ad.removeLast());
        assertEquals(4, (int) ad.removeLast());
        assertEquals(3, (int) ad.removeLast());
        assertEquals(2, (int) ad.removeLast());
        assertEquals(1, (int) ad.removeLast());
        assertEquals(6, (int) ad.removeLast());
        assertEquals(5, (int) ad.removeLast());
        assertEquals(4, (int) ad.removeLast());
        assertEquals(3, (int) ad.removeLast());
        assertEquals(2, (int) ad.removeLast());
        assertEquals(1, (int) ad.removeLast());
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testget() {
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        assertEquals(3, (int) ad.get(0));
        assertEquals(2, (int) ad.get(1));
        assertEquals(1, (int) ad.get(2));
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testEquals() {
        assertTrue(ad.equals(null));
        ad.addFirst(1); //3
        ad.addFirst(2); //2
        ad.addFirst(3); //1
        ad.addFirst(4); //0
        ad.addFirst(5); //7
        Deque<Integer> da = new ArrayDeque<Integer>();
        da.addFirst(1);
        da.addFirst(2);
        da.addFirst(3);
        da.addFirst(4);
        da.addFirst(5);
        assertTrue(ad.equals(da));

        Deque<Integer> link = new LinkedListDeque<Integer>();
        link.addFirst(1);
        link.addFirst(2);
        link.addFirst(3);
        link.addFirst(4);
        link.addFirst(5);
        assertTrue(ad.equals(link));

        da.removeLast();
        assertFalse(ad.equals(da));
        link.removeLast();
        assertFalse((ad.equals(link)));
        ad = new ArrayDeque<Integer>();
    }
}

