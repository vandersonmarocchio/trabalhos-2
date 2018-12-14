#include<stdio.h>
#include<string.h>
#include"importar.h"
#include"buscar.h"
#include"remover.h"
#include"inserir.h"

int menu();

int main(){
    FILE *arq;
    int x;

    if ((arq = fopen("registro.txt", "r+")) == NULL) {//abre o arquivo
       printf("Erro na abertura do arquivo registro!! --- Criando novo arquivo!!\n");
       prepara();
    }

    do {
      x = menu(arq);
  } while (x!=0);//fica em loop até o usuário sair
    fclose(arq);//fecha o arquivo
    return 0;
}

int menu(FILE *arq){
      int x;
      printf("Menu\n");
      printf("1 - Importação\n");
      printf("2 - Busca\n");
      printf("3 - Inserção\n");
      printf("4 - Remoção\n");
      printf("5 - Cabeça da LED\n");
      printf("0 - Sair\n");
      scanf("%i", &x);

      switch (x) {
        case 1:
          prepara();//entra na importacao
          printf("\nImportação feita com sucesso!!\n\n");
          break;
        case 2:
          buscar(arq);//entra na Busca
          break;
        case 3:
          inserir(arq);//entra na Inserção
          break;
        case 4:
          remover(arq);//entra na Remoção
          break;
        case 5:
          cabecaLed(arq);//mostra a cabeça da LED
          break;
        case 0:
          printf("\nFim de programa!!\n");
          return 0;
        default:
          printf("\nOpção Inválida!\n");
          break;
      }
      return x;
}
