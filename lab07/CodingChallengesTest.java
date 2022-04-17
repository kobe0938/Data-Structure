import org.junit.Test;

import static org.junit.Assert.*;

public class CodingChallengesTest {

    @Test
    public void testmissingNumber() {
        CodingChallenges code=null;
        int[] someList = new int[]{0,1,2,4};
        assertEquals(3,code.missingNumber(someList));
        int[] someList2 = new int[]{0,1,2,3};
        assertEquals(4,code.missingNumber(someList2));
    }
       // Date d1 = new GregorianDate(2018, 1, 30);
       // uals("2018/1/31", d1.nextDate().toString());t
    @Test
    public void testsumTo() {
        CodingChallenges code2 = null;
        int[] someList2 = new int[]{1,2,4,3,7,8};
        //int[] key2 = new int[]{0,1,2,3,4,5};
        assertEquals(true,code2.sumTo(someList2,10));
    }

    @Test
    public void testisPermutation() {
        CodingChallenges code3 = null;
        String str1 = "acc";
        String str2 = "cac";
        assertTrue(code3.isPermutation(str1,str2));
        String str3 = "accd";
        assertTrue(!code3.isPermutation(str1,str3));

    }
}