package rbc.gerenciadores.gerenciador_veiculos;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by leopuglia on 07/06/17.
 */


@Entity
public class Carreta extends DadosVeiculos {

    protected String placaCarreta;

    public Carreta(String placaCarreta, String chassi, String renavam, String modelo, String cor, String ano) {
        super(chassi, renavam, modelo, cor, ano);
        this.placaCarreta = placaCarreta;
    }

    public Carreta(){

    }

    @Id
    public String getPlacaCarreta() {
        return placaCarreta;
    }

    public void setPlacaCarreta(String placaCarreta) {
        this.placaCarreta = placaCarreta;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        super.chassi = chassi;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        super.renavam = renavam;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        super.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        super.cor = cor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        super.ano = ano;
    }

}
