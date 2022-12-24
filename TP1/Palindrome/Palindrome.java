public class Palindrome{
    /**
     * Funcao para verificar se e' palindromo,
     * iterando ate a metade da string e verificando se o caractere
     * atual e o na posicao oposta sao iguais
     * 
     * @param str - string a ser verificada
     * @return true: se a string e' palindromo; false: caso contrario
     */
    public static boolean isPalindrome(String str){
        for(int i = 0; i <= str.length() / 2; i++)
            if(str.charAt(i) != str.charAt(str.length() - i - 1))
                return false;
        return true;
    }

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
     * Metodo principal
     * 
     * Le strings ate encontrar a string FIM
     * 
     * Para cada string valida verifica se e' palindromo e printa na tela
     */
    public static void main(String[] args){
        String str = new String();
        boolean stop;
        
        do{
            str = MyIO.readLine();
            stop = isFim(str);
            boolean resp = isPalindrome(str);

            if(!stop)
                MyIO.println(resp ? "SIM" : "NAO");
        }while(!stop);
    }
}
