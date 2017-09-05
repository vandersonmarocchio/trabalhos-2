package rbc.gerenciadores.gerenciador_encargo;

import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Created by diogo on 19/07/17.
 */
public class ListaEncargosView {
    private int mes;
    private StringProperty situacao;
    private float total;

    public ListaEncargosView(List<ListaEncargos> listaEncargos) {
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getSituacao() {
        return situacao.get();
    }

    public StringProperty situacaoProperty() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao.set(situacao);
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
