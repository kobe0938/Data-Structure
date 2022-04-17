import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V>{
    /* TODO: Instance variables here */
    public LinkedList<Entry<K, V>>[] map;
    public int size = 0;
    public double loadFactor = 0.75;
    /* Returns the length of this HashMap's internal array. */
    public int capacity(){
        return map.length;
    }

    /* Creates a new hash map with a default array of size 16 and a maximum load factor of 0.75. */
    HashMap(){
        // TODO: YOUR CODE HERE
        map = new LinkedList[16];
        for(int i = 0; i < 16; i++){
            map[i] = new LinkedList<Entry<K, V>>();
        }
    }

    /* Creates a new hash map with an array of size INITIALCAPACITY and a maximum load factor of 0.75. */
    HashMap(int initialCapacity){
        map = new LinkedList[initialCapacity];
        for(int i = 0; i < initialCapacity; i++){
            map[i] = new LinkedList<Entry<K, V>>();
        }
    }

    /* Creates a new hash map with INITIALCAPACITY and LOADFACTOR. */
    HashMap(int initialCapacity, double loadFactor){
        map = new LinkedList[initialCapacity];
        for(int i = 0; i < initialCapacity; i++){
            map[i] = new LinkedList<Entry<K, V>>();
        }
        this.loadFactor = loadFactor;
    }



    public int moduloHashCode(Object key){
        return Math.floorMod(key.hashCode(), map.length);
//        return (int) (key.charAt(0) - 'A');
    }
    /* Returns the number of items contained in this map. */

    @Override
    public void clear(){
        map = new LinkedList[map.length];
        size = 0;
    }

    @Override
    public boolean remove(K key, V value){
        int index = moduloHashCode(key);
        if (this.containsKey(key)){
            V currValue = get(key);
            Entry<K, V> deletedEntry = new Entry<K, V>(key, currValue);
            if (value.equals(currValue)){
                map[index].remove(deletedEntry);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<K> iterator(){
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        // TODO: YOUR CODE HERE
        return size;
    }

    /* Returns true if the map contains the KEY. */
    @Override
    public boolean containsKey(K key) {
        // TODO: YOUR CODE HERE
        int index = moduloHashCode(key);
        LinkedList curr = map[index];
        if(curr != null){
            Entry<K, V> targetEntry = new Entry<K, V>(key, null);
            for (Object iter: curr){
                if (targetEntry.keyEquals((Entry<K, V>)iter)){
                    return true;
                }
            }
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    @Override
    public V get(K key) {
        // TODO: YOUR CODE HERE
        int index = moduloHashCode(key);
        LinkedList curr = map[index];
        if(this.containsKey(key)){
            for(Object iter : curr){
                if(key.equals(((Entry<K, V>) iter).key))
                    return ((Entry<K, V>) iter).value;
            }
        }
        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    @Override
    public void put(K key, V value) {
        // TODO: YOUR CODE HERE

        //LinkedList curr = map[index];
        if (this.containsKey(key)){
            remove(key);
        }
        if (((size()+1) / (double)map.length) > loadFactor){
            resize();
        }
        int index = moduloHashCode(key);
        Entry<K, V> newEntry = new Entry<K, V>(key, value);
        map[index].add(newEntry);
        size++;
    }


    public void resize(){
        LinkedList<Entry<K, V>>[] temp = new LinkedList[map.length];
        System.arraycopy(map, 0, temp, 0, map.length);
        int orginalLength = map.length;
        map = new LinkedList[2 * orginalLength];
        for(int i = 0; i < map.length; i++){
            map[i] = new LinkedList<Entry<K, V>>();
        }
        for(int i = 0; i<orginalLength; i++){
            LinkedList curr = temp[i];
            for(Object iter : curr){
                int newIndex = moduloHashCode(((Entry<K, V>) iter).key);
                map[newIndex].add((Entry<K, V>)iter);
            }
        }
    }

    /* Removes a single Entry<K, V>, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    @Override
    public V remove(K key) {
        // TODO: YOUR CODE HERE
        int index = moduloHashCode(key);
        if (this.containsKey(key)){
            V returnValue = get(key);
            Entry<K, V> deletedEntry = new Entry<K, V>(key, returnValue);
            map[index].remove(deletedEntry);
            size--;
            return returnValue;
        }
        return null;
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry<K, V> other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry<K, V>) other).key)
                    && value.equals(((Entry<K, V>) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
