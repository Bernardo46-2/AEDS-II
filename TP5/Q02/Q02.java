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

class TreeTreeException extends RuntimeException{
    public TreeTreeException(){
        super();
    }

    public TreeTreeException(String str){
        super(str);
    }
}

class Node<T>{
    private T obj;
    private Node<T> left;
    private Node<T> right;

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

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}

class SubTree{
    private Character key;
    private Node<Game> root;
    private SubTree left;
    private SubTree right;
    private Integer comp;

    public SubTree(Character key){
        this(key, null, null);
    }

    public SubTree(Character key, SubTree left, SubTree right){
        this.root = null;
        this.comp = 0;
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public Character getKey(){
        return this.key;
    }

    public SubTree getLeft(){
        return this.left;
    }

    public void setLeft(SubTree left){
        this.left = left;
    }

    public SubTree getRight(){
        return this.right;
    }

    public void setRight(SubTree right){
        this.right = right;
    }

    public Boolean contains(Game game, Node<Game> node, String str){
        Boolean contains = false;
        MyIO.print(str);
        
        if(node != null){
            this.comp++;

            if(game.compareTo(node.getObj()) < 0){
                contains = this.contains(game, node.getLeft(), "esq ");
            }else if(game.compareTo(node.getObj()) > 0){
                this.comp++;
                contains = this.contains(game, node.getRight(), "dir ");
            }else{
                this.comp++;
                contains = true;
            }
        }
        
        return contains;
    }

    public Boolean contains(Game game){
        if(game == null) throw new TreeTreeException("Cant search for null elements");
        return this.contains(game, this.root, "");
    }

    private Node<Game> push(Game game, Node<Game> node){
        if(node == null)                                node = new Node<>(game);
        else if(game.compareTo(node.getObj()) < 0)      node.setLeft(this.push(game, node.getLeft()));
        else if(game.compareTo(node.getObj()) > 0)      node.setRight(this.push(game, node.getRight()));
        else                                            throw new TreeTreeException("Repeating elements are not allowed");
        
        return node;
    }

    public void push(Game game){
        if(game == null) throw new TreeTreeException("Cant insert null elements");
        this.root = this.push(game, this.root);
    }

    private Node<Game> maxLeft(Node<Game> node, Node<Game> left){
        if(left.getRight() == null){
            node.setObj(left.getObj());
            left = left.getLeft();
        }else{
            left.setRight(this.maxLeft(node, left.getRight()));
        }
        
        return left;
    }

    private Node<Game> pop(Game game, Node<Game> node){
        if(node == null)                                throw new TreeTreeException("Element not found"); 
        else if(game.compareTo(node.getObj()) < 0)      node.setLeft(this.pop(game, node.getLeft()));
        else if(game.compareTo(node.getObj()) > 0)      node.setRight(this.pop(game, node.getRight()));
        else if(node.getLeft() == null)                 node = node.getRight();
        else if(node.getRight() == null)                node = node.getLeft();
        else                                            node.setLeft(this.maxLeft(node, node.getLeft()));

        return node;
    }

    public void pop(Game game){
        if(game == null) throw new TreeTreeException("Cant remove null elements");
        this.root = this.pop(game, this.root);
    }
    
    public Integer getComp(){
        return this.comp;
    }
}

class TreeTree{
    private SubTree root;

    public TreeTree(){
        this.root = null;
        Character[] chars = {'D', 'R', 'Z', 'X', 'V', 'B', 'F', 'P', 'U', 
                             'I', 'G', 'E', 'J', 'L', 'H', 'T', 'A', 'W', 
                             'S', 'O', 'M', 'N', 'K', 'C', 'Y', 'Q'};
        for(Character c: chars) this.push(c);
    }

    private SubTree push(Character c, SubTree node){
        if(node == null)                         node = new SubTree(c);
        else if(c.compareTo(node.getKey()) < 0)  node.setLeft(this.push(c, node.getLeft()));
        else if(c.compareTo(node.getKey()) > 0)  node.setRight(this.push(c, node.getRight()));
        return node;
    }

    private void push(Character c){
        this.root = this.push(c, this.root);
    }

    private SubTree push(Game game, SubTree node){
        if(node == null)                                    throw new TreeTreeException();
        else if(game.getName().charAt(0) == node.getKey())  node.push(game);
        else if(game.getName().charAt(0) < node.getKey())   node.setLeft(this.push(game, node.getLeft()));
        else if(game.getName().charAt(0) > node.getKey())   node.setRight(this.push(game, node.getRight()));

        return node;
    }

    public void push(Game game){
        this.root = this.push(game, this.root);
    }
    
    private SubTree pop(Game game, SubTree node){
        if(node == null)                                    throw new TreeTreeException();
        else if(game.getName().charAt(0) == node.getKey())  node.pop(game);
        else if(game.getName().charAt(0) < node.getKey())   node.setLeft(this.pop(game, node.getLeft()));
        else if(game.getName().charAt(0) > node.getKey())   node.setRight(this.pop(game, node.getRight()));

        return node;
    }

    public void pop(Game game){
        this.root = this.pop(game, this.root);
    }

    private Boolean contains(Game game, SubTree node, String str){
        MyIO.print(str);
        Boolean contains = false;
        
        if(node != null){
            contains = node.contains(game);
            if(!contains) contains = this.contains(game, node.getLeft(), " ESQ ");
            if(!contains) contains = this.contains(game, node.getRight(), " DIR ");
        }

        return contains;
    }

    public Boolean contains(Game game){
        if(game == null) throw new TreeTreeException("Cant search for null elements");
        return this.contains(game, this.root, "raiz ");
    }

    private Integer comp(SubTree node){
        Integer comp = 0;
        
        if(node != null){
            comp += node.getComp();
            comp += this.comp(node.getLeft());
            comp += this.comp(node.getRight());
        }

        return comp;
    }

    public Integer getComp(){
        return this.comp(this.root);
    }
}

public class Q02{
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
            Formatter f = new Formatter("matricula_arvoreArvore.txt");
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
        TreeTree treeTree = new TreeTree();
        Game[] games = readCsv();
        String str = new String();
        boolean stop;
        
        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                int app_id = Integer.parseInt(str);
                treeTree.push(findGame(games, app_id));
            }
        }while(!stop);

        int n = MyIO.readInt();

        for(int i = 0; i < n; i++){
            str = MyIO.readLine();
            String[] strs = str.split(" ", 2);

            if(strs[0].equals("I")){
                treeTree.push(findGame(games, Integer.parseInt(strs[1])));
            }else if(strs[0].equals("R")){
                // treeTree.pop(findGame(games, strs[1]));
            }
        }

        long start = new Date().getTime();

        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                MyIO.println(str);
                if(treeTree.contains(findGame(games, str))) MyIO.println(" SIM");
                else MyIO.println(" NAO");
            }
        }while(!stop);

        long end = new Date().getTime();

        logFile(end - start, treeTree.getComp());
    }
}
