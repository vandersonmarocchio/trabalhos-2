package rbc.gerenciadores.gerenciador_veiculos;



/**
 * Created by leopuglia on 07/06/17.
 */
public abstract class DadosVeiculos {

    protected String chassi;
    protected String renavam;
    protected String modelo;
    protected String cor;
    protected String ano;

    public DadosVeiculos(String chassi, String renavam, String modelo, String cor, String ano) {
        this.chassi = chassi;
        this.renavam = renavam;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
    }

    public DadosVeiculos(){

    }
}


