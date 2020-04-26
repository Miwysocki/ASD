
public class Node {
    String word;
    Node left;
    Node right;
    Node translation;

    public Node() {
        // TODO Auto-generated constructor stub
    }
    
    public Node(String word, Node trans) {
        super();
        this.word = word;
        this.translation = trans;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getTrans() {
        return translation;
    }

    public void setTrans(Node trans) {
        this.translation = trans;
    }

    public Node(String word) {
        super();
        this.word = word;
    }

    public Node(Node n) {
        super();
        this.translation = n;
        this.word = n.word;
    }


}
