#include <stdio.h>
#include <stdio_ext.h>
#include <stdlib.h>
#include <string.h>
#include "remover.h"
#include "buscar.h"
#include "importar.h"

void led(FILE *arq, int x){
  int *cabeca;
  int aux;
  int a;
  fseek(arq, 0, SEEK_SET);//atualiza a lista da LED
  fread(&aux, 4, 1, arq);
  fseek(arq, 0, SEEK_SET);
  fwrite(&x, sizeof(int), 1, arq);
  fseek(arq, x+2, SEEK_SET);
  fwrite(&aux, sizeof(short), 1, arq);
  fseek(arq, 4, SEEK_SET);
}

int remover(FILE *arq){
  int op;
  long int x;

  x = buscar(arq);
  if ( x > 0 ){
    printf("\nDeseja remover esse candidato? (1) Sim - (Outra tecla) Não\n");
    __fpurge(stdin);
    scanf("%d", &op);
    __fpurge(stdin);
    if (op == 1){
      fseek(arq, x, SEEK_SET);
      fprintf(arq, "*|");//substitui o começo do registro
      fseek(arq, 0, SEEK_SET);
      led(arq, x);
      printf("\nRemovido com sucesso!!\n\n");
    }
  }
  return 0;
}
