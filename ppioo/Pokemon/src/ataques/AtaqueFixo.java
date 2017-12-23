package ataques;

import pokemons.Pokemon;
import pokemons.Status;

public class AtaqueFixo extends Ataque {
    private String val;

    public AtaqueFixo(String[] dados) {
        super(dados);
    }

    public void efeito(Pokemon atacante, Pokemon defensor) {
        if(!atacante.getStatus().equals("Fainted")) {
            System.out.println(ANSI_PURPLE + "Ataque Fixo");
            setPpAtual(getPpAtual() - 1);
            System.out.println("Pp Atual " + getPpAtual());
            CarregaAtaques ataques = new CarregaAtaques();
            String string = ataques.carregar(getId());
            String[] partes = string.split(", ");
            this.val = partes[0];
            if (this.val.equals("lvl")) {
                if (atacante.isConfusion() && probabilidade(50.0)) {
                    System.out.println("CONFUSION!!!");
                    System.out.println("Level atacante: " + atacante.getLevel());
                    System.out.println("Hp atacante: " + atacante.getHpAtual());
                    atacante.setHpAtual(atacante.getHpAtual() - atacante.getLevel());
                    System.out.println("Hp atacante: " + atacante.getHpAtual() + ANSI_RESET);
                    if (atacante.getHpAtual() <= 0) atacante.setStatus(Status.Fainted);
                    if (atacante.getStatus().equals("Fainted"))
                        System.out.println(ANSI_RED + "Atacante FAINTED" + ANSI_RESET);
                }
                else {
                    System.out.println("Level atacante " + atacante.getLevel());
                    System.out.println("Hp defensor: " + defensor.getHpAtual());
                    defensor.setHpAtual(defensor.getHpAtual() - atacante.getLevel());
                    System.out.println("Hp defensor: " + defensor.getHpAtual() + ANSI_RESET);
                    if (defensor.getHpAtual() <= 0) defensor.setStatus(Status.Fainted);
                    if (defensor.getStatus().equals("Fainted"))
                        System.out.println(ANSI_RED + "Defensor FAINTED" + ANSI_RESET);
                }
            }
            else {
                if (atacante.isConfusion() && probabilidade(50.0)) {
                    System.out.println("CONFUSION!!!");
                    System.out.println("Valor: " + this.val);
                    System.out.println("Hp atacante: " + atacante.getHpAtual());
                    atacante.setHpAtual(atacante.getHpAtual() - Double.parseDouble(this.val));
                    System.out.println("Hp atacante: " + atacante.getHpAtual() + ANSI_RESET);
                    if (atacante.getHpAtual() <= 0) atacante.setStatus(Status.Fainted);
                    if (atacante.getStatus().equals("Fainted"))
                        System.out.println(ANSI_RED + "Atacante FAINTED" + ANSI_RESET);
                } else {
                    System.out.println("Valor: " + this.val);
                    System.out.println("Hp defensor: " + defensor.getHpAtual());
                    defensor.setHpAtual(defensor.getHpAtual() - Double.parseDouble(this.val));
                    System.out.println("Hp defensor: " + defensor.getHpAtual() + ANSI_RESET);
                    if (defensor.getHpAtual() <= 0) defensor.setStatus(Status.Fainted);
                    if (defensor.getStatus().equals("Fainted"))
                        System.out.println(ANSI_RED + "Defensor FAINTED" + ANSI_RESET);
                }
            }
        }
        else System.out.println(ANSI_RED + "Pokemon " + atacante.getEspecie().getNome() + " com status Fainted" + ANSI_RESET);
    }
}
