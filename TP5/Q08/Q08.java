/**
 * @author Bernardo Marques Fernandes - 774119
 */

class Node{
    protected Integer elem;
    protected Node right;
    protected Node left;

    public Node(){
        this.elem = null;
        this.right = this.left = null;
    }

    public Node(Integer elem){
        this.elem = elem;
        this.right = this.left = null;
    }

    public Node(Integer elem, Node left, Node right){
        this.elem = elem;
        this.left = left;
        this.right = right;
    }

    public Integer getElem(){
        return elem;
    }

    public void setElem(Integer elem){
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

        if((node == null && other != null) || (node != null && other == null) || !node.elem.equals(other.elem)){
            answer = false;
        }else{
            answer = this.equals(node.left, other.left) && this.equals(node.right, other.right);
        }

        return answer;
    }

    public void insert(Integer elem){
        this.root = this.insert(this.root, elem);
    }

    private Node insert(Node node, Integer elem){
        if(node == null){
            node = new Node(elem);
        }else if(node.elem.compareTo(elem) < 0){
            node.right = this.insert(node.right, elem);
        }else if(node.elem.compareTo(elem) > 0){
            node.left = this.insert(node.left, elem);
        }

        return node;
    }

    public boolean contains(Integer elem){
        return this.contains(elem, this.root);
    }
    
    private boolean contains(Integer elem, Node node){
        boolean answer = false;
        
        if(node != null){
            if(node.elem.equals(elem)){
                answer = true;
            }else if(node.elem.compareTo(elem) > 0){
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

    public Integer remove(Integer elem){
        return this.remove(elem, this.root).getElem();
    }
    
    private Node remove(Integer elem, Node node){
        if(node != null){
            if(elem.compareTo(node.getElem()) > 0){
                node.left = this.remove(elem, node.left);
            }else if(elem.compareTo(node.getElem()) < 0){
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

public class Q08{
    public static boolean isFim(String str){
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static void foo(int n){
        BinaryTree tree = new BinaryTree();
        int limit = Integer.parseInt(MyIO.readLine().trim());

        String[] nums = MyIO.readLine().trim().split(" ");

        for(int i = 0; i < limit; i++){
            tree.insert(Integer.parseInt(nums[i]));
        }

        MyIO.println("Case " + n + ":");
        MyIO.println("Pre.: " + tree.toString("pre"));
        MyIO.println("In..: " + tree.toString("center"));
        MyIO.println("Post: " + tree.toString("pos"));
    }

    public static void main(String[] args){
        int n = Integer.parseInt(MyIO.readLine().trim());
        for(int i = 0; i < n - 1; i++){
            foo(i + 1);
            MyIO.println("");
        }

        foo(n);
    }
}
