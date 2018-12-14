/*
 * Arquivo com funções secundárias
 * Desenvolvido por: Narcizo Gabriel, Diogo Almeida e Leonardo Puglia
 * Criado dia 04/10/2018
 * Atualizado dia 21/10/2018 
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "util.h"
#include <string.h>

void show_help(char *name) {
    fprintf(stderr, "\
            [uso] %s <opcoes>\n\
            -h          Ajuda.\n\
            -n threads  Número de threads.\n\
            -g caminho  Caminho para o grafo\n\
            -p paralelo\n", name) ;
    exit(-1) ;
}

int parameters(int argc, char **argv){
    int opt;

    if ( argc > 6 ) show_help(argv[0]) ;

    while( (opt = getopt(argc, argv, "hn:g:p")) > 0 ) {
        switch ( opt ) {
            case 'h': /* help */
                show_help(argv[0]) ;
                break ;
            case 'n': /* opção -n */
                nthreads = atoi(optarg) ;
                break ;
            case 'g': /* opção -i */
                graphPath = optarg ;
                break ;
            case 'p':
                parallel = 1;
                break;
            default:
                fprintf(stderr, "Opcao invalida ou faltando argumento: `%c'\n", optopt) ;
                return 1 ;
        }
    }
    return 0;
    }

int buildGraph(char *path){
    int size = -2, i, j;
    float distance;
    FILE *  f;
    char *line = NULL;
    size_t buffer = 0;
    f = fopen(path, "r"); // Abre o arquivo
    if(f == NULL){ // Se o arquivo não existir termina o programa
        printf("O arquivo não existe!\n");
        exit(1);
    }
    do{
        getline(&line, &buffer, f);
        size++;
    }while(strcmp(line, "#arestas\n")); // Pega o tamanho da matriz
    
    graph = (float**) calloc (size, sizeof(float*)); // Constroi o grafo
    for(int k = 0; k < size; k++)
        graph[k] = (float*) calloc (size, sizeof(float));
    
    while(getline(&line, &buffer, f) != -1){ // Popula o grafo com as distancias
        i = atoi(strtok(line, " "));
        j = atoi(strtok(NULL, " "));
        distance = atof(strtok(NULL, " \n"));
        graph[i-1][j-1] = distance;
    }
    fclose(f);

    return size;
}

void printGraph(float **matrix, int size){
    printf("\n||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n");
    for(int i = 0; i < size; i++){
        printf("| ");
        for(int j = 0; j < size; j++){
            printf("%.2f ", matrix[i][j]);
        }
        printf("\n");
    }
    printf("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n");
}