public class RecIs {
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
     * Metodo para verificar se string e' composta apenas por vogais 
     * Percorre toda a string (iterativamente) e retorna false se ao menos uma letra nao for vogal
     * 
     * @param str - string a ser verificada
     * @param i - posicao atual a ser verificada
     * @return false: se ao menos uma letra nao for vogal; true: caso contrario
     */
    public static boolean allVowels(String str, int i){
        if(i < str.length()){
            if(str.charAt(i) != 'A' && str.charAt(i) != 'a' && str.charAt(i) != 'E' && str.charAt(i) != 'e' && str.charAt(i) != 'I' && str.charAt(i) != 'i' && str.charAt(i) != 'O' && str.charAt(i) != 'o' && str.charAt(i) != 'U' && str.charAt(i) != 'u')
                return false;
            else
                return allVowels(str, i + 1);
        }else{
            return true;
        }
    }

    /**
     * Metodo para verificar se string e' composta apenas por consoantes
     * Verificando se a string o caractere atual e' maiusculo, depois verificando se 
     * e' uma vogal ou nao
     * 
     * @param str - String a ser verificada
     * @return true: se todas as letras forem consoantes; false: caso contrario
     */
    public static boolean allConsonants(String str, int i){
        if(i < str.length()){
            if('A' <= str.charAt(i) && str.charAt(i) <= 'Z'){
                if(str.charAt(i) == 'A' || str.charAt(i) == 'E' || str.charAt(i) == 'I' || str.charAt(i) == 'O' || str.charAt(i) == 'U'){
                    return false;
                }else{
                    return allConsonants(str, i + 1);
                }
            }else if('a' <= str.charAt(i) && str.charAt(i) <= 'z'){
                if(str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i' || str.charAt(i) == 'o' || str.charAt(i) == 'u'){
                    return false;
                }else{
                    return allConsonants(str, i + 1);
                }
            }else{
                return false;
            }
        }
        
        return true;
    }

    /**
     * Metodo para verificar se string e' um numero inteiro,
     * verificando se e' composta apenas por numeros
     * 
     * @param str - string a ser verificada
     * @param i - caractere atual a ser verificado
     * @return true: se todos os caracteres forem numeros; false: caso contrario
     */
    public static boolean isInt(String str, int i){
        if(i < str.length()){
            if('0' > str.charAt(i) || str.charAt(i) > '9'){
                return false;
            }else{
                return isInt(str, i + 1);
            }
        }

        return true;
    }

    /**
     * Metodo para verificar se string e' um numero real,
     * verificando se possui um (e apenas um) ponto para separar casas
     * decimais
     * 
     * @param str - string a ser verificada
     * @param i - caractere atual a ser verificado
     * @param dots - quantidade de pontos encontrados na string
     * @return true: se string for um numero real; false: caso contrario
     */
    public static boolean isFloat(String str, int i, int dots){
        if(i < str.length()){
            if(str.charAt(i) == '.' || str.charAt(i) == ','){
                if(dots == 0){
                    dots++;
                    return isFloat(str, i + 1, dots);
                }else{
                    return false;
                }
            }else if(str.charAt(i) < '0' || str.charAt(i) > '9'){
                return false;
            }else{
                return isFloat(str, i + 1, dots);
            }
        }

        return true;
    }
    
    /**
     * Metodo principal
     * Le varias strings ate encontrar a string "FIM", indicando o final do
     * pub.in
     * 
     * Para cada string valida printa se e' composta apenas por vogais,
     * apenas por consoantes, se e' um numero inteiro e/ou e' um numero real
     */
    public static void main(String[] args){
        String str = new String();
        boolean stop;
        
        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop){
                MyIO.print(allVowels(str, 0) ? "SIM " : "NAO ");
                MyIO.print(allConsonants(str, 0) ? "SIM " : "NAO ");
                MyIO.print(isInt(str, 0) ? "SIM " : "NAO ");
                MyIO.println(isFloat(str, 0, 0) ? "SIM" : "NAO");
            }
        }while(!stop);
    }
}
