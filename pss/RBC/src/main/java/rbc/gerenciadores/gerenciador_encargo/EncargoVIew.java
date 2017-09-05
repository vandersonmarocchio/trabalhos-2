package rbc.gerenciadores.gerenciador_encargo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by diogo on 14/07/17.
 */
public class EncargoVIew {

    private int id;
    private float valor;
    private StringProperty descricao;

    public EncargoVIew (Encargo encargo){
        this.id = id;
        this.valor = encargo.getValor();
        this.descricao = new SimpleStringProperty(encargo.getDescricao());
    }

    public EncargoVIew(){
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao.get();
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
