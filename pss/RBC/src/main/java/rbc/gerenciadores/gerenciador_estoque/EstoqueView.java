package rbc.gerenciadores.gerenciador_estoque;

import javafx.beans.property.*;

/**
 * Created by diogo on 20/07/17.
 */
public class EstoqueView {
    private StringProperty item;
    private IntegerProperty quantidade;
    private FloatProperty valorItem;
    private Estoque estoque;

    public EstoqueView(Estoque estoque) {
        this.item = new SimpleStringProperty(estoque.getItem());
        this.quantidade = new SimpleIntegerProperty(estoque.getQuantidade());
        this.valorItem = new SimpleFloatProperty(estoque.getValorItem());
        this.estoque = estoque;
    }

    public EstoqueView(){
    }

    public String getItem() {
        return item.get();
    }

    public StringProperty itemProperty() {
        return item;
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }

    public float getValorItem(){
        return valorItem.get();
    }

    public StringProperty valorItemProperty() {
        String str = "R$ ";
        str = str.concat(Float.toString(valorItem.getValue()));
        StringProperty property = new SimpleStringProperty();
        property.setValue(str);
        return  property;
    }

    public void setValorItem(float valorItem) {
        this.valorItem.set(valorItem);
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

}
