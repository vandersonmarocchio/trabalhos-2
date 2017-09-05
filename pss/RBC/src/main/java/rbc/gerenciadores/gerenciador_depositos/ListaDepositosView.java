package rbc.gerenciadores.gerenciador_depositos;

import java.sql.Timestamp;

/**
 * Created by diogo on 19/07/17.
 */
public class ListaDepositosView {

    private Timestamp dataSaida;
    private int total;

    public Timestamp getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Timestamp dataSaida) {
        this.dataSaida = dataSaida;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
