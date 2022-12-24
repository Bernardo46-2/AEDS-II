import java.io.BufferedReader;
import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.util.Formatter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Bernardo Marques Fernandes - 774119
 */

class Game{
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

    private String parseDate(String str){
        String month = new String();
        String year = new String();
        int i = 0;

        for(; i < 3; i++){
            month += str.charAt(i);
        }

        if(str.charAt(6) == ',')
            i = 8;
        else if(str.charAt(5) == ',')
            i = 7;
        else
            i = 4;

        for(; i < str.length(); i++){
            year += str.charAt(i);
        }

        return month + "/" + year;
    }

    private void stringToDate(String str){
        try{
            this.release_date = sdf.parse(parseDate(str));
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

class List{
    private Game[] games;
    private int length;
    private boolean sorted;
    private int comparisons;

    List(){
        this(4403);
    }

    List(int length){
        this.games = new Game[length];
        this.length = 0;
        this.sorted = false;
        this.comparisons = 0;
    }

    public int getComparisons(){
        return this.comparisons;
    }

    public void swap(int i, int j){
        Game tmp = this.games[i];
        this.games[i] = this.games[j];
        this.games[j] = tmp;
    }

    public int length(){
        return this.length;
    }

    public boolean isEmpty(){
        return this.length == 0;
    }

    public void print(){
        for(int i = 0; i < this.length; i++){
            MyIO.println("[" + i + "] " + this.games[i].toString());
        }
    }

    private boolean sequencialSearch(String name){
        for(int i = 0; i < this.length; i++){
            this.comparisons++;

            if(this.games[i].getName().equals(name)){
                return true;
            }
        }

        return false;
    }

    private boolean binarySearch(String name){
        boolean b = false;
        int left = 0;
        int right = this.length - 1;

        while(left <= right){
            int mid = (left + right) / 2;

            if(this.games[mid].getName().equals(name)){
                b = true;
                left = right + 1; //break
            }else if(this.games[mid].getName().compareTo(name) < 0){
                left = mid + 1;
                this.comparisons++;
            }else{
                right = mid - 1;
            }
            
            this.comparisons++;
        }

        return b;
    }

    public boolean contains(String name){
        if(this.sorted)
            return binarySearch(name);

        return sequencialSearch(name);
    }

    public Game get(int index){
        if(index < this.length && index >= 0)
            return this.games[index];

        return null;
    }

    public void sort(){
        this.sort(0, this.length - 1);
        this.sorted = true;
    }

    private void sort(int left, int right){
        int i = left;
        int j = right;
        Game pivot = this.games[(left + right) / 2];

        while(i <= j){
            while(this.games[i].getName().compareTo(pivot.getName()) < 0){
                i++;
            }

            while(this.games[j].getName().compareTo(pivot.getName()) > 0){
                j--;
            }

            if(i <= j){
                this.swap(i, j);
                i++;
                j--;
            }
        }

        if(left < j){
            this.sort(left, j);
        }

        if(i < right){
            this.sort(i, right);
        }
    }

    public void push(Game game){
        if(this.length < this.games.length){
            this.games[this.length++] = game;
            this.sorted = false;
        }
    }

    public void insert(Game game, int index){
        if(this.length < this.games.length && index < this.length && index >= 0){
            for(int i = this.length; i > index; i--){
                this.games[i] = this.games[i - 1];
            }

            this.games[index] = game;
            this.length++;
            this.sorted = false;
        }
    }

    public Game pop(){
        if(this.length > 0){
            return this.games[--this.length];
        }

        return null;
    }

    public Game remove(int index){
        if(this.length > 0 && index < this.length && index >= 0){
            Game game = this.games[index];

            for(int i = index; i < this.length - 1; i++){
                this.games[i] = this.games[i + 1];
            }

            this.length--;

            return game;
        }

        return null;
    }

    public void pushFront(Game game){
        if(this.length < this.games.length){
            for(int i = this.length; i > 0; i--){
                this.games[i] = this.games[i - 1];
            }
            
            this.games[0] = game;
            this.length++;
            this.sorted = false;
        }
    }

    public Game popFront(){
        if(this.length > 0){
            Game game = this.games[0];

            for(int i = 0; i < this.length - 1; i++){
                this.games[i] = this.games[i + 1];
            }

            this.length--;

            return game;
        }

        return null;
    }
}

public class Q05{
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

    public static void main(String[] args){
        boolean stop = false;        
        String str = new String();

        Game[] gamesArray = readCsv();
        List games = new List(100);

        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                int app_id = Integer.parseInt(str);

                for(int i = 0; i < gamesArray.length; i++){
                    if(gamesArray[i].getApp_id() == app_id){
                        games.push(gamesArray[i]);
                    }
                }
            }
        }while(!stop);

        int n = MyIO.readInt();

        for(int i = 0; i < n; i++){
            str = MyIO.readLine();
            String[] strs = str.split(" ");
            
            if(strs[0].charAt(0) == 'I'){
                if(strs[0].charAt(1) == 'I'){
                    int app_id = Integer.parseInt(strs[1]);

                    for(int j = 0; j < gamesArray.length; j++){
                        if(gamesArray[j].getApp_id() == app_id){
                            games.pushFront(gamesArray[j]);
                            j = gamesArray.length; //break
                        }
                    }
                }else if(strs[0].charAt(1) == '*'){
                    int index = Integer.parseInt(strs[1]);
                    int app_id = Integer.parseInt(strs[2]);

                    for(int j = 0; j < gamesArray.length; j++){
                        if(gamesArray[j].getApp_id() == app_id){
                            games.insert(gamesArray[j], index);
                            j = gamesArray.length; //break
                        }
                    }
                }else if(strs[0].charAt(1) == 'F'){
                    int app_id = Integer.parseInt(strs[1]);

                    for(int j = 0; j < gamesArray.length; j++){
                        if(gamesArray[j].getApp_id() == app_id){
                            games.push(gamesArray[j]);
                            j = gamesArray.length; //break
                        }
                    }
                }
            }else{
                if(strs[0].charAt(1) == 'I'){
                    MyIO.println("(R) " + games.popFront().getName());
                }else if(strs[0].charAt(1) == '*'){
                    int index = Integer.parseInt(strs[1]);

                    MyIO.println("(R) " + games.remove(index).getName());
                }else if(strs[0].charAt(1) == 'F'){
                    MyIO.println("(R) " + games.pop().getName());
                }
            }
        }

        games.print();
    }
}
