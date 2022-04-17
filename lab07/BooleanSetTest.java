import org.junit.Test;
import static org.junit.Assert.*;

public class BooleanSetTest {

    @Test
    public void testBasics() {
        BooleanSet aSet = new BooleanSet(100);
        assertEquals(0, aSet.size());
        for (int i = 0; i < 100; i += 2) {
            aSet.add(i);
            assertTrue(aSet.contains(i));
        }
        assertEquals(50, aSet.size());

        for (int i = 0; i < 100; i += 2) {
            aSet.remove(i);
            assertFalse(aSet.contains(i));
        }
        assertTrue(aSet.isEmpty());
        assertEquals(0, aSet.size());
        aSet.add(1);
        aSet.add(1);
        assertEquals(1, aSet.size());
        aSet.remove(2);
        assertEquals(1, aSet.size());
    }


    @Test
    public void testToIntArray() {
        BooleanSet bSet = new BooleanSet(3);
        assertEquals(0, bSet.size());
        for (int i = 0; i < 3; i += 1) {
            bSet.add(i);

        }
        int[] a = new int[]{0,1,2};
        assertTrue(bSet.toIntArray()[0] ==a[0]);
        assertTrue(bSet.toIntArray()[1] ==a[1]);
        assertTrue(bSet.toIntArray()[2] ==a[2]);
        //Array.equals(bSet.toIntArray(), a);
    }

}