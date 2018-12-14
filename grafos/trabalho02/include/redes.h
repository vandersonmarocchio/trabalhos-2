/*Header contendo a declaração das funções e variáveis globais do arquivo got.cpp
  Autor: Cristofer Oswald e Bruno Cesar
  Data: 23/05/2017*/

#ifndef REDES_H
#define REDES_H

#define FIM_DE_LINHA '\n'

#include "Vertice.h"
#include "algoritmos.h"

#include <string.h>
#include <math.h>
#include <iostream>
#include <fstream>
#include <string>
#include <unordered_map>

//Hash map com todos os vértices
std::unordered_map<std::string, Vertice*> grafo;
std::unordered_map<std::string, Vertice*>* grafo_residual = NULL;

bool verificaArgs(int argc);
void leGrafo(std::string& nome_file, Vertice** fonte, Vertice** sumidouro);

#endif //REDES_H
