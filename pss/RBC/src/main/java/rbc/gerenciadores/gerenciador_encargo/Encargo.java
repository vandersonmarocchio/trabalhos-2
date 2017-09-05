package rbc.gerenciadores.gerenciador_encargo;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by luis on 08/06/17.
 */

@Entity
public class Encargo {

    @Id
    private int id;
    private float valor;
    private String descricao;

    public Encargo() {
    }

    public Encargo( int id, float valor, String descricao) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValor(){
        return valor;
    }

    public void setValor(Float valor){
        this.valor = valor;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

}