package rbc.gerenciadores.gerenciador_veiculos;

/**
 * Created by diogo on 11/07/17.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Created by Diogo on 11/07/17.
 */

public class VeiculosView {

    private StringProperty placaCavalo;
    private StringProperty chassi;
    private StringProperty modelo;
    private StringProperty ano;
    private StringProperty cor;
    private StringProperty renavam;
    private List<Carreta> carretas;
    private Cavalo cavalo;
    private Carreta carreta;

    public VeiculosView() {

    }

    public String getplacaCavalo() {
        return placaCavalo.get();
    }

    public void setPlacaCavalo(String placaCavalo) {
        this.placaCavalo.set(placaCavalo);
    }

    public StringProperty getPlacaCavaloProperty(){
        return placaCavalo;
    }

    public String getchassi() {
        return chassi.get();
    }

    public void setChassi(String chassi) {
        this.chassi.set(chassi);
    }

    public StringProperty getChassiProperty(){
        return chassi;
    }

    public String getmodelo() {
        return modelo.get();
    }

    public void setModelo(String modelo) {
        this.modelo.set(modelo);
    }

    public StringProperty getModeloProperty(){
        return modelo;
    }

    public String getano() {
        return ano.get();
    }

    public void setAno(String ano) {
        this.ano.set(ano);
    }

    public StringProperty getAnoProperty(){
        return ano;
    }

    public String getcor() {
        return cor.get();
    }

    public void setCor(String cor) {
        this.cor.set(cor);
    }

    public StringProperty getCorProperty(){
        return cor;
    }

    public String getrenavam() {
        return renavam.get();
    }

    public void setRenavam(String renavam) {
        this.renavam.set(renavam);
    }

    public StringProperty getRenavamProperty(){
        return renavam;
    }

    public List<Carreta> getCarretas() {
        return carretas;
    }

    public Cavalo getCavalo() {
        return cavalo;
    }

    public Carreta getCarreta() {
        return carreta;
    }

    public VeiculosView(Cavalo cavalo) {
        this.placaCavalo = new SimpleStringProperty(cavalo.getPlacaCavalo());
        this.chassi = new SimpleStringProperty(cavalo.getChassi());
        this.modelo = new SimpleStringProperty(cavalo.getModelo());
        this.ano = new SimpleStringProperty(cavalo.getAno());
        this.cor = new SimpleStringProperty(cavalo.getCor());
        this.renavam = new SimpleStringProperty(cavalo.getRenavam());
        this.carretas = cavalo.getListaCarretas();
        this.cavalo = cavalo;
        this.carreta = carreta;
    }
}