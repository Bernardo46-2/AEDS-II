import java.util.Scanner;

/**
 * @author Bernardo Marques Fernandes - 774119
 */

class Node{
    protected Character elem;
    protected Node right;
    protected Node left;

    public Node(){
        this.elem = null;
        this.right = this.left = null;
    }

    public Node(Character elem){
        this.elem = elem;
        this.right = this.left = null;
    }

    public Node(Character elem, Node left, Node right){
        this.elem = elem;
        this.left = left;
        this.right = right;
    }

    public Character getElem(){
        return elem;
    }

    public void setElem(Character elem){
        this.elem = elem;
    }

    public Node getRight(){
        return right;
    }

    public void setRight(Node right){
        this.right = right;
    }

    public Node getLeft(){
        return left;
    }

    public void setLeft(Node left){
        this.left = left;
    }
}

class BinaryTree{
    protected Node root;

    public BinaryTree(){
        this.root = null;
    }

    public int height(){
        return this.height(this.root);
    }

    private int height(Node node){
        int height = 0;
        
        if(node != null){
            int h1 = 1 + this.height(node.left);
            int h2 = 1 + this.height(node.right);
            height = h1 > h2 ? h1 : h2;
        }

        return height;
    }

    public boolean equals(BinaryTree other){
        return this.equals(this.root, other.root);
    }

    private boolean equals(Node node, Node other){
        boolean answer = true;

        if((node == null && other != null) || (node != null && other == null) || node.elem != other.elem){
            answer = false;
        }else{
            answer = this.equals(node.left, other.left) && this.equals(node.right, other.right);
        }

        return answer;
    }

    public void insert(Character elem){
        this.root = this.insert(this.root, elem);
    }

    private Node insert(Node node, Character elem){
        if(node == null){
            node = new Node(elem);
        }else if(node.elem < elem){
            node.right = this.insert(node.right, elem);
        }else if(node.elem > elem){
            node.left = this.insert(node.left, elem);
        }

        return node;
    }

    public boolean contains(Character elem){
        return this.contains(elem, this.root);
    }
    
    private boolean contains(Character elem, Node node){
        boolean answer = false;
        
        if(node != null){
            if(node.elem == elem){
                answer = true;
            }else if(node.elem > elem){
                answer = this.contains(elem, node.left);
            }else{
                answer = this.contains(elem, node.right);
            }
        }
    
        return answer;
    }

    public String toString(){
        String str = this.printCenter(this.root);
        return str.substring(0, str.length() - 1);
    }
    
    public String toString(String mode){
        String str = new String();
        
        if(mode.equals("center")){
            str = this.printCenter(this.root);
        }else if(mode.equals("pre")){
            str = this.printPre(this.root);
        }else if(mode.equals("pos")){
            str = this.printPos(this.root);
        }

        return str.substring(0, str.length() - 1);
    }

    private String printCenter(Node node){
        String str = new String();
        
        if(node != null){
            str += this.printCenter(node.left);
            str += node.elem + " ";
            str += this.printCenter(node.right);
        }

        return str;
    }

    private String printPre(Node node){
        String str = new String();
        
        if(node != null){
            str += node.elem + " ";
            str += printPre(node.left);
            str += printPre(node.right);
        }

        return str;
    }
    
    private String printPos(Node node){
        String str = new String();
        
        if(node != null){
            str += printPos(node.left);
            str += printPos(node.right);
            str += node.elem + " ";
        }

        return str;
    }

    private Node highestLeft(Node a, Node b){
        if(b.right == null){
            a.setElem(b.getElem());
            b = b.left;
        }else{
            b.right = highestLeft(a, b.right);
        }

        return b;
    }

    public Character remove(Character elem){
        return this.remove(elem, this.root).getElem();
    }
    
    private Node remove(Character elem, Node node){
        if(node != null){
            if(elem > node.getElem()){
                node.left = this.remove(elem, node.left);
            }else if(elem < node.getElem()){
                node.right = this.remove(elem, node.right);
            }else if(node.right == null){
                node = node.left;
            }else if(node.left == null){
                node = node.right;
            }else{
                node.left = highestLeft(node, node.left);
            }
        }
        
        return node;
    }
}

public class Q09{
    public static boolean isFim(String str){
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static void main(String[] args){
        BinaryTree tree = new BinaryTree();
        String str = new String();
        Scanner sc = new Scanner(System.in);

        do{
            str = sc.nextLine().trim();

            if(str.length() < 4){
                if(str.charAt(0) == 'I'){
                    tree.insert(str.charAt(2));
                }else if(str.charAt(0) == 'P'){
                    MyIO.print(str.charAt(2) + " ");

                    if(tree.contains(str.charAt(2))) MyIO.println("existe");
                    else MyIO.println("nao existe");
                }
            }else{
                if(str.equals("INFIXA")){
                    MyIO.println(tree.toString());
                }else if(str.equals("PREFIXA")){
                    MyIO.println(tree.toString("pre"));
                }else if(str.equals("POSFIXA")){
                    MyIO.println(tree.toString("pos"));
                }
            }
        }while(sc.hasNext());

        sc.close();
    }
}
