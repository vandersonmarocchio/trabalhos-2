/*NAO USAR ESSE ARQUIVO
*
*
*
*
*
*
*
ESTÁ TUDO FEITO NO IMPORTAR.C E .H SÓ VOU DEIXAR NO GIT CASO DE ERRADO
*
*
*
*
*
*
*
*
*
*
*
*
*
*/
#include <stdio.h>
#include <stdlib.h>

#define DELIM_CHR '|'

typedef struct{
    char inscricao[6];
    char nome[20], curso[30];
    char score[6];
}Aluno;

struct lista{
    Aluno aluno;
    struct lista *prox;
};

int readfield(FILE*, char*, FILE*);

int main() {

    FILE* fd;
    FILE* parq;
    char s[200];
    char nome[100], curso[100];
    int fld_count;
    parq = fopen("registro.txt", "w");
    if ((fd = fopen("dados-inline.txt" , "r")) == NULL) {
       printf("Erro na abertura do arquivo!! --- Programa Abortado!!\n");
       exit(1);
    }
    /* loop do programa principal -- chama a função 'readfield'
	   enqto a função tiver sucesso (retorno > 0) */
    fld_count = 0;
    while (readfield(fd ,s, parq) > 0);
    fclose(fd);
    fclose (parq);
    printf("saiu\n");
}

/* função que lê um campo na string s
   e retorna o tamanho da string lida */

int readfield(FILE* fd, char* s, FILE *parq){
    char c;
    int i = 0;
    char itoa[2];
    int campos;
    Aluno aluno;
    c = fgetc(fd);

    /*while ( c != EOF && campos < 4){
          if (campos == 0) aluno.inscricao[j++] = c;
          else if (campos == 1) aluno.nome[j++] = c;
          else if (campos == 2) aluno.curso[j++] = c;
          else if (campos == 3) aluno.score[j++] = c;
          c = fgetc(fd);
          if(c ==DELIM_CHR){
            campos++;
            j = 0;
          }
          i++;
    }*/
    campos = 0;
    while ( c != EOF && campos < 4){
        s[i++] = c;
        c = fgetc(fd);
        if(c == DELIM_CHR)
            campos++;
}
    s[i++] = c;
    //fwrite(&i, sizeof(int), 1, parq);
    ///fwrite(&aluno, sizeof(Aluno), 1, parq);

    s[i] = '\0'; // anexa 'null' no final da string
    sprintf(itoa, "%d", i);
    fputs(itoa, parq);
    fputs(s, parq);
    i--;
    return(i);
}
