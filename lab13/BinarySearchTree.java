import java.util.Comparator;

public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. */
    public BinarySearchTree() {
        super();
        // TODO: YOUR CODE HERE
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
        super(root);
        // TODO: YOUR CODE HERE
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        // TODO: YOUR CODE HERE
        return containsHelper(root, key);
    }
    private boolean containsHelper(TreeNode t, T key){
        if (t == null){
            return false;
        }
        if (key.compareTo(t.item) == 0){
            return true;
        }
        if (key.compareTo(t.item) < 0){//left branch
            return containsHelper(t.left, key);
        }
        return containsHelper(t.right, key);//right branch
    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
        root = addHelper(root,key);
        // TODO: YOUR CODE HERE
    }

    private TreeNode addHelper(TreeNode t, T key){
        if (t == null){
            return new TreeNode(key);
        }
        if (key.compareTo(t.item) < 0){//left branch
            t.left = addHelper(t.left, key);
        }else if (key.compareTo(t.item) > 0){
            t.right = addHelper(t.right, key);
        }
        return t;
    }
    /* Deletes a node from the BST. 
     * Even though you do not have to implement delete, you 
     * should read through and understand the basic steps.
    */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (curr.item.compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}