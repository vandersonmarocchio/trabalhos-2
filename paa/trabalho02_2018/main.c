#include <stdio.h>
#include <stdlib.h>

int **alocar_tabela (int n) {
  int **tab;
  
  tab = malloc (n * sizeof (int *));
  
  if (tab == NULL) {
     printf ("Erro: Memoria Insuficiente");
     return (NULL);
     }
  for (int i = 0; i <= n; i++ ) {
      tab[i] = malloc (n * sizeof (int));
      if (tab[i] == NULL) {
         printf ("Erro: Memoria Insuficiente");
         return (NULL);
         }
      }
  return (tab);
}

int pascal( int linha, int coluna, int **tab){
    if(tab[linha][coluna] == 0){
        if( linha == 0 || coluna == 0) {
            tab[linha][coluna] = 1;
            return tab[linha][coluna];
        } else {
            tab[linha][coluna] = pascal(linha , coluna-1, tab) + pascal(linha-1, coluna, tab);    
            return tab[linha][coluna];
        }
    } else {
        return tab[linha][coluna];
    }
}

void imprimePascal(int n , int **tab){
    int x;
    x = n;
    printf("\n");
    for(int i = 0; i <= n; i++){
        for( int j = 0; j <= x; j++){
            printf("%5d", pascal(i, j, tab));
        }
        x -= 1;
        printf("\n");
    }
    printf("\n");
}

int main() {
    int n, **tab; 
    
    printf("\nTriangulo de Pascal\n");
    printf("Numero de linhas ---> n = ");
    scanf("%i", &n);

    tab = alocar_tabela(n);
    
    for(int i = 0; i <= n; i++){
        for(int j = 0; j <= n; j++){
            tab[i][j] = 0;
        }   
    }
    
    imprimePascal(n, tab);
    
    return(0);
}