/*Header que contém os cabeçalhos necessários para algoritmos.cpp
  Autor: Cristofer Oswald e Bruno Cesar
  Data: 23/05/2017 */

#ifndef ALGORITMOS_H
#define ALGORITMOS_H

#include "Vertice.h"

#include <string>
#include <unordered_map>

int fluxoMaximo(std::unordered_map<std::string, Vertice*>& grafo, Vertice* fonte, Vertice* sumidouro);
int distancia(Vertice* u, Vertice* v,
              std::unordered_map<std::string, Vertice*>& grafo);
void reiniciaVerices(std::unordered_map<std::string, Vertice*>& grafo);
void montaResidual(std::unordered_map<std::string, Vertice*>& grafo, std::unordered_map<std::string, Vertice*>** grafo_residual);
int encontraGargalo(std::unordered_map<std::string, Vertice*>& grafo, Vertice* fonte, Vertice* sumidouro, bool& tem_caminho);
void setFluxos(std::unordered_map<std::string, Vertice*>& grafo, std::unordered_map<std::string, Vertice*>& grafo_residual, Vertice* sumidouro, int fluxo);

#endif //ALGORITMOS_H
