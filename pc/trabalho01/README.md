# FloydWarshall-pthreads
A parallel version of the Floyd-Warshall algorithm 

O trabalho foi desenvolvido usando o sistema operacional Linux com o compilador GCC

## Compilação
Para compilar o programa basta digitar o seguinte comando no terminal: 

`make`

Para remover o executável basta digitar o seguinte comando no terminal:

`make clean`

## Execução

Os parâmetros do algoritmos são passadas por meio de flags na hora da execução. As flags existentes são:

* `-h` : Exibe as opções de flags possíveis

* `-n <nthread>` : Seta o núemro de threads da aplicação, o padrão sao duas threads 

* `-g <caminho>` : O caminho do arquivo que contém os dados do grafo a ser calculado

* `-p` : Colocar essa flag para rodar o algoritmo em paralelo, caso não for colocada vai ser executado sequencialmente

## Exemplos

`make` para a compilação

`./fw -n 4 -g graphs/grafo_3500.g -p` executa o algoritmo em paralelo com 4 threads e com o grafo de 3500 vértices

`./fw -g graph/grafo_5000.g` executa o algoritmo em paralelo sequencialmente com o grafo de 5000 vértices

`make clean` para deletar o o executável
