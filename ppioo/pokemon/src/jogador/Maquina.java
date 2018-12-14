package jogador;

import ataques.Ataque;
import ataques.AtaqueCharge;
import pokemons.Pokemon;

import java.util.Random;

public class Maquina extends Jogador {

    @Override
    public int escolherComando() {
        return 1;
    }

    @Override
    public void escolherAtaque(Pokemon atacante, Pokemon defensor) {
        if(atacante.isCharge()){
            System.out.println("Ataque charge agora!!!");
            System.out.println();
            atacante.getAtaques().get(atacante.getIndiceCharge()).efeito(atacante,defensor);
            atacante.setCharge(false);
        } else {
            int ataques = atacante.getAtaques().size();
            Random random = new Random();
            int rand = random.nextInt((ataques - 0)) + 0;
            int i = 0;
            for (Ataque a: atacante.getAtaques()) {
                System.out.println("(" + i++ + ")" + "Nome: " + a.getNome() + "\t\tPower: " + a.getPower() + "\t\tPp Atual:" + a.getPpAtual());
            }
            if (atacante.getAtaques().get(rand) instanceof AtaqueCharge){
                atacante.setIndiceCharge(rand);
                atacante.setCharge(true);
            }
            System.out.println(atacante.getAtaques().get(rand).getNome() + " escolhido!!");
            System.out.println();
            atacante.getAtaques().get(rand).efeito(atacante, defensor);
        }




    }
}