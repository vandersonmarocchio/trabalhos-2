package ataques;

import pokemons.Pokemon;
import pokemons.Status;

import java.util.Random;

public class AtaqueModifier extends Ataque {
    private String mod;
    private int n;
    private double chance;

    public AtaqueModifier(String[] dados) {
        super(dados);
    }

    public void efeito(Pokemon atacante, Pokemon defensor) throws NumberFormatException {
        if(!atacante.getStatus().equals("Fainted")) {
            System.out.println(ANSI_PURPLE + "Ataque Modifier");
            setPpAtual(getPpAtual() - 1);
            System.out.println("Pp Atual " + getPpAtual());
            double dano;
            CarregaAtaques ataques = new CarregaAtaques();
            String string = ataques.carregar(getId());
            String[] partes = string.split(", ");
            this.mod = partes[0];
            this.n = Integer.parseInt(partes[1]);
            this.chance = Double.parseDouble(partes[2]);
            if (calculoAcerto(atacante, defensor)) {
                boolean critico = chanceCritico(atacante);
                if(atacante.isConfusion() && probabilidade(50.0)){
                    dano = calculoDano(atacante, atacante, critico);
                    System.out.println("CONFUSION!!!!!!! ");
                    System.out.println("Hp atacante antes:" + atacante.getHpAtual());
                    atacante.setHpAtual(atacante.getHpAtual() - dano);
                    System.out.println("Hp atacante depois:" + atacante.getHpAtual()+ ANSI_RESET);
                    modifier(atacante, atacante);
                    if (atacante.getHpAtual() <= 0) atacante.setStatus(Status.Fainted);
                    if (atacante.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Atacante FAINTED" + ANSI_RESET);
                } else {
                    dano = calculoDano(atacante, defensor, critico);
                    System.out.println("Hp defensor antes: " + defensor.getHpAtual());
                    defensor.setHpAtual(defensor.getHpAtual() - dano);
                    System.out.println("Hp defensor depois: " + defensor.getHpAtual() + ANSI_RESET);
                    modifier(atacante, defensor);
                    if (defensor.getHpAtual() <= 0) defensor.setStatus(Status.Fainted);
                    if (defensor.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Defensor FAINTED" + ANSI_RESET);
                }
            } else System.out.println(ANSI_RED + "Calculo Acerto FALSE" + ANSI_RESET);
        } else System.out.println(ANSI_RED + "Pokemon " + atacante.getEspecie().getNome() + " com status Fainted" + ANSI_RESET);
    }

    public void modifier(Pokemon atacante, Pokemon defensor) {
        if (probabilidade(this.chance)) {
            if (this.mod.equals("Accuracy")) {
                System.out.println("Alterou Accuracy");
                if (n > 0) {
                    if ((atacante.getModifierAccuracy() + n) >= 6) {
                        atacante.setModifierAccuracy(6);
                    } else {
                        atacante.setModifierAccuracy(atacante.getModifierAccuracy() + n);
                    }
                } else {
                    if ((defensor.getModifierAccuracy() + n) <= -6) {
                        defensor.setModifierAccuracy(-6);
                    } else {
                        defensor.setModifierAccuracy(defensor.getModifierAccuracy() + n);
                    }
                }
            } else if (this.mod.equals("Evasion")) {
                System.out.println("Alterou Evasion");
                if (n > 0) {
                    if ((atacante.getModifierEvasion() + n) >= 6) {
                        atacante.setModifierEvasion(6);
                    } else {
                        atacante.setModifierEvasion(atacante.getModifierEvasion() + n);
                    }
                } else {
                    if ((defensor.getModifierEvasion() + n) <= -6) {
                        defensor.setModifierEvasion(-6);
                    } else {
                        defensor.setModifierEvasion(defensor.getModifierEvasion() + n);
                    }
                }
            } else if (this.mod.equals("ATK")) {
                System.out.println("Alterou Atk");
                if (n > 0) {
                    if ((atacante.getModifierAtk() + n) >= 6) {
                        atacante.setModifierAtk(6);
                    } else {
                        atacante.setModifierAtk(atacante.getModifierAtk() + n);
                    }
                } else {
                    if ((defensor.getModifierAtk() + n) <= -6) {
                        defensor.setModifierAtk(-6);
                    } else {
                        defensor.setModifierAtk(defensor.getModifierAtk() + n);
                    }
                }
            } else if (this.mod.equals("DEF")) {
                System.out.println("Alterou DEF");
                if (n > 0) {
                    if ((atacante.getModifierDef() + n) >= 6) {
                        atacante.setModifierDef(6);
                    } else {
                        atacante.setModifierDef(atacante.getModifierDef() + n);
                    }
                } else {
                    if ((defensor.getModifierDef() + n) <= -6) {
                        defensor.setModifierDef(-6);
                    } else {
                        defensor.setModifierDef(defensor.getModifierDef() + n);
                    }
                }
            } else if (this.mod.equals("SPE")) {
                System.out.println("Alterou SPE");
                if (n > 0) {
                    if ((atacante.getModifierSpe() + n) >= 6) {
                        atacante.setModifierSpe(6);
                    } else {
                        atacante.setModifierSpe(atacante.getModifierSpe() + n);
                    }
                } else {
                    if ((defensor.getModifierSpe() + n) <= -6) {
                        defensor.setModifierSpe(-6);
                    } else {
                        defensor.setModifierSpe(defensor.getModifierSpe() + n);
                    }
                }
            } else if (this.mod.equals("SPD")) {
                System.out.println("Alterou SPD");
                if (n > 0) {
                    if ((atacante.getModifierSpd() + n) >= 6) {
                        atacante.setModifierSpd(6);
                    } else {
                        atacante.setModifierSpd(atacante.getModifierSpd() + n);
                    }
                } else {
                    if ((defensor.getModifierSpd() + n) <= -6) {
                        defensor.setModifierSpd(-6);
                    } else {
                        defensor.setModifierSpd(defensor.getModifierSpd() + n);
                    }
                }
            }
        }
    }
}
