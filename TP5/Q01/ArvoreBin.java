import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class ArvoreBin {
    public static boolean isFim(String s){
        return s.equals("FIM");
    }

    public static Game[] readCsv(){
        Game[] games = new Game[4403];

        try{
            BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream("../games.csv"), "UTF-8"));
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

    public static Game findGame(Game[] games, int id){
        for(int i = 0; i < games.length; i++)
            if(games[i].getAppId() == id)
                return games[i];
        return null;
    }

    public static Game findGame(Game[] games, String name){
        for(int i = 0; i < games.length; i++)
            if(games[i].getName().equals(name))
                return games[i];
        return null;
    }

    public static void main(String[] args) throws Exception{
        ArvoreBinaria tree = new ArvoreBinaria();
        Game[] games = readCsv();
        String str = new String();
        boolean stop;
        
        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                int app_id = Integer.parseInt(str);
                tree.inserir(findGame(games, app_id));
            }
        }while(!stop);

        int n = MyIO.readInt();

        for(int i = 0; i < n; i++){
            str = MyIO.readLine();
            String[] strs = str.split(" ", 2);

            if(strs[0].equals("I")){
                tree.inserir(findGame(games, Integer.parseInt(strs[1])));
            }else if(strs[0].equals("R")){
                tree.remover(findGame(games, strs[1]));
            }
        }

        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop) tree.pesquisar(findGame(games, str));
        }while(!stop);

        // MyIO.setCharset("UTF-8");

        // Game[] games = new Game[4403];
        // // for(int i = 0; i < 4403; i++) games.add(null);

        // Game Aux = new Game();
        // ArvoreBinaria arvore = new ArvoreBinaria();
        // String s, str, master, search;
        // String file = "../games.csv";
        // int modeID;

        // BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        // for(int i = 0; i < games.length; i++){
        //     s = br.readLine();
        //     Aux.read(s);
        //     games[i] = Aux;
        // }
        // br.close();
        // System.out.println(games[0].getAppId());

        // -------------------------- primeira parte da entrada --------------------------------------
        // games.get(0).print();
        // while(true){
        //     str = MyIO.readLine();
            
        //     if (isFim(str)) {
        //         break;
        //     }

        //     System.out.println(str);
        //     int ID = Integer.parseInt(str);

        //     for(int i = 0; i < games.size(); i++){
        //         Aux = games.get(i);
                
        //         if (ID == Aux.getAppId()){
        //             Aux.print();
        //             arvore.inserir(Aux);
        //         }
        //     }
        // }

        // // -------------------------- segunda parte da entrada -----------------------------------------
        // int cont = MyIO.readInt();
        // for(int j = 0; j < cont; j++){
        //     master = MyIO.readLine();
            
        //     if(master.charAt(0) == 'I'){
        //         modeID = Integer.parseInt(master.substring(2, master.length()));
        //         for(Game game : games){
        //             if (modeID == game.getAppId()) arvore.inserir(game);
        //         }
        //     }
        //     else if(master.charAt(0) == 'R'){
        //         for(Game game : games){
        //             if (master.substring(2, master.length()) == game.getName()) arvore.remover(game);
        //         }
        //     }
        // }

        // // -------------------------- terceira parte da entrada -----------------------------------------
        // while(true){
        //     search = MyIO.readLine();

        //     if (isFim(search)) break;

        //     for(Game game : games){
        //     if(search == game.getName()) arvore.pesquisar(game);
        //     }
        // }
        
    }
}

class No{

    //-------------------------- atributos ----------------------------------
    public Game elemento;
    public No esq, dir;
    //-----------------------------------------------------------------------
    //-------------------------- construtor ---------------------------------
    public No(Game elemento){
        this(elemento,null,null);
    }
    public No(Game elemento, No esq, No dir){
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
    //-----------------------------------------------------------------------
}

class ArvoreBinaria{
    //-------------------------- atributos ----------------------------------
    private No raiz;
    //-----------------------------------------------------------------------
    //-------------------------- construtor ---------------------------------
    public ArvoreBinaria(){
    raiz = null;
    }
    //-----------------------------------------------------------------------

    public boolean pesquisar(Game x) {
        MyIO.println(x.getName());
		MyIO.print("=> raiz ");
        return pesquisar(x, raiz);
	}

	/**
	 * Metodo privado recursivo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @param i No em analise.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	private boolean pesquisar(Game x, No i) {
      boolean resp;
		if (i == null) {
            MyIO.println("NAO");
         resp = false;

      } else if (x.getName().compareTo(i.elemento.getName()) == 0) {
        MyIO.println("SIM"); 
        resp = true;

      } else if (x.getName().compareTo(i.elemento.getName()) < 0) {
        MyIO.print("esq "); 
        resp = pesquisar(x, i.esq);

      } else {
        MyIO.print("dir ");
         resp = pesquisar(x, i.dir);
      }
      return resp;
	}

    // ----------------------- inserir ----------------------- 

    public void inserir(Game x) throws Exception {
		raiz = inserir(x, raiz);
	}

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se o elemento existir.
	 */
	private No inserir(Game x, No i) throws Exception {
		if (i == null) {
         i = new No(x);

      } else if (x.getName().compareTo(i.elemento.getName()) < 0) {
         i.esq = inserir(x, i.esq);

      } else if (x.getName().compareTo(i.elemento.getName()) > 0) {
         i.dir = inserir(x, i.dir);

      } else {
         throw new Exception("Erro ao inserir!");
      }

		return i;
	}

    // -------------------------------------------------------

    // ----------------------- remover -----------------------

    public void remover(Game game) throws Exception {
        raiz = remover(game, raiz);
    }
	private No remover (Game newGame, No i) throws Exception{
  
          if (i == null) {
           throw new Exception("ERRO ArvoreBinaria.remover ( " + 
                               newGame.getName() + " ): Nome não cadatrado!");
  
        } else {
           
           if (newGame.getName().compareTo(i.elemento.getName()) < 0) {
              i.esq = remover(newGame, i.esq);
           } else {
              if (newGame.getName().compareTo(i.elemento.getName()) > 0) {
                 i.dir = remover(newGame, i.dir);
              } else {
                 if (i.dir == null) {
                    i = i.esq;
                 } else {
                    if (i.esq == null) {
                       i = i.dir;
                    } else {
                       i.esq = maiorEsq(i, i.esq);
                    }
                 }
              }
           }
        }
  
          return i;
      }
  
      /**
       * Metodo para trocar o game "removido" pelo maior da esquerda.
       * @param i No que teve o game removido.
       * @param j No da subarvore esquerda.
       * @return No em analise, alterado ou nao.
       */
      private No maiorEsq(No i, No j) {
          if (j.dir == null) {
              i.elemento = j.elemento;
              j = j.esq;
  
          } else {
              j.dir = maiorEsq(i, j.dir);
          }
  
          return j;
      }

}


class Game {

    static SimpleDateFormat default_dateFormat = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);

    private String name, owners, website, developers;
    private ArrayList<String> languages, genres;
    private Date release_date;
    private int app_id, age, dlcs, avg_playtime;
    private float price, upvotes;
    private boolean windows, mac, linux;

    public Game() {

        this.name = this.owners = this.website = this.developers = null;
        this.languages = new ArrayList<String>();
        this.genres = new ArrayList<String>();
        this.release_date = null;
        this.app_id = this.age = this.dlcs = this.avg_playtime = -1;
        this.price = this.upvotes = -1;
        this.windows = this.mac = this.linux = false;
    }

    public Game(String name, String owners, String website, String developers, ArrayList<String> languages, ArrayList<String> genres, Date release_date, int app_id, int age, int dlcs, int upvotes, int avg_playtime, float price, boolean windows, boolean mac, boolean linux) {

        this.name = name;
        this.owners = owners;
        this.website = website;
        this.developers = developers;
        this.languages = languages;
        this.genres = genres;
        this.release_date = release_date;
        this.app_id = app_id;
        this.age = age;
        this.dlcs = dlcs;
        this.upvotes = upvotes;
        this.avg_playtime = avg_playtime;
        this.price = price;
        this.windows = windows;
        this.mac = mac;
        this.linux = linux;
    }

    public void setName(String name) { this.name = name; }
    public void setOwners(String owners) { this.owners = owners; }
    public void setWebsite(String website) { this.website = website; }
    public void setDevelopers(String developers) { this.developers = developers; }
    public void setLanguages(ArrayList<String> languages) { this.languages = languages; }
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }
    public void setReleaseDate(Date release_date) { this.release_date = release_date; }
    public void setAppId(int app_id) { this.app_id = app_id; }
    public void setAge(int age) { this.age = age; }
    public void setDlcs(int dlcs) { this.dlcs = dlcs; }
    public void setAvgPlaytime(int avg_playtime) { this.avg_playtime = avg_playtime; }
    public void setPrice(float price) { this.price = price; }
    public void setUpvotes(float upvotes) { this.upvotes = upvotes; }
    public void setWindows(boolean windows) { this.windows = windows; }
    public void setMac(boolean mac) { this.mac = mac; }
    public void setLinux(boolean linux) { this.linux = linux; }

    public String getName() { return this.name; }
    public String getOwners() { return this.owners; }
    public String getWebsite() { return this.website; }
    public String getDevelopers() { return this.developers; }
    public ArrayList<String> getLanguages() { return this.languages; }
    public ArrayList<String> getGenres() { return this.genres; }
    public Date getReleaseDate() { return this.release_date; }
    public int getAppId() { return this.app_id; }
    public int getAge() { return this.age; }
    public int getDlcs() { return this.dlcs; }
    public int getAvgPlaytime() { return this.avg_playtime; }
    public float getPrice() { return this.price; }
    public float getUpvotes() { return this.upvotes; }
    public boolean getWindows() { return this.windows; }
    public boolean getMac() { return this.mac; }
    public boolean getLinux() { return this.linux; }
    
    public Game clone() {

        Game cloned = new Game();

        cloned.name = this.name;
        cloned.owners = this.owners;
        cloned.website = this.website;
        cloned.developers = this.developers;
        cloned.languages = this.languages;
        cloned.genres = this.genres;
        cloned.release_date = this.release_date;
        cloned.app_id = this.app_id;
        cloned.age = this.age;
        cloned.dlcs = this.dlcs;
        cloned.avg_playtime = this.avg_playtime;
        cloned.price = this.price;
        cloned.upvotes = this.upvotes;
        cloned.windows = this.windows;
        cloned.mac = this.mac;
        cloned.linux = this.linux;

        return cloned;
    }

    public static Game gameSearch(ArrayList<Game> games, int app_id) {

        for(Game game : games) if(game.getAppId() == app_id) return game;
        return null;
    }

    public void read(String line) {

        char c_search;
        int index = 0, atr_index = 0;

        // ---------------------------------- //

        // Find "AppID"
        while(true) {

            index++;

            if(line.charAt(index) == ',') {
                
                this.app_id = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //
        
        // Find "Name"
        if(line.charAt(atr_index) != ',') {

            if(line.charAt(atr_index) == '\"') {
                
                atr_index++;
                c_search = '\"';
            }
            else c_search = ',';
            
            while(true) {

                index++;

                if(line.charAt(index) == c_search) {
                    
                    this.name = line.substring(atr_index, index);

                    if(c_search == ',') index++;
                    else if(c_search == '\"') index += 2;
                    
                    atr_index = index;
                    break;
                }
            }
        }
        else atr_index = ++index;

        // ---------------------------------- //
        
        // Find release date
        if(line.charAt(atr_index) != ',') {

            SimpleDateFormat df;
            
            if(line.charAt(atr_index) == '\"') {
                
                df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                atr_index++;
                c_search = '\"';
            }
            else {
                
                df = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);

                c_search = ',';
            }

            while(true) {

                index++;

                if(line.charAt(index) == c_search) {
                    
                    try { this.release_date = df.parse(line.substring(atr_index, index)); } 
                    catch (java.text.ParseException e) { e.printStackTrace(); }

                    if(c_search == ',') index++;
                    else if(c_search == '\"') index += 2;
                    
                    atr_index = index;
                    break;
                }
            }
        }
        else atr_index = ++index;

        // ---------------------------------- //
        
        // Find "Owners"
        while(true) {

            index++;

            if(line.charAt(index) == ',') {
                
                this.owners = line.substring(atr_index, index);

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //
        
        // Find "Age"
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.age = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //
        
        // Find "Price"
        while(true) {

            index++;

            if(line.charAt(index) == ',') {
                
                this.price = Float.parseFloat(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //
        
        // Find "DLCs"
        while(true) {

            index++;

            if(line.charAt(index) == ',') {
                
                this.dlcs = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //
        
        // Find "Languages"
        while(true) {

            index++;

            if(line.charAt(index) == ']') {

                index++;
                
                if(line.charAt(index) == ',') index++;
                else if(line.charAt(index) == '\"') index += 2;

                atr_index = index;
                break;
            }
            else if(line.charAt(index) == '\'') {

                int wordStart = index + 1;

                while(true) {

                    index++;

                    if(line.charAt(index) == '\'') {
                        
                        this.languages.add(line.substring(wordStart, index));
                        break;
                    }
                }
            }
        }

        // atr_index = ++index;

        // ---------------------------------- //
        
        // Find "Website"
        if(line.charAt(atr_index) != ',') {

            if(line.charAt(atr_index) == '\"') {
                
                atr_index++;
                c_search = '\"';
            }
            else c_search = ',';
            
            while(true) {

                index++;

                if(line.charAt(index) == c_search) {
                    
                    this.website = line.substring(atr_index, index);

                    atr_index = ++index;
                    break;
                }
            }
        }
        else atr_index = ++index;

        // ---------------------------------- //
        
        // Find "Windows"
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.windows = Boolean.parseBoolean(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Find "Mac"
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.mac = Boolean.parseBoolean(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Find "Linux"
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.linux = Boolean.parseBoolean(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //
        
        // Find "Upvotes"
        int positives, negatives;

        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                positives = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                negatives = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        this.upvotes = (float)(positives * 100) / (float)(positives + negatives);

        // ---------------------------------- //
        
        // Find "AVG Playtime"
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.avg_playtime = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //
        
        // Find "Developers"
        if(line.charAt(atr_index) != ',') {

            if(line.charAt(atr_index) == '\"') {
                
                atr_index++;
                c_search = '\"';
            }
            else c_search = ',';
            
            while(true) {

                index++;

                if(line.charAt(index) == c_search) {
                    
                    this.developers = line.substring(atr_index, index);

                    atr_index = ++index;
                    break;
                }
            }
        }
        else atr_index = ++index;
       
        // ---------------------------------- //
        
        // Find "Genres"
        if(index < line.length() - 1) {

            if(line.charAt(index) == ',') atr_index = ++index;                    
            if(line.charAt(atr_index) == '\"') {

                atr_index++;
                
                while(true) {

                    index++;

                    if(line.charAt(index) == ',') {
                        
                        this.genres.add(line.substring(atr_index, index));

                        atr_index = ++index;
                    }
                    else if(line.charAt(index) == '\"') {

                        this.genres.add(line.substring(atr_index, line.length() - 1));
                        break;
                    }
                }
            }
            else this.genres.add(line.substring(atr_index, line.length()));
        }

        // -------------------------------------------------------------------------------- //
    }

    public void print() {

        String avg_pt = null;

        if(this.avg_playtime == 0) avg_pt = "null ";
        else if(this.avg_playtime < 60) avg_pt = this.avg_playtime + "m ";
        else {

            if(this.avg_playtime % 60 == 0) avg_pt = this.avg_playtime / 60 + "h ";
            else avg_pt = (this.avg_playtime / 60) + "h " + (this.avg_playtime % 60) + "m ";
        }

        DecimalFormat df = new DecimalFormat("##");

        System.out.println(this.app_id + " " + this.name + " " + default_dateFormat.format(this.release_date) + " " + this.owners + " " + this.age + " " + String.format(Locale.ENGLISH, "%.2f", this.price) + " " + this.dlcs + " " + this.languages + " " + this.website + " " + this.windows + " " + this.mac + " " + this.linux + " " + (Float.isNaN(this.upvotes) ? "0% " : df.format(this.upvotes) + "% ") + avg_pt + this.developers + " " + this.genres);
    }

    // -------------------------------------------------------------------------------- //

    public static String getGameData(String csvFile, int app_id) throws Exception {

        try {

            // Read CSV file
            FileInputStream fstream = new FileInputStream(csvFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            // ------------------------------------ //

            // Start to explode CSV file
            String line;

            while((line = br.readLine()) != null) {

                String id_s = Integer.toString(app_id);
  
                if(line.substring(0, line.indexOf(',')).equals(id_s)) {

                    fstream.close();
                    br.close();
                    return line;
                }
            }

            // Close CSV file
            fstream.close();
        }
        catch(IOException e) { e.printStackTrace(); }
        return null;
    }

    // -------------------------------------------------------------------------------------- //
}