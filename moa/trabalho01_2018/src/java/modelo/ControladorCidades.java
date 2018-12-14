package modelo;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author diogo
 */
public class ControladorCidades {

    private ArrayList<Cidade> cidadesDestino = new ArrayList<Cidade>();

    public void adicionarCidade(Cidade cidade) {
        cidadesDestino.add(cidade);
    }

    public Cidade getCidade(int indice) {
        return (Cidade) this.cidadesDestino.get(indice);
    }

    public int numerosDeCidades() {
        return this.cidadesDestino.size();
    }

    public ArrayList<Cidade> getCidadesDestino() {
        return cidadesDestino;
    }

    public void setCidadesDestino(ArrayList<Cidade> cidadesDestino) {
        this.cidadesDestino = cidadesDestino;
    }

    public String printa() {
        String geneString = "|";
        for (int i = 0; i < this.cidadesDestino.size(); i++) {
            geneString += getCidade(i) + "|  ";
        }
        return geneString;
    }
}
