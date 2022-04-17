import java.util.LinkedList;
import java.util.List;

public class SimpleNameMap {

    /* TODO: Instance variables here */
    public LinkedList<Entry>[] map;
    public int size = 0;
    public final double loadFactor = 0.75;

    public SimpleNameMap() {
        // TODO: YOUR CODE HERE
        map = new LinkedList[2];
        for(int i = 0; i < 2; i++){
            map[i] = new LinkedList<Entry>();
        }
    }

    public int moduloHashCode(Object key){
        return Math.floorMod(key.hashCode(), map.length);
//        return (int) (key.charAt(0) - 'A');
    }
    /* Returns the number of items contained in this map. */
    public int size() {
        // TODO: YOUR CODE HERE
        return size;
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(String key) {
        // TODO: YOUR CODE HERE
        int index = moduloHashCode(key);
        LinkedList curr = map[index];
        if(curr != null){
            Entry targetEntry = new Entry(key, null);
            for (Object iter: curr){
                if (targetEntry.keyEquals((Entry)iter)){
                    return true;
                }
            }
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public String get(String key) {
        // TODO: YOUR CODE HERE
        int index = moduloHashCode(key);
        LinkedList curr = map[index];
        if(this.containsKey(key)){
            for(Object iter : curr){
                if(key.equals(((Entry) iter).key))
                    return ((Entry) iter).value;
            }
        }
        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(String key, String value) {
        // TODO: YOUR CODE HERE

        //LinkedList curr = map[index];
        if (this.containsKey(key)){
            remove(key);
        }
        if (((size()+1) / (double)map.length) > loadFactor){
            resize();
        }
        int index = moduloHashCode(key);
        Entry newEntry = new Entry(key, value);
        map[index].add(newEntry);
        size++;
    }

    public void resize(){
        LinkedList<Entry>[] temp = new LinkedList[map.length];
        System.arraycopy(map, 0, temp, 0, map.length);
        int orginalLength = map.length;
        map = new LinkedList[2 * orginalLength];
        for(int i = 0; i < map.length; i++){
            map[i] = new LinkedList<Entry>();
        }
        for(int i = 0; i<orginalLength; i++){
            LinkedList curr = temp[i];
            for(Object iter : curr){
                int newIndex = moduloHashCode(((Entry) iter).key);
                map[newIndex].add((Entry)iter);
            }
        }
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public String remove(String key) {
        // TODO: YOUR CODE HERE
        int index = moduloHashCode(key);
        if (this.containsKey(key)){
            String returnValue = get(key);
            Entry deletedEntry = new Entry(key, returnValue);
            map[index].remove(deletedEntry);
            size--;
            return returnValue;
        }
        return null;
    }

    private static class Entry {

        private String key;
        private String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}