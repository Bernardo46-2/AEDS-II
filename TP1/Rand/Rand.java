import java.util.Random;

public class Rand{
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
     * Metodo para alterar letras aleatorias da string
     * cria uma string nova para retornar com as alteracoes
     * sorteia 2 letras aleatorias e se a primeira aparece na string
     * substitui pela segunda na na nova string, caso contrario
     * copia a letra da primeira string normalmente
     * 
     * @param rand - objeto da classe random para ser extraido os valores aleatorios
     * @param str - string base para ser feita a alteracao
     * @return nova string ja com as alteracoes feitas
     */
    public static String alter(Random rand, String str){
        String str2 = new String();
        
        char letra1 = (char)('a' + (Math.abs(rand.nextInt()) % 26));
        char letra2 = (char)('a' + (Math.abs(rand.nextInt()) % 26));

        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) != letra1)
                str2 += str.charAt(i);
            else
                str2 += letra2;
        }

        return str2;
    }
    
    /**
     * Metodo principal
     * 
     * Le strings ate encontrar a string FIM
     * 
     * Para cada string valida faz a alteracao aleatoria e printa na tela
     */
    public static void main(String[] args){
        Random rand = new Random();
        rand.setSeed(4);
        
        String str = new String();
        boolean stop;

        do{
            str = MyIO.readLine();
            stop = isFim(str);

            if(!stop)
                MyIO.println(alter(rand, str));
        }while(!stop);
    }
}