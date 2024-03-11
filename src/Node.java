public class Node implements Comparable<Node> {
    public int studentNumber;
    public Integer mark;
    public Node left;
    public Node right;

    public Node(int studentNumber, Integer mark) {
        this.studentNumber = studentNumber;
        this.mark = mark;
        left = null;
        right = null;
    }

    public Node(String input) {
        input = input.substring(1, input.length()-1);

        String data = input.substring(0, input.indexOf('{'));
        input = input.substring(input.indexOf('{'));

        StringBuilder leftInput = new StringBuilder();

        int openBraces = 0;

        do {
            int openIndex = input.indexOf('{');
            int closedIndex = input.indexOf('}');
            if (openIndex >= 0 && openIndex < closedIndex) {
                leftInput.append(input, 0, openIndex+1);
                input = input.substring(openIndex+1);
                openBraces++;
            } else {
                leftInput.append(input, 0, closedIndex+1);
                input = input.substring(closedIndex+1);
                openBraces--;
            }
        } while (openBraces > 0);

        data = data.substring(2, data.indexOf('%'));

        String[] splitData = data.split(":");

        studentNumber = Integer.parseInt(splitData[0]);

        mark = splitData[1].equals("null") ? null : Integer.parseInt(splitData[1]);

        if (leftInput.length() != 2) {
            left = new Node(leftInput.toString());
        }

        if (input.length() != 2) {
            right = new Node(input);
        }

    }

    public String toString() {
        return "[u" + studentNumber + ":" + (mark == null ? "null" : mark) + "%]";
    }

    @Override
    public int compareTo(Node o) {
        return ((Integer) studentNumber).compareTo((Integer) o.studentNumber);
    }

    public int compareToMark(Node o) {
        return mark == null ? (o.mark == null ? 0 : -1) : (o.mark == null ? 1 : mark.compareTo(o.mark));
    }
}
