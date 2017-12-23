package ataques;

import pokemons.Pokemon;
import pokemons.Status;
import pokemons.Tipo;
import java.util.Random;

public class Ataque {
    private int id;
    private String nome;
    private double ppMax;
    private double ppAtual;
    private int power;
    private int accuracy;
    private Tipo tipo;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public Ataque(String[] dados) {
        this.id = Integer.parseInt(dados[0]);
        this.nome = dados[1];
        this.tipo = Tipo.valueOf(dados[2]);
        this.ppMax = Double.parseDouble(dados[3]);
        this.ppAtual = Double.parseDouble(dados[3]);
        this.power = Integer.parseInt(dados[4]);
        this.accuracy = Integer.parseInt(dados[5]);
    }

    public void imprimeDadosAtaque(){
        System.out.println("Id do ataque: " + this.id);
        System.out.println("Nome: " + this.nome);
        System.out.println("Tipo: " + this.tipo);
        System.out.printf("ppMax: %.2f\n", this.ppMax);
        System.out.printf("ppAtual: %.2f\n", this.ppAtual);
        System.out.println("Power: " + this.power);
        System.out.println("Accuracy: " + this.accuracy);
        System.out.println();
    }

    public void efeito(Pokemon atacante, Pokemon defensor) {
        if(!atacante.getStatus().equals("Fainted")) { //verifica se o atacante esta com o status fainted, caso esteja ele nao ataca
            System.out.println(ANSI_PURPLE + "Ataque Comum");
            this.ppAtual -= 1;
            double dano;
            if (calculoAcerto(atacante, defensor)) { //verifica o acerto
                boolean critico = chanceCritico(atacante); //verifica o critico
                if(atacante.isConfusion() && probabilidade(50.0)){ //caso ele esteja com o boolean confusion como true e a probabilidade de
                    dano = calculoDano(atacante, atacante, critico); // 50% dele se acertar seja verdadeiro ele ataca ele mesmo
                    System.out.println("CONFUSION!!!!!!! ");
                    System.out.println("Hp atacante antes:" + atacante.getHpAtual());
                    atacante.setHpAtual(atacante.getHpAtual() - dano);
                    System.out.println("Hp atacante depois:" + atacante.getHpAtual()+ ANSI_RESET);
                    if (atacante.getHpAtual() <= 0) atacante.setStatus(Status.Fainted);
                    if (atacante.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Atacante FAINTED" + ANSI_RESET);
                }
                else {
                    dano = calculoDano(atacante, defensor, critico);
                    System.out.println("Hp defensor antes: " + defensor.getHpAtual());
                    defensor.setHpAtual(defensor.getHpAtual() - dano);
                    System.out.println("Hp defensor depois: " + defensor.getHpAtual() + ANSI_RESET);
                    if (defensor.getHpAtual() <= 0) defensor.setStatus(Status.Fainted);
                    if (defensor.getStatus().equals("Fainted")) System.out.println(ANSI_RED + "Defensor FAINTED" + ANSI_RESET);
                }
            } else System.out.println(ANSI_RED + "Calculo Acerto FALSE" + ANSI_RESET);
        } else System.out.println(ANSI_RED + "Pokemon " + atacante.getEspecie().getNome() + " com status Fainted" + ANSI_RESET);
    }

    public boolean probabilidade(double prob){
        Random r = new Random();
        int num = r.nextInt(100)+1;
        if(num <= prob){
            return true;
        } else
            return false;
    }

    public boolean chanceCritico(Pokemon atacante){
        Random r = new Random();
        int num = r.nextInt(100)+1;
        System.out.println("Chance do critico:" + ((atacante.getSpd()/512.00)*100.00));
        System.out.println("Random critico:" + num);
        if(num <= ((atacante.getSpd()/512.00)*100.00)){
            return true;
        } else return false;
    }

    public boolean calculoAcerto(Pokemon atacante, Pokemon defensor){
        Tabelas tabelas = new Tabelas();
        double prob;
//        System.out.println("\t\t\t\tACCURACY: " + this.accuracy);
//        System.out.println("\t\t\t\tAA: " + tabelas.chancesModifier(atacante.getModifierAccuracy()));
//        System.out.println("\t\t\t\tED: " + tabelas.chancesModifier(defensor.getModifierEvasion()));
        prob = this.accuracy * (tabelas.chancesModifier(atacante.getModifierAccuracy()) / tabelas.chancesModifier(defensor.getModifierEvasion()));
        System.out.println("Chance de acerto: " + prob);
        Random r = new Random();
        int num = r.nextInt(100) + 1;
        System.out.println("Random acerto: " + num);

        if(atacante.getStatus().equals("Frozen")) {
            System.out.println(atacante.getEspecie().getNome() + " esta com status Frozen");
            return false;
        }

        if(atacante.isFlinch()){
            System.out.println(atacante.getEspecie().getNome() + " esta com status Flinch");
            return false;
        }

        if(atacante.getStatus().equals("Sleep")){
            System.out.println(atacante.getEspecie().getNome() + " esta com status Sleep");
            return false;
        }

        if(atacante.getStatus().equals("Paralysis")){
            System.out.println(atacante.getEspecie().getNome() + " esta com status Paralysis");
            prob -= 25;
        }

        if (num <= prob) {
            return true;
        } else return false;
    }

    public double calculoDano(Pokemon atacante, Pokemon defensor, boolean critico){
        Tabelas tabela = new Tabelas();
        int lvl = atacante.getLevel();
        int power = this. power;
        double ataque = 0;
        double defesa = 0;
        double dano;
        Random random = new Random();
        int rand = random.nextInt((255 - 217) + 1) + 217;

        if ((this.tipo.ordinal() >=1) && (this.tipo.ordinal() <= 8)){
            ataque = atacante.getAtk();
           defesa = defensor.getDef();
        } else {
            ataque = atacante.getSpe();
           defesa = defensor.getSpe();
        }
        if (critico) lvl *= 2;

        if (atacante.getStatus().equals("Burn")) ataque /= 2;

        if ( ataque < 0 ) ataque = 0;
        else if (ataque > 255) ataque = 255;

        if (defesa < 0 ) defesa = 0;
        else if (defesa > 255) defesa = 255;

        dano = (lvl * ataque * power / defesa / 50) + 2;

        if ((this.tipo == atacante.getEspecie().getTipo1()) || (this.tipo == atacante.getEspecie().getTipo2())) {
            dano *= 1.5;
        }
        else {
            dano *= tabela.multiplicador(this.tipo, defensor.getEspecie().getTipo1());
        }
        dano = (dano * rand) / 255;

        if(atacante.getStatus().equals("Burn")) dano /= 2;

        System.out.println("Dano: " + dano);
        return dano;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPpMax() {
        return ppMax;
    }

    public double getPpAtual() {
        return ppAtual;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setPpAtual(double ppAtual) {
        this.ppAtual = ppAtual;
    }
}
