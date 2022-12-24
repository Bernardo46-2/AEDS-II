/*
 * Classe criada para receber a equacao e o valor das variaveis
 * A, B e C. 
 * Possui todos os metodos necessarios para resolver a equacao
 * contanto que existam apenas 3 variaveis
*/
class Equation{
    private boolean a;
    private boolean b;
    private boolean c;
    private String eq;

    /**
     * Metodo construtor
     * Recebe uma equacao como parametro
     * verifica quantas variaveis estao presentes e atribui seus 
     * valores as variaveis da classe
     * depois, corta o comeco da string (com os valores das variaveis)
     * e copia o resto para a variavel "eq", logo, esta contem apenas a 
     * equacao em si
     * 
     * @param str - equacao a ser aplicada
    */
    Equation(String str){
        this.a = str.charAt(2) == '0' ? false : true;
        this.b = str.charAt(4) == '0' ? false : true;
        
        if(str.charAt(0) == '3'){
            this.c = str.charAt(6) == '0' ? false : true;

            if(str.charAt(str.length() - 1) == ' '){
                this.eq = Bool.substring(str, 8, str.length() - 1);
            }else{
                this.eq = Bool.substring(str, 8, str.length());
            }
        }else{
            if(str.charAt(str.length() - 1) == ' '){
                this.eq = Bool.substring(str, 6, str.length() - 1);
            }else{
                this.eq = Bool.substring(str, 6, str.length());
            }            
        }

    }

    /**
     * Metodo para resolver and
     * Recebe um array de booleanos e o tamanho desse array
     * O tamanho precisa ser uma variavel independente pois nao e' o 
     * tamanho de fato do array, e sim ate onde os dados presentes foram
     * inicializados.
     * Ex: o array pode ter 10 espacos, mas a funcao deve verificar apenas
     * ate o 4o espaco. Entao length == 4.
     * 
     * Itera sobre os itens do array ate length e se 1 deles for false
     * retorna false, caso contrario, true
     * 
     * @param bool - array de booleanos
     * @param length - tamanho do array
     * @return false: se ao menos um item do array for false, true: caso contrario.
     */
    private boolean and(boolean[] bool, int length){
        for(int i = 0; i < length; i++)
            if(bool[i] == false)
                return false;
        return true;
    }
    
    /**
     * Metodo para resolver or.
     * 
     * Recebe um array de booleanos e o tamanho desse array
     * O tamanho precisa ser uma variavel independente pois nao e' o 
     * tamanho de fato do array, e sim ate onde os dados presentes foram
     * inicializados.
     * Ex: o array pode ter 10 espacos, mas a funcao deve verificar apenas
     * ate o 4o espaco. Entao length == 4.
     * 
     * Itera sobre os itens do array ate length e se 1 deles for true
     * retorna true, caso contrario, false
     *
     * @param bool - array de booleanos
     * @param length - tamanho do array
     * @return true: se ao menos um item do array for true, false: caso contrario.
    */
    private boolean or(boolean[] bool, int length){
        for(int i = 0; i < length; i++)
            if(bool[i] == true)
                return true;
        return false;
    }

    /**
     * Metodo para percorrer a string alterando os caracteres A, B e C por seus
     * respectivos valores.
     * 
     * Cria uma nova string vazia e enquanto percorre a string eq, se encontrar
     * 'A', 'B' ou 'C', em vez de copiar esses caracteres para a nova string, 
     * copia '1' caso o valor da variavel for true, '0' caso contrario.
    */
    private void updateValues(){
        String newEq = new String();
        
        for(int i = 0; i < this.eq.length(); i++){
            if(this.eq.charAt(i) == 'A'){
                newEq += this.a ? '1' : '0';
            }else if(this.eq.charAt(i) == 'B'){
                newEq += this.b ? '1' : '0';
            }else if(this.eq.charAt(i) == 'C'){
                newEq += this.c ? '1' : '0';
            }else{
                newEq += this.eq.charAt(i);
            }
        }

        this.eq = newEq;
    }

    /**
     * metodo para identificar se a proxima conta e' simples ou composta
     * and(A , B) -> simples
     * and(not(A) , not(B)) -> composta
     * 
     * Isso resolve o problema de acabar resolvendo uma conta e ter que pausar
     * a resolucao da mesma para resolver a conta menor.
     * O custo provavelmente e' maior, porem e' a maneira iterativa que eu pensei
     * para resolver a questao.
     * 
     * Caso pudesse misturar iteracao com recursao seria mais facil resolver 
     * equacoes que dependem de outras, porem nao e' o caso
     * 
     * @param start - de onde comecar a iterar na string
     * @return - booleano indicando se a conta e' simples ou composta
    */
    private boolean isSimple(int start){
        for(int i = start; i < this.eq.length(); i++){
            if(this.eq.charAt(i) == '('){
                return false;
            }else if(this.eq.charAt(i) == ')'){
                return true;
            }
        }

        return true; //essa linha nunca sera executada, mas java me obriga a coloca-la aqui
    }

    /**
     * Metodo principal para resolver a equacao
     * 
     * Primeiro chama a funcao updateValues para trocar as variaveis por 
     * numeros.
     * Percorre a string repetidamente enquanto o tamanho dela for maior que 1
     * length = 1 indica que apenas a resposta restou
     * 
     * Se encontra a palavra and, or ou not, verifica se a operacao e' simples atraves da
     * funcao isSimple
     * se nao: continua iterando ate achar uma operacao simples.
     * se sim: resolve a operacao
     * 
     * Caso encontrar uma operacao simples de not:
     * Resolve a operacao invertendo o valor e substituindo a operacao not na string pelo
     * resultado. Para isso, copia tudo na string ate a operacao not, depois e resultado e
     * em seguida o restante da string para a nova string e substitui this.eq pela nova string
     * 
     * Caso encontrar uma operacao simples de and/or:
     * Armazena todas as variaveis presentes dentro do parenteses da operacao dentro de um array
     * e envia o array e a quantidade de variaveis para a funcao and/or e em seguida faz o mesmo processo
     * de substituicao da operacao pelo seu resultado como descrito no paragrafo acima
     * 
     * Caso a variavel i, que controla o for, chegue no final da string da equacao e ela ainda nao foi
     * resolvida (isso e' verificado atraves do tamanho da string (a string deve ter 1 de tamanho para
     * indicar que apenas a resposta restou)) o for e' resetado. Isso se repete ate resolver toda a equacao.
     * 
     * @return string contendo o resultado
     */
    public String solveEquation(){
        this.updateValues();
        
        for(int i = 0; this.eq.length() > 1; i++){
            if(this.eq.charAt(i) == 'a' && this.eq.charAt(i + 1) == 'n' && this.eq.charAt(i + 2) == 'd' && this.eq.charAt(i + 3) == '('){
                if(isSimple(i + 4)){
                    int j = i + 4;
                    int index = 0;
                    char resp = '0';
                    boolean[] values = new boolean[10];

                    for(; this.eq.charAt(j) != ')'; j++){
                        if(this.eq.charAt(j) == '0'){
                            values[index++] = false;
                        }else if(this.eq.charAt(j) == '1'){
                            values[index++] = true;
                        }
                    }

                    resp = and(values, index) ? '1' : '0';

                    this.eq = Bool.substring(this.eq, 0, i) + resp + Bool.substring(this.eq, j + 1, this.eq.length());

                    i--;
                }
            }
            
            else if(this.eq.charAt(i) == 'o' && this.eq.charAt(i + 1) == 'r' && this.eq.charAt(i + 2) == '('){
                if(isSimple(i + 3)){
                    int j = i + 3;
                    int index = 0;
                    char resp = '0';
                    boolean[] values = new boolean[10];

                    for(; this.eq.charAt(j) != ')'; j++){
                        if(this.eq.charAt(j) == '0'){
                            values[index++] = false;
                        }else if(this.eq.charAt(j) == '1'){
                            values[index++] = true;
                        }
                    }

                    resp = or(values, index) ? '1' : '0';

                    this.eq = Bool.substring(this.eq, 0, i) + resp + Bool.substring(this.eq, j + 1, this.eq.length());

                    i--;
                }
            }

            else if(this.eq.charAt(i) == 'n' && this.eq.charAt(i + 1) == 'o' && this.eq.charAt(i + 2) == 't' && this.eq.charAt(i + 3) == '('){
                if(isSimple(i + 4)){
                    char resp = '0';
                    int j = i;

                    for(j = i; this.eq.charAt(j) != ')'; j++){
                        if(this.eq.charAt(j) == '0'){
                            resp = '1';
                        }else if(this.eq.charAt(j) == '1'){
                            resp = '0';
                        }
                    }

                    this.eq = Bool.substring(this.eq, 0, i) + resp + Bool.substring(this.eq, j + 1, this.eq.length());

                    i--;
                }
            }

            if(i >= this.eq.length() - 1 && this.eq.length() > 1){
                i = -1;
            }
        }
        
        return this.eq;
    }
}

public class Bool{
    /**
     * Metodo para separar um pedaco da string (porque por algum motivo nao e'
     * permitido usar a funcao substring que ja existe...)
     * 
     * @param str - string de onde e' retirado o slice
     * @param start - comeco do slice
     * @param end - fim do slice (exclusivo)
     * @return slice copiado da string str
     */
    public static String substring(String str, int start, int end){
        String newStr = new String();
        
        for(int i = start; i < end; i++){
            newStr += str.charAt(i);
        }

        return newStr;
    }
    
    /*
     * Metodo principal
     * 
     * Le varias strings ate encontrar a string "0", indicando o final
     * do pub.in
     * 
     * Para cada string valida resolve a equacao
     */
    public static void main(String[] args){
        String str = new String();

        do{
            str = MyIO.readLine();
            
            if(str.charAt(0) != '0'){
                Equation eq = new Equation(str);

                MyIO.println(eq.solveEquation());
            }
        }while(str.charAt(0) != '0');
    }
}