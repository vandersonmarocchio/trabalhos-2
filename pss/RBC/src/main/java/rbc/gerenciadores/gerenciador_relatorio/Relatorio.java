package rbc.gerenciadores.gerenciador_relatorio;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by luis on 08/06/17.
 */
@Entity
public class Relatorio {
    @Id
    private int periodo;
    private String veiculo;

    public int getPeriodo(){
        return periodo;
    }

    public void setPeriodo(int periodo){
        this.periodo = periodo;
    }

    public String getVeiculo(){
        return veiculo;
    }

    public void setVeiculo(String veiculo){
        this.veiculo = veiculo;
    }
}
