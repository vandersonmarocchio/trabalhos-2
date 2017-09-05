package rbc.gerenciadores.gerenciador_encargo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diogo on 19/07/17.
 */

@Entity
public class ListaEncargos {
    @Id
    private String RG;
    private int mes;
    private String situacao;
    private float total;
    @OneToMany
    private List<Encargo> listaEncargos =  new ArrayList<Encargo>();

    public ListaEncargos(String RG, int mes, String situacao, float total, List<Encargo> listaEncargos) {
        this.RG = RG;
        this.mes = mes;
        this.situacao = situacao;
        this.total = total;
        this.listaEncargos = listaEncargos;
    }

    public ListaEncargos() {
    }

    public void somar(){
        float total = 0;
        for(Encargo e: listaEncargos){
            total = (total + e.getValor());
        }
        setTotal(total);
    }

    public void addEncargo(Encargo encargo){
        if (listaEncargos.size()==0){
            encargo.setId(0);
        } else {
            encargo.setId(listaEncargos.size() + 1);
        }
        listaEncargos.add(encargo);
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public List<Encargo> getListaEncargos() {
        return listaEncargos;
    }

    public void setListaEncargos(List<Encargo> listaEncargos) {
        this.listaEncargos = listaEncargos;
    }
}
