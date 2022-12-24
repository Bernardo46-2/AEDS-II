/**
 * @author Bernardo Marques Fernandes - 774119
 */

class Node{
    private Integer elem;
    protected Node left;
    protected Node right;
    protected Node up;
    protected Node down;
    
    public Node(){
        this(null, null, null, null, null);
    }

    public Node(Integer elem){
        this(elem, null, null, null, null);
    }

    public Node(Node left, Node right, Node up, Node down){
        this(null, left, right, up, down);
    }
    
    public Node(Integer elem, Node left, Node right, Node up, Node down){
        this.elem = elem;
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }
    
    public Integer getElem(){
        return elem;
    }

    public void setElem(Integer elem){
        this.elem = elem;
    }
}

class Matrix{
    private int rows;
    private int columns;
    private Node start;
    
    public Matrix(){
        this(4, 4);
    }

    public Matrix(int rows, int columns){
        this(rows, columns, null);
    }

    public Matrix(int rows, int columns, Integer initial){
        this.rows = rows;
        this.columns = columns;

        this.start = new Node(initial);
        Node ptr = this.start;


        // ==== First row ==== //

        ptr.right = new Node(initial);
        ptr.right.left = ptr;

        ptr.down = new Node(initial);
        ptr.down.up = ptr;
        
        ptr = ptr.right;

        for(int i = 1; i < columns - 1; i++){
            ptr.right = new Node(initial);
            ptr.right.left = ptr;
            
            ptr.down = new Node(initial);
            ptr.down.up = ptr;

            ptr = ptr.right;
        }

        ptr.down = new Node(initial);
        ptr.down.up = ptr;
        
        ptr = ptr.down;
        

        // ==== Middle rows ==== //

        int i = 1;

        for(i = 1; i < rows - 1; i++){
            if(i % 2 == 0){
                for(int j = 0; j < columns - 1; j++){
                    ptr.right = ptr.up.right.down;
                    ptr.right.left = ptr;

                    ptr.down = new Node(initial);
                    ptr.down.up = ptr;

                    ptr = ptr.right;
                }

                ptr.down = new Node(initial);
                ptr.down.up = ptr;
            }else{
                for(int j = 0; j < columns - 1; j++){
                    ptr.left = ptr.up.left.down;
                    ptr.left.right = ptr;
                    
                    ptr.down = new Node(initial);
                    ptr.down.up = ptr;
                    
                    ptr = ptr.left;
                }

                ptr.down = new Node(initial);
                ptr.down.up = ptr;
            }

            ptr = ptr.down;
        }


        // ==== Last row ==== //

        if(i % 2 == 0){
            ptr.right = ptr.up.right.down;
            ptr.right.left = ptr;

            for(i = 1; i < columns; i++){
                ptr.right = ptr.up.right.down;
                ptr.right.left = ptr;

                ptr = ptr.right;
            }
        }else{
            ptr.left = ptr.up.left.down;
            ptr.left.right = ptr;

            for(i = 1; i < columns; i++){
                ptr.left = ptr.up.left.down;
                ptr.left.right = ptr;

                ptr = ptr.left;
            }
        }
    }

    public int getRows(){
        return this.rows;
    }

    public int getColumns(){
        return this.columns;
    }

    public void print(){
        Node ptr = this.start;
        
        for(int i = 0; i < this.rows; i++){
            int j;

            for(j = 0; j < this.columns - 1; j++){
                System.out.print(ptr.getElem() + " ");
                ptr = ptr.right;
            }
            
            System.out.println(ptr.getElem() + " ");

            while(ptr.left != null) ptr = ptr.left;
            ptr = ptr.down;
        }    
    }

    public void push(Integer n){
        Node ptr = this.start;
        
        for(int i = 0; i < this.rows; i++){
            int j;

            for(j = 0; j < this.columns - 1; j++){
                if(ptr.getElem() != null){
                    ptr = ptr.right;
                }else{
                    ptr.setElem(n);
                    ptr = null;
                    
                    j = this.columns; //break
                    i = this.rows; //break
                }
            }

            if(j == this.columns - 1){
                if(ptr.getElem() == null){
                    ptr.setElem(n);
                    ptr = null;

                    i = this.rows; //break
                    j = this.columns; //break
                }else{
                    while(ptr.left != null) ptr = ptr.left;
                    ptr = ptr.down;
                }
            }
        }
    }

    public boolean isSquare(){
        return this.rows == this.columns;
    }

    public Integer nth(int n, int m){
        if(n >= 0 && n < this.rows && m >= 0 && m < this.columns){
            Node ptr = this.start;
            
            for(int i = 0; i < n; i++) ptr = ptr.down;
            for(int i = 0; i < m; i++) ptr = ptr.right;

            Integer elem = ptr.getElem();
            ptr = null;

            return elem;
        }

        return null;
    }

    public void setNth(int n, int m, int elem){
        if(n >= 0 && n < this.rows && m >= 0 && m < this.columns){
            Node ptr = this.start;
            
            for(int i = 0; i < n; i++) ptr = ptr.down;
            for(int i = 0; i < m; i++) ptr = ptr.right;

            ptr.setElem(elem);
        }
    }

    public void printMainDiagonal(){
        if(this.isSquare()){
            Node ptr = this.start;

            for(int i = 0; i < this.rows - 1; i++){
                System.out.print(ptr.getElem() + " ");

                ptr = ptr.right.down;
            }
            
            System.out.println(ptr.getElem() + " ");
            ptr = null;
        }
    }

    public void printSecondaryDiagonal(){
        if(this.isSquare()){
            Node ptr = this.start;
            while(ptr.right != null) ptr = ptr.right;

            for(int i = 0; i < this.rows - 1; i++){
                System.out.print(ptr.getElem() + " ");

                ptr = ptr.left.down;
            }

            System.out.println(ptr.getElem() + " ");
            ptr = null;
        }
    }

    public Matrix sum(Matrix other){
        if(this.rows == other.getRows() && this.columns == other.getColumns()){
            Matrix matrix = new Matrix(this.rows, this.columns);

            for(int i = 0; i < this.rows; i++){
                for(int j = 0; j < this.columns; j++){
                    matrix.push(this.nth(i, j) + other.nth(i, j));
                }
            }

            return matrix;
        }
        
        return null;
    }

    public Matrix mul(Matrix other){
        if(this.columns == other.getRows()){
            Matrix matrix = new Matrix(this.rows, other.getColumns(), 0);

            for(int i = 0; i < this.rows; i++){
                for(int j = 0; j < other.getColumns(); j++){
                    for(int l = 0; l < other.getRows(); l++){
                        matrix.setNth(i, j, matrix.nth(i, j) + (this.nth(i, l) * other.nth(l, j)));
                    }
                }
            }
            
            return matrix;
        }
        
        return null;
    }
}

public class Q07{
    public static boolean isFim(String str){
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static void main(String[] args){
        int n = MyIO.readInt();

        for(int l = 0; l < n; l++){
            int rows = MyIO.readInt();
            int columns = MyIO.readInt();

            Matrix matrix1 = new Matrix(rows, columns);
            Matrix matrix2 = new Matrix(rows, columns);

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    matrix1.push(MyIO.readInt());
                }
            }

            rows = MyIO.readInt();
            columns = MyIO.readInt();

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    matrix2.push(MyIO.readInt());
                }
            }

            matrix1.printMainDiagonal();
            matrix1.printSecondaryDiagonal();

            matrix1.sum(matrix2).print();
            matrix1.mul(matrix2).print();
        }
    }
}
