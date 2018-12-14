#include<stdio.h>
#include<stdlib.h>
#include "importar.h"

#define DELIM_CHR '|'

int prepara(){
    FILE* fd;
    FILE* parq;
    char s[200];
    char nome[100], curso[100];
    int a = -1;

    if ((parq = fopen("registro.txt", "w")) == NULL) {
       printf("Erro na abertura do arquivo registro!! --- Programa Abortado!!\n");
       exit(1);
    }
    if ((fd = fopen("dados-inline.txt" , "r")) == NULL) {
       printf("Erro na abertura do arquivo dados-inline!! --- Programa Abortado!!\n");
       exit(1);
    }
    fwrite(&a, sizeof(int), 1, parq);
    while (importacao(fd ,s, parq) > 0);
    fclose(fd);
    fclose (parq);
}

int importacao(FILE* fd, char* s, FILE *parq){
    char c;
    int i = 0;
    int campos;

    c = fgetc(fd);
    campos = 0;
    while ( c != EOF && campos < 4){
        s[i++] = c;
        c = fgetc(fd);
        if(c == DELIM_CHR)
            campos++;
    }
    if (c!=EOF){
      s[i++] = c;
      s[i] = '\0';
      fwrite(&i, sizeof(short), 1, parq);
      fputs(s, parq);
      i--;
    }
    return(i);
}
