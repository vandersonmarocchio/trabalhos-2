#include<stdio.h>
#include<stdlib.h>
#include<math.h>

#define MAX_BK_TAM 10 // tamanho do bucket

typedef struct{
  int prof; //inteiro que armazena a profundidade do bucket, bits de endereço
  int cont; //inteiro que armazena o numero de chaves contidas no bucket
  int *keys; //armazena chaves
  int id;
}Bucket;

typedef struct{
  Bucket *Bucket_Ref;
}Dir_Cel;

typedef struct{
    Dir_Cel *vet;
    int prof;
}Diretorio;

int make_address(int key, int profundidade);
void ler();
int op_add(int key);
int op_find(int key, Bucket **found_bucket);

Diretorio Dir; //Declaração do diretório
int global = 0; //ID

void print_bucket(Bucket *bucket){
  printf("\n == Bucket #%d == \n", bucket->id);
  printf("#Id = %d  ", bucket->id);
  printf("Depth = %d\n", bucket->prof);
  for(int i=0; i<bucket->cont; i++){
    printf("Chave[%d] = %d \n", i, bucket->keys[i]);
  }
}

void print_diretorio(){
  printf("Diretório:\n");
  for(int i = 0; i<pow(2,Dir.prof); i++){
    printf("Dir[%2d] = Bucket #%d\n", i, Dir.vet[i].Bucket_Ref->id);
  }
  for(int i = 0; i<pow(2,Dir.prof); i++){
    print_bucket(Dir.vet[i].Bucket_Ref);
  }
}

int make_address(int key, int profundidade){
  //inverte os bits do endereço e extrai profundidade bits
  int retval = 0; //armazenara a sequencia invertidas de bits
  int mask = 1; //mascara 0...001 para extrair o bit de mais baixa ordem
  int lowbit;
  for(int j=0; j < profundidade; j++){
      retval = retval << 1; //extrai o item de mais baixa ordem de hashval
      lowbit = key & mask; //insere lowbit no final de hashval
      retval = retval | lowbit;
      key = key >> 1;
  }
  return retval;
}

void dir_double(){
  int atual_tam;
  int i;
  int novo_tam;
  Diretorio aux;

  atual_tam = pow(2, Dir.prof);
  novo_tam = 2 * atual_tam;
  aux.vet = Dir.vet;
  Dir.vet = malloc(novo_tam * sizeof(Dir_Cel));
  for (i = 0; i < atual_tam; i++){
    Dir.vet[2*i].Bucket_Ref = aux.vet[i].Bucket_Ref;
    Dir.vet[2*i+1].Bucket_Ref = aux.vet[i].Bucket_Ref;
  }
  Dir.prof++;
  free(aux.vet);
}

void ler(){
  FILE *arq;
  int chaves;
  char str[8];
  int i = 0;

  if((arq = fopen("chaves.txt", "r"))==NULL){
    printf("Problema ao abrir o arquivo!!\n");
  }

  while(fscanf(arq, "%s\n", str)!=EOF){
    chaves = atoi(str);
    op_add(chaves);
  }
}

int op_find(int key, Bucket **found_bucket){
  int address;
  address = make_address(key, Dir.prof);
  (*found_bucket) = Dir.vet[address].Bucket_Ref;
  for(int i=0; i < MAX_BK_TAM; i++){
    if((*found_bucket)->keys[i] == key){
      return 1;
    }
  }
  return 0;
}

void dir_ins_bucket (Bucket *bucket, int new_start, int new_end){
  for(int i = new_start; i <=  new_end; i++)
    Dir.vet[i].Bucket_Ref = bucket;
  //Para J = START até END faça
  //DIRETORIO[J].BUCKET_REF = BUCKET_ADDRESS
}

void find_new_range(Bucket *old_bucket, int* new_start, int* new_end){
  int mask = 1;
  int shared_adress;
  int new_shared;
  int bits_to_fill;

  shared_adress = make_address(old_bucket->keys[0], old_bucket->prof);
  new_shared = shared_adress << 1;
  new_shared = new_shared | mask;
  bits_to_fill = Dir.prof - (old_bucket->prof + 1);
  shared_adress = shared_adress | mask;
  shared_adress = shared_adress << 1;
  *new_start = *new_end = new_shared;
  for(int j=0;j<bits_to_fill;j++){
    *new_start = *new_start << 1;
    *new_end = *new_end << 1;
    *new_end = *new_end | mask;
  }
}

void bk_split(Bucket *bucket){
  int new_start, new_end;
  int *keys = bucket->keys;
  int cont = bucket->cont;

  if((bucket)->prof == Dir.prof){
    dir_double();
  }
  Bucket *novo_bucket;
  novo_bucket = (Bucket*)malloc(sizeof(Bucket));// alocar novo bucket e atribuir o seu endereço a END_NOVO_BUCKET
  novo_bucket->keys = malloc(sizeof(int) * MAX_BK_TAM);
  novo_bucket->id = global;
  novo_bucket->cont = 0;
  global++;
  find_new_range(bucket, &new_start, &new_end); //2 inteiros new_end new_start
  dir_ins_bucket(novo_bucket, new_start, new_end);
  bucket->prof++;
  novo_bucket->prof = bucket->prof;
  bucket->cont = 0;
  bucket->keys = malloc(sizeof(int) * MAX_BK_TAM);
    for(int i = 0; i < cont; i++){
        int new_add = make_address(keys[i], Dir.prof);
        if((new_add >= new_start) && (new_add <= new_end)){
            novo_bucket->keys[novo_bucket->cont] = keys[i];
            novo_bucket->cont = novo_bucket->cont + 1;
        }
        else{
            bucket->keys[bucket->cont] = keys[i];
            bucket->cont = bucket->cont + 1;
        }
    }
    free(keys);
  //redistribuir as chaves entre o bucket e o novo_bucket
}

void bk_add_key(Bucket **bucket, int key){
  if ( (*bucket)->cont < MAX_BK_TAM){
    //inserir key em bucket
    (*bucket)->keys[(*bucket)->cont] = key;
    (*bucket)->cont++;
  }
  else{
    bk_split(*bucket);
  //  print_diretorio();
    op_add(key);
  }
}

int op_add(int key){
  Bucket *found_bucket;
  if(!op_find(key, &found_bucket)){
    bk_add_key(&found_bucket, key);
    return 1;
  }
  return 0;
}

int main(){
  Dir.vet = (Dir_Cel*)malloc(sizeof(Dir_Cel));
  Dir.prof = 0;
  Dir.vet[0].Bucket_Ref = (Bucket*)malloc(sizeof(Bucket));
  Dir.vet[0].Bucket_Ref->keys = malloc(sizeof(int) * MAX_BK_TAM);
  global = 0;
  Dir.vet[0].Bucket_Ref->id = global;
  Dir.vet[0].Bucket_Ref->cont = 0;
  global++;
  ler();
  print_diretorio();
}
