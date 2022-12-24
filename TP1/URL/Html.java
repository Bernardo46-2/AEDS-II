//AEDs II - TP I
//Bernardo Marques Fernandes - 774119

import java.io.*;
import java.net.*;

/**
 * classe para contar todas as ocorrencias de 
 * vogais acentuadas e nao acentuadas, consoantes,
 * <br> e <table>
 */
class Counter{
    public int[] a;        //ok?
    public int[] e;        //ok?
    public int[] i;        //ok?
    public int[] o;        //ok?
    public int[] u;        //ok?
    public int consonants; //ok
    public int br;         //ok
    public int table;      //ok
    public String show;    //ok

    /**
     * Metodo construtor
     * 
     * Cria vetores para armazenar diferentes ocorrencias dos 
     * caracteres e tags procuradas
     * 
     * @param show nome da serie 
     */
    Counter(String show){ 
        //indices dos arrays:
        // 0 - letra normal
        // 1 - letra com acento agudo
        // 2 - letra com crase
        // 3 - letra com til
        // 4 - letra com acento circunflexo
        this.a = new int[5];
        this.e = new int[5];
        this.i = new int[5];
        this.o = new int[5];
        this.u = new int[5];

        this.consonants = 0;
        this.br = 0;
        this.table = 0;
        this.show = show;
    }

    /**
     * Metodo toString
     * 
     * @return string a ser printada na tela
     */
    public String toString(){
        return "a(" + this.a[0] + ") " +
        "e(" + this.e[0] + ") " + 
        "i(" + this.i[0] + ") " + 
        "o(" + this.o[0] + ") " + 
        "u(" + this.u[0] + ") " + 
        "á(" + this.a[1] + ") " +
        "é(" + this.e[1] + ") " +
        "í(" + this.i[1] + ") " +
        "ó(" + this.o[1] + ") " +
        "ú(" + this.u[1] + ") " +
        "à(" + this.a[2] + ") " +
        "è(" + this.e[2] + ") " +
        "ì(" + this.i[2] + ") " +
        "ò(" + this.o[2] + ") " +
        "ù(" + this.u[2] + ") " +
        "ã(" + this.a[3] + ") " +
        "õ(" + this.o[3] + ") " +
        "â(" + this.a[4] + ") " +
        "ê(" + this.e[4] + ") " +
        "î(" + this.i[4] + ") " +
        "ô(" + this.o[4] + ") " +
        "û(" + this.u[4] + ") " +
        "consoante(" + this.consonants + ") " +
        "<br>(" + this.br + ") " + 
        "<table>(" + this.table + ") " +
        this.show;
    }
}

public class Html{
    /**
     * Metodo para verificar se str == FIM
     * 
     * @param str String para verificar se e' FIM
     * @return true: se str == FIM, false: se str != FIM
     */
    public static boolean isFim(String str){
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    /**
     * Metodo para buscar o html de uma pagina a partir da url da pagina
     * 
     * @param addr url da pagina
     * @return string contendo o html da pagina
     */
    public static String getHtml(String addr){
        URL url;
        InputStream is = null;
        BufferedReader br;

        String resp = new String();
        String line = new String();

        try{
            url = new URL(addr);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));

            while((line = br.readLine()) != null){
                resp += line + "\n";
            }
        }catch(MalformedURLException mue){
            mue.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        try{
            is.close();
        }catch(IOException ioe){}

        return resp;
    }

    /**
     * Metodo para verificar se caractere e' uma letra maiuscula
     * 
     * @param c caractere a ser testado
     * @return booleano indicando se c e' letra maiuscula ou nao
     */
    public static boolean isUpper(char c){
        return 'A' <= c && c <= 'Z';
    }

    /**
     * Metodo para verificar se caractere e' uma letra minuscula
     * 
     * @param c caractere a ser testado
     * @return booleano indicando se c e' letra minuscula ou nao
     */
    public static boolean isLower(char c){
        return 'a' <= c && c <= 'z';
    }

    /**
     * Metodo para verificar se caractere e' uma letra
     * 
     * @param c caractere a ser testado
     * @return booleano indicando se c e' letra ou nao
     */
    public static boolean isLetter(char c){
        return isUpper(c) || isLower(c);
    }

    /**
     * Metodo para verificar se caractere e' uma vogal
     * 
     * @param c caractere a ser testado
     * @return booleano indicando se c e' vogal ou nao
     */
    public static boolean isVowel(char c){
        if(isUpper(c))
            return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
        else if(isLower(c))
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
        else
            return false;
    }

    /**
     * Metodo para verificar se caractere e' uma consoante
     * 
     * @param c caractere a ser testado
     * @return booleano indicando se c e' consoante ou nao
     */
    public static boolean isConsonant(char c){
        return isLower(c) && !isVowel(c);
    }

    /**
     * Metodo para verificar se pedaco da string e' igual a "<br>"
     * 
     * @param str string a ser testada
     * @param i posicao na string para procurar "<br>"
     * @return booleano indicando se a posicao possui <br> ou nao
     */
    public static boolean isBr(String str, int i){
        return str.charAt(i) == '<' &&
        str.charAt(i) == 'b' &&
        str.charAt(i) == 'r' &&
        str.charAt(i) == '>';
    }
    
    /**
     * Metodo para verificar se pedaco da string e' igual a "<table>"
     * 
     * @param str string a ser testada
     * @param i posicao na string para procurar "<table>"
     * @return booleano indicando se a posicao possui <table> ou nao
     */
    public static boolean isTable(String str, int i){
        return str.charAt(i) == '<' &&
        str.charAt(i + 1) == 't' &&
        str.charAt(i + 2) == 'a' &&
        str.charAt(i + 3) == 'b' &&
        str.charAt(i + 4) == 'l' &&
        str.charAt(i + 5) == 'e' &&
        str.charAt(i + 6) == '>';
    }

    /**
     * Metodo para percorrer o html e contar todas as ocorrencias dos
     * caracteres pedidos, incluindo as tags <br> e <table>.
     * 
     * @param counter classe criada para contar os caracteres
     * @param html string contendo o html a ser percorrido
     */
    public static void searchHtml(Counter counter, String html){
        for(int i = 0; i < html.length(); i++){
            if(isTable(html, i)){
                counter.table++;
                i += 6;
            }else if(isBr(html, i)){
                counter.br++;
                i += 3;
            }else if(isConsonant(html.charAt(i))){
                counter.consonants++;
            }else{
                switch(html.charAt(i)){
                    case 'a': counter.a[0]++; break;
                    case 'e': counter.e[0]++; break;
                    case 'i': counter.i[0]++; break;
                    case 'o': counter.o[0]++; break;
                    case 'u': counter.u[0]++; break;
                    case 225: counter.a[1]++; break;
                    case 233: counter.e[1]++; break;
                    case 237: counter.i[1]++; break;
                    case 243: counter.o[1]++; break;
                    case 250: counter.u[1]++; break;
                    case 224: counter.a[2]++; break;
                    case 232: counter.e[2]++; break;
                    case 236: counter.i[2]++; break;
                    case 242: counter.o[2]++; break;
                    case 249: counter.u[2]++; break;
                    case 227: counter.a[3]++; break;
                    case 245: counter.o[3]++; break;
                    case 226: counter.a[4]++; break;
                    case 234: counter.e[4]++; break;
                    case 238: counter.i[4]++; break;
                    case 244: counter.o[4]++; break;
                    case 251: counter.u[4]++; break;
                    default: break;
                }
            }
        }
    }
    
    /**
     * Metodo principal
     * 
     * le strings enquanto nao encontrar a palavra FIM
     * para cada string valida, le o nome da pagina e em seguida o 
     * endereco, abre o html da pagina e procura pelos caracteres pedidos
     */
    public static void main(String[] args){        
        String title = new String();
        String addr = new String();
        String html = new String();

        boolean stop;
        
        do{
            title = MyIO.readLine();
            stop = isFim(title);
            
            if(!stop){
                Counter counter = new Counter(title);

                addr = MyIO.readLine();
                html = getHtml(addr);
                
                searchHtml(counter, html);

                MyIO.println(counter.toString());
            }
        }while(!stop);
    }
}
