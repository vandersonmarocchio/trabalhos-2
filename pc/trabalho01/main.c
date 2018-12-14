/*
 * Versão Paralela do Algoritmo Floyd-Warshall 
 * Desenvolvido por: Narcizo Gabriel, Diogo Almeida e Leonardo Puglia
 * Criado dia 10/11/2018
 * Atualizado dia 21/11/2018 
 */
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <pthread.h>
#include "./util.h"

#define MIN(x, y) (((x) < (y)) ? (x) : (y))

int nthreads = 1, size, parallel = 0;
char *graphPath;
float **graph, **path;
pthread_barrier_t barrier;

// Algoritmo de Floyd-Warshall paralelo
void *fwParallel(void *args){
    int tid = (int)args;
    float **aux;

    // m[k] = graph
    // m[k-1] = path

    for(int k = 0; k < size; k++){
        for(int i = tid; i < size; i+=nthreads) // Paralelização das linhas
            for(int j = 0; j < size; j++)
                graph[i][j] = MIN(path[i][k] + path[k][j], path[i][j]);
        // Barreira para esperar o término da execução de cada iteração
        pthread_barrier_wait(&barrier); 
        if(tid == 0){
            aux = graph;
            graph = path;
            path = aux;
        }
        // Barreira para esperar a troca dos ponteiros das matrizes
        pthread_barrier_wait(&barrier);
    }
}

// Algoritmo de Floyd-Warshall
void fw(){
    for(int k = 0; k < size; k++){
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(path[i][k] + path[k][j] < path[i][j])
                    path[i][j] = path[i][k] + path[k][j];
    }
}

int main(int argc, char **argv) {
    int i;
    pthread_t *t;

    if(parameters(argc, argv))
        exit(1);

    // Cria e popula o grafo graph
    size = buildGraph(graphPath);

    // Cria o grafo path
    path = (float**) calloc(size, sizeof(float*));
    for(int i = 0; i < size; i++)
        path[i] = (float*) calloc(size, sizeof(float));

    // Popula grafo path
    for(int i = 0; i < size; i++){
        for(int j = 0; j < size; j++){
            path[i][j] = graph[i][j];
            if((path[i][j] == 0) && (i != j)){
                path[i][j] = INT_MAX;   
                graph[i][j] = INT_MAX;
            }
        }
    }

    if(parallel){
        printf("Paralelo\n");
        // Cria o vetor de threads de tamanho nthreads
        t = (pthread_t*)malloc (nthreads*sizeof(pthread_t));

        // Inicia a barreira 
        pthread_barrier_init(&barrier, NULL, nthreads);
        
        // Cria as threads e chama a função fwParallel()
        for(i = 0; i < nthreads; i++)
            pthread_create(&t[i], NULL, fwParallel, (void*)i);

        // Espera o término da execução de todas a threads
        for(i = 0; i < nthreads; i++)
            pthread_join(t[i], NULL);
    }
    else{
        printf("Sequencial\n");
        // Chama a função sequencial
        fw();
    }

    // Printa o grafo
    // printGraph(path, size);
    return 0 ;
}
