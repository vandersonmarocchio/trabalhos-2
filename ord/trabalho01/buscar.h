#ifndef buscar_H_
#define buscar_H_
#include <stdio.h>

//int buscar(FILE *arq);
short get_rec(char* recbuff, FILE* arq);
int buscar(FILE *arq);
void cabecaLed(FILE *arq);
#endif
