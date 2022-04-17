public class UnionFind {

    /* TODO: Add instance variables here. */
    private int[] tree;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        tree = new int[N];
        for(int i = 0; i < N; i++){
            tree[i] = -1;
        }
        // TODO: YOUR CODE HERE
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        return -tree[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
//        if(tree[v] < 0) {
//            return sizeOf(v);
//        }
        return tree[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(find(v1) == find(v2)){
            return true;
        }
        return false;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v >= tree.length) {
            throw new IllegalArgumentException();
        }
        int rootOfSet = parent(v);
        if(rootOfSet < 0){
            return v;
        }
        int p = parent(v);
        while( p >= 0){
            rootOfSet = p;
            p = parent(p);
        }
        tree[v] = rootOfSet;
        return rootOfSet;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(find(v1) == find(v2)){
            return;
        }
        int size1 = -sizeOf(v1);
        int size2 = -sizeOf(v2);
        if(size1 < size2){
            tree[find(v1)] = size1+size2;
            tree[find(v2)] = find(v1);
        } else {
            tree[find(v2)] = size1+size2;
            tree[find(v1)] = find(v2);
        }


    }
}
