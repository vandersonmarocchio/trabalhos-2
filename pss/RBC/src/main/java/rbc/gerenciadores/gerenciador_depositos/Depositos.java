package rbc.gerenciadores.gerenciador_depositos;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by diogo on 19/07/17.
 */

@Entity
public class Depositos {
    @Id
    private Timestamp dia;
    private float valor;
    private String metodo;

    public Depositos(Timestamp dia, float valor, String metodo) {
        this.dia = dia;
        this.valor = valor;
        this.metodo = metodo;
    }

    public Depositos() {
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
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}