/*  Diogo Alves de Almeida
 *  Luis Felipe Costa Guimarães Teixeira
 */

#include<stdlib.h> 
#include<stdio.h> 

void printaVetor(int vetor[], int tamanho);

// Separa em 2 subvetores do vetor[]. 
// Primeiro subvetor é vetor[inicio..meio] 
// Segundo subvetor é vetor[meio+1..fim] 
void merge(int vetor[], int inicio, int meio, int fim) { 
	int i, j, k; 
	int n1 = meio - inicio + 1; 
	int n2 = fim - meio; 

	/* Cria vetores temporários */
	int esquerta[n1], direita[n2]; 

	/* Copia os valores para os vetores temporários esquerta[] e direita[] */
	for (i = 0; i < n1; i++) 
		esquerta[i] = vetor[inicio + i]; 
	for (j = 0; j < n2; j++) 
		direita[j] = vetor[meio + 1+ j]; 

	/* Mescla os vetores temporários novamente em vetor[inicio..fim]*/
	i = 0; // Indice inicial do primeiro subvetor 
	j = 0; // Indice inicial do primeiro subvetor
	k = inicio; // Indice inicial do subvetor intercalado 
	while (i < n1 && j < n2) { 
		if (esquerta[i] <= direita[j]) { 
			vetor[k] = esquerta[i]; 
			i++; 
		} else { 
			vetor[k] = direita[j]; 
			j++; 
		} 
		k++; 
	} 

	/* Copia os elementos restantes de esquerta[] se houver */
	while (i < n1) { 
		vetor[k] = esquerta[i]; 
		i++; 
		k++; 
	} 

	/* Copia os elementos restantes de direita[] se houver*/
	while (j < n2) { 
		vetor[k] = direita[j]; 
		j++; 
		k++; 
	} 
    printf("Esquerda: ");
    printaVetor(esquerta, n1);
    printf("Direita: ");
    printaVetor(direita, n2);
} 

/* inicio é o índice da esquerda e fim é o índice da direita do vetor a ser classificado */
void mergeSort(int vetor[], int inicio, int fim) { 
	if (inicio < fim) { 
        //O mesmo que (inicio + r) / 2, mas evita estouro para inicio e fim grandes
		int meio = inicio+(fim-inicio)/2; 

		// Ordena primeiro and segundo vetor 
		mergeSort(vetor, inicio, meio); 
		mergeSort(vetor, meio+1, fim); 

		merge(vetor, inicio, meio, fim); 
	} 
} 

/* Função para printar um vetor */
void printaVetor(int vetor[], int tamanho) { 
	int i; 
	for (i=0; i < tamanho; i++) 
		printf("%d ", vetor[i]); 
	printf("\n"); 
} 

int main(){
	int vetor[] = {12, 11, 13, 5, 6, 7}; 
	int tamanho_vetor = sizeof(vetor)/sizeof(vetor[0]); 

	printf("Vetor recebido: \n"); 
	printaVetor(vetor, tamanho_vetor); 

	mergeSort(vetor, 0, tamanho_vetor - 1); 

	printf("\nVetor ordenado:\n"); 
	printaVetor(vetor, tamanho_vetor); 
	return 0; 
} 
