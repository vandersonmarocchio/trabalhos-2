#ifndef util_H_
#define util_H_
#include <stdio.h>


typedef struct caixinha{
 int info;
 struct caixinha *prox;
}CAIXINHA;

int Fila_Vazia(CAIXINHA **inicio);
void Inserir_Fila(CAIXINHA **inicio, CAIXINHA **fim, int valor);
int Remover_Fila(CAIXINHA **inicio);
void Empilha(CAIXINHA **topo, int valor);
void Inserir_Lista(PALAVRA *lista, int totalpalavras);
void Inserir_Palavra(PALAVRA *lista, int cabeca, int endereco, char* nome);
int Comparar(char* str1, char* str2);
void Imprimir(PALAVRA *lista, int totalpalavras);
int Procurar(PALAVRA *lista, int totalpalavras, char* str1);
int Caminho(PALAVRA *lista, int totalpalavras);

#endif
