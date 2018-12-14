package modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author diogo
 */
public class SimulatedAnnealing {
    
    public static double atualizacaoProbabilidade(int energia, int novaEnergia, double temp){
        if(novaEnergia > energia){
            return 1.0;
        }
        return Math.exp((energia - novaEnergia) / temp);
    }
}
