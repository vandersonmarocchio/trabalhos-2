/*Arquivo que contém a implementação de algoritmos em grafos
  Autor: Cristofer Oswald e Bruno Cesar
  Data: 23/05/2017 */

#include "include/algoritmos.h"
#include "include/Vertice.h"

#include <queue>
#include <iostream>
#include <algorithm>
#include <climits>

int fluxoMaximo(std::unordered_map<std::string, Vertice*>& grafo, Vertice* fonte, Vertice* sumidouro){
    int fluxo_maximo = 0;
    int gargalo = 0;
    bool tem_caminho = true;

    std::unordered_map<std::string, Vertice*>* grafo_residual = NULL;

    while(tem_caminho){
        montaResidual(grafo, &grafo_residual);

        distancia((*grafo_residual)[fonte->getNome()], (*grafo_residual)[sumidouro->getNome()], *grafo_residual);

        gargalo = encontraGargalo(*grafo_residual, (*grafo_residual)[fonte->getNome()], (*grafo_residual)[sumidouro->getNome()], tem_caminho);

        if(tem_caminho){
            setFluxos(grafo, *grafo_residual, (*grafo_residual)[sumidouro->getNome()], gargalo);

            fluxo_maximo = fluxo_maximo + gargalo;
        }

    }

    return fluxo_maximo;
}

void montaResidual(std::unordered_map<std::string, Vertice*>& grafo, std::unordered_map<std::string, Vertice*>** grafo_residual){
    //Libera a memória do grafo anterior
    if(*grafo_residual){
        for(auto& a : (**grafo_residual)){
            free(a.second);
        }
        free(*grafo_residual);
    }

    (*grafo_residual) = new std::unordered_map<std::string, Vertice*>();

    //Itera sobre os vértices do grafo original
    for(auto& a : grafo){
        Vertice* origem = a.second;

        //Aloca um novo vértice se ele não existe
        if((*grafo_residual)->find(origem->getNome()) == (*grafo_residual)->end()){
            (**grafo_residual)[origem->getNome()] = new Vertice(origem->getNome());
        }
        origem = (**grafo_residual)[origem->getNome()];

        //Itera sobre os vizinhos
        for(auto& b : a.second->getVizinhos()){
            Vertice* destino = b.first;
            std::tuple<int, int, bool> capacidade_fluxo = b.second;

            if((*grafo_residual)->find(destino->getNome()) == (*grafo_residual)->end()){
                (**grafo_residual)[destino->getNome()] = new Vertice(destino->getNome());
            }
            destino = (**grafo_residual)[destino->getNome()];

            //Se existe fluxo, cria uma aresta de retorno
            if(std::get<FLUXO>(capacidade_fluxo) > 0){
                destino->addVizinho(origem, std::get<FLUXO>(capacidade_fluxo), true);
            }

            //Se a capacidade for igual ao fluxo, não cria aresta
            if(std::get<CAPACIDADE>(capacidade_fluxo) != std::get<FLUXO>(capacidade_fluxo)){
                origem->addVizinho(destino, (std::get<CAPACIDADE>(capacidade_fluxo) - std::get<FLUXO>(capacidade_fluxo)), false);
            }
        }
    }
}

int distancia(Vertice* u, Vertice* v, std::unordered_map<std::string, Vertice*>& grafo){
  std::queue<Vertice*> fila;
  Vertice *atual;

  u->setCor(Cor::CINZA);
  //As outras inicializações já acontecem em reinicia;

  fila.push(u);
  while(!fila.empty()){
    atual = fila.front();
    fila.pop();
    for(auto& a : atual->getVizinhos()){
      Vertice* vizinho = a.first;
      if(vizinho->getCor() == Cor::BRANCO){
        vizinho->setCor(Cor::CINZA);
        vizinho->setDistancia(atual->getDistancia() + 1);
        vizinho->setPredecessor(atual);
        fila.push(vizinho);
      }
      atual->setCor(Cor::PRETO);
    }
  }

  return v->getDistancia();
}

int encontraGargalo(std::unordered_map<std::string, Vertice*>& grafo,  Vertice* fonte, Vertice* sumidouro, bool& tem_caminho){
    int gargalo = INT_MAX;
    int capacidade;
    Vertice* vertice = sumidouro;

    //Itera sobre o caminho mínimo gerado
    while(vertice->getPredecessor()){
        capacidade = std::get<CAPACIDADE>(vertice->getPredecessor()->getVizinhos()[vertice]);
        if(capacidade < gargalo){
            gargalo = capacidade;
        }

        vertice = vertice->getPredecessor();
    }

    //Só existe caminho se o final do caminho é a fonte
    tem_caminho = vertice == fonte;

    return gargalo;
}

void setFluxos(std::unordered_map<std::string, Vertice*>& grafo, std::unordered_map<std::string, Vertice*>& grafo_residual, Vertice* sumidouro, int fluxo){
    Vertice* vertice = sumidouro;

    //Itera sobre o caminho mínimo gerado
    while(vertice->getPredecessor()){
        //Itera sobre os vizinhos (arestas) do vértice no grafo original
        for(auto& a : grafo[vertice->getPredecessor()->getNome()]->getVizinhos()){
            //Se o vizinho que estou olhando pertence ao caminho
            if(!a.first->getNome().compare(vertice->getNome())){
                //Se a aresta que estamos olhando é de retorno, o fluxo é negativo
                if(std::get<RETORNO>(vertice->getPredecessor()->getVizinhos()[vertice])){
                    fluxo = -1 * fluxo;
                }

                std::get<FLUXO>(a.second) = std::get<FLUXO>(a.second) + fluxo;
            }
        }
        vertice = vertice->getPredecessor();
    }
}

void reiniciaVerices(std::unordered_map<std::string, Vertice*>& grafo){
  for(auto& vertice : grafo){
    vertice.second->limparDados();
  }
}
