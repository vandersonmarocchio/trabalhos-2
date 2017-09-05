package rbc.gerenciadores.gerenciador_depositos;

import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

/**
 * Created by diogo on 19/07/17.
 */
public class DepositosView {
    private Timestamp dia;
    private float valor;
    private StringProperty metodo;

    public DepositosView() {
    }

    public Timestamp getDia() {
        return dia;
    }

    public void setDia(Timestamp dia) {
        this.dia = dia;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getMetodo() {
        return metodo.get();
    }

    public StringProperty metodoProperty() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo.set(metodo);
    }

    public DepositosView(Depositos deposito) {
        this.dia = dia;
        this.valor = valor;
        this.metodo = metodo;
    }
}
