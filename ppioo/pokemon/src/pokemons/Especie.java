package pokemons;

public class Especie {
    private int id;
    private String nome;
    private double baseHp;
    private double baseAtk;
    private double baseDef;
    private double baseSpe;
    private double baseSpd;
    private Tipo tipo1;
    private Tipo tipo2;

    public Especie(String[] dados) {
        this.id = Integer.parseInt(dados[0]);
        this.nome = dados[1];
        this.tipo1 = Tipo.valueOf(dados[2]);
        if (dados[3].equals("")) this.tipo2 = null;
        else this.tipo2 = Tipo.valueOf(dados[3]);
        this.baseHp =  Double.parseDouble(dados[4]);
        this.baseAtk = Double.parseDouble(dados[5]);
        this.baseDef = Double.parseDouble(dados[6]);
        this.baseSpe = Double.parseDouble(dados[7]);
        this.baseSpd = Double.parseDouble(dados[8]);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getBaseHp() {
        return baseHp;
    }

    public double getBaseAtk() {
        return baseAtk;
    }

    public double getBaseDef() {
        return baseDef;
    }

    public double getBaseSpe() {
        return baseSpe;
    }

    public double getBaseSpd() {
        return baseSpd;
    }

    public Tipo getTipo1() {
        return tipo1;
    }

    public Tipo getTipo2() {
        return tipo2;
    }

    public double calcularAtributoHp(double level, double base){
        double hp = 0;
        hp = 2 * base * level / 100 + level + 10;
        return hp;
    }

    public double calcularAtributo(double level, double base){
        double atributo = 0;
        atributo = 2 * base * level/100 + 5 ;
        return atributo;
    }

}
