/*  Diogo Alves de Almeida, RA: 95108
 *  Luis Felipe Costa Guimarães Teixeira, RA:95372
 */

#include <stdlib.h>
#include <stdio.h>

/* Função para printar um vetor */
void printaVetor(int vetor[], int tamanho)
{
	int i;
	for (i = 0; i < tamanho; i++)
		printf("%d ", vetor[i]);
	printf("\n\n");
}

/* Função para ordenar em log(n)*/
void radixsort(int vetor[], int tamanho)
{
	int i;
	int *b;
	int maior = vetor[0];
	int exp = 1;

	b = (int *)calloc(tamanho, sizeof(int));

	for (i = 0; i < tamanho; i++)
	{
		if (vetor[i] > maior)
			maior = vetor[i];
	}

	while (maior / exp > 0)
	{
		int bucket[10] = {0};
		for (i = 0; i < tamanho; i++)
			bucket[(vetor[i] / exp) % 10]++;
		for (i = 1; i < 10; i++)
			bucket[i] += bucket[i - 1];
		for (i = tamanho - 1; i >= 0; i--)
			b[--bucket[(vetor[i] / exp) % 10]] = vetor[i];
		for (i = 0; i < tamanho; i++)
			vetor[i] = b[i];
		exp *= 10;
	}
	printf("Vetor ordenado: ");
	printaVetor(vetor, tamanho);

	free(b);
}

/* Função de busca binária em tempo log(n)*/
int buscaBinaria(int vetor[], int inicio, int fim){

	if(inicio == fim){
		// printf("ACHEI\n");
		// printf("inicio: [%d]%d\n", inicio, vetor[inicio]);
		// printf("fim: [%d]%d\n",fim, vetor[fim]);
	 return inicio;

	}

	//O mesmo que (inicio + r) / 2, mas evita estouro para inicio e fim grandes
	int meio = inicio + (fim - inicio) / 2;

	if(vetor[meio + 1] < vetor[meio] && vetor[meio - 1] < vetor[meio] ){
		// printf("TA NO MEIO\n");
		// printf("inicio: [%d]%d\n", inicio, vetor[inicio]);
		// printf("meio: [%d]%d\n", meio,vetor[meio]);
		// printf("fim: [%d]%d\n",fim, vetor[fim]);
		return meio;
	} else if (vetor[meio - 1] >= vetor[meio]){
		// printf("TA NA ESQUERDA\n");
		// printf("inicio: [%d]%d\n", inicio,vetor[inicio]);
		// printf("meio: [%d]%d\n", meio,vetor[meio]);
		// printf("fim: [%d]%d\n", fim,vetor[fim]);
		return buscaBinaria(vetor, inicio, meio);
	} else if (vetor[meio + 1] >= vetor[meio]){
		// printf("TA NA DIREITA\n");
		// printf("inicio: [%d]%d\n", inicio, vetor[inicio]);
		// printf("meio: [%d]%d\n", meio,vetor[meio]);
		// printf("fim: [%d]%d\n", fim,vetor[fim]);
		return buscaBinaria(vetor, meio+1, fim);
	} 
	printf("ERRO\n");
}

int main(){
	int vetor[] = {1,2,3,7,6,5,4,3,2,1};
	int tamanho_vetor = sizeof(vetor) / sizeof(vetor[0]);
	printf("Formato do vetor:  A1< A2< ...< Ak> Ak+1> Ak+2> ... > An \n\n");

	printf("Vetor recebido: ");
	printaVetor(vetor, tamanho_vetor);

	int k = buscaBinaria(vetor, 0, tamanho_vetor - 1);
	printf("k encontrado: %d\n", k);
	printf("A[k] encontrado: %d\n\n", vetor[k]);

	radixsort(vetor, tamanho_vetor);
	return 0;
}
