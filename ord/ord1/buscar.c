#include <stdio.h>
#include <stdlib.h>
#include <stdio_ext.h>
#include <string.h>
#include "buscar.h"
#include "inserir.h"

#define  TRUE     1
#define  FALSE    0
#define MAX_REC_SIZE 512
#define DELIM_CHR '|'

short get_rec(char* recbuff, FILE* arq){
      short rec_lgth;

      if (fread(&rec_lgth, sizeof(rec_lgth), 1, arq) == 0) // get record length
         return 0;
      rec_lgth = fread(recbuff, 1, rec_lgth, arq); // read record
      recbuff[rec_lgth] = '\0';
      return rec_lgth;
}

int buscar(FILE *arq){
    int matched;
    short rec_lgth;
    char search_key[30];
    char recbuff[MAX_REC_SIZE + 1];
    char *field;
    char *insc;
    int byte_offset;

    fseek(arq, 4, SEEK_SET);
    do{
      printf("\nDigite o CODIGO a ser buscado (6 digitos): "); // get search key]
      __fpurge(stdin);
      fgets(search_key, 30, stdin);
      search_key[strlen(search_key)-1] = '\0';
      if (strlen(search_key)!=6){
        printf("Número de digitos incorreto!!\n");
      }
    }while(strlen(search_key)!=6);
    __fpurge(stdin);
    matched = FALSE;
    while (!matched && (rec_lgth = get_rec(recbuff, arq)) > 0){
        byte_offset = ftell(arq);
        insc = strtok(recbuff, "|");
        if (strcmp(insc, search_key) == 0){
          matched = TRUE;
        }
    }
    byte_offset = byte_offset - rec_lgth;
    if (matched){
		  printf("\n\nRegistro encontrado.\n");
      printf("\tInscrição: %s\n", insc);
      printf("\tNome: %s\n", field = strtok(NULL, "|"));
      printf("\tCurso: %s\n", field = strtok(NULL, "|"));
      printf("\tNota: %s\n\n", field = strtok(NULL, "|"));
      return byte_offset;
    }
    else{
      printf("\n\nRegistro nao encontrado.\n\n");
      return -1;
    }
}

void cabecaLed(FILE *arq){
    int a;
    fseek(arq, 0, SEEK_SET);
    fread(&a, 4, 1, arq);
    printf("cabecaLed = %d\n", a);
}
