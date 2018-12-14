package ataques;

import pokemons.Tipo;

import java.util.HashMap;

/**
 * Created by luis on 16/12/17.
 */
public class Tabelas {

    public Double chancesModifier (Integer modifier){
        HashMap<Integer, Double> hashMap = new HashMap<>();
        hashMap.put(-6, 0.33);
        hashMap.put(-5, 0.37);
        hashMap.put(-4, 0.43);
        hashMap.put(-3, 0.50);
        hashMap.put(-2, 0.60);
        hashMap.put(-1, 0.75);
        hashMap.put(0, 1.00);
        hashMap.put(1, 1.33);
        hashMap.put(2, 1.66);
        hashMap.put(3, 2.00);
        hashMap.put(4, 2.33);
        hashMap.put(5, 2.66);
        hashMap.put(6, 3.00);
        return hashMap.get(modifier);
    }

    public double multiplicador(Tipo ataque, Tipo defesa){
        HashMap<String, Integer> hashMap = new HashMap<>();
        double multiplicadores[][] = new double[][]{
                {1, 1, 1, 1, 1, 0.5, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {2, 1, 0.5, 0.5, 1, 2, 0.5, 0, 1, 1, 1, 1, 0.5, 2, 1},
                {1, 2, 1, 1, 1, 0.5, 2, 1, 1, 1, 2, 0.5, 1, 1, 1},
                {1, 1, 1, 0.5, 0.5, 0.5, 2, 0.5, 1, 1, 2, 1, 1, 1, 1},
                {1, 1, 0, 2, 1, 2, 0.5, 1, 2, 1, 0.5, 2, 1, 1, 1},
                {1, 0.5, 2, 1, 0.5, 1, 2, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 0.5, 0.5, 2, 1, 1, 1, 0.5, 0.5, 1, 2, 1, 2, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 0, 1, 1},
                {1, 1, 1, 1, 1, 0.5, 2, 1, 0.5, 0.5, 2, 1, 1, 2, 0.5},
                {1, 1, 1, 1, 2, 2, 1, 1, 2, 0.5, 0.5, 1, 1, 1, 0.5},
                {1, 1, 0.5, 0.5, 2, 2, 0.5, 1, 0.5, 2, 0.5, 1, 1, 1, 0.5},
                {1, 1, 2, 1, 0, 1, 1, 1, 1, 2, 0.5, 0.5, 1, 1, 0.5},
                {1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 1, 1},
                {1, 1, 2, 1, 2, 1, 1, 1, 1, 0.5, 2, 1, 1, 0.5, 2},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2}
        };

        hashMap.put("Normal", 0);
        hashMap.put("Fighting", 1);
        hashMap.put("Flying", 2);
        hashMap.put("Poison", 3);
        hashMap.put("Ground", 4);
        hashMap.put("Rock", 5);
        hashMap.put("Bug", 6);
        hashMap.put("Ghost", 7);
        hashMap.put("Fire", 8);
        hashMap.put("Water", 9);
        hashMap.put("Grass", 10);
        hashMap.put("Electric", 11);
        hashMap.put("Psychic", 12);
        hashMap.put("Ice", 13);
        hashMap.put("Dragon", 14);

        return multiplicadores[hashMap.get(ataque.toString())][hashMap.get(defesa.toString())];
    }

}
