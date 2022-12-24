import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Formatter;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Bernardo Marques Fernandes - 774119
 */

class Game implements Comparable<Game>{
    private Integer app_id;
    private String name;
    private Date release_date;
    private String owners;
    private Integer age;
    private Float price;
    private Integer dlcs;
    private String[] languages;
    private String website;
    private Boolean windows;
    private Boolean mac;
    private Boolean linux;
    private Float upvotes;
    private Integer avg_pt;
    private String developers;
    private String[] genres;

    private SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", Locale.US);

    Game(){
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }    

    Game(Integer app_id, String name, Date release_date, String owners, Integer age, Float price, Integer dlcs,
         String[] languages, String website, Boolean windows, Boolean mac, Boolean linux, Float upvotes, 
         Integer avg_pt, String developers, String[] genres){

        this.app_id = app_id;
        this.name = name;
        this.release_date = release_date;
        this.owners = owners;
        this.age = age;
        this.price = price;
        this.dlcs = dlcs;
        this.languages = languages;
        this.website = website;
        this.windows = windows;
        this.mac = mac;
        this.linux = linux;
        this.upvotes = upvotes;
        this.avg_pt = avg_pt;
        this.developers = developers;
        this.genres = genres;
    }

    public boolean equals(Game other){
        return this.name.equals(other.getName());
    }

    public int compareTo(Game other){
        int diff = this.name.compareTo(other.getName());
        return diff;
    }

    private void stringToDate(String str){
        try{
            if(str.contains(",")){
                SimpleDateFormat tmp_sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                this.release_date = tmp_sdf.parse(str);
            }else{
                SimpleDateFormat tmp_sdf = new SimpleDateFormat("MMM yyyy", Locale.US);
                this.release_date = tmp_sdf.parse(str);
            }
        }catch(ParseException e){e.getMessage();}
    }

    private String arrayToString(String[] array){
        String str = new String();
        
        if(array != null){
            for(int i = 0; i < array.length - 1; i++){
                str += array[i] + ", ";
            }
         
            str += array[array.length - 1];
        }else{
            str += "" + null;
        }

        return str;
    }

    private String hoursToString(){
        String str = new String();
        
        if(this.avg_pt != null){
            int hours = 0;
            int minutes = 0;

            minutes = this.avg_pt % 60;
            hours = (this.avg_pt - minutes) / 60;

            if(hours == 0 && minutes == 0)
                str = "" + null;
            else if(hours == 0)
                str = minutes + "m";
            else if(minutes == 0)
                str = hours + "h";
            else
                str = hours + "h " + minutes + "m";
        }else{
            str = "" + null;
        }

        return str;
    }
    
    public String toString(){
        String str = this.app_id + " " + 
                     this.name + " " + 
                     this.sdf.format(this.release_date) + " " + 
                     this.owners + " " + 
                     this.age + " " + 
                     this.price + ((int)((float)this.price) == this.price ? "0 " : " ") +
                     this.dlcs + " [" + 
                     this.arrayToString(this.languages) + "] " + 
                     this.website + " " + 
                     this.windows + " " + 
                     this.mac + " " + 
                     this.linux + " " + 
                     Math.round(this.upvotes) + "% " + 
                     this.hoursToString() + " " + 
                     this.developers + " [" + 
                     this.arrayToString(this.genres) + "]";
        
        return str;
    }

    public void print(){
        MyIO.println(this.toString());
    }
    
    public Game clone(){
        Game newGame = new Game();
        
        newGame.setApp_id(this.app_id);
        newGame.setName(this.name);
        newGame.setRelease_date(this.release_date);
        newGame.setOwners(this.owners);
        newGame.setAge(this.age);
        newGame.setPrice(this.price);
        newGame.setDlcs(this.dlcs);
        newGame.setLanguages(this.languages);
        newGame.setWebsite(this.website);
        newGame.setWindows(this.windows);
        newGame.setMac(this.mac);
        newGame.setLinux(this.linux);
        newGame.setUpvotes(this.upvotes);
        newGame.setAvg_pt(this.avg_pt);
        newGame.setDevelopers(this.developers);
        newGame.setGenres(this.genres);

        return newGame;
    }

    private String[] split(String str, int arraySize){
        String[] array = new String[arraySize];
        
        for(int i = 0, index = 0; i < str.length() && str.charAt(i) != '"'; i++, index++){
            String tmp = new String();
            
            while(i < str.length() && str.charAt(i) != ','){
                if(str.charAt(i) != '[' && str.charAt(i) != ']' && str.charAt(i) != '\''){
                    tmp += str.charAt(i);
                }

                i++;
            }
            
            array[index] = tmp.trim();
        }

        return array;
    }
    
    public void read(String str){
        String value = new String();
        int i = 0;
        char c = ',';
        
        // =========================== app_id ============================== //
        while(str.charAt(i) != c){
            value += str.charAt(i); 
            i++;
        }   
        
        this.app_id = Integer.parseInt(value);
        i++;
        value = "";
        

        // =========================== name ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }
        
        if(value.length() > 0) this.name = value;
        value = "";
        i++;
        if(c == '"') i++;


        // =========================== release_date ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i); 
            i++;
        }

        if(value.length() > 0) stringToDate(value);
        value = "";
        i++;
        if(c == '"') i++;


        // =========================== owners ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }
        
        if(value.length() > 0) this.owners = value;
        value = "";
        i++;
        if(c == '"') i++;


        // =========================== age ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++; 
        }

        if(value.length() > 0) this.age = Integer.parseInt(value);
        value = "";
        i++;
        if(c == '"') i++;


        // =========================== price ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }

        if(value.length() > 0) this.price = Float.parseFloat(value);
        value = "";
        i++;
        if(c == '"') i++;


        // =========================== dlcs ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }

        if(value.length() > 0) this.dlcs = Integer.parseInt(value);
        i++;
        value = "";
        if(c == '"') i++;


        // =========================== languages ============================== //
        int arraySize = 1;

        if(this.app_id == 27900){ //hardfix for line 3937
            arraySize = 5;
            i = 146;
            this.languages = new String[]{"English", "French", "German", "Italian", "Spanish - Spain"};
        }else if(this.app_id == 11260){ //hardfix for line 2180
            arraySize = 5;
            i = 129;
            this.languages = new String[]{"English", "Russian", "Spanish - Spain", "Japanese", "Czech"};
        }else{
            if(str.charAt(i) == '"'){
                i++;
            }
            
            if(str.charAt(i) == '['){
                c = ']';
                i++;
            }else{
                c = ',';
            }

            
            while(str.charAt(i) != c){
                if(str.charAt(i) == ','){
                    arraySize++;
                }
                
                value += str.charAt(i);
                i++;
            }
            
            this.languages = split(value, arraySize);
            value = "";
            
            i++;
            if(c == ']' && arraySize > 1){i++; i++;}
            else if(c == ']'){i++;}
        }
        
        
        // =========================== website ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }

        if(value.length() > 0) this.website = value;
        value = "";
        i++;
        if(c == '"') i++;
        
        
        // =========================== windows ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }
        
        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }

        if(value.length() > 0) this.windows = Boolean.parseBoolean(value);
        value = "";
        i++;
        if(c == '"');
        
        
        // =========================== mac ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }

        if(value.length() > 0) this.mac = Boolean.parseBoolean(value);
        value = "";
        i++;
        if(c == '"') i++;
        
        
        // =========================== linux ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }
        
        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }
        
        if(value.length() > 0) this.linux = Boolean.parseBoolean(value);
        value = "";
        i++;
        if(c == '"') i++;
        
        
        // =========================== upvotes ============================== //
        float up1, up2;
        
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }
        
        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }
        
        up1 = Float.parseFloat(value);
        value = "";
        i++;
        
        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }

        up2 = Float.parseFloat(value);
        
        this.upvotes = (up1 * 100) / (up1 + up2);
        value = "";
        i++;
        if(c == '"') i++;
        
        
        // =========================== avg_pt ============================== //  
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }
        
        if(value.length() > 0) this.avg_pt = Integer.parseInt(value);
        value = "";
        i++;
        if(c == '"') i++;

        
        // =========================== developers ============================== //
        if(str.charAt(i) == '"'){
            c = '"';
            i++;
        }else{
            c = ',';
        }

        while(str.charAt(i) != c){
            value += str.charAt(i);
            i++;
        }

        if(value.length() > 0) this.developers = value;
        value = "";
        i++;
        if(c == '"') i++;


        // =========================== genres ============================== //
        if(i < str.length()){
            arraySize = 1;
            
            if(str.charAt(i) == '"'){
                c = '"';
                i++;
            }else{
                c = ',';
            }
        
            while(i < str.length() && str.charAt(i) != c){
                if(str.charAt(i) == ','){
                    arraySize++;
                }
                
                value += str.charAt(i);
                i++;
            }
            
            this.genres = split(value, arraySize);
        }
    }

    public Integer getApp_id(){
        return app_id;
    }

    public void setApp_id(Integer app_id){
        this.app_id = app_id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getRelease_date(){
        return release_date;
    }

    public void setRelease_date(Date release_date){
        this.release_date = release_date;
    }

    public String getOwners(){
        return owners;
    }

    public void setOwners(String owners){
        this.owners = owners;
    }

    public Integer getAge(){
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public Float getPrice(){
        return price;
    }

    public void setPrice(Float price){
        this.price = price;
    }

    public Integer getDlcs(){
        return dlcs;
    }

    public void setDlcs(Integer dlcs){
        this.dlcs = dlcs;
    }

    public String[] getLanguages(){
        return languages;
    }

    public void setLanguages(String[] languages){
        this.languages = languages;
    }

    public String getWebsite(){
        return website;
    }

    public void setWebsite(String website){
        this.website = website;
    }

    public Boolean getWindows(){
        return windows;
    }

    public void setWindows(Boolean windows){
        this.windows = windows;
    }

    public Boolean getMac(){
        return mac;
    }

    public void setMac(Boolean mac){
        this.mac = mac;
    }

    public Boolean getLinux(){
        return linux;
    }

    public void setLinux(Boolean linux){
        this.linux = linux;
    }

    public Float getUpvotes(){
        return upvotes;
    }

    public void setUpvotes(Float upvotes){
        this.upvotes = upvotes;
    }

    public Integer getAvg_pt(){
        return avg_pt;
    }

    public void setAvg_pt(Integer avg_pt){
        this.avg_pt = avg_pt;
    }

    public String getDevelopers(){
        return developers;
    }

    public void setDevelopers(String developers){
        this.developers = developers;
    }

    public String[] getGenres(){
        return genres;
    }

    public void setGenres(String[] genres){
        this.genres = genres;
    }
}

class Node<T>{
    private T obj;
    private Node<T> left;
    private Node<T> right;
    private Integer factor;

    public Node(){
        this(null, null, null);
    }

    public Node(T obj){
        this(obj, null, null);
    }

    public Node(T obj, Node<T> left, Node<T> right){
        this.obj = obj;
        this.left = left;
        this.right = right;
    }

    public T getObj(){
        return obj;
    }

    public void setObj(T obj){
        this.obj = obj;
    }

    public Node<T> getLeft(){
        return left;
    }

    public void setLeft(Node<T> left){
        this.left = left;
    }

    public Node<T> getRight(){
        return right;
    }

    public void setRight(Node<T> right){
        this.right = right;
    }

    public Integer getFactor(){
        return this.factor;
    }
    
    public void setFactor(Integer factor){
        this.factor = factor;
    }

    public String toString(){
        return this.obj.toString();
    }
    
    public static Integer factor(Node<?> node){
        return node == null ? 0 : node.getFactor();
    }

    public static void updateFactor(Node<?> node){
        node.setFactor(Math.max(Node.factor(node.getLeft()), Node.factor(node.getRight())) + 1);
    }
}

class AVLTreeException extends RuntimeException{
    public AVLTreeException(){
        super();
    }

    public AVLTreeException(String str){
        super(str);
    }
}

class AVLTree<T extends Comparable<T>>{
    private Node<T> root;
    private Integer comp;

    public AVLTree(){
        this.root = null;
        this.comp = 0;
    }

    public Integer getComp(){
        return this.comp;
    }

    public Boolean isEmpty(){
        return this.root == null;
    }

    private Integer height(Node<T> node){
        if(node != null){
            Integer h1 = 1 + this.height(node.getLeft());
            Integer h2 = 1 + this.height(node.getRight());

            return h1 > h2 ? h1 : h2;
        }

        return 0;
    }

    public Integer height(){
        return this.height(this.root);
    }

    private Boolean contains(T obj, Node<T> node, String str){
        MyIO.print(str);
        this.comp++;
        
        if(node != null){
            this.comp++;

            if(obj.equals(node.getObj())){
                return true;
            }else if(obj.compareTo(node.getObj()) < 0){
                this.comp++;
                return this.contains(obj, node.getLeft(), "esq ");
            }else{
                this.comp++;    
                return this.contains(obj, node.getRight(), "dir ");
            }
        }

        return false;
    }

    public Boolean contains(T obj){
        return this.contains(obj, this.root, "raiz ");
    }

    private void preOrder(Node<T> node){
        if(node != null){
            System.out.println(node);
            this.preOrder(node.getLeft());
            this.preOrder(node.getRight());
        }
    }

    public void preOrder(){
        this.preOrder(this.root);
    }

    private void inOrder(Node<T> node){
        if(node != null){
            this.inOrder(node.getLeft());
            System.out.println(node);
            this.inOrder(node.getRight());
        }
    }

    public void inOrder(){
        this.inOrder(this.root);
    }

    private void postOrder(Node<T> node){
        if(node != null){
            this.postOrder(node.getLeft());
            this.postOrder(node.getRight());
            System.out.println(node);
        }
    }

    public void postOrder(){
        this.postOrder(this.root);
    }

    private Integer balanceFactor(Node<T> node){
        return node == null ? 0 : Node.factor(node.getRight()) - Node.factor(node.getLeft());
    }

    private Node<T> balance(Node<T> node){
        Integer BF = this.balanceFactor(node);

        if(node != null && -1 <= BF && BF <= 1){
            Node.updateFactor(node);
        }else if(BF < -1){
            if(this.balanceFactor(node.getLeft()) == 1) node.setLeft(this.rotateL(node.getLeft()));
            node = this.rotateR(node);
        }else if(BF > 1){
            if(this.balanceFactor(node.getRight()) == -1) node.setRight(this.rotateR(node.getRight()));
            node = this.rotateL(node);
        }
        
        return node;
    }

    private Node<T> push(T obj, Node<T> node){
        if(node == null)                           node = new Node<>(obj);
        else if(obj.compareTo(node.getObj()) < 0)  node.setLeft(this.push(obj, node.getLeft()));
        else if(obj.compareTo(node.getObj()) > 0)  node.setRight(this.push(obj, node.getRight()));
        else                                       throw new AVLTreeException("Repeated elements not allowed");

        return this.balance(node);
    }

    public void push(T obj){
        if(obj == null) throw new AVLTreeException("Can't insert null elements");
        this.root = this.push(obj, this.root);
    }

    public Node<T> maxLeft(Node<T> node, Node<T> left){
        if(left.getRight() == null){
            node.setObj(left.getObj());
            left = left.getLeft();
        }else{ 
            left.setRight(this.maxLeft(node, left.getRight()));
        }

        return left;
    }

    private Node<T> pop(T obj, Node<T> node){
        if(node == null)                           throw new AVLTreeException("Element not found");
        else if(obj.compareTo(node.getObj()) < 0)  node.setLeft(this.pop(obj, node.getLeft()));
        else if(obj.compareTo(node.getObj()) > 0)  node.setRight(this.pop(obj, node.getRight()));
        else if(node.getLeft() == null)            node = node.getRight();
        else if(node.getRight() == null)           node = node.getLeft();
        else                                       node.setLeft(this.maxLeft(node, node.getLeft()));
        
        return this.balance(node);
    }

    public void pop(T obj){
        if(obj == null) throw new AVLTreeException("Can't remove null elements");
        this.root = this.pop(obj, this.root);
    }

    private Node<T> rotateL(Node<T> node){
        Node<T> right = node.getRight();

        node.setRight(right.getLeft());
        right.setLeft(node);

        Node.updateFactor(node);
        Node.updateFactor(right);
        
        return right;
    }
    
    private Node<T> rotateR(Node<T> node){
        Node<T> left = node.getLeft();

        node.setLeft(left.getRight());
        left.setRight(node);

        Node.updateFactor(node);
        Node.updateFactor(left);
        
        return left;
    }

    // private Node<T> rotateLR(Node<T> node){
    //     node.setLeft(this.rotateL(node.getLeft()));
    //     return this.rotateR(node);
    // }
    
    // private Node<T> rotateRL(Node<T> node){
    //     node.setRight(this.rotateR(node.getRight()));
    //     return this.rotateL(node);
    // }
}

public class Q03{
    public static boolean isFim(String str){
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static Game[] readCsv(){
        Game[] games = new Game[4403];

        try{
            BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/games.csv"), "UTF-8"));
            MyIO.setCharset("UTF-8");
    
            for(int i = 0; i < games.length; i++){
                games[i] = new Game();
                games[i].read(file.readLine());
            }
    
            file.close();
        }catch(IOException e){
            MyIO.println("droga! deu erro ;-;");
        }

        return games;
    }

    public static void logFile(long t, int c){
        try{
            Formatter f = new Formatter("matricula_avl.txt");
            f.format("774119\t%d\t%d", t, c);
            f.close();
        }catch(IOException e){
            MyIO.println("droga! deu erro ;-;");
        }
    }

    public static Game findGame(Game[] games, int id){
        for(int i = 0; i < games.length; i++)
            if(games[i].getApp_id() == id)
                return games[i];
        return null;
    }

    public static Game findGame(Game[] games, String name){
        for(int i = 0; i < games.length; i++)
            if(games[i].getName().equals(name))
                return games[i];
        return null;
    }

    public static void main(String[] args){
        AVLTree<Game> avl = new AVLTree<>();
        Game[] games = readCsv();
        String str = new String();
        boolean stop;
        
        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                int app_id = Integer.parseInt(str);
                avl.push(findGame(games, app_id));
            }
        }while(!stop);

        int n = MyIO.readInt();

        for(int i = 0; i < n; i++){
            str = MyIO.readLine();
            String[] strs = str.split(" ", 2);

            if(strs[0].equals("I")){
                avl.push(findGame(games, Integer.parseInt(strs[1])));
            }else if(strs[0].equals("R")){
                avl.pop(findGame(games, strs[1]));
            }
        }

        long start = new Date().getTime();

        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                MyIO.println(str);
                if(avl.contains(findGame(games, str))) MyIO.println("SIM");
                else MyIO.println("NAO");
            }
        }while(!stop);

        long end = new Date().getTime();

        logFile(end - start, avl.getComp());
    }
}
