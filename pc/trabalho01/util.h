/*
 * Arquivo com funções secundárias 
 * Desenvolvido por: Narcizo Gabriel, Diogo Almeida e Leonardo Puglia
 * Criado dia 04/10/2018
 * Atualizado dia 21/10/2018 
 */
#ifndef UTIL_H
#define UTIL_H

//Número de threads
extern int nthreads;
//Caminho do arquivo do grafo
extern char *graphPath;
//Grafo
extern float **graph;
//Predecessor
extern float **path;
//Paralelo
extern int parallel;

//Mostra a ajuda dos parametros
void show_help(char *name);
//Seta os parametros informados na chamada de programa
int parameters(int argc, char **argv);
//constroi o grafo
int buildGraph(char *path);
//printa o grafo
void printGraph(float **graph, int size);

#endif