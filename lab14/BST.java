import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayList;

public class BST<T> {

    BSTNode<T> root;

    public BST(LinkedList<T> list) {
        root = sortedIterToTree(list.iterator(), list.size());
    }

    /* Returns the root node of a BST (Binary Search Tree) built from the given
       iterator ITER  of N items. ITER will output the items in sorted order,
       and ITER will contain objects that will be the item of each BSTNode. */
    private BSTNode<T> sortedIterToTree(Iterator<T> iter, int N) {  //1 2 3 4 5 6 7    ->4  2,6  1,3 5,7
        ArrayList<T> convertArray = new ArrayList<>();
        if (!iter.hasNext()){
            return null;
        } else{
            while (iter.hasNext()){
                convertArray.add(iter.next());
            }
        }
        return sortedIterToTreeHelper(convertArray, 0, N-1);
        // TODO: YOUR CODE HERE
    }

    public BSTNode<T> sortedIterToTreeHelper(ArrayList convertArray, int low, int high){
        int mid = (low+high)/2;
        if (low == high){
            return new BSTNode(convertArray.get(low));
        }else if (low > high){
            return null;
        }else{
            BSTNode node = new BSTNode(convertArray.get(mid));
            node.left = sortedIterToTreeHelper(convertArray, low, mid-1);
            node.right = sortedIterToTreeHelper(convertArray, mid+1, high);
            return node;
        }
    }

//    private BSTNode<T> sortedIterToTreeHelper(Iterator<T> iter, BSTNode currBSTNode) {
//
//        // TODO: YOUR CODE HERE
//        return currBSTNode;
//    }
    /* Prints the tree represented by ROOT. */
    private void print() {
        print(root, 0);
    }

    private void print(BSTNode<T> node, int d) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < d; i++) {
            System.out.print("  ");
        }
        System.out.println(node.item);
        print(node.left, d + 1);
        print(node.right, d + 1);
    }

    class BSTNode<T> {
        T item;
        BSTNode<T> left;
        BSTNode<T> right;

        BSTNode(T item) {
            this.item = item;
        }
    }
}
