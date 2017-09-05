/*	Trabalho da disciplina Estrutura de Dados
 *	Prof: Franklin Cesar Flores (fcflores@din.uem.br)
 *	Alunos: Diogo Alves de Almeida,	Ra:95108
 *		Danillo Dias Nascimento,	Ra:90561
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "importar.h"
#include "util.h"

int menu();

int main(){
	FILE *arq;
	int totalpalavras;
	int x;

	if((arq = fopen("entrada.txt", "r")) == NULL) {//abre o arquivo
		printf("Erro na abertura do arquivo registro!!\n");
		return 0;
	}

	totalpalavras = Tamanho_Vetor(arq);
	if(totalpalavras==0){
		printf("Arquivo Vazio!!\n");
		return 0;
	}
	else {
		PALAVRA lista[totalpalavras-1];
		Inicializar_Vetor(lista, arq);
		fclose(arq);
		Inserir_Lista(lista, totalpalavras);
		do{
			x = menu(lista,totalpalavras);
		} while (x!=0);//fica em loop até o usuário sair com o '0'
		return 0;
	}
}

int menu(PALAVRA *lista, int totalpalavras){
	int x;
	printf("\n\t# # # # # # # Menu # # # # # # #\n");
	printf("\t#                              #\n");
	printf("\t#   1 - Imprimir               #\n");
	printf("\t#   2 - Escrever no Registro   #\n");
	printf("\t#   3 - Buscar Caminho         #\n");
	printf("\t#   0 - Sair                   #\n");
	printf("\t#                              #\n");
	printf("\t# # # # # # # #  # # # # # # # #\n\t-> ");
	scanf("%i", &x);
	switch (x) {
    case 1:
			Imprimir(lista, totalpalavras);
			break;
    case 2:
			Escreve_Registro(lista, totalpalavras);
      break;
  	case 3:
			Caminho(lista, totalpalavras);
      break;
    case 0:
			printf("\n\tFim de programa!!\n\n");
    	return 0;
    default:
			system("clear");
			printf("\n\tOpção Inválida!\n");
      break;
  }
  return x;
}
