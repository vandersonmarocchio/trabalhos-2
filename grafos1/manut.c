/*
	Name: 1º Trabalho de Grafos (MANUT - Manutenção)
	Author:  Diogo Alves de Almeida,	Ra: 95108
	         Luis Felipe Costa Guimarães Teixeira,	Ra: 95372
           William Kenji Takahara Sakata,	Ra: 95387
	Description:  http://br.spoj.com/problems/MANUT/
*/

#include <stdio.h>
#include <stdlib.h>

int contador, raiz, filhosDaRaiz; //declarando variáveis globais para não ter problema de declaração
int tempo[400]; // se tempo[i] = k, i foi visitado no tempo k
int low[400]; // low(u) é o acestral de u com o menor tempo de descoberta que pode pode ser alcançado a partir de um descendente de u por meio de uma aresta de retorno na árvore de busca em profundidade.
int visitado[400] ; // se visitado[i] = 0, vertice i não foi visitado, se visitado[i] = 1, vertice i foi visitado
int adj[400][400]; //matriz adjacente
int pai[400]; // se pai[i] = v, o pai de i é v
int articulacao[400]; // se articulacao[i] = 1, i é ponto de articulação, se articulacao[i] = 0, i não é ponto de articulação

int lowmin(int a, int b){ //função para retornar o valor de low minimo
    if(a < b){
        return a;
    }
    return b;
}

void buscaEmProfundidade(int v, int n) {
	int u;
	visitado[v] = 1; // garantindo que esse vertice foi visitado
	contador++;	//aumenta 1 cada vez que entra na função, ou seja, cada vez que achar um vertice
	low[v] = contador;
	tempo[v] = contador;
	for (u = 0; u < n; u++){
		if (adj[v][u]==1) //se u for adjacente de V...
			if (visitado[u]==0) { //se o vertice u não foi visitado...
				if (v == raiz){ // se v for raiz...
					filhosDaRaiz++;  // adiciona um filho para a raiz (se a raiz tiver + que 2 é ponto de articulação)
				}
				pai[u] = v; // v é pai de u
				buscaEmProfundidade(u, n); // busca em profundidade a partir de u
				if (low[u] >= tempo[v]){ // v é ponto de articulação se tiver um filho u tal que low(u) >= tempo(v), ou seja, low(u) >= tempo de descoberta de v.
					articulacao[v] = 1; // vertice v é ponto de articulação
				}
				low[v] = lowmin(low[v], low[u]);  // calcula o low minimo de low de u e v para colocar em low[v]
			}else if(pai[v] != u){ // Se não for aresta de retorno (se o pai de V for diferente de u)
					low[v] = lowmin(low[v], tempo[u]);  // calcula o low minimo de low de v e o tempo de descoberta de u para colocar em low[v]
			 }
	}
}

void PontosDeArticulacao(int raizEntrada, int n) {
	int i;
	raiz = raizEntrada;
	contador = 0;
	filhosDaRaiz = 0; // seta raiz com nenhum filho
	for ( i = 0; i < n; i++){ //inicializa a arvore
		tempo[i] = 0; //seta o tempo de visita dos vertices
		low[i] = 0; //seta o low dos vertices
		visitado[i] = 0; //seta todos os vertices como não visitados
		articulacao[i] = 0; //seta todos os vertices como não sendo ponto de articulação
		pai[i] = i; // pai de i é proprio pai de i, como se fosse null
	}
  buscaEmProfundidade(raizEntrada, n);
	articulacao[raizEntrada] = filhosDaRaiz > 1; //A raiz será ponto de articulação se tiver mais de 1 filho na arvore
}

int main() {
	int N, M, x, y, i, j, primeiro, grafo = 1;

	while(1){
    scanf("%d %d", &N, &M);
		if ((N == 0) && (M == 0)){	//para o fim do programa
			printf("Fim de programa!!\n");
			exit(0);
		}
		if ((M<N-1)||(M>(N*(N-1)/2))){	//trata das restrições do exercicio
			printf("Números de N e M inválidos!!\n");
			exit(0);
		}
		for (i = 0; i < N; i++){	//seta todos os vertices na matriz adjacente com nenhuma aresta
			for (j = 0; j < N; j++){
				adj[i][j] = 0;
			}
		}
		for ( i = 0; i < M; i++) { //recebe as M arestas do grafo
			scanf("%d %d", &x, &y);
			while ((x<1)||(x>N)||(y<1)||(y>N)||(x==y)){	//trata das restrições do exercicio
				printf("Aresta inválida, digite novamente:\n");
				scanf("%d %d", &x, &y);
			}
			x--; //tira 1 para colocar na posição certa da matriz, que começa em 0
			y--; //tira 1 para colocar na posição certa da matriz, que começa em 0
			adj[x][y] = adj[y][x] = 1; //diz que há ligação entre os vertices
		}

        printf("Grafo representado por matriz de adjacentes:\n\n"); // printando matriz
		for ( i = 0; i < N; i++){
			for ( j = 0; j < N; j++){
				printf(" %i", adj[i][j]);
				if (j==N-1)
				printf("\n");
			}
		}
		printf("\n");
		PontosDeArticulacao(0, N);
		primeiro = 1;
		printf("Teste %d\n", grafo++);
		for ( i = 0; i < N; i++){ // vai varrer o vetor de ponto de articulação
			if (articulacao[i] == 1) { //se o vertice i for ponto de articulação... (articulação[i]=1)
				if (primeiro == 0) //se primeiro = 0 significa que já foi printado um ponto de articulação, então deve ser printado um "espaço" para o próximo vertice, se tiver outro
					printf(" ");
				primeiro = 0; //garantindo que foi printado o primeiro
				printf("%d", i + 1); //printa o vertice que é ponto de articulação, deve somar 1 por ser uma matriz começada em 0
			}
		}
		if (primeiro == 1){ //se depois de todo o for rodar a variável "primeiro" continuar sendo 1, significa que nenhum ponto de articulação foi printado, ou seja, não tem nenhum ponto de articulação
			printf("Nenhum");
		}
    printf("\n\n");
    printf("# # # # # # # # # # # # # # # # # # # # # #\n\n");
	}
	return 0;
}
