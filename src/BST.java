import java.util.ArrayList;
import java.util.List;

public class BST {
    class Node {
        int data;
        Node left, right;

        public Node(int item) {
            data = item;
            left = right = null;
        }
    }

    Node root;

    BST() {
        root = null;
    }

    void insert(int data) {
        root = insertRec(root, data);
    }

    Node insertRec(Node root, int data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        if (data < root.data)
            root.left = insertRec(root.left, data);
        else if (data > root.data)
            root.right = insertRec(root.right, data);
        return root;
    }

    void delete(int data) {
        root = deleteRec(root, data);
    }

    Node deleteRec(Node root, int data) {
        if (root == null)
            return root;
        if (data < root.data)
            root.left = deleteRec(root.left, data);
        else if (data > root.data)
            root.right = deleteRec(root.right, data);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            root.data = minValue(root.right);
            root.right = deleteRec(root.right, root.data);
        }
        return root;
    }

    int minValue(Node root) {
        int minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }

    List<Integer> preorder() {
        List<Integer> result = new ArrayList<>();
        preorderRec(root, result);
        return result;
    }

    void preorderRec(Node root, List<Integer> result) {
        if (root != null) {
            result.add(root.data);
            preorderRec(root.left, result);
            preorderRec(root.right, result);
        }
    }

    List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    void inorderRec(Node root, List<Integer> result) {
        if (root != null) {
            inorderRec(root.left, result);
            result.add(root.data);
            inorderRec(root.right, result);
        }
    }

    List<Integer> postorder() {
        List<Integer> result = new ArrayList<>();
        postorderRec(root, result);
        return result;
    }

    void postorderRec(Node root, List<Integer> result) {
        if (root != null) {
            postorderRec(root.left, result);
            postorderRec(root.right, result);
            result.add(root.data);
        }
    }
}
