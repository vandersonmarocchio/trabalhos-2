package ataques;

import pokemons.Pokemon;
import pokemons.Status;

import java.util.Random;

public class AtaqueStatus extends Ataque {
    private String secundario;
    private Status primario;
    private double chance;

    public AtaqueStatus(String[] dados) {
        super(dados);
    }

    public void efeito(Pokemon atacante, Pokemon defensor) {
        if(!atacante.getStatus().equals("Fainted")) {
            System.out.println(ANSI_PURPLE + "Ataque Status");
            setPpAtual(getPpAtual() - 1);
            System.out.println("Pp Atual " + getPpAtual());
            double dano;
            CarregaAtaques ataques = new CarregaAtaques();
            String string = ataques.carregar(getId());
            String[] partes = string.split(", ");
            secundario = partes[0];
            if(!secundario.equals("Confusion") && !secundario.equals("Flinch")){
                this.primario = Status.valueOf(partes[0]);
            }
            this.chance = Integer.parseInt(partes[1]);
            if (calculoAcerto(atacante, defensor)) {
                boolean critico = chanceCritico(atacante);
                if (atacante.isConfusion() && probabilidade(50.0)){
                    dano = calculoDano(atacante, atacante, critico);
                    System.out.println("Hp Atacante antes: " + atacante.getHpAtual());
                    atacante.setHpAtual(atacante.getHpAtual() - dano);
                    System.out.println("Hp Atacante depois: " + atacante.getHpAtual() + ANSI_RESET);
                    modifier(atacante);
                    if (atacante.getHpAtual() <= 0) atacante.setStatus(Status.Fainted);
                    if(atacante.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Atacante FAINTED" + ANSI_RESET);
                }
                else {
                    dano = calculoDano(atacante, defensor, critico);
                    System.out.println("Hp defensor antes: " + defensor.getHpAtual());
                    defensor.setHpAtual(defensor.getHpAtual() - dano);
                    System.out.println("Hp defensor depois: " + defensor.getHpAtual() + ANSI_RESET);
                    modifier(defensor);
                    if (defensor.getHpAtual() <= 0) defensor.setStatus(Status.Fainted);
                    if(defensor.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Defensor FAINTED" + ANSI_RESET);
                }
            } else System.out.println(ANSI_RED + "Calculo Acerto FALSE" + ANSI_RESET);
        } else System.out.println(ANSI_RED + "Pokemon " + atacante.getEspecie().getNome() + " com status Fainted" + ANSI_RESET);
    }

    public void modifier(Pokemon defensor){
        if(probabilidade(this.chance)){
            if(this.secundario.equals("Confusion")) {
                System.out.println("Setou confusion true");
                defensor.setConfusion(true);
            }
            else if(this.secundario.equals("Flinch")) {
                System.out.println("Setou confusion true");
                defensor.setFlinch(true);
            }
            else {
                System.out.println("Setou status " + this.primario.toString());
                defensor.setStatus(this.primario);
            }
        } else System.out.println("Nao entrou na prob de alterar Status");
    }
}
