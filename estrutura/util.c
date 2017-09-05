#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "importar.h"
#include "util.h"

int Fila_Vazia(CAIXINHA **inicio){  //verifica se a Fila ta vazia
  if(*inicio == NULL){
    return 1;
  } else return 0;
}

void Inserir_Fila(CAIXINHA **inicio, CAIXINHA **fim, int valor){  //insere na Fila (no fim da Fila)
  CAIXINHA *q;
  q = (CAIXINHA*) malloc(sizeof(CAIXINHA));
  q->info = valor;
  q->prox = NULL;
  if (Fila_Vazia(inicio)){
    *inicio = q;
  } else (*fim)->prox = q;
  *fim = q;
}

int Remover_Fila(CAIXINHA **inicio){  //remove da Fila (do inicio da Fila)
  int x;
  CAIXINHA *k;
  k = *inicio;
  x = k->info;
  *inicio = (*inicio)->prox;
  free(k);
  return x;
}

void Empilha(CAIXINHA **topo, int valor){ //função de empilhar no topo da pilha
  CAIXINHA *p;
  p = (CAIXINHA*) malloc(sizeof(CAIXINHA));
  p->info = valor;
  p->prox = *topo;
  *topo = p;
}

int Comparar(char* str1, char* str2){ //faz uma comparação entre duas strings e retorna se elas são iguais, diferentes ou se tem 1 letra de diferença
  int i;
  int diferente = 0;

  if(strlen(str1) == strlen(str2)){
    for (i=0; i<= strlen(str1); i++){
      if(str1[i]!=str2[i]){
        diferente++;
      }
    }
    if(diferente == 0){ //as duas palavras são iguais
      return 0;
    }
    else if (diferente == 1){ //as palavras tem 1 letra de diferença só
      return 1;
    }
  }
  return -1;  //as palavras tem mais de 1 letra de diferença
}

void Inserir_Palavra(PALAVRA *lista, int cabeca, int endereco, char* nome){ //insera a palavra na lista de adjacentes
  PALAVRA *novo;

  novo = (PALAVRA*) malloc(sizeof(PALAVRA));
  novo->nome = nome;
  novo->endereco = endereco;
  novo->prox = NULL;

  if(lista[cabeca].prox == NULL) // Caso a lista estiver vazia
  lista[cabeca].prox = novo;
  else	{
    novo->prox = lista[cabeca].prox;
    lista[cabeca].prox = novo;
  }
}

void Inserir_Lista(PALAVRA *lista, int totalpalavras){  //varre todos os elementos do vetor verificando se a palavra i e j tem apenas uma letra
  //int arestas = 0;                                      //de diferença, caso for, i é inserido na lista de j e j é inserido na lista de i
  for(int i = 0; i<=totalpalavras-1; i++){
		for(int j = 0; j<=i-1; j++){
      if(Comparar(lista[i].nome,lista[j].nome)==1){ //se as duas palavras tem apenas 1 letra de diferença
				Inserir_Palavra(lista, i, j, lista[j].nome);
				Inserir_Palavra(lista, j , i, lista[i].nome);
        //arestas++;
      }
		}
	}
  //printf("Arestas: %d\n", arestas);
}

void Imprimir(PALAVRA *lista, int totalpalavras){ //imprime a lista de adjacentes
  PALAVRA *q;
  printf("\n\n");
  for(int i=0; i<=totalpalavras-1; i++){  //vai andando pelas cabeças das listas (indices do vetor)
    printf("%4d [%2d] %s ==> ", lista[i].endereco, i , lista[i].nome);
		q = lista[i].prox;
		if (q == NULL){ //se a lista for vazia
			printf("NULL");
    }
		else {
			while (q != NULL) {  //percorre até o ultimo elemento da lista
      	printf("[%d]%s  ", q->endereco, q->nome);
				q = q->prox;
    	}
		}
		printf("\n");
		}
    printf("\n");
}

int Procurar(PALAVRA *lista, int totalpalavras, char* str1){ //verifica se a string str1 está no vetor (grafo), se sim retorna 1, caso contrário, 0.
  for(int i=0 ; i<= totalpalavras-1; i++){
    if(Comparar(lista[i].nome, str1)==0){
      return 1;
    }
  }
  return 0;
}

int Caminho(PALAVRA *lista, int totalpalavras){ //recebe duas palavras, verifica se elas estão no vetor (grafo) e devolve a distancia
  CAIXINHA *topo;                               // e o caminho delas se existir
  CAIXINHA *inicio;
  CAIXINHA *fim;
  PALAVRA *q;
  topo = NULL;
  inicio = NULL;
  fim = NULL;
  char origem[10], destino[10];
  int distancia = 0;

  printf("\n\tDigite duas palavras para saber o caminho e sua distância:\n");

  do{
    printf("\t\tInício: ");
    scanf(" %s", origem);
  }while (Procurar(lista, totalpalavras, origem)!=1); //fica em loop até que o usuário coloque uma palavra que exista no vetor (grafo)

  do{
    printf("\t\tDestino: ");
    scanf(" %s", destino);
  }while (Procurar(lista, totalpalavras, destino)!=1);  //fica em loop até que o usuário coloque uma palavra que exista no vetor (grafo)

  printf("\n\n");

  if (Comparar(origem, destino)==0){  //caso as duas palavras sejam iguais, é o caso mais fácil, a distancia é zero
    printf("\tInício e Destino são a mesma palavra!! ---- Distância = 0\n\n");
  } else {
    for (int i = 0 ; i<=totalpalavras-1; i++){ //varre todo o vetor inicializando tudo com -1(não visitado), menos o vertice "origem"
      if (Comparar(lista[i].nome, origem)==0){
        lista[i].endereco = i;  //o endereço do vertice de origem é setado com o seu proprio endereço e inserido na fila
        Inserir_Fila(&inicio, &fim, i);
      } else lista[i].endereco = -1; //inicializa tudo com -1 menos o vertice de origem
    }
    int indice;
    int ori;
    ori = inicio->info; //necessário guardar esse valor para saber parar a hora de empilar na pilha dos vertices do caminho
    while(!Fila_Vazia(&inicio)){
        indice = Remover_Fila(&inicio); //retira o elemento da fila para ver seus filhos
        q = lista[indice].prox;
        while(q!=NULL){
          if(lista[q->endereco].endereco == -1){  //se não for visitado ainda
            lista[q->endereco].endereco = indice; // marca como visitado com o indice de seu pai
            Inserir_Fila(&inicio, &fim, q->endereco); // insere esse elemento na fila
          }
          if(Comparar(q->nome, destino)==0){    //se for a palavra de destino
            int aux;
            aux = q->endereco;
            Empilha(&topo, aux);  //empilha o endereço do vertice de destino na pilha
            while(topo->info!=ori){ //vai empilhando até chegar no vertice de origem, por isso necessário a variavel "ori"
              aux = lista[aux].endereco;
              Empilha(&topo, aux);
              distancia++;
            }
            printf("\t");
            while(topo!=NULL){  //vai desempilhando e printando correntamente da origem até o destino
              if(topo->prox == NULL){
                printf("%s\n",lista[topo->info].nome);
              }else printf("%s -> ",lista[topo->info].nome);
              topo = topo->prox;
            }
            printf("\n\n\tDistância de %s para %s = %d\n\n",origem, destino, distancia);
            return 0;
          }
          q = q->prox;
        }
      }
      printf("\tNão há caminho de %s para %s !! ---- Distância = 0\n\n", origem, destino );
      return -1;
    }
}
