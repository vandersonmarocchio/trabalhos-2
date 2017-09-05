package rbc.gerenciadores.gerenciador_depositos;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diogo on 19/07/17.
 */
@Entity
public class ListaDepositos {
    @Id
    private int RG;
    private Timestamp dataSaida;
    private Timestamp dataChegada;
    private int total;
    @OneToMany
    private List<Depositos> listaDepositos =  new ArrayList<Depositos>();

    public ListaDepositos(int RG, Timestamp dataSaida, Timestamp dataChegada, int total, List<Depositos> listaDepositos) {
        this.RG = RG;
        this.dataSaida = dataSaida;
        this.dataChegada = dataChegada;
        this.total = total;
        this.listaDepositos = listaDepositos;
    }

    public ListaDepositos() {
    }

    public List<Depositos> getListaDepositos() {
        return listaDepositos;
    }

    public void setListaDepositos(List<Depositos> listaDepositos) {
        this.listaDepositos = listaDepositos;
    }

    public Timestamp getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Timestamp dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Timestamp getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Timestamp dataChegada) {
        this.dataChegada = dataChegada;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRG() {
        return RG;
    }

    public void setRG(int RG) {
        this.RG = RG;
    }
}
