package jogador;

import pokemons.Pokemon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public abstract class Jogador {
    private List<Pokemon> pokemons = new ArrayList<>();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> getPokemons() { return pokemons; }

    public abstract int escolherComando();

    public  abstract  void escolherAtaque(Pokemon atacante, Pokemon defensor);

    public boolean trocarPokemon(){
        Scanner in = new Scanner(System.in);
        int indice;
        boolean trocaPermitida = false;
        do {
            System.out.println();
            imprimeListaPokemons();
            System.out.println("Digite o indice do Pokemon que deseja utilizar: (7) para voltar ao menu do turno");
            indice = in.nextInt();
            if (indice == 7){
                return false;
            } else if ((indice < 0) || (indice >= pokemons.size())) {
                System.out.println("Indice incorreto!!");
            } else if (indice == 0) {
                System.out.println("Este pokemon ja eh o seu principal!!");
            } else if (pokemons.get(indice).getStatus() == "Fainted") {
                System.out.println("Pokemon esta com status Fainted, impossivel trocar!!");
            } else trocaPermitida = true;
        } while(!trocaPermitida);

        Collections.swap(pokemons, indice, 0);
        System.out.println("\nTrocado!!");
        imprimeListaPokemons();
        return true;
    }

    public void imprimeListaPokemons(){
        System.out.println("Lista de pokemons");
        for(int i = 0; i<pokemons.size();i++){
            if( i == 0) System.out.printf("[%d] %s [Hp: %.2f] (%s) (Principal)", i, pokemons.get(i).getEspecie().getNome(), pokemons.get(i).getHpAtual(), pokemons.get(i).getStatus().toString());
            else   System.out.printf("[%d] %s [Hp: %.2f] (%s)", i, pokemons.get(i).getEspecie().getNome(), pokemons.get(i).getHpAtual(), pokemons.get(i).getStatus().toString());
        }
        System.out.println();
        System.out.println();
    }

    public boolean verificaTime(){
        for(Pokemon p : pokemons){
            if (p.getStatus() != "Fainted"){
                return true;
            }
        }
        return false;
    }

    public void trocaFainted () {
        for (int i = 1; i < pokemons.size(); i++) {
            Collections.swap(pokemons, i, i-1);
        }
    }

}
