// Assessment 7: Text Classifier
// Text Classifier takes in text and determines whether or not the text
// should be classified as toxic.

public class TextClassifier {
    private Vectorizer vector;
    private Node overallRoot;

    // Pre: Takes in a vectorizer and a splitter.
    // Post: Initializes vectorizer and overallRoot.
    public TextClassifier(Vectorizer vectorizer, Splitter splitter) {
        vector = vectorizer;
        overallRoot = txtHelp(splitter);
    }

    // Pre: Takes in a splitter.
    // Post: Constructs a binary tree using the splitter object and recursion.
    // Returns a root node.
    private Node txtHelp(Splitter splitter) {
        Splitter.Result resName = splitter.split();
        boolean labelName = splitter.label();
        if (resName == null) {
            return new Node(labelName);
        }
        Node root = new Node(labelName);
        root.left = txtHelp(resName.left);
        root.right = txtHelp(resName.right);
        root = new Node(resName.split, labelName, root.left, root.right);
        return root;
    }

    // Pre: Takes in a text.
    // Post: Determines whether or not the text is toxic. Returns a boolean.
    public boolean classify(String text) {
        double[] arr = vector.transform(text)[0];
        Node root = overallRoot;
        boolean b = classHelp(arr, root);
        return b;
    }

    // Pre: Takes in a double array and a node.
    // Post: Traverses the tree using recursion to determine whether or not the 
    // text is toxic. Returns a boolean.
    private boolean classHelp(double[] arr, Node root) {
        Split split = root.split;
        boolean boolName = false;
        if (split == null) {
            return root.label;
        } else if (split.goLeft(arr)) {
            boolName = classHelp(arr, root.left);
        } else if (!split.goLeft(arr)) {
            boolName = classHelp(arr, root.right);
        }
        return boolName;
    }

    // Post: Prints the binary tree.
    public void print() {
        Node root = overallRoot;
        printHelp(overallRoot, 0);
    }

    // Pre: Takes in a node and an int.
    // Post: Traverses through the tree printing the tree with if else statements
    // and adding indentation when needed.
    private void printHelp(Node root, int count) {
        String space = "";
        for (int i = 0; i < count; i++) {
            space = space + " ";
        }
        if (root.split != null) {
            System.out.println(space + "if (" + root.split + ")");
            printHelp(root.left, count + 1);
            System.out.println(space + "else");
            printHelp(root.right, count + 1);
        } else if (root.split == null) {
            System.out.println(space + "return " + root.label + ";");
        }
    }

    // Pre: Takes in an int.
    // Post: Deletes all layers in the binary tree below the depth value.
    public void prune(int depth) {
        Node root = overallRoot;
        pruneHelp(depth, root, 0);
    }

    // Pre: Takes in an int, a node, and another int.
    // Post: Using recursion, traverses to the desired depth layer and severs
    // connection with any nodes beneath said layer. 
    private void pruneHelp(int depth, Node root, int count) {
        if (root != null) {
            if (count < depth) {
                pruneHelp(depth, root.left, count + 1);
                pruneHelp(depth, root.right, count + 1);
            } else if (count == depth) {
                root.split = null;
                root.left = null;
                root.right = null;
            }
        }
    }

    // An internal node or a leaf node in the decision tree.
    private static class Node {
        public Split split;
        public boolean label;
        public Node left;
        public Node right;

        // Constructs a new leaf node with the given label.
        public Node(boolean label) {
            this(null, label, null, null);
        }

        // Constructs a new internal node with the given split, label, and left and right nodes.
        public Node(Split split, boolean label, Node left, Node right) {
            this.split = split;
            this.label = label;
            this.left = left;
            this.right = right;
        }

        // Returns true if and only if this node is a leaf node.
        public boolean isLeaf() {
            return left == null && right == null;
        }
    }
}
