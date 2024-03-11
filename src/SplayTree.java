public class SplayTree {
    public Node root;
    /*
     * The functions below this line was given
     */

    @Override
    public String toString() {
        return (root == null ? "Empty Tree" : toString(root, "", true));
    }

    public String toString(Node node, String prefix, boolean end) {
        String res = "";
        if (node.right != null) {
            res += toString(node.right, prefix + (end ? "│   " : "    "), false);
        }
        res += prefix + (end ? "└── " : "┌── ") + node + "\n";
        if (node.left != null) {
            res += toString(node.left, prefix + (end ? "    " : "│   "), true);
        }
        return res;
    }

    public String toStringOneLine() {
        return (root == null ? "Empty Tree" : "{" + toStringOneLine(root) + "}");
    }

    public String toStringOneLine(Node node) {
        return node.toString()
                + (node.left == null ? "{}" : "{" + toStringOneLine(node.left) + "}")
                + (node.right == null ? "{}" : "{" + toStringOneLine(node.right) + "}");
    }

    public SplayTree() {
        root = null;
    }

    /*
     * The functions above this line was given
     */

    public SplayTree(String input) {
        if (input.equals("Empty Tree")) {
            root = null;
            return;
        }
        root = new Node(input);
    }

    public Node access(int studentNumber) {
        return access(studentNumber, null);
    }

    public Node access(int studentNumber, Integer mark) {
        Node node = search(studentNumber);

        if (node == null) {
            node = insert(studentNumber, mark);
        }
        splay(node);
        if (mark != null) {
            node.mark = mark;
        }
        return node;
    }

    private void splay(Node node) {
        if (root == node) {
            return;
        }

        Node parent;
        Node grandParent;

        while (root != node) {
            parent = getParent(node);
            grandParent = getParent(parent);
            // Single Rotation
            if (grandParent == null) {
                if (node.compareTo(parent) < 0) {
                    rotateRight(parent, null);
                } else {
                    rotateLeft(parent, null);
                }
            } else {
                // Double Rotation
                if (parent.compareTo(grandParent) < 0) {
                    // Parent is left child
                    if (node.compareTo(parent) < 0) {
                        // Node is left child
                        rotateRight(grandParent);
                        rotateRight(parent);
                    } else {
                        // Node is right child
                        rotateLeft(parent);
                        rotateRight(grandParent);
                    }
                } else {
                    // Parent is right child
                    if (node.compareTo(parent) > 0) {
                        // Node is right child
                        rotateLeft(grandParent);
                        rotateLeft(parent);
                    } else {
                        // Node is left child
                        rotateRight(parent);
                        rotateLeft(grandParent);
                    }
                }
            }
        }
    }

    private Node getParent(Node node) {
        // Begin recursive search
        return getParent(node, root);
    }

    private Node getParent(Node data, Node current) {
        if (current == null) {
            return null;
        }

        // look to the left
        if (data.compareTo(current) < 0) {
            // check left child
            if (current.left.compareTo(data) == 0) {
                return current;
            }
            // Continue search to the left
            return getParent(data, current.left);

        }

        // look to the right
        if (data.compareTo(current) > 0) {
            // Check right child
            if (current.right.compareTo(data) == 0) {
                // Return this node when data is found
                return current;
            }
            // Continue search to the right
            return getParent(data, current.right);
        }

        return null;
    }

    private void rotateRight(Node node) {
        if (node == null) {
            return;
        }
        rotateRight(node, getParent(node));
    }

    private void rotateRight(Node node, Node parent) {
        Node temp = node.left;
        if (parent != null) {
            switch (Integer.compare(node.compareTo(parent), 0)) {
                case -1:
                    parent.left = temp;
                    break;
                case 1:
                    parent.right = temp;
            }
        } else {
            root = temp;
        }

        node.left = node.left.right;
        temp.right = node;
    }

    private void rotateLeft(Node node) {
        if (node == null) {
            return;
        }
        rotateLeft(node, getParent(node));
    }

    private void rotateLeft(Node node, Node parent) {
        Node temp = node.right;
        if (parent != null) {
            switch (Integer.compare(node.compareTo(parent), 0)) {
                case -1:
                    parent.left = temp;
                    break;
                case 1:
                    parent.right = temp;
            }
        } else {
            root = temp;
        }

        node.right = node.right.left;
        temp.left = node;
    }

    private Node insert(int studentNum, Integer mark) {
        if (root == null) {
            root = new Node(studentNum, mark);
            return root;
        }
        return insert(studentNum, mark, root);
    }

    private Node insert(int studentNum, Integer mark, Node node) {
        if (node == null) {
            return null;
        }

        if (studentNum < node.studentNumber) {
            if (node.left == null) {
                node.left = new Node(studentNum, mark);
                return node.left;
            }
            return insert(studentNum, mark, node.left);
        }
        if (node.right == null) {
            node.right = new Node(studentNum, mark);
            return node.right;
        }
        return insert(studentNum, mark, node.right);
    }

    private Node search(int studentNumber) {
        return search(studentNumber, root);
    }

    private Node search(int studentNumber, Node node) {
        if (node == null) {
            return null;
        }

        if (studentNumber < node.studentNumber) {
            return search(studentNumber, node.left);
        }
        if (studentNumber > node.studentNumber) {
            return search(studentNumber, node.right);
        }
        return node;
    }

    public Node remove(int studentNumber) {
        if (root == null) {
            return null;
        }
        access(studentNumber);

        Node element = root;

        Node rightTree = element.right;

        if (element.left == null) {
            root = rightTree;
        } else if (element.right == null) {
            root = element.left;
        } else {
            root = element.left;

            int largest = findLargest();
            access(largest);

            root.right = rightTree;
        }

        element.left = null;
        element.right = null;
        return element;
    }

    private int findLargest() {
        return findLargest(root);
    }

    private int findLargest(Node node) {
        if (node.right == null) {
            return node.studentNumber;
        }
        return findLargest(node.right);
    }

    public String sortByStudentNumber() {
        if (root == null) {
            return "Empty Tree";
        }
        return sortByStudentNumber(new StringBuilder(), root).toString();
    }

    private StringBuilder sortByStudentNumber(StringBuilder sb, Node node) {
        if (node != null) {
            sortByStudentNumber(sb, node.left);
            sb.append(node);
            sortByStudentNumber(sb, node.right);
        }
        return sb;
    }

    private int count(Node node) {
        if (node == null) {
            return 0;
        }
        return count(node.left) + count(node.right) + 1;
    }

    private void populate(Node[] nodes) {
        populate(nodes, root, 0);
    }

    private int populate(Node[] nodes, Node node, int i) {
        if (node == null) {
            return i - 1;
        }
        nodes[i] = node;
        i = populate(nodes, node.left, i + 1);
        i = populate(nodes, node.right, i + 1);
        return i;
    }

    public String sortByMark() {
        if (root == null) {
            return "Empty Tree";
        }

        Node[] students = new Node[count(root)];

        populate(students);

        // sort students
        sort(students);

        StringBuilder sb = new StringBuilder();

        for (Node student : students) {
            sb.append(student.toString());
        }
        return sb.toString();
    }

    private void sort(Node[] nodes) {
        int lastElement = nodes.length - 1;

        // loop to access each array element
        for (int i = 0; i < lastElement; i++)

            // loop to compare array elements
            for (int j = 0; j < lastElement - i; j++)
                if (nodes[j].compareToMark(nodes[j + 1]) > 0) { //array[j] > array[j + 1]
                    Node temp = nodes[j];
                    nodes[j] = nodes[j + 1];
                    nodes[j + 1] = temp;
                } else if (nodes[j].compareToMark(nodes[j + 1]) == 0 && nodes[j].compareTo(nodes[j + 1]) > 0) {
                    Node temp = nodes[j];
                    nodes[j] = nodes[j + 1];
                    nodes[j + 1] = temp;

                }
    }
}
