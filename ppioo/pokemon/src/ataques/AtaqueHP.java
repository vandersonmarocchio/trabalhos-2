package ataques;

import pokemons.Pokemon;
import pokemons.Status;

public class AtaqueHP extends Ataque {
    private String valor;
    private double porcentagem;

    public AtaqueHP(String[] dados) {
        super(dados);
    }

    public void efeito(Pokemon atacante, Pokemon defensor) {
        if(!atacante.getStatus().equals("Fainted")) {
            System.out.println(ANSI_PURPLE + "Ataque HP");
            setPpAtual(getPpAtual() - 1);
            System.out.println("Pp Atual " + getPpAtual());
            double dano;
            CarregaAtaques ataques = new CarregaAtaques();
            String string = ataques.carregar(getId());
            String[] partes = string.split(", ");
            this.valor = partes[0];
            this.porcentagem = Double.parseDouble(partes[1]);
            if (calculoAcerto(atacante, defensor)) {
                boolean critico = chanceCritico(atacante);
                if (atacante.isConfusion() && probabilidade(50.0)){
                    dano = calculoDano(atacante, atacante, critico);
                    System.out.println("Hp atacante antes: " + atacante.getHpAtual());
                    atacante.setHpAtual(atacante.getHpAtual() - dano);
                    System.out.println("Hp atacante depois: " + atacante.getHpAtual() + ANSI_RESET);
                    if (atacante.getHpAtual() <= 0) defensor.setStatus(Status.Fainted);
                    if (atacante.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Atacante FAINTED" + ANSI_RESET);
                    if (this.valor.equals("max_hp")) {
                        atacante.setHpAtual(atacante.getHpAtual() + (atacante.getHpMax() * this.porcentagem));
                    } else {
                        atacante.setHpAtual(atacante.getHpAtual() + (dano * this.porcentagem));
                    }
                }
                else {
                    dano = calculoDano(atacante, defensor, critico);
                    System.out.println("Hp defensor antes: " + defensor.getHpAtual());
                    defensor.setHpAtual(defensor.getHpAtual() - dano);
                    System.out.println("Hp defensor depois: " + defensor.getHpAtual() + ANSI_RESET);
                    if (defensor.getHpAtual() <= 0) defensor.setStatus(Status.Fainted);
                    if (defensor.getStatus().equals("Fainted")) System.out.println(ANSI_RED +"Defensor FAINTED" + ANSI_RESET);
                    if (this.valor.equals("max_hp")) {
                        atacante.setHpAtual(atacante.getHpAtual() + (atacante.getHpMax() * this.porcentagem));
                    } else {
                        atacante.setHpAtual(atacante.getHpAtual() + (dano * this.porcentagem));
                    }
                }
                if(atacante.getHpAtual() > atacante.getHpMax()){
                    atacante.setHpAtual(atacante.getHpMax());
                }
            } else System.out.println(ANSI_RED + "Calculo Acerto FALSE" + ANSI_RESET);
        } else System.out.println(ANSI_RED + "Pokemon " + atacante.getEspecie().getNome() + " com status Fainted" + ANSI_RESET);
    }
}
