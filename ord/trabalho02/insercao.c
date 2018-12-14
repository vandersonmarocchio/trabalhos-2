#include <stdio.h>
#include <stdlib.h>

/*typedef struct{
  int profundidade;
  int num;
}bucket;

typedef struct{
}dir;
*/

void op_find(int key){
  int address;
  address = op_find(key, dir_prof);
}

int op_add(int key){
  if(!op_find(key, found_bucket)){
    return 0;
  }
  bk_add_key(found_bucket, key);
  return 1;
}

void bk_add_key ();

void bk_split();

void make_address(int key, int profundidade){
  int retval = 0;
  int mask = 1;
  int i;
  int lowbit;

  for(i = 0; i<profundidade; i++){
    retval = reval << 1;
    lowbit = key & mask;
    retval =  retval | lowbit;
    key = key >> 1;
  }
}

void dir_double();

void find_new_range();

void dir_ins_bucket();

void ler(){
  FILE *arq;
  int chaves;
  char str[8];
  int i = 0;

  if((arq = fopen("chaves.txt", "r"))==NULL){
    printf("Problema ao abrir o arquivo!!\n");
  }

  while(fscanf(arq, "%s\n", str)!=EOF){
    //printx'f("[%d] ", ++i );
    //printf("%s -> ",str );
    chaves = atoi(str);
    //printf("%d\n", chaves );
  }
}
