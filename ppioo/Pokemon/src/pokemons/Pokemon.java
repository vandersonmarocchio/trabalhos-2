package pokemons;

import ataques.Ataque;
import java.util.List;

public class Pokemon {
    private int level;
    private double hpAtual;
    private double hpMax;
    private double atk;
    private double def;
    private double spe;
    private double spd;
    private int modifierAccuracy;
    private int modifierEvasion;
    private int modifierAtk;
    private int modifierDef;
    private int modifierSpe;
    private int modifierSpd;
    private boolean confusion;
    private boolean flinch;
    private Especie especie;
    private Status status;
    private List<Ataque> ataques;
    private boolean charge = false;
    private int indiceCharge;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public Pokemon(int level, Especie especie) {
        this.level = level;
        this.especie = especie;
        this.hpAtual = especie.calcularAtributoHp(level, especie.getBaseHp());
        this.hpMax = especie.calcularAtributoHp(level, especie.getBaseHp());
        this.atk = especie.calcularAtributo(level, especie.getBaseAtk());
        this.def = especie.calcularAtributo(level, especie.getBaseDef());
        this.spe = especie.calcularAtributo(level, especie.getBaseSpe());
        this.spd = especie.calcularAtributo(level, especie.getBaseSpd());
        this.modifierAtk = 0;
        this.modifierDef = 0;
        this.modifierSpe = 0;
        this.modifierSpd = 0;
        this.modifierAccuracy = 0;
        this.modifierEvasion = 0;
        this.confusion = false;
        this.flinch = false;
        this.status = Status.valueOf("Ok");
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getAtk() {
        return valorAtributoAtk(this.atk);
    }

    public double getDef() {
        return valorAtributoDef(this.def);
    }

    public double getSpe() {
        return valorAtributoSpe(this.spe);
    }

    public double getSpd() {
        return valorAtributoSpd(this.spd);
    }

    public double getHpAtual() {
        return hpAtual;
    }

    public double valorAtributoAtk(double atributoAtk){
        return atributoAtk * (Math.max(2, 2 + this.modifierAtk)/Math.max(2, 2 - this.modifierAtk))  ;
    }

    public double valorAtributoDef(double atributoDef){
        return atributoDef * (Math.max(2, 2 + modifierDef)/Math.max(2, 2 - modifierDef)) ;
    }

    public double valorAtributoSpe(double atributoSpe){
        return atributoSpe * (Math.max(2, 2 + modifierSpe)/Math.max(2, 2 - modifierSpe));
    }

    public double valorAtributoSpd(double atributoSpd){
        return atributoSpd * (Math.max(2, 2 + modifierSpd)/Math.max(2, 2 - modifierSpd)) ;
    }

    public void imprimeDadosPokemon(){
        System.out.println(ANSI_BLUE + "Id do Pokemon: " + especie.getId());
        System.out.println("Nome: " + especie.getNome());
        System.out.println("Tipo 1:  " + especie.getTipo1());
        if(especie.getTipo2() != null){
            System.out.println("Tipo 2 : " + especie.getTipo2());
        }
        System.out.println("Level: " + this.level);
        System.out.printf("HpMAx: %.2f\n", this.hpMax);
        System.out.printf("HpAtual: %.2f\n", this.hpAtual);
        System.out.printf("Atk: %.2f\n", valorAtributoAtk(this.atk));
        System.out.printf("Def: %.2f\n", valorAtributoDef(this.def));
        System.out.printf("Spd: %.2f\n", valorAtributoSpd(this.spd));
        System.out.printf("Spe: %.2f\n", valorAtributoSpe(this.spe));
        System.out.println("Modifier Accuracy: " + this.modifierAccuracy);
        System.out.println("Modifier Evasion: " + this.modifierEvasion);
        System.out.println("Modifier Atk: " + this.modifierAtk);
        System.out.println("Modifier Def: " + this.modifierDef);
        System.out.println("Modifier Spd: " + this.modifierSpd);
        System.out.println("Modifier Spe: " + this.modifierSpe);
        System.out.println("Status: " + this.status);
        if (this.confusion) System.out.println("Confusion: True");
        else System.out.println("Confusion: False");
        if (this.flinch) System.out.println("Flinch: True" + ANSI_RESET);
        else System.out.println("Flinch: False" +  ANSI_RESET);
        System.out.println();
    }

    public boolean isConfusion() {
        return confusion;
    }

    public void setConfusion(boolean confusion) {
        this.confusion = confusion;
    }

    public boolean isFlinch() {
        return flinch;
    }

    public void setFlinch(boolean flinch) {
        this.flinch = flinch;
    }

    public int getLevel() {
        return level;
    }

    public Especie getEspecie() {
        return especie;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setAtaques(List<Ataque> ataques) {
        this.ataques = ataques;
    }

    public List<Ataque> getAtaques() {
        return ataques;
    }

    public void setHpAtual(double hpAtual) {
        this.hpAtual = hpAtual;
    }

    public int getModifierAccuracy() {
        return modifierAccuracy;
    }

    public void setModifierAccuracy(int modifierAccuracy) {
        this.modifierAccuracy = modifierAccuracy;
    }

    public int getModifierEvasion() {
        return modifierEvasion;
    }

    public void setModifierEvasion(int modifierEvasion) {
        this.modifierEvasion = modifierEvasion;
    }

    public int getModifierAtk() {
        return modifierAtk;
    }

    public void setModifierAtk(int modifierAtk) {
        this.modifierAtk = modifierAtk;
    }

    public int getModifierDef() {
        return modifierDef;
    }

    public void setModifierDef(int modifierDef) {
        this.modifierDef = modifierDef;
    }

    public int getModifierSpe() {
        return modifierSpe;
    }

    public void setModifierSpe(int modifierSpe) {
        this.modifierSpe = modifierSpe;
    }

    public int getModifierSpd() {
        return modifierSpd;
    }

    public void setModifierSpd(int modifierSpd) {
        this.modifierSpd = modifierSpd;
    }

    public double getHpMax() {
        return hpMax;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public int getIndiceCharge() {
        return indiceCharge;
    }

    public void setIndiceCharge(int indiceCharge) {
        this.indiceCharge = indiceCharge;
    }
}
