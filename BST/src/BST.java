import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BST {

    public static Node found;
    public static Node root_pl;
    public static Node root_eng;
    public static Scanner sc = null;
    public static PrintWriter writer = null;
    public static boolean exist = false;
    public static boolean file = true;

    public static void add(Node current, Node node) {
        if(root_pl == null && !file)
        {
            root_pl = node;
            return;
        }

        if(root_eng == null)
        {
            root_eng = node;
            return;
        }
        String word = node.word;
        int compare = word.compareToIgnoreCase(current.word);

        if (compare <0 ) {    //word < current.word)

            if(current.left == null)
            {
                current.left=node;
                return;
            }
            add(current.left, node);


        } else if (compare >= 0) { //word > current.word

            if(current.right == null)
            {
                current.right=node;
                return;
            }
            add(current.right, node);

        } else {
            // value already exists
            System.out.println("value already exists");
        }

    }


    private static Node Search (Node current,String sought) {

        if(current == null) {
            return null;
        }
        if(current.word.equalsIgnoreCase(sought)) found = current;

        int comp =  sought.compareToIgnoreCase(current.word);

        if (comp <0 ) {    //word < current.word)
            Search (current.left, sought);
        } else if (comp > 0) { //word > current.word
            Search (current.right, sought);
        } else {
            // value already exists
            exist = true;
            return found;
        }
        return found;
    }


    private static Node deleteNode(Node root, String removed, boolean polish) { //cannot delete root
        if(root == null) return root;
        int comp =  removed.compareToIgnoreCase(root.word);
        if(comp < 0) {
            root.setLeft(deleteNode(root.getLeft(), removed, polish));
        } else if(comp > 0) {
            root.setRight(deleteNode(root.getRight(), removed, polish));
        } else {
            //found
        	//removing translation (if it has one)
            Node removed_translation=root.translation;
            if(removed_translation != null) { 
            if(polish) deleteNode(root_eng,removed_translation.word,polish);
            else deleteNode(root_pl,removed_translation.word,polish);
            	}
            // node with no leaf nodes
            if(root.getLeft() == null && root.getRight() == null) {
                System.out.println("deleting "+removed);
                return null;
            } else if(root.getLeft() == null) {
                // node with one node (no left node)
                System.out.println("deleting "+removed);
                return root.getRight();
            } else if(root.getRight() == null) {
                // node with one node (no right node)
                System.out.println("deleting "+removed);
                return root.getLeft();
            } else {
                // nodes with two nodes
                // search for min number in right sub tree
                String minValue = minValue(root.getRight()); //minValue = farest left node
                root.setWord(minValue);
                root.setRight(deleteNode(root.getRight(), minValue,polish));
                System.out.println("deleting "+removed);
            }
        }
        return root;
    }

    private static String minValue(Node node) {

        if(node.getLeft() != null) {
            return minValue(node.getLeft());
        }
        return node.getWord();
    }

    public static void main(String[] args) throws FileNotFoundException {
        sc = new Scanner(new File("In0402.txt"));
        writer = new PrintWriter(new File("OutA0402.txt"));


        while (sc.hasNext())   //reading from file
        {
            exist = false;
            String k =sc.next();
            Search(root_eng,k); //checking for repetitions
            if(exist == false)
            {
               writer.print(k+"\n");
               Node newNode = new Node(k);//adding to english tree
               add(root_eng,newNode);
            }


        }
        file= false;
        sc.close();
        writer.close();

        // TODO Auto-generated method stub
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("1.Add word");
            System.out.println("2.Search");
            System.out.println("3.Show");
            System.out.println("4.Delete");
            System.out.println("5.Usun (polskie)");
            System.out.println("6.Second part");
            int w = in.nextInt();
            switch(w){
                case 1:
                    System.out.println("podaj slowo ");
                    String word_pl = in.next();
                    System.out.println("add translation ");

                    String word_en = in.next();
                    if(word_en== null) word_pl = "'"+word_en+"'";
                    Node nowe = new Node(word_pl);

                    Node new_node = new Node(word_en, nowe); //connecting new nodes
                    nowe.translation = new_node;

                    add(root_pl, nowe);

                    add(root_eng, new_node);
                    break;
                case 2:
                    System.out.println("podaj szukane sÅ‚owo ");
                    String sought = in.next();
                    found = null;
                    Search(root_pl,sought);
                    if(found != null) {
                        System.out.println(found.word + " " + found.translation.word);
                    }
                    else System.out.println("nie ma takiego slowa w bazie");
                    break;
                case 3:
                    System.out.println("wypisywanie polskiego drzewa BST");
                    showTree(root_pl);
                    System.out.println("wypisywanie angielskiego drzewa BST");
                    showTree(root_eng);
                    break;

                case 4:
                    System.out.println("Delet word:");
                    String sought2 = in.next();
                    deleteNode(root_eng,sought2, false);
                    break;
                case 5: //gdy recznie uzupelnjiono
                    System.out.println("Podaj slowo");
                    String usuwane = in.next();
                    deleteNode(root_pl,usuwane,true);
                    break;
                case 6:
                	Second();
                    break;
            }
        }

    }

    private static void Second() throws FileNotFoundException {	//W drugim kroku tworzone jest (polskie) drzewo t³umaczeñ
        sc = new Scanner(new File("OutA0402.txt"));
        writer = new PrintWriter(new File("OutB0402.txt"));
        while (sc.hasNext()) {  //reading file
            String a =sc.next(); //eng
            String p;
            if(a.equalsIgnoreCase("the")||a.equalsIgnoreCase("of") || a.equalsIgnoreCase("a") || a.equalsIgnoreCase("an"))
            {
                Node angielskie=Search(root_eng,a);
                 p = "'" + a + "'"; // special words
            }
            else
                 p =sc.next();

           Node angielskie=Search(root_eng,a);
           Node polskie = new Node(p,angielskie);
                angielskie.translation = polskie;

           add(root_pl,polskie);
        }
        to_file(root_eng); //english with translation to file outB
        sc.close();
        writer.close();
    }

    public static void showTree(Node focusNode) {
        if (focusNode != null) {
            System.out.println(focusNode.word);
            showTree(focusNode.left);
            showTree(focusNode.right);
        }
    }
    public static void to_file(Node focusNode) {
        if (focusNode != null) {
            writer.print(focusNode.word+" ");
            writer.print(focusNode.translation.word+"\n");
            //System.out.println("lewe");
            to_file(focusNode.left);
            //System.out.println("prawe");
            to_file(focusNode.right);
        }
    }
}
