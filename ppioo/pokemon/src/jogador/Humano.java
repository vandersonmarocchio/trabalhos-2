package jogador;

import ataques.Ataque;
import ataques.AtaqueCharge;
import pokemons.Pokemon;
import java.util.Scanner;

public class Humano extends Jogador {

    @Override
    public int escolherComando() {
        Scanner in = new Scanner(System.in);
        int op;
        System.out.println( ANSI_YELLOW + "Digite o que deseja fazer:");
        System.out.println("1 - Atacar");
        System.out.println("2 - Trocar de Pokemon");
        System.out.println("3 - Desistir" + ANSI_RESET);
        op = in.nextInt();
        switch (op) {
            case 1:
                return op;
            case 2:
                if(getPokemons().size()==1){
                    System.out.println("Voce so tem um pokemon, impossivel realizar o comando de troca!!");
                    imprimeListaPokemons();
                    return escolherComando();
                } else {
                    boolean trocou = trocarPokemon();
                    if (!trocou) {
                        escolherComando();
                    }
                }
                return op;
            case 3:
                return op;
            default:
                System.out.println("Numero invalido!!");
                escolherComando();
                break;
        }
        System.out.println("\n\n");
        return op;
    }

    @Override
    public void escolherAtaque(Pokemon atacante, Pokemon defensor){ //mostra a lista de ataques
        if(atacante.isCharge()){
            System.out.println("Ataque charge agora!!!");
            System.out.println();
            atacante.getAtaques().get(atacante.getIndiceCharge()).efeito(atacante,defensor);
            atacante.setCharge(false);
        } else {
            Scanner in = new Scanner(System.in);
            int i = 0;
            for (Ataque a: atacante.getAtaques()) {
                System.out.println("(" + i++ + ")" + "Nome: " + a.getNome() + "\t\tPower: " + a.getPower() + "\t\tPp Atual:" + a.getPpAtual());
            }
            int op = in.nextInt();
            while ((op < 0) || (op>(atacante.getAtaques().size()-1))){
                System.out.println("Ataque inexistente, por favor digite novamente:");
                op = in.nextInt();
            }
            while (atacante.getAtaques().get(op).getPpAtual()<=0){
                System.out.println("Nao eh mais possivel realizar esse ataque, por favor digite novamente:");
                op = in.nextInt();
            }
            if (atacante.getAtaques().get(op) instanceof AtaqueCharge){
                atacante.setIndiceCharge(op);
                atacante.setCharge(true);
            }

            System.out.println(atacante.getAtaques().get(op).getNome() + " escolhido!!");
            System.out.println();
            atacante.getAtaques().get(op).efeito(atacante,defensor);

        }

    }
}
