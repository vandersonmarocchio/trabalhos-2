/*
Título: Trabalho de PAA (Projeto 1)
Nome: Diogo Alves de Almeida, Ra: 95108
      Luis Felipe Costa Guimarães Teixeira, Ra: 95
Observações: Para trocar o arquivo de leitura, por favor, trocar o nme do arquivo na linha 30.
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>

FILE *arq;
char str[10];
int max;
int chamadas = 0;
int elaborada, simples; //total de numeros, tempo do simples, tempo do elaborada

//cabeçalho
void importar(int *vetor);
void mergeSort(int *vetor, int comeco, int fim);
void merge(int *vetor, int comeco, int meio, int fim);
int busca_simples(int *vetor, int num);
int busca_elaborada(int *vetor, int inicio, int fim, int valor);
void print(int *vetor, int inicio, int meio, int fim);


int main(){
    struct timeval tvalBefore, tvalAfter;
    struct timeval tvalAntes, tvalDepois;
    if((arq = fopen("1000numeros.txt", "r")) == NULL) {//abre o arquivo
      printf("Erro na abertura do arquivo registro!!\n");
      return 0;
    }
    fscanf(arq,"%s", str);
    max = atoi(str);
    printf("Total de palavras: %i\n", max);
    int vetor[max - 1];
    importar(vetor);
    mergeSort(vetor, 0 , max -1);

    gettimeofday (&tvalBefore, NULL);
    printf("Simples => Nesse vetor há %i vezes o número.\n", busca_simples(vetor , 5));
    gettimeofday (&tvalAfter, NULL);
    simples = (((tvalAfter.tv_sec - tvalBefore.tv_sec)*1000000L+tvalAfter.tv_usec) - tvalBefore.tv_usec);
    printf("Tempo em microsegungos: %i microsegungos.\n\n", simples);

    gettimeofday (&tvalAntes, NULL);
    printf("Elaborada => Nesse vetor há %i vezes o número.\n", busca_elaborada(vetor, 0, max -1, 5));
    gettimeofday (&tvalDepois, NULL);
    elaborada = (((tvalDepois.tv_sec - tvalAntes.tv_sec)*1000000L+tvalDepois.tv_usec) - tvalAntes.tv_usec);
    printf("Tempo em microsegungos: %i microsegungos.\n\n", elaborada);
    if (elaborada < simples) printf("Elaborada %i vezes mais rápida que Simples => %i microsegungos de dif.\n", simples / elaborada, simples - elaborada);
    else if (elaborada > simples) printf("ERRO!! Simples %i vezes mais rápida que Elaborada => %i microsegungos de dif.\n", elaborada / simples, elaborada - simples);
    else printf("Empate.\n");
    fclose(arq);

    return 0;
}

int busca_elaborada(int *vetor, int inicio, int fim, int valor){ //busca binaria recursiva
    int quantia = 0;
    chamadas++;
    if (inicio > fim) return 0;
    else {
        int meio = (inicio + fim)/2;
        if ((vetor[inicio] == vetor[fim]) && (vetor[inicio] == valor) && (vetor[fim] == valor)){ // SE TODOS DO VETOR FOR O NUMERO
            quantia += fim - inicio + 1;
            return quantia;
        } else if ((vetor[inicio] == vetor[meio]) && (vetor[inicio] == valor) && (vetor[meio] == valor)){ // SE O INICIO ATÉ O MEIO DO VETOR FOR O NUMERO
              quantia += meio - inicio + 1;
              if (vetor[meio+1] == valor){ // SE AINDA TEM ESSE ELEMENTO NA DIREITA DO MEIO
                  quantia += busca_elaborada(vetor, meio+1, fim, valor);
              }
              return quantia;
          } else if ((vetor[meio] == vetor[fim]) && (vetor[meio] == valor) && (vetor[fim] == valor)){ // SE O MEIO ATÉ O FIM DO VETOR FOR O NUMERO
                quantia += fim - meio + 1;
                if (vetor[meio-1] == valor){ //SE AINDA TEM ESSE ELEMENTO NA ESQUERDA DO MEIO
                    quantia += busca_elaborada(vetor, inicio, meio-1, valor);
                }
                return quantia;
          } else if (vetor[meio] == valor){ // SE O MEIO DO VETOR FOR O NUMERO
                quantia++;
                if((vetor[meio+1] == valor) && (vetor[meio-1] == valor)){ // SE O DA ESQUERDA E O DA DIREITA TAMBÉM É O ELEMENTO
                  quantia += busca_elaborada(vetor, meio+1, fim, valor);
                  quantia += busca_elaborada(vetor, inicio, meio-1, valor);
                } else if (vetor[meio+1] == valor){ // SÓ O DA DIREITA É O ELEMENTP
                    quantia += busca_elaborada(vetor, meio+1, fim, valor);
                } else if (vetor[meio-1] == valor){ // SÓ O DA ESQUERDA É O ELEMENTO
                    quantia += busca_elaborada(vetor, inicio, meio-1, valor);
                }
            } else if(vetor[meio+1] <= valor){ // SE O ELEMENTO DA DIREITA É MENOR OU IGUAL O ELEMENTO
                quantia += busca_elaborada(vetor, meio+1, fim, valor);
            } else if (vetor[meio-1] >= valor){ // SE O ELEMENTO DA ESQUERDA É MAIOR OU IGUAL O ELEMENTO
                quantia += busca_elaborada(vetor, inicio, meio-1, valor);
            }
          }
    return quantia;
}

int busca_simples(int *vetor, int num){  //varre vetor no tempo O(n)
    int contador = 0;
    for (int i = 0; i < max; i++){
      if (vetor[i] == num){
        contador++;
      }
    }
    printf("Número procurado: %i\n\n", num );
    return contador;
}

void importar(int *vetor){  //coloca os numeros no vetor
    int i = 0, numero;
    while(fscanf(arq,"%s", str)!=EOF){
      numero = atoi(str);
      vetor[i] = numero;
      i++;
    }
}

void merge(int *vetor, int comeco, int meio, int fim) {
    int com1 = comeco, com2 = meio+1, comAux = 0;
    int vetAux[fim-comeco+1];

    while(com1<=meio && com2<=fim){
        if(vetor[com1] <= vetor[com2]){
            vetAux[comAux] = vetor[com1];
            com1++;
        }else{
            vetAux[comAux] = vetor[com2];
            com2++;
         }
         comAux++;
     }

     while(com1<=meio){  //Caso ainda haja elementos na primeira metade
         vetAux[comAux] = vetor[com1];
         comAux++;com1++;
     }

     while(com2<=fim){   //Caso ainda haja elementos na segunda metade
         vetAux[comAux] = vetor[com2];
         comAux++;com2++;
     }

    for(comAux=comeco;comAux<=fim;comAux++){    //Move os elementos de volta para o vetor original
         vetor[comAux] = vetAux[comAux-comeco];
     }
}

void mergeSort(int *vetor, int comeco, int fim){
     if (comeco < fim) {
         int meio = (fim+comeco)/2;

         mergeSort(vetor, comeco, meio);
         mergeSort(vetor, meio+1, fim);
         merge(vetor, comeco, meio, fim);
     }
}

void print(int *vetor, int inicio, int meio, int fim){  //auxilio pra enxergar funcionando o recursivo
    printf("\n");
    for (int i = inicio; i <= fim; i++){
        if (i == inicio){
          printf("\t\tinicio[%d]= %d .",i, vetor[i]);
        } else if (i==meio){
          printf(" meio[%d]= %d .",i, vetor[i]);
        } else if (i==fim){
          printf(" fim[%d]= %d .",i, vetor[i]);
        } else printf(" [%d]= %d .",i, vetor[i]);
    }
    printf("\n");
}
