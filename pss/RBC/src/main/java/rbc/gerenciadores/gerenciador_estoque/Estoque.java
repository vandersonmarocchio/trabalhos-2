package rbc.gerenciadores.gerenciador_estoque;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by luis on 08/06/17.
 */
@Entity
public class Estoque {
    @Id
    private String item;
    private int quantidade;
    private float valorItem;

    public Estoque() {

    }

    public Estoque(String item, int quantidade, float valorItem) {
        this.item = item;
        this.quantidade = quantidade;
        this.valorItem = valorItem;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorItem() {
        return valorItem;
    }

    public void setValorItem(float valorItem) {
        this.valorItem = valorItem;
    }
}
