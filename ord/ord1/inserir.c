#include<stdio.h>
#include <stdio_ext.h>
#include<stdlib.h>
#include<string.h>
#include"buscar.h"
#include"importar.h"

#define TRUE 1
#define FALSE 0
#define MAX_REC_SIZE 512

int escreve(FILE *arq, int byte_offset, int tam, char* inscricao, char* nome, char* curso, char* notas){
    short comp;
    int aux, tell;

    tell = ftell(arq);
    printf("entrando na funcao, byte_offset = %d\n", tell);
    if(byte_offset == -1){//escreve no fim do arquivo
        fseek(arq, 0, SEEK_END);
        fwrite(&tam, sizeof(short), 1, arq);//escreve o tamanho do registro
        fseek(arq, 2, SEEK_CUR);
        fputs(inscricao, arq);
        fputs("|", arq);
        fputs(nome, arq);
        fputs("|", arq);
        fputs(curso, arq);
        fputs("|", arq);
        fputs(notas, arq);
        fputs("|", arq);
        tell = ftell(arq);
        printf("byte_offset = %d\n", tell);

    }
    else{
        fseek(arq, byte_offset-2, SEEK_SET);
        fread(&comp, 2, 1, arq);
        if(comp < tam){//compara o tamanho do registro do arquivo com o tamanho do registro a ser inserido
            fseek(arq, 2, SEEK_CUR);
            fread(&comp, 2, 1, arq);
            escreve(arq, comp, tam, inscricao, nome, curso, notas);//escreve com o novo byte_offset
        }
        tell = ftell(arq);
        printf("byte_offset = %d\n", tell);
        fseek(arq, 2, SEEK_CUR);
        fread(&comp, 2, 1, arq);//guarda o byte_offset para inserir na cabeça
        fseek(arq, tell, SEEK_SET);//reseta o ponteiro para escrever

        fputs(inscricao, arq);//escreve no arquivo os registros
        fputs("|", arq);
        fputs(nome, arq);
        fputs("|", arq);
        fputs(curso, arq);
        fputs("|", arq);
        fputs(notas, arq);
        fputs("|", arq);
        tell = ftell(arq);
        printf("byte_offset = %d\n", tell);

        fseek(arq, 0, SEEK_SET);
        fwrite(&comp, sizeof(int), 1, arq);//short -> int
    }
}


int inserir(FILE *arq){
  int tamanho, aux;
  char nome[100], inscricao[10];
  char curso[100], notas[10];

  int matched;
  short rec_lgth;
  char search_key[30];
  char recbuff[MAX_REC_SIZE + 1];
  char *field;
  char *insc;
  int byte_offset, boole;

  boole = FALSE;
  do{
      fseek(arq, 4, SEEK_SET);
      do{
        printf("\nDigite o CODIGO a ser buscado (6 digitos): "); // get search key
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
  		printf("\n\nRegistro já inserido.\n");
        printf("\tInscrição: %s\n", insc);
        printf("\tNome: %s\n", field = strtok(NULL, "|"));
        printf("\tCurso: %s\n", field = strtok(NULL, "|"));
        printf("\tNota: %s\n\n", field = strtok(NULL, "|"));
      }
      else{
        printf("\n\nRegistro nao encontrado.\n\n");
        boole = TRUE;
    }
}while(!boole);

    printf("Inscrição: %s\n", search_key);//pega as informações para o registro
    printf("Nome:");
    __fpurge(stdin);
    fgets(nome, 100, stdin);
    nome[strlen(nome)-1]='\0';
    printf("Curso:");
    __fpurge(stdin);
    fgets(curso, 100, stdin);
    curso[strlen(curso)-1] = '\0';
    printf("Nota:");
    __fpurge(stdin);
    fgets(notas, 10, stdin);
    notas[strlen(notas)-1] = '\0';

    tamanho = strlen(inscricao) + 1 + strlen(nome) + 1 + strlen(curso) + 1 + strlen(notas) + 1; //calcula o tamanho do registro
    printf("Tamanho: %d\n", tamanho);

    fseek(arq, 0, SEEK_SET);
    fread(&aux, 4, 1, arq);//le a cabeça da LED
    if(aux == -1){
        escreve(arq, -1, tamanho, search_key, nome, curso, notas);//escreve no fim do arquivo
    }else{
        escreve(arq, aux, tamanho, search_key, nome, curso, notas);//busca onde escrever
    }
}
