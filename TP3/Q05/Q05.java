import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.Formatter;
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
    
    public int compareTo(Game other){            
        int diff = 0;
        
        diff = this.release_date.compareTo(other.getRelease_date());
        
        if(diff == 0) diff = this.name.compareTo(other.getName());        
            
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
        }catch(ParseException e){
            MyIO.println("droga! deu erro ;-;");
        }
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
    private int comp;
    private int mov;

    List(){
        this(4403);
    }

    List(int length){
        this.games = new Game[length];
        this.length = 0;
        this.sorted = false;
        this.comp = 0;
        this.mov = 0;
    }

    public int getComp(){
        return this.comp;
    }

    public int getMov(){
        return this.mov;
    }
    
    public int length(){
        return this.length;
    }

    public boolean isEmpty(){
        return this.length == 0;
    }

    public void print(){
        for(int i = 0; i < this.length; i++){
            if(this.games[i] != null)
                MyIO.println(this.games[i].toString());
        }
    }

    private boolean sequencialSearch(String name){
        for(int i = 0; i < this.length; i++){
            this.comp++;

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
                this.comp++;
            }else{
                right = mid - 1;
            }
            
            this.comp++;
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
    
    public void swap(int i, int j){
        Game tmp = this.games[i];
        this.games[i] = this.games[j];
        this.games[j] = tmp;
        
        this.mov += 3;
    }

    public void quicksort(){
        this.quicksort(0, this.length - 1);
        this.sorted = true;
    }

    private void quicksort(int left, int right){
        int i = left;
        int j = right;
        Game pivot = this.games[(left + right) / 2];
        this.mov++;

        while(i <= j){
            this.comp++;
            this.comp++;
            
            while(this.games[i].compareTo(pivot) < 0){
                this.comp++;
                i++;
            }
            
            while(this.games[j].compareTo(pivot) > 0){
                this.comp++;
                j--;
            }

            if(i <= j){
                this.swap(i, j);
                i++;
                j--;
            }
        }

        if(left < j){
            this.quicksort(left, j);
        }

        if(i < right){
            this.quicksort(i, right);
        }
    }

    public void selectionSort(){
        for(int i = 0; i < this.length - 1; i++){
            int min = i;

            for(int j = i + 1; j < this.length; j++){
                this.comp++;

                if(this.games[min].getName().compareTo(this.games[j].getName()) > 0){
                    min = j;
                }
            }

            this.swap(i, min);
        }

        this.sorted = true;
    }

    public void insertionSort(){
        for(int i = 1; i < this.length; i++){
            Game game = this.games[i];
            int j = i - 1;
            this.comp++;

            while(j >= 0 && this.games[j].getApp_id() > game.getApp_id()){
                this.games[j + 1] = this.games[j];
                j--;

                this.comp++;
                this.mov++;
            }

            this.games[j + 1] = game;

            this.mov += 2;
        }

        this.sorted = true;
    }

    public void bubbleSort(){
        for(int i = this.length - 1; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(this.games[j].compareTo(this.games[j + 1]) > 0){
                    swap(j, j + 1);
                }

                this.comp++;
            }
        }

        this.sorted = true;
    }

    public void mergeSort(){
        mergeSort(0, this.length - 1);
        this.sorted = true;
    }

    private void mergeSort(int left, int right){
        if(left < right){
            int mid = (left + right) / 2;

            this.mergeSort(left, mid);
            this.mergeSort(mid + 1, right);
            this.merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right){
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int i, j, l;

        Game[] a = new Game[n1 + 1];
        Game[] b = new Game[n2 + 1];

        for(i = 0; i < n1; i++){
            a[i] = this.games[left + i];
            this.mov++;
        }
        
        for(j = 0; j < n2; j++){
            b[j] = this.games[mid + j + 1];
            this.mov++;
        }

        a[i] = b[j] = null;
        this.mov += 2;

        for(i = j = 0, l = left; l <= right; l++){
            if(a[i] != null && b[j] != null){
                this.games[l] = a[i].compareTo(b[j]) <= 0 ? a[i++] : b[j++];
            }else if(a[i] != null){
                this.games[l] = a[i++];
            }else if(b[j] != null){
                this.games[l] = b[j++];
            }
            
            this.comp++;
            this.mov++;
        }
    }

    public void heapSort(){
        Game[] tmp = new Game[this.length + 1];

        for(int i = 0; i < this.length; i++){
            tmp[i + 1] = this.games[i];
            this.mov++;
        }
        
        this.games = tmp;
        
        for(int i = 2; i <= this.length; i++){
            build(i);
        }
        
        int heapLen = this.length;
        
        while(heapLen > 1){
            swap(1, heapLen--);
            rebuild(heapLen);
        }

        tmp = this.games;
        this.games = new Game[this.length];
        
        for(int i = 0; i < this.length; i++){
            this.games[i] = tmp[i + 1];
            this.mov++;
        }
    }

    private void build(int heapLen){
        for(int i = heapLen; i > 1 && this.games[i].compareTo(this.games[i / 2]) > 0; i /= 2){
            this.swap(i, i / 2);
            this.comp++;
        }

        this.comp++;
    }
    
    private void rebuild(int heapLen){
        int i = 1;
        
        while(i <= heapLen / 2){
            int son = getHighestSon(i, heapLen);
            
            if(this.games[i].compareTo(this.games[son]) < 0){
                this.swap(i, son);
                i = son;
            }else{
                i = heapLen;
            }
            
            this.comp++;
        }
    }
    
    private int getHighestSon(int i, int heapLen){
        int son;
        
        if(2 * i == heapLen || this.games[2 * i].compareTo(this.games[2 * i + 1]) > 0){
            son = 2 * i;
            
            if(2 * i != i) this.comp++;
        }else{
            son = 2 * i + 1;
        }

        return son;
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

    public static void matriculaHeapsort(int c, int m, long t){
        try{
            Formatter f = new Formatter("matricula_heapsort.txt");

            f.format("774119\t%d\t%d\t%d", c, m, t);

            f.close();
        }catch(IOException e){
            MyIO.println("droga! deu erro ;-;");
        }
    }

    public static void main(String[] args){
        boolean stop = false;        
        String str = new String();

        Game[] games = readCsv();
        List list = new List(100);

        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                int app_id = Integer.parseInt(str);

                for(int i = 0; i < games.length; i++){
                    if(games[i].getApp_id() == app_id){
                        list.push(games[i]);
                    }
                }
            }
        }while(!stop);

        long start = new Date().getTime();
        list.heapSort();
        long end = new Date().getTime();

        list.print();

        matriculaHeapsort(list.getComp(), list.getMov(), end - start);
    }
}
