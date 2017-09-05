package rbc.gerenciadores.gerenciador_veiculos;

import javax.persistence.*;

/**
 * Created by leopuglia on 07/06/17.
 */

@Entity
public class Despesas {


    protected String placa;
    protected float documento;
    protected float manutencao;
    protected float combustivel;

    public Despesas() {

    }

    public Despesas(String placa, float documento, float manutencao, float combustivel) {
        this.placa = placa;
        this.documento = documento;
        this.manutencao = manutencao;
        this.combustivel = combustivel;
    }

    @Id
    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public float getDocumento() {
        return documento;
    }

    public void setDocumento(float documento) {
        this.documento = documento;
    }

    public float getManutencao() {
        return manutencao;
    }

    public void setManutencao(float manutencao) {
        this.manutencao = manutencao;
    }

    public float getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(float combustivel) {
        this.combustivel = combustivel;
    }


    private Carreta manyToOneCarreta;

    @ManyToOne
    public Carreta getManyToOneCarreta() {
        return manyToOneCarreta;
    }

    public void setManyToOneCarreta(Carreta manyToOneCarreta) {
        this.manyToOneCarreta = manyToOneCarreta;
    }

    private Cavalo manyToOneCavalo;

    @ManyToOne
    public Cavalo getManyToOneCavalo() {
        return manyToOneCavalo;
    }

    public void setManyToOneCavalo(Cavalo manyToOneCavalo) {
        this.manyToOneCavalo = manyToOneCavalo;
    }
}
