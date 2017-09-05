#ifndef importar_H_
#define importar_H_
#include <stdio.h>

typedef struct palavra{
	char* nome;
	int endereco;
	struct palavra* prox;
}PALAVRA;
int Tamanho_Vetor(FILE *arq);
void Inicializar_Vetor(PALAVRA *lista, FILE *arq);
void Escreve_Registro(PALAVRA *lista, int totalpalavras);

#endif
