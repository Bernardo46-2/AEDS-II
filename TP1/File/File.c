#include <stdio.h>
#include <stdbool.h>

/**
 * Funcao principal
 * 
 * le um numero inteiro indicando quantos numeros reais serao
 * lidos, le essa quantidade de numeros reais e salva no arquivo
 * tmp.dat. Em seguida abre o arquivo tmp.dat e le os numeros de tras 
 * para frente printando na tela
 */
int main(){
    int n;
    double tmp;

    FILE* tmpFile = fopen("tmp.dat", "wb+");

    scanf("%d", &n);

    for(int i = 0; i < n; i++){
        scanf("%lf", &tmp);
        fwrite(&tmp, 8, 1, tmpFile);
    }

    for(int i = 0; i < n; i++){
        fseek(tmpFile, (n - i - 1) * 8, SEEK_SET);
        fread(&tmp, 8, 1, tmpFile);
        printf("%g\n", tmp);
    }

    fclose(tmpFile);
    return 0;
}