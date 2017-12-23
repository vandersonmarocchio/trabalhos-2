package ataques;

import pokemons.Pokemon;
import pokemons.Status;

import java.util.Random;

public class AtaqueMultihit extends Ataque {
    private int min;
    private int max;

    public AtaqueMultihit(String[] dados) {
        super(dados);
    }

    public void efeito(Pokemon atacante, Pokemon defensor) {
        if(!atacante.getStatus().equals("Fainted")) {
            System.out.println(ANSI_PURPLE + "Ataque Multihit");
            setPpAtual(getPpAtual() - 1);
            System.out.println("Pp Atual " + getPpAtual());
            double dano;
            CarregaAtaques ataques = new CarregaAtaques();
            String string = ataques.carregar(getId());
            String[] partes = string.split(", ");
            this.min = Integer.parseInt(partes[0]);
            this.max = Integer.parseInt(partes[1]);
            if (calculoAcerto(atacante, defensor)) {
                boolean critico = chanceCritico(atacante);
                if (atacante.isConfusion() && probabilidade(50.0)){
                    dano = calculoDano(atacante, atacante, critico);
                    System.out.println("Hp defensor antes: " + atacante.getHpAtual());
                    atacante.setHpAtual(atacante.getHpAtual() - dano);
                    System.out.println("Hp defensor depois: " + atacante.getHpAtual() + ANSI_RESET);
                    Random random = new Random();
                    int rand = random.nextInt((this.max - this.min) + 1) + this.min;
                    System.out.println(ANSI_PURPLE + "Atacara " + rand + " vezes!!");
                    while (rand > 0) {
                        dano = calculoDano(atacante, atacante, critico);
                        System.out.println("Hp defensor antes: " + atacante.getHpAtual());
                        atacante.setHpAtual(atacante.getHpAtual() - dano);
                        System.out.println("Hp defensor depois: " + atacante.getHpAtual());
                        rand--;
                    }
                    System.out.println(ANSI_RESET);
                    if (atacante.getHpAtual() <= 0) atacante.setStatus(Status.Fainted);
                    if (atacante.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Atacante FAINTED" + ANSI_RESET);
                }
                else {
                    dano = calculoDano(atacante, defensor, critico);
                    System.out.println("Hp defensor antes: " + defensor.getHpAtual());
                    defensor.setHpAtual(defensor.getHpAtual() - dano);
                    System.out.println("Hp defensor depois: " + defensor.getHpAtual() + ANSI_RESET);
                    Random random = new Random();
                    int rand = random.nextInt((this.max - this.min) + 1) + this.min;
                    System.out.println(ANSI_PURPLE + "Atacara " + rand + " vezes!!");
                    while (rand > 0) {
                        dano = calculoDano(atacante, defensor, critico);
                        System.out.println("Hp defensor antes: " + defensor.getHpAtual());
                        defensor.setHpAtual(defensor.getHpAtual() - dano);
                        System.out.println("Hp defensor depois: " + defensor.getHpAtual());
                        rand--;
                    }
                    System.out.println(ANSI_RESET);
                    if (defensor.getHpAtual() <= 0) defensor.setStatus(Status.Fainted);
                    if (defensor.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Defensor FAINTED" + ANSI_RESET);
                }
            } else System.out.println(ANSI_RED + "Calculo Acerto FALSE" + ANSI_RESET);
        } else System.out.println(ANSI_RED + "Pokemon " + atacante.getEspecie().getNome() + " com status Fainted" + ANSI_RESET);
    }
}
