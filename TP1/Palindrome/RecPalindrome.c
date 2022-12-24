#include <stdio.h>
#include <stdbool.h>
#include <string.h>

/**
 * Funcao para verificar se e' palindromo,
 * iterando ate a metade da string e verificando se o caractere
 * atual e o na posicao oposta sao iguais
 * 
 * @param str string a ser verificada
 * @return true: se a string for palindromo; false: caso contrario
 */
bool isPalindrome(char* str, int i){
    if(i >= strlen(str) / 2)
        return true;
    else if(str[i] != str[strlen(str) - i - 1])
        return false;
    else
        return isPalindrome(str, i + 1);
}

/**
 * Funcao para verificar se str == FIM
 * 
 * @param str String para verificar se e' FIM
 * @return true: se str == FIM, false: se str != FIM
 */
bool isFim(char* str){
    return strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M';
}

/**
 * Funcao principal
 * 
 * Le strings ate encontrar a string FIM
 * 
 * Para cada string valida verifica se e' palindromo e printa na tela
 */
int main(){
    char str[1000];
    bool stop;

    do{
        scanf(" %1000[^\n]", str);

        stop = isFim(str);

        if(!stop)
            printf("%s\n", isPalindrome(str, 0) ? "SIM" : "NAO");
    }while(!stop);

    return 0;
}
