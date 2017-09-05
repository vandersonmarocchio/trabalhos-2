package rbc.gerenciadores.gerenciador_veiculos;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leopuglia on 07/06/17.
 */
@Entity
public class Cavalo extends DadosVeiculos{
    private String placaCavalo;
    private List<Carreta> listaCarretas = new ArrayList<Carreta>();

    public Cavalo(String placaCavalo, String chassi, String renavam, String modelo, String cor, String ano) {
        super(chassi, renavam, modelo, cor, ano);
        this.placaCavalo = placaCavalo;
    }


    public Cavalo(){

    }

    @Id
    public String getPlacaCavalo() {
        return placaCavalo;
    }

    public void setPlacaCavalo(String placaCavalo) {
        this.placaCavalo = placaCavalo;
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

    @OneToMany (fetch = FetchType.EAGER)
    public List<Carreta> getListaCarretas() {
        return listaCarretas;
    }

    public void setListaCarretas(List<Carreta> listaCarretas) {
        this.listaCarretas = listaCarretas;
    }
}
