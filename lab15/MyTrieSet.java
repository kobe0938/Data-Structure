
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MyTrieSet implements TrieSet61BL{
    private Node root;
    private static final int R = 128;

    public MyTrieSet(){
        root = new Node('0',false,R);
    }
    public static class DataIndexedCharMap<V> {
        private V[] items;
        public DataIndexedCharMap(int R) {
            items = (V[]) new Object[R];
        }
        public void put(char c, V val) { items[c] = val; }
        public V get(char c) { return items[c]; }
        public boolean contains(char c) {
            return get(c) != null;
        }
    }

    private static class Node{
        public boolean isKey;
        private DataIndexedCharMap<Node> next;
        public char ch;

        private Node(char c, boolean b, int R) {
            ch = c;
            isKey = b;
            next = new DataIndexedCharMap<Node>(R);
        }
    }

    @Override
    public void clear() {
        root = new Node('0',false,R);
    }

    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return true;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.next.contains(c)) {
                return false;
            }
            curr = curr.next.get(c);
        }
        if (curr.isKey == true){
            return true;
        }
        return false;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.next.contains(c)) {
                curr.next.put(c, new Node(c, false, R));
            }
            curr = curr.next.get(c);
        }
        curr.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node p = root;
        for (int i = 0; i < prefix.length(); i += 1) {
            char c = prefix.charAt(i);
            p = p.next.get(c);
            if (p == null) break;
        }

        List<String> x = new ArrayList<>();

        for (char i = 'a'; i<='z'; i++){
            if (p.next.contains(i)){
                char c = i;
                Helper(prefix + c, x, p.next.get(c));
            }
        }
        return x;
    }

    private void Helper(String s, List<String> x, Node n) {
        if (n == null) return;
        if (n.isKey) {
            x.add(s);
        }
        for (char i = 'a'; i<='z'; i++){
            if (n.next.contains(i)){
                char c = i;
                Helper(s + c, x, n.next.get(c));
            }
        }
    }
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
