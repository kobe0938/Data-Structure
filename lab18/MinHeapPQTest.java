import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void test1() {
        MinHeapPQ<Character> pq = new MinHeapPQ<>();
        pq.insert('f', 6);
        assertEquals((Object) 'f', pq.peek());

        pq.insert('h', 8);
        assertEquals((Object) 'f', pq.peek());

        pq.insert('d', 4);
        assertEquals((Object) 'd', pq.peek());

        pq.insert('b', 2);
        assertEquals((Object) 'b', pq.peek());

        pq.insert('c', 3);
        assertEquals((Object) 'b', pq.peek());

        pq.insert('w', 23);
        pq.insert('x', 24);
        pq.insert('y', 25);
        pq.insert('z', 26);


//        assertEquals((Object)'b', pq.poll());
//        assertEquals((Object) 'c', pq.peek());
//        assertEquals((Object)'c', pq.poll());
//        assertEquals((Object) 'd', pq.peek());

        pq.changePriority('d',100);
//        assertEquals((Object)'b', pq.peek());
        pq.changePriority('h', 1);
//        assertEquals((Object) 'h', pq.peek());
        while (pq.size() != 0){
            System.out.println(pq.poll());
        }
    }
}
