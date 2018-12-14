#ifndef inserir_H_
#define inserir_H_
#include <stdio.h>

//short get_rec(char* recbuff, FILE* fd);
int inserir(FILE *arq);
int escreve(FILE *arq, int byte_offset, int tam);
#endif
