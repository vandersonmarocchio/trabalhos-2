package rbc.gerenciadores.gerenciador_balancoMensal;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by diogo on 20/07/17.
 */

@Entity
public class BalancoMensal {
    @Id
    private String veiculo;
    private Timestamp data_inicial;
    private Timestamp data_final;

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public Timestamp getData_inicial() {
        return data_inicial;
    }

    public void setData_inicial(Timestamp data_inicial) {
        this.data_inicial = data_inicial;
    }

    public Timestamp getData_final() {
        return data_final;
    }

    public void setData_final(Timestamp data_final) {
        this.data_final = data_final;
    }
}
