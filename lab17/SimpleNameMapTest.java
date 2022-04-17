import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleNameMapTest {

    @Test
    public void size() {
    }

    @Test
    public void containsKey() {
        SimpleNameMap testMap1 = new SimpleNameMap();
        testMap1.put("Alex", "Schedel");
        assertEquals(1, testMap1.size());
        assertEquals(testMap1.containsKey("Alex"), true);
        assertFalse(testMap1.containsKey("Blex"));
    }

    @Test
    public void get() {
        SimpleNameMap testMap1 = new SimpleNameMap();
        testMap1.put("Alex", "Schedel");
        assertEquals(testMap1.get("Alex"), "Schedel");
    }

    @Test
    public void put() {
        SimpleNameMap testMap1 = new SimpleNameMap();
        testMap1.put("Alex", "Schedel");
        assertEquals(testMap1.get("Alex"), "Schedel");
        testMap1.put("Alex", "qwe");
        assertEquals(testMap1.get("Alex"), "qwe");
        testMap1.put("Brenda", "Huang");//length =2 , size=1 (size+1)>0.75*2
        assertEquals(2, testMap1.size());
        assertEquals(4,testMap1.map.length);
        testMap1.put("Dexter", "To");//length =4 , size =2
        testMap1.put("Jack", "Wang");
        assertEquals(4, testMap1.size());
        assertEquals(8,testMap1.map.length);
    }

    @Test
    public void remove() {
        SimpleNameMap testMap1 = new SimpleNameMap();
        testMap1.put("Alex", "Schedel");
        assertEquals(testMap1.get("Alex"), "Schedel");
        testMap1.remove("Alex");
        assertFalse(testMap1.containsKey("Alex"));
    }
}