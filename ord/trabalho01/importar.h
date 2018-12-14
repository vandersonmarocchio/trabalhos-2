#ifndef importar_H_
#define importar_H_
#include <stdio.h>

extern FILE *arq;
int prepara();
int importacao(FILE* fd, char* s, FILE* parq);

#endif
