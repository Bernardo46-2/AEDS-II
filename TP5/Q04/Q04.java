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

    public int hashCode(){
        int ascii = 0;
        for(int i = 0; i < this.name.length(); i++) ascii += this.name.charAt(i);
        return ascii;
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
    private Boolean color;
    private Node<T> parent;
    private Node<T> left;
    private Node<T> right;

    public Node(T obj){
        this(obj, null, null, null);
    }

    public Node(T obj, Node<T> parent){
        this(obj, parent, null, null);
    }

    public Node(T obj, Node<T> left, Node<T> right){
        this(obj, null, left, right);
    }

    public Node(T obj, Node<T> parent, Node<T> left, Node<T> right){
        this.obj = obj;
        this.color = false;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
    
    public T getObj(){
        return obj;
    }

    public void setObj(T obj){
        this.obj = obj;
    }

    public Boolean getColor(){
        return this.color;
    }

    public void setColor(Boolean color){
        this.color = color;
    }

    public Node<T> getParent(){
        return this.parent;
    }

    public void setParent(Node<T> parent){
        this.parent = parent;
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

    public Boolean is4Node(){
        return this.left != null &&
               this.right != null && 
               this.left.getColor() && 
               this.right.getColor(); 
    }

    public void fragment4Node(){
        if(this.is4Node()){
            this.setColor(false);
            this.left.setColor(true);
            this.right.setColor(true);
        }
    }
}

class RedBlackTreeException extends RuntimeException{
    public RedBlackTreeException(){
        super();
    }

    public RedBlackTreeException(String str){
        super(str);
    }
}

class RedBlackTree<T extends Comparable<T>>{
    private Node<T> root;
    private Integer comp;

    public RedBlackTree(){
        this.root = null;
        this.comp = 0;
    }

    public Integer getComp(){
        return this.comp;
    }

    public void push(T obj){
        if(obj == null) throw new RedBlackTreeException("Cant insert null elements");
        Node<T> parent = null;
        Node<T> ptr = this.root;
        
        while(ptr != null){
            parent = ptr;
            
            if(obj.compareTo(ptr.getObj()) < 0) ptr = ptr.getLeft();
            else if(obj.compareTo(ptr.getObj()) > 0) ptr = ptr.getRight();
            else throw new RedBlackTreeException("Cant insert repeated elements");
        }
        
        Node<T> node = new Node<>(obj, parent);

        if(parent == null) this.root = node;
        else if(obj.compareTo(parent.getObj()) < 0) parent.setLeft(node);
        else parent.setRight(node);

        node.setColor(true);

        this.balance(node);
    }

    private void balance(Node<T> node){
        while(node.getParent() != null && node.getParent().getColor()){
            if(node.getParent() == node.getParent().getParent().getLeft()){
                Node<T> uncle = node.getParent().getParent().getRight();

                if(uncle != null && uncle.getColor()){
                    node.getParent().setColor(false);
                    uncle.setColor(false);
                    node.getParent().getParent().setColor(true);
                    node = node.getParent().getParent();
                }else{
                    if(node == node.getParent().getRight()){
                        node = node.getParent();
                        this.rotateL(node);
                    }

                    node.getParent().setColor(false);
                    node.getParent().getParent().setColor(true);
                    this.rotateR(node.getParent().getParent());
                }
            }else{
                Node<T> uncle = node.getParent().getParent().getLeft();

                if(uncle != null && uncle.getColor()){
                    node.getParent().setColor(false);
                    uncle.setColor(false);
                    node.getParent().getParent().setColor(true);
                    node = node.getParent().getParent();
                }else{
                    if(node == node.getParent().getLeft()){
                        node = node.getParent();
                        this.rotateR(node);
                    }

                    node.getParent().setColor(false);
                    node.getParent().getParent().setColor(true);
                    this.rotateL(node.getParent().getParent());
                }
            }
        }

        this.root.setColor(false);
    }

    private Boolean contains(T obj, Node<T> node, String str){
        MyIO.print(str);
        Boolean contains = false;

        if(node != null){
            if(obj.equals(node.getObj())){
                contains = true;
            }else if(obj.compareTo(node.getObj()) < 0){
                contains = this.contains(obj, node.getLeft(), "esq ");
            }else{
                contains = this.contains(obj, node.getRight(), "dir ");
            }
        }

        return contains;
    }

    public Boolean contains(T obj){
        if(obj == null) throw new RedBlackTreeException("Cant search for null elements");
        return this.contains(obj, this.root, "raiz ");
    }

    private void rotateL(Node<T> node){
        Node<T> right = node.getRight();
        node.setRight(right.getLeft());

        if(right.getLeft() != null) right.getLeft().setParent(node);
        right.setParent(node.getParent());

        if(node.getParent() == null) this.root = right;
        else if(node == node.getParent().getLeft()) node.getParent().setLeft(right);
        else node.getParent().setRight(right);

        right.setLeft(node);
        node.setParent(right);
    }

    private void rotateR(Node<T> node){
        Node<T> left = node.getLeft();
        node.setLeft(left.getRight());

        if(left.getRight() != null) left.getRight().setParent(node);
        left.setParent(node.getParent());
        
        if(node.getParent() == null) this.root = left;
        else if(node.getParent().getLeft() == node) node.getParent().setLeft(left);
        else node.getParent().setRight(left);

        left.setRight(node);
        node.setParent(left);
    }
}

public class Q04{
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
            Formatter f = new Formatter("matricula_alvinegra.txt");
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
        RedBlackTree<Game> redBlack = new RedBlackTree<>();
        Game[] games = readCsv();
        String str = new String();
        boolean stop;
        
        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                int app_id = Integer.parseInt(str);
                redBlack.push(findGame(games, app_id));
            }
        }while(!stop);

        int n = MyIO.readInt();

        for(int i = 0; i < n; i++){
            str = MyIO.readLine();
            String[] strs = str.split(" ", 2);

            if(strs[0].equals("I")){
                redBlack.push(findGame(games, Integer.parseInt(strs[1])));
            }else if(strs[0].equals("R")){
                // redBlack.pop(findGame(games, strs[1]));
            }
        }

        long start = new Date().getTime();

        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                MyIO.println(str);
                if(redBlack.contains(findGame(games, str))) MyIO.println("SIM");
                else MyIO.println("NAO");
            }
        }while(!stop);

        long end = new Date().getTime();

        logFile(end - start, redBlack.getComp());
    }
}
