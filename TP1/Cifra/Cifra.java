public class Cifra{
    private static int chave = 3;
    
    /**
     * Metodo para verificar se str == FIM
     * 
     * @param str - String para verificar se e' FIM
     * @return true: se str == FIM, false: se str != FIM
     */
    public static boolean isFim(String str){
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }
    
    /**
     * Metodo para cifrar string
     * cria um nova string para receber as alteracoes da primeira
     * usa a chave de ciframento (3) e itera sobre a string original 
     * enviando o caractere atual acrescentado de 3 no seu codigo da tabela
     * ascii para a nova string
     * 
     * @param str - String para cifrar
     * @return string contendo a versao cifrada de str
     */
    public static String cifra(String str){
        String cif = new String();
        
        for(int i = 0; i < str.length(); i++){
            cif += (char)(str.charAt(i) + chave);
        }

        return cif;
    }

    /**
     * Metodo principal
     * 
     * Le varias strings e faz o ciframento de todas ate encontrar
     * a palavra "FIM", indicando o fim do pub.in
     */
    public static void main(String[] args){
        String str = new String();
        boolean stop;

        do{
            str = MyIO.readLine();
            
            stop = isFim(str);
            
            if(!stop){
                String cif = cifra(str);
                MyIO.println(cif);
            }
        }while(!stop);
    }
}
