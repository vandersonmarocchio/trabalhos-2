#include <stdio.h>
#include <stdlib.h>

int **alocarTabela(int n){
  int **tab;

  tab = malloc(n * sizeof(int *));

  if(tab == NULL){
    printf("Erro: memória insuficiente!");
    return NULL;
  }
  for(int i = 0; i <= n; i++){
    tab[i] = malloc(n * sizeof(int));
    if(tab[i] == NULL){
      printf("Erro: memória insuficiente!");
      return NULL;
    }
  }
  return tab;
}

int pascal(int linha, int coluna, int **tab){
  if(tab[linha][coluna] == 0){
    if(linha == 0 || coluna == 0){
      tab[linha][coluna] = 1;
    } else {
      tab[linha][coluna] = pascal(linha, coluna - 1, tab) + pascal(linha - 1, coluna, tab);
    }
  }
  return tab[linha][coluna];
}

void imprimePascal(int n, int **tab){
  int x;
  x = n;
  printf("\n");
  for(int i = 0; i <= n; i++){
    printf("\n");
    for(int j = 0; j <= x; j++){
      printf("%5d", pascal(i, j, tab));
    }
    x--;
  }
  printf("\n");
}

int main(){
  int n, **tab;

  printf("\nTriângulo de Pascal\n");
  printf("Número de linhas ---> n = ");
  scanf("%i", &n);

  tab = alocarTabela(n);

  for(int i = 0; i <= n; i++){
    for(int j = 0; j <= n; j++){
      tab[i][j] = 0;
    }
  }
  
  imprimePascal(n, tab);

  return 0;
}