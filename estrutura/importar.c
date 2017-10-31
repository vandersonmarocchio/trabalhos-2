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

int Tamanho_Vetor(FILE *arq){		//vai até o fim do arquivo contando quantas palavras tem no registro e retorna essa quantidade
	char c;
	int totalpalavras = 0;
	c = fgetc(arq);
	while (c!=EOF){
		if(c == '\n'){
			totalpalavras++;
		}
		c = fgetc(arq);
	}
	return totalpalavras;
}

void Inicializar_Vetor(PALAVRA *lista, FILE *arq){	//le a palavra , coloca na caixinha de cada vetor com seu respectivo nome , enderecom e prox aterrado
	char str[10];
	int i = 0;
	fseek(arq, 0, SEEK_SET);
	while(fscanf(arq,"%s", str)!=EOF){
		lista[i].nome = strdup(str);
		lista[i].endereco = i;
		lista[i].prox = NULL;
		i++;
		}
}

void Escreve_Registro(PALAVRA *lista, int totalpalavras){	//escreve no registro para melhor visualzação de indices e palavras
  int i;
  PALAVRA *q;
	FILE *fd;

	if((fd = fopen("registro.txt", "w")) == NULL) {//abre o arquivo
		printf("Erro na abertura do arquivo registro!! --- Criando novo arquivo!!\n");
	}

  for(i=0; i<=totalpalavras-1; i++){ //vai andando pelas cabeças das listas (indices do vetor)
		fprintf(fd, "%2d [%2d] %s ==> ",lista[i].endereco, i, lista[i].nome);
		q = lista[i].prox;
		if (q == NULL){ // se a lista for vazia
			fprintf(fd, "NULL");
		}
		else {
			while (q != NULL) {  // percorre até o ultimo elemento da lista
				fprintf(fd, "[%d]%s  ", q->endereco, q->nome);
				q = q->prox;
    	}
		}
		fprintf(fd, "\n");
  }
	printf("\n\tRegistro modificado com sucesso!!\n");
	fclose(fd);
}
