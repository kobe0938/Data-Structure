import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void test1() {
        MinHeap<Character> h = new MinHeap<>();
        assertEquals(0, h.size());
        assertFalse(h.contains('f'));

        h.insert('f');
        assertEquals(1, h.size());
        assertTrue(h.contains('f'));
        System.out.println(h.toString());
        assertEquals((Object)'f', h.findMin());

        h.insert('h');
        assertEquals(2, h.size());
        System.out.println(h.toString());
        assertEquals((Object)'f', h.findMin());

        h.insert('d');
        assertEquals(3, h.size());
        System.out.println(h.toString());
        assertEquals((Object)'d', h.findMin());

        h.insert('b');
        assertEquals(4, h.size());
        System.out.println(h.toString());
        assertEquals((Object)'b', h.findMin());

        h.insert('c');
        assertEquals(5, h.size());
        System.out.println(h.toString());
        assertEquals((Object)'b', h.findMin());

        assertEquals((Object)'b', h.removeMin());
        assertEquals(4, h.size());
        System.out.println(h.toString());
        assertEquals((Object)'c', h.findMin());

        assertEquals((Object)'c', h.removeMin());
        assertEquals(3, h.size());
        System.out.println(h.toString());
        assertEquals((Object)'d', h.findMin());
    }
}
