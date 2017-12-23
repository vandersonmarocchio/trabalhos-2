package ataques;

import pokemons.Pokemon;
import pokemons.Status;

public class AtaqueCharge extends Ataque {
    private boolean podeAtacar = false;

    public AtaqueCharge(String[] dados) {
        super(dados);
    }

    public void efeito(Pokemon atacante, Pokemon defensor ) {
        if(!atacante.getStatus().equals("Fainted")) {
            System.out.println(ANSI_PURPLE + "Ataque Charge");
            if(!podeAtacar){ //define que ele ira atacar so no proximo turno
                podeAtacar = true;
                setPpAtual(getPpAtual() - 1);
                System.out.println("Pp Atual " + getPpAtual());
                System.out.println("Na proxima rodada pode atacar!!" + ANSI_RESET);
            } else {
                podeAtacar = false;
                if (calculoAcerto(atacante, defensor)) {
                    double dano;
                    boolean critico = chanceCritico(atacante);
                    if(atacante.isConfusion() && probabilidade(50.0)){
                        dano = calculoDano(atacante, atacante, critico);
                        System.out.println("CONFUSION!!!!!!! ");
                        System.out.println("Hp atacante antes:" + atacante.getHpAtual());
                        atacante.setHpAtual(atacante.getHpAtual() - dano);
                        System.out.println("Hp atacante depois:" + atacante.getHpAtual()+ ANSI_RESET);
                        if (atacante.getHpAtual() <= 0) atacante.setStatus(Status.Fainted);
                        if (atacante.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Defensor FAINTED" + ANSI_RESET);
                    } else {
                        dano = calculoDano(atacante, defensor, critico);
                        System.out.println("Hp defensor antes: " + defensor.getHpAtual());
                        defensor.setHpAtual(defensor.getHpAtual() - dano);
                        System.out.println("Hp defensor depois: " + defensor.getHpAtual() + ANSI_RESET);
                        if (defensor.getHpAtual() <= 0) defensor.setStatus(Status.Fainted);
                        if (defensor.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Defensor FAINTED" + ANSI_RESET);
                    }
                } else System.out.println(ANSI_RED + "Calculo Acerto FALSE" + ANSI_RESET);
            }
        } else System.out.println(ANSI_RED + "Pokemon " + atacante.getEspecie().getNome() + " com status Fainted" + ANSI_RESET);
    }
}
