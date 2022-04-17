import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        //List<Integer> list = new
        // TODO
        ArrayList<Integer> newlist= new ArrayList<>();
        for (int i=0; i<values.length; i++) {
            newlist.add(i, values[i]);
        }
        //values = 0,1,2,3,5 n=5 length=5 missing=4
        for (int i=0; i<=values.length; i++){
            if (!newlist.contains(i)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if and only if two integers in the array sum up to n.
     * Assume all values in the array are unique.
     */
    public static boolean sumTo(int[] values, int n) {
        HashMap<Integer, Integer> newList = new HashMap<>();
        for(int i =0; i < values.length; i++) {
            newList.put(i,values[i]);
        }
        for(int i = 0; i < values.length; i++) {
            for(int a = i+1; a < values.length;a++){
                if(newList.get(i) + newList.get(a) == n){
                    return true;
                }
            }
        }
        // TODO

        return false;
    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    // s1 = "aabc" s2 "baac"
    public static boolean isPermutation(String s1, String s2) {


        ArrayList<String> newList = new ArrayList<>();
        for(int i = 0; i < s1.length(); i++) {
            newList.add(s1.substring(i,i+1));
        }
        for(int i = 0; i < s2.length(); i++) {
            if(!newList.contains(s2.substring(i,i+1)) || newList.size() == 0){
                return false;
            }else{
                newList.remove(s2.substring(i,i+1));
            }
        }
        // TODO
        if (newList.size()!=0) {
            return false;
        }
        return true;
    }
}