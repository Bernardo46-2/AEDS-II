#include <stdio.h>
#include <stdbool.h>
#include <string.h>

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
 * Funcao para verificar se string e' composta apenas por vogais 
 * Percorre toda a string e retorna false se ao menos uma letra nao for vogal
 * 
 * @param str string a ser verificada
 * @return false: se ao menos uma letra nao for vogal; true: caso contrario
 */
bool allVowels(char* str){
    for(int i = 0; i < strlen(str); i++)
        if(str[i] != 'A' && str[i] != 'a' && str[i] != 'E' && str[i] != 'e' && str[i] != 'I' && str[i] != 'i' && str[i] != 'O' && str[i] != 'o' && str[i] != 'U' && str[i] != 'u')
            return false;
    return true;
}

/**
 * Funcao para verificar se string e' composta apenas por consoantes
 * Verificando se a string o caractere atual e' maiusculo, depois verificando se 
 * e' uma vogal ou nao
 * 
 * @param str String a ser verificada
 * @return true: se todas as letras forem consoantes; false: caso contrario
 */
bool allConsonants(char* str){
    for(int i = 0; i < strlen(str); i++)
        if('A' <= str[i] && str[i] <= 'Z'){
            if(str[i] == 'A' || str[i] == 'E' || str[i] == 'I' || str[i] == 'O' || str[i] == 'U')
                return false;
        }else if('a' <= str[i] && str[i] <= 'z'){
            if(str[i] == 'a' || str[i] == 'e' || str[i] == 'i' || str[i] == 'o' || str[i] == 'u')
                return false;
        }else{
            return false;
        }
    
    return true;
}

/**
 * Funcao para verificar se string e' um numero inteiro,
 * verificando se e' composta apenas por numeros
 * 
 * @param str string a ser verificada
 * @return true: se todos os caracteres forem numeros; false: caso contrario
 */
bool isInt(char* str){
    for(int i = 0; i < strlen(str); i++)
        if('0' > str[i] || str[i] > '9')
            return false;
    return true;
}

/**
 * Funcao para verificar se string e' um numero real,
 * verificando se possui um (e apenas um) ponto para separar casas
 * decimais
 * 
 * @param str string a ser verificada
 * @return true: se string for um numero real; false: caso contrario
 */
bool isFloat(char* str){
    int dots = 0;
    
    for(int i = 0; i < strlen(str); i++){
        if(str[i] == '.' || str[i] == ','){
            if(dots == 0){
                dots++;
            }else{
                return false;
            }
        }else if(str[i] < '0' || str[i] > '9'){
            return false;
        }
    }

    return true;
}

/**
 * Funcao principal
 * Le varias strings ate encontrar a string "FIM", indicando o final do
 * pub.in
 * 
 * Para cada string valida printa se e' composta apenas por vogais,
 * apenas por consoantes, se e' um numero inteiro e/ou e' um numero real
 */
int main(){
    char str[1000];
    bool stop;
        
    do{
        scanf(" %1000[^\n]", str);
        stop = isFim(str);

        if(!stop){
            printf(allVowels(str) ? "SIM " : "NAO ");
            printf(allConsonants(str) ? "SIM " : "NAO ");
            printf(isInt(str) ? "SIM " : "NAO ");
            printf(isFloat(str) ? "SIM\n" : "NAO\n");
        }
    }while(!stop);

    return 0;
}