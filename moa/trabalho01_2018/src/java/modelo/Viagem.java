package modelo;

import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author diogo
 */
public class Viagem {

    private ArrayList<Cidade> viagens = new ArrayList<Cidade>();

    private int distancia = 0;

    public Viagem(ControladorCidades controlador) {
        for (int i = 0; i < controlador.numerosDeCidades(); i++) {
            viagens.add(null);
        }
    }

    public Viagem(ArrayList<Cidade> viagens) {
        this.viagens = viagens;
    }

    public void setCidade(int posicaoViagem, Cidade cidade) {
        viagens.set(posicaoViagem, cidade);
        this.distancia = 0; // se o trajeto vou alterado a distancia deve ser resetada
    }

    public Cidade getCidade(int posicao) {
        return (Cidade) this.viagens.get(posicao);
    }

    public void geradorRandom(ControladorCidades controlador) {
        for (int i = 0; i < controlador.numerosDeCidades(); i++) {
            this.setCidade(i, controlador.getCidade(i));
        }
        Collections.shuffle(viagens); //embaralha lista
    }

    public int getDistancia() {
        int distanciaViagem = 0;

        for (int i = 0; i < this.viagens.size(); i++) {

            Cidade cidadeOrigem = getCidade(i);
            Cidade cidadeDestino;

            if (i + 1 < this.viagens.size()) {
                cidadeDestino = getCidade(i + 1);
            } else {
                cidadeDestino = getCidade(0);
            }
            distanciaViagem += cidadeOrigem.distanciaAte(cidadeDestino);
        }
        this.distancia = distanciaViagem;
        return this.distancia;
    }

    public ArrayList<Cidade> getViagens() {
        return viagens;
    }

    public void setViagens(ArrayList<Cidade> viagens) {
        this.viagens = viagens;
    }

    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < this.viagens.size(); i++) {
            geneString += getCidade(i) + "|";
        }
        return geneString;
    }
}
